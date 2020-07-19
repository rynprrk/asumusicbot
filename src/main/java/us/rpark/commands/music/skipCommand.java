package us.rpark.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.managers.AudioManager;
import us.rpark.config.EmbedHandler;
import us.rpark.music.GuildMusicManager;
import us.rpark.music.PlayerManager;
import us.rpark.music.TrackScheduler;

import java.awt.*;

public class skipCommand extends Command {

    public skipCommand() {
        super.name = "skip";
        super.aliases = new String[] {"s", "next"};
        super.help = "Skips the song that is currently playing.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        AudioManager manager = event.getGuild().getAudioManager();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;


        if (player.getPlayingTrack() == null) {
            EmbedHandler.sendErrorMessage(event, "The player is currently not playing anything.");
            return;
        }

        if (!manager.getConnectedChannel().getMembers().contains(event.getMember())) {
            EmbedHandler.sendErrorMessage(event, "You must be in the same voice channel to do this!");
            return;
        }

        scheduler.nextTrack();
        EmbedHandler.sendEmbedMessage(event, Color.GREEN, "Song Skipped!", "Skipped the current audio track!");
    }
}

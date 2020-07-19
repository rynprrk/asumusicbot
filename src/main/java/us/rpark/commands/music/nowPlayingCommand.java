package us.rpark.commands.music;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import us.rpark.config.EmbedHandler;
import us.rpark.music.GuildMusicManager;
import us.rpark.music.PlayerManager;


public class nowPlayingCommand extends Command {
    public nowPlayingCommand() {
        super.name = "playing";
        super.aliases = new String[]{"playing", "nowplaying"};
        super.help = "Shows the song that is currently playing.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            EmbedHandler.sendErrorMessage(event, "No track is currently playing!");
            return;
        }

        AudioTrackInfo info = player.getPlayingTrack().getInfo();
        EmbedHandler.sendEmbedMessage(event, Color.GREEN, "Now Playing...", String.format("[%s](%s)", info.title, info.uri));

    }
}
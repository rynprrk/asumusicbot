package us.rpark.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import us.rpark.config.EmbedHandler;
import us.rpark.music.GuildMusicManager;
import us.rpark.music.PlayerManager;
import java.awt.*;

public class stopCommand extends Command {
    public stopCommand() {
        super.name = "stop";
        super.aliases = new String[] {"st", "mute"};
        super.help = "Stops the song that is currently playing.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        Command command = new stopCommand();
        AudioManager manager = event.getGuild().getAudioManager();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        VoiceChannel voiceChannel = manager.getConnectedChannel();


        if(musicManager.player.getPlayingTrack() == null) {
            EmbedHandler.sendErrorMessage(event, "No song is currently playing.");
            return;
        }

        if (!voiceChannel.getMembers().contains(event.getMember())) {
            EmbedHandler.sendErrorMessage(event, "You must be in the same voice channel to use this command.");
            return;
        }

        if (!manager.isConnected()) {
            EmbedHandler.sendErrorMessage(event, "The bot is not currently connected to any channel.");
            return;
        }

        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);
        EmbedHandler.sendEmbedMessage(event, Color.GREEN, "Stopped the current song", "Stopped the song that is currently playing.");
        EmbedHandler.sendLogMessage(event, command);
    }
}

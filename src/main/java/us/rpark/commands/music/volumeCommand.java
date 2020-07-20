package us.rpark.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import us.rpark.config.EmbedHandler;
import us.rpark.config.checkPermission;
import us.rpark.music.GuildMusicManager;
import us.rpark.music.PlayerManager;
import java.awt.*;

public class volumeCommand extends Command {

    public volumeCommand() {
        super.name = "volume";
        super.aliases = new String[] {"v", "vol"};
        super.help = "Adjusts the volume of the music player.";
        super.arguments = " <volume>";
    }

    protected void execute(CommandEvent event) {
        Command command = new volumeCommand();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;

        if (!checkPermission.isAdmin(event)) {
            EmbedHandler.sendErrorMessage(event, "You must be an **Admin** to use this command.");
            return;
        }

        if (player.getPlayingTrack() == null) {
            EmbedHandler.sendErrorMessage(event, "There is no song currently playing!");
            return;
        }

        Integer volume = Integer.parseInt(event.getArgs());
        playerManager.getGuildMusicManager(event.getGuild()).player.setVolume(volume);
        EmbedHandler.sendEmbedMessage(event, Color.GREEN, "Volume Changed", "Set the volume to **" + volume + "**");
        EmbedHandler.sendLogMessage(event, command, event.getArgs());
    }
}

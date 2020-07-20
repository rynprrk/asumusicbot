package us.rpark.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import us.rpark.config.EmbedHandler;
import java.awt.*;

public class disconnectCommand extends Command {

    public disconnectCommand() {
        super.name = "disconnect";
        super.aliases = new String[] {"dc", "leave"};
        super.help = "Makes the bot leave your channel.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        Command command = new disconnectCommand();
        AudioManager manager = event.getGuild().getAudioManager();
        VoiceChannel voiceChannel = manager.getConnectedChannel();

        if (!manager.isConnected()) {
            EmbedHandler.sendErrorMessage(event, "The bot is not currently connected to any channel.");
            return;
        }

        if (!voiceChannel.getMembers().contains(event.getMember())) {
            EmbedHandler.sendErrorMessage(event, "You must be in the same voice channel to use this command.");
            return;
        }

        manager.closeAudioConnection();
        EmbedHandler.sendEmbedMessage(event, Color.GREEN, "Disconnected from voice!", "I have disconnected from your voice channel.");
        EmbedHandler.sendLogMessage(event, command);
    }
}

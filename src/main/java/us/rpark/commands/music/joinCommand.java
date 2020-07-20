package us.rpark.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import us.rpark.config.EmbedHandler;
import java.awt.*;

public class joinCommand extends Command {

    public joinCommand() {
        super.name = "join";
        super.aliases = new String[] {"j", "connect"};
        super.help = "Makes the bot join your channel.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        Command command = new joinCommand();
        AudioManager manager = event.getGuild().getAudioManager();
        VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
        Member selfMember = event.getSelfMember();

        if (manager.isConnected()) {
            EmbedHandler.sendErrorMessage(event, "The bot is already connected to a voice channel.");
            return;
        }

        if (!event.getMember().getVoiceState().inVoiceChannel()) {
            EmbedHandler.sendErrorMessage(event, "You must be in a voice channel to do that! Please join a channel.");
            return;
        }

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            EmbedHandler.sendErrorMessage(event, "I do not have permission to join your channel.");
            return;
        }

        manager.openAudioConnection(voiceChannel);
        EmbedHandler.sendEmbedMessage(event, Color.GREEN, "Connected to voice!", "The bot has connected to your voice channel.");
        EmbedHandler.sendLogMessage(event, command);
    }

}

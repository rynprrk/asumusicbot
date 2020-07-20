package us.rpark.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import us.rpark.config.EmbedHandler;
import us.rpark.music.GuildMusicManager;
import us.rpark.music.PlayerManager;
import java.awt.*;

public class clearCommand extends Command {

    public clearCommand() {
        super.name = "clear";
        super.aliases = new String[]{"clearqueue", "cq"};
        super.help = "Clears everything in the music queue.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        Command command = new clearCommand();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager manager = playerManager.getGuildMusicManager(event.getGuild());

        if (manager.scheduler.getQueue().isEmpty()) {
            EmbedHandler.sendErrorMessage(event, "There is nothing in the queue.");
            return;
        }

        if (event.getMember().getVoiceState() == null) {
            EmbedHandler.sendErrorMessage(event, "You must be connected to a voice channel to do that.");
            return;
        }

        if (!event.getGuild().getAudioManager().getConnectedChannel().getMembers().contains(event.getMember())) {
            EmbedHandler.sendErrorMessage(event, "You must be in the **same** voice channel as the bot to do that command.");
            return;
        }

        manager.scheduler.getQueue().clear();
        EmbedHandler.sendEmbedMessage(event, Color.GREEN, "Cleared the queue!", "You have cleared the queue!");
        EmbedHandler.sendLogMessage(event, command);

    }
}

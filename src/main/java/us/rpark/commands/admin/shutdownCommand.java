package us.rpark.commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.JDA;
import us.rpark.config.EmbedHandler;
import us.rpark.config.checkPermission;

public class shutdownCommand extends Command {
    public shutdownCommand() {
        super.name = "shutdown";
        super.help = "Shuts down the bot.";
        super.arguments = "";
        super.ownerCommand = true;
    }

    protected void execute(CommandEvent event) {
        Command command = new shutdownCommand();

        if (!checkPermission.isAdmin(event)) {
            EmbedHandler.sendErrorMessage(event, "You must be an **Admin** to do this command.");
            return;
        }

        EmbedHandler.sendLogMessage(event, command);
        EmbedHandler.sendEmbedMessage(event, event.getSelfMember().getColor(), "Shutdown", "The bot is currently shutting down.");
        JDA jda = event.getJDA();
        jda.shutdown();
        
    }

}
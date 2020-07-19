package us.rpark.commands.admin;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import us.rpark.config.EmbedHandler;
import us.rpark.config.checkPermission;

import java.awt.*;

public class testCommand extends Command {

    public testCommand() {
        super.name = "test";
        super.help = "This is a test command.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {

        if (!checkPermission.isAdmin(event)) {
            EmbedHandler.sendErrorMessage(event, "You must be an **Admin** to do this command!");
            return;
        }

        EmbedHandler.sendEmbedMessage(event, Color.ORANGE, "Embed Test", event.getArgs());

    }
}

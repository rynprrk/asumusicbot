package us.rpark.commands.utility;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class infoCommand extends Command {
    public infoCommand() {
        super.name = "info";
        super.aliases = new String[] {"i", "uptime"};
        super.help = "Shows bot info.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {

    }
}
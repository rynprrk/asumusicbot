package us.rpark.commands.utility;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import us.rpark.config.Config;
import us.rpark.config.EmbedHandler;

import java.util.List;

public class helpCommand extends Command {

    public helpCommand() {
        super.name = "help";
        super.help = "Displays the help menu.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        Command command = new helpCommand();

        CommandClient client = event.getClient();
        List<Command> commands = client.getCommands();
        EmbedBuilder builder = EmbedUtils.defaultEmbed().setTitle("ASU Music Bot: Commands").setColor(event.getSelfMember().getColor()).setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix") + Config.getInstance().getString("helpword") + " | https://asu.gg");

        for (int i = 0; i < commands.size(); i++) {
            builder.appendDescription("`" + client.getPrefix() + commands.get(i).getName() + commands.get(i).getArguments() + "` " + commands.get(i).getHelp() + "\n");
        }

        event.getTextChannel().sendMessage(builder.build()).queue();
        
        EmbedHandler.sendLogMessage(event, command);
    }
}

package us.rpark.commands.utility;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import us.rpark.config.EmbedHandler;

public class pingCommand extends Command {

    public pingCommand() {
        super.name = "ping";
        super.help = "Checks API Latency.";
        super.arguments = "";

    }

    protected void execute(CommandEvent event) {
        Command command = new pingCommand();

        // Creates the embed
        EmbedBuilder builder = EmbedUtils.defaultEmbed().setTitle("Current Gateway Latency")
                .appendDescription("**" + event.getJDA().getGatewayPing() + "ms**") // Gets gateway latency
                .setFooter("ASU Music Bot | %help | https://asu.gg")
                .setColor(event.getSelfMember().getColor());

        // Sends the embed
        event.getTextChannel().sendMessage(builder.build()).queue();

        // Server Logging
        EmbedHandler.sendLogMessage(event, command);
    }
}

package us.rpark.config;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;

public class EmbedHandler {

    public static void sendErrorMessage(CommandEvent event, String errorMessage) {
        EmbedBuilder builder = EmbedUtils.defaultEmbed().setTitle("Error!").appendDescription(errorMessage)
                .setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix")
                        + Config.getInstance().getString("helpword") + " | https://asu.gg")
                .setColor(Color.RED);
        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    public static void sendEmbedMessage(CommandEvent event, Color color, String embedTitle, String message) {
        EmbedBuilder builder = EmbedUtils.defaultEmbed().setTitle(embedTitle).appendDescription(message)
                .setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix")
                        + Config.getInstance().getString("helpword") + " | https://asu.gg")
                .setColor(color);
        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    public static void sendLogMessage(CommandEvent event, Command command) {
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("Command executed in #" + event.getTextChannel().getName())
                .appendDescription("<@" + event.getAuthor().getId() + "> (ID: " + event.getAuthor().getId() + ") performed `"
                        + Config.getInstance().getString("prefix") + command.getName() + "`")
                .setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix")
                        + Config.getInstance().getString("helpword") + " | https://asu.gg")
                .setColor(event.getSelfMember().getColor());

        event.getGuild().getTextChannelById(Config.getInstance().getString("logChannelId")).sendMessage(builder.build())
                .queue();
    }

    public static void sendLogMessage(CommandEvent event, Command command, String args) {
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("Command executed in #" + event.getTextChannel().getName())
                .appendDescription("<@" + event.getAuthor().getId() + "> (ID: " + event.getAuthor().getId() + ") performed `"
                        + Config.getInstance().getString("prefix") + command.getName() + " " + args + "`")
                .setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix")
                        + Config.getInstance().getString("helpword") + " | https://asu.gg")
                .setColor(event.getSelfMember().getColor());

        event.getGuild().getTextChannelById(Config.getInstance().getString("logChannelId")).sendMessage(builder.build())
                .queue();
    }
}

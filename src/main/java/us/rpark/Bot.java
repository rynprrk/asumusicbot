package us.rpark;

import java.io.File;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import us.rpark.commands.admin.shutdownCommand;
import us.rpark.commands.admin.testCommand;
import us.rpark.commands.music.clearCommand;
import us.rpark.commands.music.disconnectCommand;
import us.rpark.commands.music.earrapeCommand;
import us.rpark.commands.music.joinCommand;
import us.rpark.commands.music.nowPlayingCommand;
import us.rpark.commands.music.playCommand;
import us.rpark.commands.music.queueCommand;
import us.rpark.commands.music.skipCommand;
import us.rpark.commands.music.stopCommand;
import us.rpark.commands.music.volumeCommand;
import us.rpark.commands.utility.helpCommand;
import us.rpark.commands.utility.pingCommand;
import us.rpark.config.Config;

public class Bot {
    private Bot() throws LoginException, IOException {
        Config config = new Config(new File("./config.json"));

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix(config.getString("prefix"));
        builder.setHelpWord(config.getString("helpword"));
        builder.setOwnerId(config.getString("botOwnerId"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing(config.getString("activityMessage")));
        builder.useHelpBuilder(false);

        CommandClient client = builder.build();
        client.addCommand(new pingCommand());
        client.addCommand(new clearCommand());
        client.addCommand(new joinCommand());
        client.addCommand(new disconnectCommand());
        client.addCommand(new playCommand());
        client.addCommand(new stopCommand());
        client.addCommand(new queueCommand());
        client.addCommand(new skipCommand());
        client.addCommand(new nowPlayingCommand());
        client.addCommand(new volumeCommand());
        client.addCommand(new earrapeCommand());
        client.addCommand(new helpCommand());
        client.addCommand(new testCommand());
        client.addCommand(new shutdownCommand());

        JDABuilder.createDefault(config.getString("token"), GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES).build().addEventListener(client);
        
    }

    public static void main(String[] args) throws LoginException, IOException {
        long enableTime = System.currentTimeMillis();
        new Bot();
        System.out.println("Bot launched! Took " + (System.currentTimeMillis() - enableTime) + "ms.");
    }

}

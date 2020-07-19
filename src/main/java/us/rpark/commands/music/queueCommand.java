package us.rpark.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import us.rpark.config.EmbedHandler;
import us.rpark.music.GuildMusicManager;
import us.rpark.music.PlayerManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class queueCommand extends Command {

    public queueCommand() {
        super.name = "queue";
        super.aliases = new String[] {"songs", "musicqueue"};
        super.help = "This command shows all the music in the queue.";
        super.arguments = "";
    }

    protected void execute(CommandEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

        if (queue.isEmpty()) {
            EmbedHandler.sendEmbedMessage(event, Color.RED, "The queue is empty!", "There are currently no songs in the queue.");
            return;
        }

        int trackCount = Math.min(queue.size(), 20);
        List<AudioTrack> tracks = new ArrayList<>(queue);
        EmbedBuilder builder = EmbedUtils.defaultEmbed().setTitle("Current Queue (Total: " + (queue.size() + 1) + ")");
        AudioTrackInfo nowPlayingInfo = musicManager.player.getPlayingTrack().getInfo();
        builder.appendDescription("**Now Playing: **" + nowPlayingInfo.title + " - " + nowPlayingInfo.author + "\n");
        builder.setColor(event.getSelfMember().getColor());

        for (int i = 0; i < trackCount; i++) {
            AudioTrack track = tracks.get(i);
            AudioTrackInfo info = track.getInfo();

            builder.appendDescription(String.format("%s - %s \n", info.title, info.author));
        }

        event.getTextChannel().sendMessage(builder.build()).queue();
    }
}

package us.rpark.commands.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import us.rpark.config.Config;
import us.rpark.config.EmbedHandler;
import us.rpark.config.checkPermission;
import us.rpark.music.GuildMusicManager;
import us.rpark.music.PlayerManager;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class earrapeCommand extends Command {

    private final YouTube youTube;

    public earrapeCommand() {
        YouTube temp = null;
        super.name = "earrape";
        super.aliases = new String[] {"er", "loud"};
        super.help = "Plays a song very loud from (youtube search) or soundcloud link.";
        super.arguments = " <search query> *or* [link] (Supporters only)";

        try {
            temp = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null)
                    .setApplicationName("ASU Music Bot")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        youTube = temp;
    }

    protected void execute(CommandEvent event) {
        Command command = new earrapeCommand();
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = event.getGuild().getAudioManager();
        VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
        GuildMusicManager musicManager = manager.getGuildMusicManager(event.getGuild());
        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        try {
            if (!checkPermission.isStaff(event) || !checkPermission.isSupporter(event)) {
                EmbedHandler.sendErrorMessage(event, "You must be a supporter in order to do this command!");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (musicManager.player.getPlayingTrack() != null || !musicManager.scheduler.getQueue().isEmpty()) {
            EmbedHandler.sendErrorMessage(event, "Clear the song currently playing and the queue before trying again.");
            return;
        }

        if (event.getArgs().isEmpty()) {
            EmbedHandler.sendErrorMessage(event, "Please specify a search term or valid YouTube/SoundCloud URL.");
            return;
        }

        String input = String.join(" ", event.getArgs());

        if (!isURL(input)) {
            String ytSearched = searchYoutube(input);

            if (ytSearched == null) {
                EmbedHandler.sendErrorMessage(event, "YouTube returned no results.");
                return;
            }

            input = ytSearched;
        }

        if (!audioManager.isConnected()) {
            if(memberVoiceState.inVoiceChannel()) {
                audioManager.openAudioConnection(voiceChannel);
                manager.loadAndPlay(event.getTextChannel(), input);
                manager.getGuildMusicManager(event.getGuild()).player.setVolume(1500);
                return;
            }

            EmbedHandler.sendErrorMessage(event, "You must be in a voice channel to do that!");
            return;
        }

        if (!event.getGuild().getAudioManager().getConnectedChannel().getMembers().contains(event.getMember())) {
            EmbedHandler.sendErrorMessage(event, "You must be in the same voice channel to do this!");
            return;
        }

        manager.loadAndPlay(event.getTextChannel(), input);
        manager.getGuildMusicManager(event.getGuild()).player.setVolume(1500);
        EmbedHandler.sendLogMessage(event, command, event.getArgs());
    }


    private boolean isURL(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Nullable
    private String searchYoutube(String input) {
        try {
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey(Config.getInstance().getString("youtubekey"))
                    .execute()
                    .getItems();

            if(!results.isEmpty()) {
                String videoId = results.get(0).getId().getVideoId();

                return "https://youtube.com/watch?v=" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

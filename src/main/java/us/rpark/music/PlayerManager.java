package us.rpark.music;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import us.rpark.config.Config;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackURL) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("Song Added to Queue!")
                        .appendDescription("Added **" + track.getInfo().title + "** to the queue!")
                        .setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix") + Config.getInstance().getString("helpword") + " | https://asu.gg");
                channel.sendMessage(builder.build()).queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().remove(0);
                }

                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("Playlist loaded!")
                        .appendDescription("Added **" + firstTrack.getInfo().title + "** to the queue, from playlist " + playlist.getName() + ".")
                        .setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix") + Config.getInstance().getString("helpword") + " | https://asu.gg");
                channel.sendMessage(builder.build()).queue();


                play(musicManager, firstTrack);

                playlist.getTracks().forEach(musicManager.scheduler::queue);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found on " + trackURL);
            }

            @Override
            public void loadFailed(FriendlyException exception) {

                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("Playback Error!")
                        .appendDescription("Could not play the track. (" + exception.getMessage() +")")
                        .setFooter("ASU Music Bot | " + Config.getInstance().getString("prefix") + Config.getInstance().getString("helpword") + " | https://asu.gg");
                channel.sendMessage(builder.build()).queue();
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

}

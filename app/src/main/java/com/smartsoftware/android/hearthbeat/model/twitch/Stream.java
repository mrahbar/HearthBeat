package com.smartsoftware.android.hearthbeat.model.twitch;

import com.google.gson.annotations.SerializedName;
import com.smartsoftware.android.hearthbeat.model.Model;

import java.util.Date;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 12.01.2016
 * Time: 21:45
 * Email: mrahbar.azad@gmail.com
 */
public class Stream implements Model {

    public static final String KEY_SUFFIX = "STREAM:";

    @SerializedName("_id")
    private long id;

    private String game;
    private int viewers;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("video_height")
    private int videoHeight;
    @SerializedName("average_fps")
    private double averageFps;
    private boolean is_playlist;
    private int delay;
    private StreamPreview preview;
    private Channel channel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public double getAverageFps() {
        return averageFps;
    }

    public void setAverageFps(double averageFps) {
        this.averageFps = averageFps;
    }

    public StreamPreview getPreview() {
        return preview;
    }

    public void setPreview(StreamPreview preview) {
        this.preview = preview;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean is_playlist() {
        return is_playlist;
    }

    public void setIs_playlist(boolean is_playlist) {
        this.is_playlist = is_playlist;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public String buildKey() {
        return KEY_SUFFIX+id;
    }
}

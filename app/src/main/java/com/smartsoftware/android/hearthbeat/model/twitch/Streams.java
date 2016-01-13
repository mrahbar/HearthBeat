package com.smartsoftware.android.hearthbeat.model.twitch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 12.01.2016
 * Time: 21:43
 * Email: mrahbar.azad@gmail.com
 */
public class Streams {

    private List<Stream> streams;

    @SerializedName("_total")
    private int total;

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

package com.smartsoftware.android.hearthbeat.ui.feed;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 12.01.2016
 * Time: 21:50
 * Email: mrahbar.azad@gmail.com
 */
public class TwitchPost extends FeedPost {

    public final String displayName, preview;
    public final int viewers;

    public TwitchPost(String id, String title, String url,
                      String displayName, String preview, int viewers) {
        super(id, "Twitch", title, url);
        this.displayName = displayName;
        this.preview = preview;
        this.viewers = viewers;
    }
}

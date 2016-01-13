package com.smartsoftware.android.hearthbeat.ui.feed;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 10.01.2016
 * Time: 21:36
 * Email: mrahbar.azad@gmail.com
 */
public class RedditPost extends FeedPost {

    public final long num_comments, score;
    public final String thumbnail;

    public RedditPost(String id, String title, String url, long num_comments, long score, String thumbnail) {
        super(id, "Reddit", title, url.replace("www.","m."));
        this.num_comments = num_comments;
        this.score = score;
        this.thumbnail = thumbnail;
    }
}

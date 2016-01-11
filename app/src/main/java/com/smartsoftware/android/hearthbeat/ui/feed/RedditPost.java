package com.smartsoftware.android.hearthbeat.ui.feed;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 10.01.2016
 * Time: 21:36
 * Email: mrahbar.azad@gmail.com
 */
public class RedditPost extends FeedPost {

    public final long num_comments, score;

    public RedditPost(long id, String title, String url, long num_comments, long score) {
        super(id, title, url);
        this.num_comments = num_comments;
        this.score = score;
    }
}

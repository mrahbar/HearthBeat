package com.smartsoftware.android.hearthbeat.ui.feed;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 10.01.2016
 * Time: 15:54
 * Email: mrahbar.azad@gmail.com
 */
public abstract class FeedPost {

    public final long id;
    public final String title;
    public String url;

    public FeedPost(long id,
                     String title,
                     String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    /**
     * Equals check based on the id field
     */
    @Override
    public boolean equals(Object o) {
        return (o.getClass() == getClass() && ((FeedPost) o).id == id);
    }
}

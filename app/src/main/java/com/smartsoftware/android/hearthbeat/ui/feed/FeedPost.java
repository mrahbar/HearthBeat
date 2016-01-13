package com.smartsoftware.android.hearthbeat.ui.feed;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 10.01.2016
 * Time: 15:54
 * Email: mrahbar.azad@gmail.com
 */
public abstract class FeedPost {

    public final String id;
    public final String type;
    public final String title;
    public String url;

    public FeedPost(String id,
                    String type, String title,
                    String url) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.url = url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedPost feedPost = (FeedPost) o;

        if (!id.equals(feedPost.id)) return false;
        return type.equals(feedPost.type);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}

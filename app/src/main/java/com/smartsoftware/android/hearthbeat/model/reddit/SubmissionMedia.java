package com.smartsoftware.android.hearthbeat.model.reddit;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 22:51
 * Email: mrahbar.azad@gmail.com
 */
public class SubmissionMedia {
    public String type;
    public Oembed oembed;

    public static class Oembed {
        public String provider_url;
        public String description;
        public String title;
        public String url;
        public String author_name;
        public String author_url;
        public String thumbnail_url;
        public String type;
        public String version;

        public int height;
        public int width;
        public int thumbnail_width;
        public int thumbnail_height;
    }
}

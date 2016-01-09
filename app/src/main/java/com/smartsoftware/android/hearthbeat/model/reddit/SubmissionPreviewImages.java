package com.smartsoftware.android.hearthbeat.model.reddit;

import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 22:54
 * Email: mrahbar.azad@gmail.com
 */
public class SubmissionPreviewImages {

    public List<Images> images;

    public static class Images {
        public Image source;
        public List<Image> resolutions;
    }

    public static class Image {
        public String url;
        public int width;
        public int height;
    }
}

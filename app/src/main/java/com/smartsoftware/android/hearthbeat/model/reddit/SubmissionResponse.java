package com.smartsoftware.android.hearthbeat.model.reddit;

import java.util.List;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 20:52
 * Email: mrahbar.azad@gmail.com
 */
public class SubmissionResponse {
    public String kind;
    public Data data;

    public static class Data {
        public String modhash;
        public List<DataChildren> children;
        public String after;
        public String before;
    }

    public static class DataChildren {
        public String kind;
        public Submission data;
    }
}

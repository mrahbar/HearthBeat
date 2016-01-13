package com.smartsoftware.android.hearthbeat.model.twitch;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 12.01.2016
 * Time: 21:45
 * Email: mrahbar.azad@gmail.com
 */
public class StreamPreview {
    private String small;
    private String medium;
    private String large;
    private String template;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}

package com.smartsoftware.android.hearthbeat.model.reddit;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 20:19
 * Email: mrahbar.azad@gmail.com
 */
public class Submission extends Thing {

    /* All String values */
    public String id;
    public String name;
    public String url;
    public String permalink;
    public String author;
    public String title;
    public String subreddit;
    public String subreddit_id;
    public String thumbnail;
    public String selftext;
    public String selftext_html;
    public String domain;
    public String banned_by;
    public String approved_by;
    public String author_flair_css_class;
    public String link_flair_css_class;
    public String author_flair_text;
    public String link_flair_text;
    public String distinguished;
    public String from;
    public String from_id;
    public String removal_reason;
    public String from_kind;

    public SubmissionMedia media;
    public SubmissionPreviewImages preview;

    /* All Long values */
    public long gilded;
    public long num_comments;
    public long num_reports;
    public long score;
    public long ups;
    public long downs;

    /* All Double values */
    public double created;
    public double created_utc;
    public double upvote_ratio;

    /* All Boolean values */
    public boolean visited;
    public boolean is_self;
    public boolean saved;
//    public boolean edited; can either be boolean or Number
    public boolean stickied;
    public boolean over_18;
    public boolean hidden;
    public boolean clicked;
    public boolean likes;

    /**
     * The kind of this thing.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'kind prefixes')</a>
     */
    protected Kind kind;

    /**
     * The identifier of this thing.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'identifier')</a>
     */
    protected String identifier;

    /**
     * The full name of this thing.
     * Combination of its kind ({@link #getKind() getKind}) and its unique ID.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names</a>
     */
    protected String fullName;

    public void initKind() {
        assert name.contains("_") : "A full name must contain an underscore.";
        this.fullName = name;
        String[] split = name.split("_");
        this.kind = Kind.match(split[0]);
        this.identifier = split[1];
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Submission(" + this.getFullName() + ")<" + title + ">";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Submission && this.getFullName().equals(((Submission) other).getFullName());
    }

    @Override
    public int hashCode() {
        return this.hashCode() * this.getFullName().hashCode();
    }

    @Override
    public int compareTo(Thing o) {
        return this.getFullName().compareTo(o.getFullName());
    }
}

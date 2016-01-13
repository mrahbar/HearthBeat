package com.smartsoftware.android.hearthbeat.model.reddit;

import com.google.gson.annotations.SerializedName;
import com.smartsoftware.android.hearthbeat.model.Model;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 20:19
 * Email: mrahbar.azad@gmail.com
 */
public class Submission implements Model {
    public static final String KEY_SUFFIX = "SUBMISSION:";

    /* All String values */
    private String id;
    private String name;
    private String url;
    private String permalink;
    private String author;
    private String title;
    private String subreddit;
    private String subreddit_id;
    private String thumbnail;
    private String selftext;
    private String selftext_html;
    private String domain;
    private String banned_by;
    private String approved_by;
    private String author_flair_css_class;
    private String link_flair_css_class;
    private String author_flair_text;
    private String link_flair_text;
    private String distinguished;

    @SerializedName("from")
    private String fromVal;
    private String from_id;
    private String removal_reason;
    private String from_kind;

    private SubmissionMedia media;
    private SubmissionPreviewImages preview;

    /* All Long values */
    private long gilded;
    private long num_comments;
    private long num_reports;
    private long score;
    private long ups;
    private long downs;

    /* All Double values */
    private double created;
    private double created_utc;
    private double upvote_ratio;

    /* All Boolean values */
    private boolean visited;
    private boolean is_self;
    private boolean saved;
//    private boolean edited; can either be boolean or Number
    private boolean stickied;
    private boolean over_18;
    private boolean hidden;
    private boolean clicked;
    private boolean likes;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromVal() {
        return fromVal;
    }

    public void setFromVal(String fromVal) {
        this.fromVal = fromVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getSubreddit_id() {
        return subreddit_id;
    }

    public void setSubreddit_id(String subreddit_id) {
        this.subreddit_id = subreddit_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public String getSelftext_html() {
        return selftext_html;
    }

    public void setSelftext_html(String selftext_html) {
        this.selftext_html = selftext_html;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBanned_by() {
        return banned_by;
    }

    public void setBanned_by(String banned_by) {
        this.banned_by = banned_by;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public String getAuthor_flair_css_class() {
        return author_flair_css_class;
    }

    public void setAuthor_flair_css_class(String author_flair_css_class) {
        this.author_flair_css_class = author_flair_css_class;
    }

    public String getLink_flair_css_class() {
        return link_flair_css_class;
    }

    public void setLink_flair_css_class(String link_flair_css_class) {
        this.link_flair_css_class = link_flair_css_class;
    }

    public String getAuthor_flair_text() {
        return author_flair_text;
    }

    public void setAuthor_flair_text(String author_flair_text) {
        this.author_flair_text = author_flair_text;
    }

    public String getLink_flair_text() {
        return link_flair_text;
    }

    public void setLink_flair_text(String link_flair_text) {
        this.link_flair_text = link_flair_text;
    }

    public String getDistinguished() {
        return distinguished;
    }

    public void setDistinguished(String distinguished) {
        this.distinguished = distinguished;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getRemoval_reason() {
        return removal_reason;
    }

    public void setRemoval_reason(String removal_reason) {
        this.removal_reason = removal_reason;
    }

    public String getFrom_kind() {
        return from_kind;
    }

    public void setFrom_kind(String from_kind) {
        this.from_kind = from_kind;
    }

    public SubmissionMedia getMedia() {
        return media;
    }

    public void setMedia(SubmissionMedia media) {
        this.media = media;
    }

    public SubmissionPreviewImages getPreview() {
        return preview;
    }

    public void setPreview(SubmissionPreviewImages preview) {
        this.preview = preview;
    }

    public long getGilded() {
        return gilded;
    }

    public void setGilded(long gilded) {
        this.gilded = gilded;
    }

    public long getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(long num_comments) {
        this.num_comments = num_comments;
    }

    public long getNum_reports() {
        return num_reports;
    }

    public void setNum_reports(long num_reports) {
        this.num_reports = num_reports;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getUps() {
        return ups;
    }

    public void setUps(long ups) {
        this.ups = ups;
    }

    public long getDowns() {
        return downs;
    }

    public void setDowns(long downs) {
        this.downs = downs;
    }

    public double getCreated() {
        return created;
    }

    public void setCreated(double created) {
        this.created = created;
    }

    public double getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(double created_utc) {
        this.created_utc = created_utc;
    }

    public double getUpvote_ratio() {
        return upvote_ratio;
    }

    public void setUpvote_ratio(double upvote_ratio) {
        this.upvote_ratio = upvote_ratio;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean is_self() {
        return is_self;
    }

    public void setIs_self(boolean is_self) {
        this.is_self = is_self;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isStickied() {
        return stickied;
    }

    public void setStickied(boolean stickied) {
        this.stickied = stickied;
    }

    public boolean isOver_18() {
        return over_18;
    }

    public void setOver_18(boolean over_18) {
        this.over_18 = over_18;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void initKind() {
        assert name.contains("_") : "A full name must contain an underscore.";
        this.fullName = name;
        String[] split = name.split("_");
        this.kind = Kind.match(split[0]);
        this.identifier = split[1];
    }

    public Kind getKind() {
        return kind;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String buildKey() {
        return KEY_SUFFIX+id;
    }
}

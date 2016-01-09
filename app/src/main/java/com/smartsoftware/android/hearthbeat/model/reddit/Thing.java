package com.smartsoftware.android.hearthbeat.model.reddit;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 09.01.2016
 * Time: 20:22
 * Email: mrahbar.azad@gmail.com
 */
public abstract class Thing implements Comparable<Thing> {

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

    /**
     * Constructor. Must be called.
     * @param name Full name of the thing
     */
    public void setName(String name) {
        assert name.contains("_") : "A full name must contain an underscore.";
        this.fullName = name;
        String[] split = name.split("_");
        this.kind = Kind.match(split[0]);
        this.identifier = split[1];
    }

    /**
     * Retrieve the kind of this thing.
     * Example: t3 indicates a kind 3 (a link).
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'kind prefixes')</a>
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * Retrieve the identifier of this thing.
     * Example: 15bfi0.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'identifier')</a>
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Retrieve the full name of this thing.
     * Combination of its kind (see {@link #getKind() getKind}) and its unique ID, combined with a underscore.
     * Example: t3_15bfi0 indicates a kind 3 (a link) and as unique identifier 15bfi0.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names</a>
     */
    public String getFullName() {
        return fullName;
    }

}
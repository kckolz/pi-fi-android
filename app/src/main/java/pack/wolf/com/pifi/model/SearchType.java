package pack.wolf.com.pifi.model;

/**
 * Created by kevinkolz on 8/29/15.
 */
public enum SearchType {
    ALBUM("album"), ARTIST("artist"), PLAYLIST("playlist"), TRACK("track");

    private final String text;

    /**
     * @param text
     */
    private SearchType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}

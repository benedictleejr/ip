package wooper;

import java.io.Serializable;

/**
 * Class which encapsulates notes, small snippets of textual information the
 * user wants to record
 */
public class Note implements Serializable {
    protected String title;
    protected String content;

    /**
     * Initializes a new Note object with a given title and content
     *
     * @param title title of the note
     * @param content content of the note
     */
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Gets title of a note
     *
     * @return String of the note's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets content of a note
     *
     * @return String of the note's content
     */
    public String getContent() {
        return this.content;
    }

}

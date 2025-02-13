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
     * @param title title of the note
     * @param content content of the note
     */
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public void editTitle(String newTitle) {
        this.title = newTitle;
    }

    public void editContent(String newContent) {
        this.content = newContent;
    }

}

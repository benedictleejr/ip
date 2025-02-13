package wooper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores a collection of notes, which are small snippets of textual information
 * the user wants to record
 */
public class Notebook implements Serializable {
    protected ArrayList<Note> notebook;

    public Notebook() {
        this.notebook = new ArrayList<>();
    }

    public Notebook(ArrayList<Note> notebook) {
        this.notebook = notebook;
    }

    public ArrayList<Note> getAllNotes() {
        return this.notebook;
    }

    public int getNotebookSize() {
        return this.notebook.size();
    }

    public Note getNote(int index) {
        return this.notebook.get(index);
    }

    public void addNote(Note newNote) {
        this.notebook.add(newNote);
    }

    public void deleteNote(int index) throws IndexOutOfBoundsException {
        this.notebook.remove(index);
    }

}

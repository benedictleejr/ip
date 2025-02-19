package wooper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores a collection of notes, which are small snippets of textual information
 * the user wants to record
 */
public class Notebook implements Serializable {
    protected ArrayList<Note> notebook;

    /**
     * Creates new Notebook object with fresh notebook
     */
    public Notebook() {
        this.notebook = new ArrayList<>();
    }

    /**
     * Creates new Notebook object, with pre-existing notebook info
     *
     * @param notebook pre-existing notebook info
     */
    public Notebook(ArrayList<Note> notebook) {
        this.notebook = notebook;
    }

    /**
     * Retrieves all notes in notebook
     *
     * @return list of all notes
     */
    public ArrayList<Note> getAllNotes() {
        return this.notebook;
    }

    /**
     * Retrieves the number of notes in the notebook
     *
     * @return number of notes in notebook
     */
    public int getNotebookSize() {
        return this.notebook.size();
    }

    /**
     * Retrieves a note by index
     *
     * @param index of note to be retrieved
     * @return Note at index specified
     */
    public Note getNote(int index) {
        return this.notebook.get(index);
    }

    /**
     * Adds a new note to the notebook
     *
     * @param newNote new note to be added
     */
    public void addNote(Note newNote) {
        this.notebook.add(newNote);
    }

    /**
     * Deletes note at specified index
     *
     * @param index index where deletion should occur
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public void deleteNote(int index) throws IndexOutOfBoundsException {
        this.notebook.remove(index);
    }

}

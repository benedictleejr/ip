package wooper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Responsible for saving and loading tasks from a file.
 * It also stores an arraylist of tasks, to be kept in sync with the main
 * program.
 */
public class Storage {
    protected ArrayList<Task> tasks;
    protected ArrayList<Note> notes;

    /**
     * Saves all current tasks to a file, for persistent memory.
     *
     * @param filePath path to tasklist text file
     * @param tasks list of tasks to be saved
     */
    public void saveTasks(String filePath, ArrayList<Task> tasks) {
        this.tasks = tasks;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this.tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Saves all current notes to a file, for persistent memory.
     *
     * @param filePath path to notebook text file
     * @param notes list of notes to be saved
     */
    public void saveNotes(String filePath, ArrayList<Note> notes) {
        this.notes = notes;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this.notes);
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from a file.
     *
     * @param filePath path to the file to load tasks from
     * @return a Tasklist object containing the tasks loaded from the file
     */
    @SuppressWarnings("unchecked")
    public Tasklist loadTasks(String filePath) {
        ArrayList<Task> tasklist = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            tasklist = (ArrayList<Task>) ois.readObject();

        } catch (FileNotFoundException e) { // if no existing storage file, then create a new storage file
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e1) {
                System.err.println("Error creating new file: " + e1.getMessage());
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());

        } catch (IOException e) {
            System.err.println("Error reading tasks from file: " + e.getMessage());
        }
        return tasklist.isEmpty() ? new Tasklist() : new Tasklist(tasklist);
    }

    /**
     * Loads notes from a file.
     *
     * @param filePath path to the file to load notes from
     * @return a Notebook object containing the notes loaded from the file
     */
    @SuppressWarnings("unchecked")
    public Notebook loadNotes(String filePath) {
        ArrayList<Note> notebook = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            notebook = (ArrayList<Note>) ois.readObject();

        } catch (FileNotFoundException e) { // if no existing storage file, then create a new storage file
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e1) {
                System.err.println("Error creating new file: " + e1.getMessage());
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());

        } catch (IOException e) {
            System.err.println("Error reading tasks from file: " + e.getMessage());
        }
        return notebook.isEmpty() ? new Notebook() : new Notebook(notebook);
    }
}

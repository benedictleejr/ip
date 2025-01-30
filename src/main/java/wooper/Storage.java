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
 * Storage class is responsible for saving and loading tasks from a file.
 * It also stores an arraylist of tasks, to be kept in sync with the main program.
 * 
 */
public class Storage {
    protected ArrayList<Task> tasks;

    /**
     * Saves all current tasks to a file, for persistent memory.
     * 
     * @param filePath
     */
    public void saveTasks(String filePath, ArrayList<Task> tasklist) {
        this.tasks = tasklist;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this.tasks);
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

        } catch (FileNotFoundException e) { // if file not found, then create the new file
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

}

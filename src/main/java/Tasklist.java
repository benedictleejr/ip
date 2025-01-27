import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Tasklist {
    protected ArrayList<Task> tasklist;

    public Tasklist() {
        this.tasklist = new ArrayList<>();
    }

    public Tasklist(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }

    /**
     * Returns the tasklist.
     * 
     * @return the tasklist
     */
    public ArrayList<Task> getTasklist() {
        return this.tasklist;
    }

    /**
     * Saves all current tasks to a file, for persistent memory.
     * 
     * @param filePath
     */
    public void saveTasks(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this.tasklist);
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
    public static Tasklist loadTasks(String filePath) {
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

    /**
     * Adds a task to the tasklist and prints a message to the user.
     * @param pw printwriter to print the message to the user
     * @param task task to be added to the tasklist
     */
    public void addTask(PrintWriter pw, Task task) {
        this.tasklist.add(task);
        String output = String.format("""
                ____________________________________________________________
                
                Wooper:
                Woop Woop! I've added this task:
                    [%s][ ] %s
                Now you have %d tasks in the list.
                ____________________________________________________________

                """, task.getTaskType(), task.getDescription(), tasklist.size());
        pw.println(output);
    }

    /**
     * Prints all tasks in the lists with beautiful formatting
     * @param pw printwriter to print the message to the user
     */
    public void printTasklist(PrintWriter pw) {
        if (this.tasklist.size() == 0) {
            String output = """
                    ____________________________________________________________

                    Wooper:
                    Task List:
                    No tasks in the list.
                    ____________________________________________________________
                    """;
            pw.println(output);
            return;
        }

        pw.println("____________________________________________________________\n");
        pw.println("Task List:");
        for (int i = 0; i < tasklist.size(); i++) {
            Task t = this.tasklist.get(i);
            pw.printf("%d.[%s][%s] %s\n", i+1, t.getTaskType(), t.getStatusIcon(), t.getDescription());
        }
        pw.println("____________________________________________________________");
    }

    public Task getTask(int index) {
        return this.tasklist.get(index);
    }

    /**
     * Deletes a task from the tasklist and prints a message to the user.
     * @param pw printwriter to print the message to the user
     * @param index index of the task to be deleted
     */
    public void deleteTask(PrintWriter pw, int index) {
        Task t = this.tasklist.get(index);
        this.tasklist.remove(index);
        String output = String.format("""
                ____________________________________________________________
                
                Wooper:
                Woop Woop! I've removed this task:
                    [%s][%s] %s
                Now you have %d tasks in the list.
                ____________________________________________________________

                """, t.getTaskType(), t.getStatusIcon(), t.getDescription(), tasklist.size());
        pw.println(output);
    }

    /**
     * Gets all deadlines due/events happening on a certain date, and prints them to the user.
     * @param pw printwriter to print the message to the user
     * @param date date to check for deadlines/events in format "YYYY-MM-DD"
     */
    public void printTasksOnDate(PrintWriter pw, String date) {
        ArrayList<Task> tasksOnDate = new ArrayList<>();
        for (Task t : this.tasklist) {
            if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                if (d.simpleGetDueDate().equals(date)) {
                    tasksOnDate.add(d);
                }
            } else if (t instanceof Event) {
                Event e = (Event) t;
                if (e.simpleGetStartDate().equals(date)) {
                    tasksOnDate.add(e);
                }
            }
        }

        if (tasksOnDate.size() == 0) {
            String output = """
                    ____________________________________________________________

                    Wooper:
                    Task List:
                    No tasks on this date.
                    ____________________________________________________________
                    """;
            pw.println(output);
            pw.flush();
            return;
        }

        pw.println("____________________________________________________________\n");
        pw.println("Task List:");
        for (int i = 0; i < tasksOnDate.size(); i++) {
            Task t = tasksOnDate.get(i);
            pw.printf("%d.[%s][%s] %s\n", i+1, t.getTaskType(), t.getStatusIcon(), t.getDescription());
        }
        pw.println("____________________________________________________________");
        pw.flush();
    }
}

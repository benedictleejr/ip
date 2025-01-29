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

    public Task getTask(int index) {
        return this.tasklist.get(index);
    }

    /**
     * Adds a task to the tasklist and prints a message to the user.
     * 
     * @param task task to be added to the tasklist
     */
    public void addTask(Ui ui, Task task) {
        this.tasklist.add(task);
        ui.printTaskAdded(task, this.tasklist.size());
        return;
    }

    /**
     * Deletes a task from the tasklist and prints a message to the user.
     * 
     * @param pw printwriter to print the message to the user
     * @param index index of the task to be deleted
     */
    public void deleteTask(Ui ui, int index) {
        try {
            Task task = this.tasklist.get(index);
            this.tasklist.remove(index);
            ui.printTaskDeleted(task, this.tasklist.size());
            return;

        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("Invalid task number.");
            return;
        }
    }

    public ArrayList<Task> getTasksOnDate(String date) {
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
        return tasksOnDate;
    }
}

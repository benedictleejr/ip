package wooper;

import java.util.ArrayList;

/**
 * Tasklist class is responsible for storing and managing tasks.
 * It stores an arraylist of tasks.
 * 
 */
public class Tasklist {
    protected ArrayList<Task> tasks;

    public Tasklist() {
        this.tasks = new ArrayList<>();
    }

    public Tasklist(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the tasklist.
     * 
     * @return the tasklist
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Gets a specific task from the tasklist.
     * 
     * @param index index of the task to get
     * @return the task at the specified index
     */
    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Adds a task to the tasklist and prints a message to the user.
     * 
     * @param task task to be added to the tasklist
     */
    public void addTask(Ui ui, Task task) {
        this.tasks.add(task);
        ui.printTaskAdded(task, this.tasks.size());
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
            Task task = this.tasks.get(index);
            this.tasks.remove(index);
            ui.printTaskDeleted(task, this.tasks.size());
            return;

        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("Invalid task number.");
            return;
        }
    }

    /**
     * Retrieves all deadlines and events happening on a certain date.
     * 
     * @param date date to check for deadlines/events in format "YYYY-MM-DD"
     * @return ArrayList of tasks happening on the specified date
     */
    public ArrayList<Task> getTasksOnDate(String date) {
        ArrayList<Task> tasksOnDate = new ArrayList<>();
        for (Task t : this.tasks) {
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

    /**
     * Searchs tasklist for all tasks containing the keyword,
     * and returns an arraylist of those tasks
     * 
     * @param keyword keyword to search for in task descriptions
     * @return ArrayList of tasks containing the keyword
     * @throws WooperException if no tasks are found
     */
    public ArrayList<Task> findTasks(String keyword) throws WooperException {
        ArrayList<Task> tasksFound = new ArrayList<>();
        for (Task t : this.tasklist) {
            if (t.getDescription().contains(keyword)) {
                tasksFound.add(t);
            }
        }
        if (tasksFound.isEmpty()) {
            throw new WooperException("No tasks found.");
        }
        return tasksFound;
    }
}

package wooper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tasklist class is responsible for storing and managing tasks.
 * It stores an arraylist of tasks.
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
     * @return the tasklist
     */
    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }

    /**
     * Returns the number of tasks on the tasklist
     * @return int number of tasks on the tasklist
     */
    public int getTasklistSize() {
        return this.tasks.size();
    }

    /**
     * Gets a specific task from the tasklist.
     * @param index index of the task to get
     * @return the task at the specified index
     */
    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Adds a task to the tasklist.
     * @param task task to be added to the tasklist
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Deletes a task from the tasklist
     * @param index index of the task to be deleted
     */
    public void deleteTask(int index) throws IndexOutOfBoundsException {
        this.tasks.remove(index);
    }

    /**
     * Retrieves all deadlines and events happening on a certain date.
     * @param date date to check for deadlines/events in format "YYYY-MM-DD"
     * @return ArrayList of tasks happening on the specified date
     */
    public List<Task> getTasksOnDate(String date) {
        return this.tasks.stream()
                .filter(t -> (t instanceof Deadline && ((Deadline) t).simpleGetDueDate().equals(date))
                    || (t instanceof Event && ((Event) t).simpleGetStartDate().equals(date)))
                .collect(Collectors.toList());
    }

    /**
     * Searchs tasklist for all tasks containing the keyword,
     * and returns an arraylist of those tasks
     * @param keyword keyword to search for in task descriptions
     * @return ArrayList of tasks containing the keyword
     * @throws WooperException if no tasks are found
     */
    public ArrayList<Task> findTasks(String keyword) throws WooperException {
        ArrayList<Task> tasksFound = new ArrayList<>();
        for (Task t : this.tasks) {
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

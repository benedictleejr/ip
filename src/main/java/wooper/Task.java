package wooper;

import java.io.Serializable;

/**
 * Task class is an abstract class that represents a task.
 * Tasks have a description and a status (done or not done).
 */
public abstract class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * All tasks have a description, and an isDone status
     * @param description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Method to get the status icon of the task.
     * @return Returns the status icon of the task - "X" if done, " " if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Method to get the description of the task.
     * @return Returns the description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Method to mark the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Method to unmark the task - mark it as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    public abstract String getTaskType();

    /**
     * Capitalizes the first letter of a string and makes the rest lowercase.
     * @param str The string to capitalize.
     * @return The capitalized string.
     */
    public String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

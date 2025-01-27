import java.io.Serializable;

public abstract class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Method to get the status icon of the task.
     * 
     * @return Returns the status icon of the task - "X" if done, " " if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Method to get the description of the task.
     * 
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

    abstract public String getTaskType();
}

public abstract class Task {
    protected String description;
    protected boolean isDone;

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

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    /**
     * Method to get the type of the task.
     * @return Returns the type of the task.
     */
    abstract public String getTaskType();
}

package wooper;

/**
 * ToDo class is a subclass of Task, and represents a task that has no specific date or time.
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    /**
     * Method to get the type of the task.
     * @return Returns the type of the task.
     */
    @Override
    public String getTaskType() {
        return "T";
    }

}

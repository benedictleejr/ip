package wooper;
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    /**
     * Method to get the type of the task.
     * 
     * @return Returns the type of the task.
     */
    @Override
    public String getTaskType() {
        return "T";
    }

}

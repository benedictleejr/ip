public class Deadline extends Task {
    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     * Method to get the description of the task.
     * 
     * @return Returns the description of the task.
     */
    @Override
    public String getDescription() {
        return String.format("%s (by: %s)", this.description, this.getDeadline());
    }

    /**
     * Method to get the deadline of the task.
     * 
     * @return Returns the deadline of the task.
     */
    public String getDeadline() {
        return this.deadline;
    }

    /**
     * Method to set the deadline of the task.
     * 
     * @param deadline Deadline to be set for the task.
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Method to get the type of the task.
     * 
     * @return Returns the type of the task.
     */
    @Override
    public String getTaskType() {
        return "D";
    }

}

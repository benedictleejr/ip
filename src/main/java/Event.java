public class Event extends Task {
    protected String startTime;
    protected String endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Method to get the description of the task.
     * 
     * @return Returns the description of the task.
     */
    @Override
    public String getDescription() {
        return String.format("%s (From: %s To: %s)", this.description, this.startTime, this.endTime);
    }

    /**
     * Method to get the start time of the event.
     * 
     * @return Returns a string of the start time of the event.
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * Method to get the end time of the event.
     * 
     * @return Returns a string of the end time of the event.
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * Method to set the start time of the event.
     * 
     * @param startTime Start time to be set for the event.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Method to set the end time of the event.
     * 
     * @param endTime End time to be set for the event.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Method to get the type of the task.
     * 
     * @return Returns the type of the task.
     */
    @Override
    public String getTaskType() {
        return "E";
    }

}

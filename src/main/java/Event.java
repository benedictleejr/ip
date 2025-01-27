import java.time.LocalDate;
import java.time.LocalTime;

public class Event extends Task {
    protected LocalDate startDate;
    protected LocalTime startTime;
    protected LocalDate endDate;
    protected LocalTime endTime;

    public Event(String description, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        super(description);
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    /**
     * Method to get the description of the task.
     * 
     * @return Returns the description of the task.
     */
    @Override
    public String getDescription() {
        return String.format("%s (From: %s %s To: %s %s)", this.description, this.getStartDate(), this.getStartTime(),
                this.getEndDate(), this.getEndTime());
    }

    /**
     * Gets the start date of the event as a string, in the format "YYYY-MM-DD".
     * 
     * @return Returns the start date of the event.
     */
    public String getStartDate() {
        StringBuilder sb = new StringBuilder();
        sb.append(capitalize(this.startDate.getDayOfWeek().toString())).append(" ")
                .append(this.startDate.getDayOfMonth()).append(" ")
                .append(capitalize(this.startDate.getMonth().toString())).append(" ")
                .append(this.startDate.getYear());
        return sb.toString();
    }

    /**
     * Gets the start time of the event as a string, in the format "HH:MM".
     * 
     * @return Returns the start time of the event.
     */
    public String getStartTime() {
        return this.startTime.toString();
    }

    /**
     * Gets the end date of the event as a string, in the format "YYYY-MM-DD".
     * 
     * @return Returns the end date of the event.
     */
    public String getEndDate() {
        StringBuilder sb = new StringBuilder();
        sb.append(capitalize(this.endDate.getDayOfWeek().toString())).append(" ")
                .append(this.endDate.getDayOfMonth()).append(" ")
                .append(capitalize(this.endDate.getMonth().toString())).append(" ")
                .append(this.endDate.getYear());
        return sb.toString();
    }

    /**
     * Gets the end time of the event as a string, in the format "HH:MM".
     * 
     * @return Returns the end time of the event.
     */
    public String getEndTime() {
        return this.endTime.toString();
    }

    /**
     * Method to set the start date of the event.
     * 
     * @param startDate Start date to be set for the event.
     */
    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate);
    }

    /**
     * Method to set the start time of the event.
     * 
     * @param startTime Start time to be set for the event.
     */
    public void setStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime);
    }

    /**
     * Method to set the end date of the event.
     * 
     * @param endDate End date to be set for the event.
     */
    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate);
    }

    /**
     * Method to set the end time of the event.
     * 
     * @param endTime End time to be set for the event.
     */
    public void setEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
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

import java.time.LocalDate;
import java.time.LocalTime;

public class Deadline extends Task {
    protected LocalDate dueDate;
    protected LocalTime dueTime;

    public Deadline(String description, LocalDate dueDate, LocalTime dueTime) {
        super(description);
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    /**
     * Method to get the description of the task.
     * 
     * @return Returns the description of the task.
     */
    @Override
    public String getDescription() {
        return String.format("%s (by: %s %s)", this.description, this.getDueDate(), this.getDueTime());
    }

    /**
     * Returns the due date of the task as a string in the format "YYYY-MM-DD".
     * @return
     */
    public String simpleGetDueDate() {
        return this.dueDate.toString();
    }

    /**
     * Gets the due date of the task as a string, nicely formatted to "Monday 9 July 2002".
     * 
     * @return Returns the deadline of the task.
     */
    public String getDueDate() {
        StringBuilder sb = new StringBuilder();
        sb.append(capitalize(this.dueDate.getDayOfWeek().toString())).append(" ")
                .append(this.dueDate.getDayOfMonth()).append(" ")
                .append(capitalize(this.dueDate.getMonth().toString())).append(" ")
                .append(this.dueDate.getYear());
        return sb.toString();
    }

    /**
     * Gets the due time of the task as a string, in the format "HH:MM".
     * 
     * @return Returns the deadline of the task.
     */
    public String getDueTime() {
        return this.dueTime.toString();
    }

    /**
     * Parses input string "YYYY-MM-DD" into a LocalDate object and sets it as the due date
     * 
     * @param deadline Deadline to be set for the task.
     */
    public void setDueDate(String dueDate) {
        this.dueDate = LocalDate.parse(dueDate);
    }

    /**
     * Parses input string "HH:MM" into a LocalTime object and sets it as the due time
     * 
     * @param dueTime Due time to be set for the task.
     */
    public void setDueTime(String dueTime) {
        this.dueTime = LocalTime.parse(dueTime);
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

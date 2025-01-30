package wooper;

import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Wooper {
    protected static final String FILE_PATH = "tasklist.txt";
    protected Storage storage;
    protected Tasklist tasklist;
    protected Parser parser;
    protected Ui ui;

    public Wooper() {
        this.storage = new Storage();
        this.tasklist = storage.loadTasks(FILE_PATH);
        this.parser = new Parser();
        this.ui = new Ui();
    }

    /**
     * Main method to run the Wooper chatbot.
     * 
     * @throws IOException
     */
    public void run() throws IOException {
        ui.printOpeningMessage();

        boolean isRunning = true;
        while (isRunning) {
            ui.printPrompt();
            String action = parser.readUserInput();
            String[] l = action.split(" ");
            Parser.CommandType command = parser.parseCommand(action);
            switch (command) {
                case EXIT:
                    storage.saveTasks(FILE_PATH, tasklist.getTasks());
                    isRunning = false;
                    break;

                case LIST:
                    ui.printTaskList(tasklist.getTasks());
                    break;
                
                case DELETE:
                    handleDelete(l);
                    break;

                case VIEW:
                    handleView(l);
                    break;
                
                case MARK:
                case UNMARK:
                    // get task number & verify its validity
                    int taskNumber = getTaskNumber(l);
                    if (taskNumber == -1) {
                        ui.printMessage("Invalid task number.");
                        break;
                    }
                    Task t = tasklist.getTask(taskNumber);
                    if (command == Parser.CommandType.MARK) {
                        t.mark();
                        ui.printTaskMarked(t, taskNumber, true);
                    } else {
                        t.unmark();
                        ui.printTaskMarked(t, taskNumber, false);
                    }
                    break;

                case TODO:
                    handleTodo(l);
                    break;

                case DEADLINE:
                    handleDeadline(l);
                    break;
                
                case EVENT:
                    handleEvent(l);
                    break;

                default:
                    ui.printMessage("Invalid command.");
                    break;
            }
            storage.saveTasks(FILE_PATH, tasklist.getTasks());
        }
        ui.printClosingMessage();
        ui.close();
    }

    /**
     * Handles the deletion of a task.
     * 
     * @param l The string array containing the user input.
     */
    public void handleDelete(String[] l) {
        try {
            int index = Integer.parseInt(l[1]) - 1;
            tasklist.deleteTask(ui, index);

        } catch (NumberFormatException e) {
            ui.printMessage("Invalid task number.");
        }
    }

    /**
     * Handles the viewing of tasks on a specific date.
     * 
     * @param l The string array containing the user input.
     */
    public void handleView(String[] l) {
        if (l.length < 2) {
            ui.printMessage("Please enter a date to view.");
            return;
        }
        String date = l[1];
        ui.printTaskList(tasklist.getTasksOnDate(date));
    }

    /**
     * Given a string array, returns the task number at index 1 if it is valid, else returns -1.
     * 
     * @param l The string array to extract the task number from.
     * @return The task number if valid, else -1.
     */
    public int getTaskNumber(String[] l) {
        try {
            Integer.parseInt(l[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
        int index = Integer.parseInt(l[1]) - 1;

        // check that index is valid
        if (index < 0 || index >= tasklist.getTasks().size()) {
            return -1;
        }
        return index;
    }

    /**
     * Handles the creation of a new ToDo task.
     * 
     * @param l The string array containing the user input.
     */
    public void handleTodo(String[] l) {
        StringBuilder descriptionBuilder = new StringBuilder();
        int i = 1;
        while (i < l.length && !l[i].equals("/by")) {
            descriptionBuilder.append(l[i]).append(" ");
            i++;
        }
        String description = descriptionBuilder.toString().trim();
        tasklist.addTask(ui, new ToDo(description));
    }

    /**
     * Handles the creation of a new Deadline task.
     * 
     * @param l The string array containing the user input.
     */
    public void handleDeadline(String[] l) {
        // get the full description
        StringBuilder descriptionBuilder = new StringBuilder();
        int i = 1;
        while (i < l.length && !l[i].equals("/by")) {
            descriptionBuilder.append(l[i]).append(" ");
            i++;
        }
        String description = descriptionBuilder.toString().trim();

        // try to parse the deadline date and time: YYYY-MM-DD HH:MM
        StringBuilder deadlineBuilder = new StringBuilder();
        i++; // skip the /by
        while (i < l.length) {
            deadlineBuilder.append(l[i]).append(" ");
            i++;
        }
        String deadline = deadlineBuilder.toString().trim();
        String[] dueDateTime = deadline.split(" ");
        LocalDate dueDate;
        LocalTime dueTime;
        try {
            dueDate = LocalDate.parse(dueDateTime[0]);
            dueTime = LocalTime.parse(dueDateTime[1]);
            tasklist.addTask(ui, new Deadline(description, dueDate, dueTime));
        } catch (Exception e) {
            ui.printMessage("Invalid date/time format - use YYYY-MM-DD HH:MM");
        }
    }

    /**
     * Handles the creation of a new Event task.
     * 
     * @param l The string array containing the user input.
     */
    public void handleEvent(String[] l) {
        // get the full description & start time & end time
        StringBuilder descriptionBuilder = new StringBuilder();
        int i = 1;
        while (i < l.length && !l[i].equals("/from")) {
            descriptionBuilder.append(l[i]).append(" ");
            i++;
        }
        String description = descriptionBuilder.toString().trim();

        // parse the start/end date and time: YYYY-MM-DD HH:MM
        StringBuilder startDateTimeBuilder = new StringBuilder();
        i++; // skip the /from
        while (i < l.length && !l[i].equals("/to")) {
            startDateTimeBuilder.append(l[i]).append(" ");
            i++;
        }
        String[] startDateTime = startDateTimeBuilder.toString().trim().split(" ");

        StringBuilder endDateTimeBuilder = new StringBuilder();
        i++; // skip the /to
        while (i < l.length) {
            endDateTimeBuilder.append(l[i]).append(" ");
            i++;
        }
        String[] endDateTime = endDateTimeBuilder.toString().trim().split(" ");

        LocalDate startDate;
        LocalTime startTime;
        LocalDate endDate;
        LocalTime endTime;

        // then, try to assign and reject if invalid inputs
        try {
            startDate = LocalDate.parse(startDateTime[0]);
            startTime = LocalTime.parse(startDateTime[1]);
            endDate = LocalDate.parse(endDateTime[0]);
            endTime = LocalTime.parse(endDateTime[1]);
            tasklist.addTask(ui, new Event(description, startDate, startTime, endDate, endTime));
        } catch (Exception e) {
            ui.printMessage("Invalid date/time format - use YYYY-MM-DD HH:MM");
        }
    }

    public static void main(String[] args) throws IOException {
        Wooper wooper = new Wooper();
        wooper.run();                
    }
}

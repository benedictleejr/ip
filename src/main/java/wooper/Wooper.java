package wooper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Main program class for the Wooper chatbot program.
 */
public class Wooper {
    protected static final String FILE_PATH = "tasklist.txt";
    protected Storage storage;
    protected Tasklist tasks;
    protected Parser parser;

    /**
     * Each run of the program will have its own storage object, tasklist object,
     * as well as parser and ui objects
     */
    public Wooper() {
        this.storage = new Storage();
        this.tasks = storage.loadTasks(FILE_PATH);
        this.parser = new Parser();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        StringBuilder response = new StringBuilder();
        String[] l = input.split(" ");
        Parser.CommandType command = parser.parseCommand(input);
        assert command instanceof Parser.CommandType : "Command variable is not of type Parser.CommandType";

        switch (command) {
        case EXIT:
            storage.saveTasks(FILE_PATH, tasks.getAllTasks());
            response.append("Goodbye! See you next time.");
            break;
        case LIST:
            response.append(handleList());
            break;
        case DELETE:
            response.append(handleDelete(l));
            break;
        case VIEW:
            response.append(handleView(l));
            break;
        case FIND:
            response.append(handleFind(l));
            break;
        case MARK:
        case UNMARK:
            response.append(handleMarking(command, l));
            break;
        case TODO:
            response.append(handleTodo(l));
            break;
        case DEADLINE:
            response.append(handleDeadline(l));
            break;
        case EVENT:
            response.append(handleEvent(l));
            break;
        default:
            response.append("Invalid command.");
            break;
        }
        storage.saveTasks(FILE_PATH, tasks.getAllTasks());
        return response.toString();
    }

    /**
     * Retrieves list of all tasks and formats for output
     * @return formatted list of all tasks
     */
    public String handleList() {
        ArrayList<Task> allTasks = tasks.getAllTasks();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allTasks.size(); i++) {
            Task t = allTasks.get(i);
            sb.append(String.format("%d.[%s][%s] %s\n", i + 1, t.getTaskType(), t.getStatusIcon(), t.getDescription()));
        }
        return sb.toString().trim();
    }

    /**
     * Given user input, handles deletion of a task. If successful, returns success
     * message
     * Else, returns Invalid message
     * @param l The string array containing the user input
     * @return Success or invalid message
     */
    public String handleDelete(String[] l) {
        try {
            int index = Integer.parseInt(l[1]) - 1;
            assert index > 0 && index < tasks.getAllTasks().size() : "Index is out of bounds!";
            Task deletedTask = tasks.getTask((index));
            tasks.deleteTask(index);
            return String.format("""
                    Task removed: %s
                    Now you have %d tasks in the list.
                    """, deletedTask.getDescription(), tasks.getAllTasks().size());

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "Invalid task number.";
        }
    }

    /**
     * Handles the viewing of tasks on a specific date.
     * @param l The string array containing the user input.
     * @return Formatted String of tasks on specified date
     */
    public String handleView(String[] l) {
        if (l.length < 2) { // Invalid input, prompt user
            return "Please enter a date to view";
        }
        String date = l[1];
        List<Task> tasksOnDate = tasks.getTasksOnDate(date);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasksOnDate.size(); i++) {
            Task t = tasksOnDate.get(i);
            sb.append(String.format("%d.[%s][%s] %s\n", i + 1, t.getTaskType(), t.getStatusIcon(), t.getDescription()));
        }
        return sb.toString().trim();
    }

    /**
     * Handles searching for a task based on keyword
     * @param l The string array containing the user input.
     * @return Formatted String of tasks matching keyword
     */
    public String handleFind(String[] l) {
        if (l.length < 2) {
            return "Please enter a keyword to search for.";
        }
        String keyword = String.join(" ", java.util.Arrays.copyOfRange(l, 1, l.length));
        try {
            ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < matchingTasks.size(); i++) {
                Task t = matchingTasks.get(i);
                sb.append(String.format("%d.[%s][%s] %s\n", i + 1, t.getTaskType(), t.getStatusIcon(),
                        t.getDescription()));
            }
            return sb.toString().trim();
        } catch (WooperException e) { // thrown when no tasks found in tasks.findTasks(keyword)
            return e.getMessage();
        }
    }

    /**
     * Handles marking a task as done/not done
     * @param command Command type MARK or UNMARK
     * @param l The string array containing the user input.
     * @return Success message or error message.
     */
    public String handleMarking(Parser.CommandType command, String[] l) {
        // first, test if given input is valid. Then, get task number
        try {
            Integer.parseInt(l[1]);
        } catch (NumberFormatException e) {
            return "Invalid task number: Not a number!";
        }
        int taskNumber = Integer.parseInt(l[1]) - 1;

        // check that taskNumber is valid
        if (taskNumber < 0 || taskNumber >= tasks.getAllTasks().size()) {
            return "Invalid task number: Index out of bounds!";
        }

        // now, get task and mark/unmark
        Task t = tasks.getTask(taskNumber);
        assert t instanceof Task : "t is not a task!";
        if (command == Parser.CommandType.MARK) {
            t.mark();
            return String.format("Task %d marked as done.", taskNumber + 1);
        } else if (command == Parser.CommandType.UNMARK) {
            t.unmark();
            return String.format("Task %d marked as not done.", taskNumber + 1);
        } else {
            return "Invalid task type for marking/unmarking!";
        }
    }

    /**
     * Handles the creation of a new ToDo task.
     * @param l The string array containing the user input.
     */
    public String handleTodo(String[] l) {
        StringBuilder descriptionBuilder = new StringBuilder();
        int i = 1;
        while (i < l.length && !l[i].equals("/by")) {
            descriptionBuilder.append(l[i]).append(" ");
            i++;
        }
        String description = descriptionBuilder.toString().trim();
        ToDo newTask = new ToDo(description);

        tasks.addTask(newTask);
        return String.format("""
                Woop Woop! I've added this task:
                    [%s][ ] %s
                Now you have %d tasks in the list.
                """, newTask.getTaskType(), newTask.getDescription(), tasks.getAllTasks().size());
    }

    /**
     * Handles the creation of a new Deadline task.
     * @param l The string array containing the user input.
     */
    public String handleDeadline(String[] l) {
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
            Deadline newTask = new Deadline(description, dueDate, dueTime);
            tasks.addTask(newTask);
            return String.format("""
                    Woop Woop! I've added this task:
                        [%s][ ] %s
                    Now you have %d tasks in the list.
                    """, newTask.getTaskType(), newTask.getDescription(), tasks.getAllTasks().size());

        } catch (Exception e) {
            return "Invalid date/time format - use YYYY-MM-DD HH:MM";
        }
    }

    /**
     * Handles the creation of a new Event task.
     * @param l The string array containing the user input.
     */
    public String handleEvent(String[] l) {
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
            Event newTask = new Event(description, startDate, startTime, endDate, endTime);
            tasks.addTask(newTask);
            return String.format("""
                    Woop Woop! I've added this task:
                        [%s][ ] %s
                    Now you have %d tasks in the list.
                    """, newTask.getTaskType(), newTask.getDescription(), tasks.getAllTasks().size());

        } catch (Exception e) {
            return "Invalid date/time format - use YYYY-MM-DD HH:MM";
        }
    }

}

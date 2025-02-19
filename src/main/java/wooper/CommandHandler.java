package wooper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Receives commands from Wooper program and handles them accordingly
 * Abstracts out code from main wooper program into one unified place
 */
public class CommandHandler {
    protected static final String TASKLIST_FILE_PATH = "tasklist.txt";
    protected static final String NOTEBOOK_FILE_PATH = "notebook.txt";
    protected Storage storage;
    protected Tasklist tasks;
    protected Notebook notes;

    /**
     * Object that handles commands from user, and returns output to GUI
     */
    public CommandHandler() {
        this.storage = new Storage();
        this.tasks = storage.loadTasks(TASKLIST_FILE_PATH);
        this.notes = storage.loadNotes(NOTEBOOK_FILE_PATH);
    }

    /**
     * Saves tasks and notes, and handles all other cleanup tasks before exiting
     * program
     */
    public void handleExit() {
        saveAllInfo();
        // insert other cleanup tasks
    }

    /**
     * Saves all tasks and notes
     */
    public void saveAllInfo() {
        storage.saveTasks(TASKLIST_FILE_PATH, tasks.getAllTasks());
        storage.saveNotes(NOTEBOOK_FILE_PATH, notes.getAllNotes());
    }

    /**
     * Retrieves list of either all tasks or notes, and formats for output
     *
     * @return formatted list of all tasks/notes
     */
    public String handleList(Parser.CommandType command) {
        StringBuilder sb = new StringBuilder();

        if (command.equals(Parser.CommandType.LISTTASKS)) {
            ArrayList<Task> allTasks = tasks.getAllTasks();
            for (int i = 0; i < allTasks.size(); i++) {
                Task t = allTasks.get(i);
                sb.append(String.format("%d.[%s][%s] %s\n", i + 1, t.getTaskType(), t.getStatusIcon(),
                        t.getDescription()));
            }
            if (sb.length() == 0) {
                return "You have no tasks in your tasklist right now.";
            }

        } else if (command.equals(Parser.CommandType.LISTNOTES)) {
            ArrayList<Note> allNotes = notes.getAllNotes();
            for (int i = 0; i < allNotes.size(); i++) {
                Note n = allNotes.get(i);
                sb.append(String.format("Note #%d\nTitle: %s\nContent: %s\n", i + 1, n.getTitle(), n.getContent()));
                sb.append("---------------------------------------------------\n");
            }
            if (sb.length() == 0) {
                return "You have no notes in your notebook right now.";
            }

        } else {
            return "Error: Incorrect command type!";
        }

        return sb.toString().trim();
    }

    /**
     * Given user input, handles deletion of a task OR note.
     * If successful, returns success message
     * Else, returns Invalid message
     *
     * @param l The string array containing the user input
     * @return Success or invalid message
     */
    public String handleDelete(String[] l, Parser.CommandType command) {
        try {
            int index = Integer.parseInt(l[1]) - 1;

            if (command.equals(Parser.CommandType.DELTASK)) {
                assert index > 0 && index < tasks.getAllTasks().size() : "Index is out of bounds!";
                Task deletedTask = tasks.getTask(index);
                tasks.deleteTask(index);

                return String.format("""
                        Task removed: %s
                        Now you have %d tasks in the list.
                        """, deletedTask.getDescription(), tasks.getAllTasks().size());

            } else if (command.equals(Parser.CommandType.DELNOTE)) {
                assert index > 0 && index < notes.getAllNotes().size() : "Index is out of bounds!";
                Note deletedNote = notes.getNote(index);
                notes.deleteNote(index);

                return String.format("""
                        Note removed: %s
                        Now you have %d notes in the list.
                        """, deletedNote.getTitle(), notes.getNotebookSize());
            } else {
                return "Error: Invalid command type for deletion!";
            }

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            String placeholder = command.equals(Parser.CommandType.DELTASK) ? "task" : "note";
            return String.format("Invalid %s number.", placeholder);
        }
    }

    /**
     * Handles the viewing of tasks on a specific date.
     *
     * @param l The string array containing the user input.
     * @return Formatted String of tasks on specified date
     */
    public String handleViewOnDate(String[] l) {
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
     *
     * @param l The string array containing the user input.
     * @return Formatted String of tasks matching keyword
     */
    public String handleFindTaskByKeyword(String[] l) {
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
     *
     * @param command Command type MARK or UNMARK
     * @param l       The string array containing the user input.
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
     * Handles the creation of a new note.
     *
     * @param l The string array containing the user input.
     * @return output after successful creation
     */
    public String handleNewNote(String[] l) {
        StringBuilder titleBuilder = new StringBuilder();
        int i = 1;
        while (i < l.length && !l[i].equals("/content")) {
            titleBuilder.append(l[i]).append(" ");
            i++;
        }

        if (i >= l.length) {
            return "Invalid command.";
        }
        String title = titleBuilder.toString().trim();

        StringBuilder contentBuilder = new StringBuilder();
        i++; // skip the /content
        while (i < l.length) {
            contentBuilder.append(l[i]).append(" ");
            i++;
        }
        String content = contentBuilder.toString().trim();

        Note newNote = new Note(title, content);
        notes.addNote(newNote);

        return String.format("""
                Woop Woop! I've added this note:
                    Title: %s
                    Content: %s
                Now you have %d notes in the list.
                """, newNote.getTitle(), newNote.getContent(), notes.getNotebookSize());
    }

    /**
     * Handles the creation of a new ToDo task.
     *
     * @param l The string array containing the user input.
     * @return output after successful creation
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
     *
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
     *
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

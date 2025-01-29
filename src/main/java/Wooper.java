import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Wooper {


    private static final String FILE_PATH = "tasklist.txt";

    public static void main(String[] args) throws IOException {
        Ui ui = new Ui();
        Parser ps = new Parser();

        Storage storage = new Storage();
        Tasklist tasklist = storage.loadTasks(FILE_PATH);

        ui.printOpeningMessage();       

        boolean isRunning = true;
        while (isRunning) {
            ui.printPrompt();
            String action = ps.readUserInput();
            if (ps.isExit(action)) {
                storage.saveTasks(FILE_PATH, tasklist.getTasklist());
                break;
            }

            if (ps.isList(action)) {
                ui.printTaskList(tasklist.getTasklist());
                continue;
            }

            // 7 possibilities - 3 tasks, mark/unmark, delete, view <date
            String[] l = action.split(" ");

            // first, check delete
            if (l.length == 2 && (l[0].equals("delete"))) {
                try {
                    Integer.parseInt(l[1]);
                } catch (NumberFormatException e) {
                    ui.printMessage("Invalid task number.");
                    action = ps.readUserInput();
                    continue;
                }
                int index = Integer.parseInt(l[1]) - 1;
                tasklist.deleteTask(ui, index);

            // then, check view <date>
            } else if (l.length == 2 && (l[0].equals("view"))) {
                String date = l[1];
                ui.printTasksOnDate(tasklist.getTasksOnDate(date));
                
            // then, check mark & unmark
            } else if (l.length == 2 && (l[0].equals("mark") || l[0].equals("unmark"))) {
                try {
                    Integer.parseInt(l[1]);
                } catch (NumberFormatException e) {
                    ui.printMessage("Invalid task number.");
                    continue;
                }
                int index = Integer.parseInt(l[1]) - 1;

                // check that index is valid
                if (index < 0 || index >= tasklist.tasklist.size()) {
                    ui.printMessage("Invalid task number.");
                    continue;
                }

                if (l[0].equals("mark")) {
                    tasklist.getTask(index).mark();
                    ui.printTaskMarked(tasklist.getTask(index), index, true);

                } else if (l[0].equals("unmark")) {
                    tasklist.getTask(index).unmark();
                    ui.printTaskMarked(tasklist.getTask(index), index, false);
                }

            // else, check task type
            } else if (l.length >= 2 && l[0].equals("todo")) {
                // get the full description
                StringBuilder descriptionBuilder = new StringBuilder();
                int i = 1;
                while (i < l.length && !l[i].equals("/by")) {
                    descriptionBuilder.append(l[i]).append(" ");
                    i++;
                }
                String description = descriptionBuilder.toString().trim();
                tasklist.addTask(ui, new ToDo(description));
            
            } else if (l.length >= 4 && l[0].equals("deadline")) {
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
                    System.out.println("\nInvalid date/time format - use YYYY-MM-DD HH:MM\n");
                }

            } else if (l.length >= 6 && l[0].equals("event")) {
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

            } else {
                ui.printMessage("Invalid command.");
            }
            
            storage.saveTasks(FILE_PATH, tasklist.getTasklist());
        }
        ui.printClosingMessage();
        ui.close();        
    }
}

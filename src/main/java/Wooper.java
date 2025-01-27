import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class Wooper {
    // ASCII art generated at
    // https://patorjk.com/software/taag/#p=display&f=Doom&t=Wooper!
    static final String logo = """
             _    _                             _
            | |  | |                           | |
            | |  | | ___   ___  _ __   ___ _ __| |
            | |/\\| |/ _ \\ / _ \\| '_ \\ / _ \\ '__| |
            \\  /\\  / (_) | (_) | |_) |  __/ |  |_|
             \\/  \\/ \\___/ \\___/| .__/ \\___|_|  (_)
                               | |
                               |_|
            """;

    static String openingMessage = """
            ____________________________________________________________

            Wooper:
            Hello! I'm Wooper, your personal tasklist chatbot.
            How can I help you today?

            Valid Commands:
            1. Add tasks:
                a. todo <task description>
                b. deadline <task description> /by <due date> <due time>
                c. event <task description> /from <start date> <start time> /to <end date> <end time>
                NOTE: Date and time should be in the format YYYY-MM-DD HH:MM
            2. View tasks:
                a. list - views all tasks
                b. view <date> - views tasks due on a specific date
            3. Mark/unmark tasks as done:
                a. mark <task number>
                b. unmark <task number>
            4. Delete tasks:
                a. delete <task number>
            5. Type 'exit' to exit
            ____________________________________________________________
            """;

    static String closingMessage = """
            ____________________________________________________________

            Wooper:
            Bye bye, see you soon!
            ____________________________________________________________
            """;

    static String promptMessage = "User: ";

    private static final String FILE_PATH = "tasklist.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        Tasklist tasklist = Tasklist.loadTasks(FILE_PATH);

        pw.println("Hello from");
        pw.println(logo);
        pw.println(openingMessage);
        pw.println(promptMessage);
        pw.flush();

        String action = br.readLine();
        boolean isRunning = true;

        while (isRunning) {
            if (action.equals("exit")) {
                tasklist.saveTasks(FILE_PATH);
                break;
            }

            if (action.equals("list")) {
                tasklist.printTasklist(pw);                
                
            } else {
                // 7 possibilities - 3 tasks, mark/unmark, delete, view <date
                String[] l = action.split(" ");

                // first, check delete
                if (l.length == 2 && (l[0].equals("delete"))) {
                    try {
                        Integer.parseInt(l[1]);
                    } catch (NumberFormatException e) {
                        pw.println("\nInvalid task number.\n");
                        pw.println(promptMessage);
                        pw.flush();
                        action = br.readLine().toLowerCase();
                        continue;
                    }
                    int index = Integer.parseInt(l[1]) - 1;

                    // check that index is valid
                    if (index < 0 || index >= tasklist.tasklist.size()) {
                        pw.println("\nInvalid task number.\n");
                        pw.println(promptMessage);
                        pw.flush();
                        action = br.readLine().toLowerCase();
                        continue;
                    }

                    tasklist.deleteTask(pw, index);

                // then, check view <date>
                } else if (l.length == 2 && (l[0].equals("view"))) {
                    tasklist.printTasksOnDate(pw, l[1]);
                    
                // then, check mark & unmark
                } else if (l.length == 2 && (l[0].equals("mark") || l[0].equals("unmark"))) {
                    try {
                        Integer.parseInt(l[1]);
                    } catch (NumberFormatException e) {
                        pw.println("\nInvalid task number.\n");
                        pw.println(promptMessage);
                        pw.flush();
                        action = br.readLine().toLowerCase();
                        continue;
                    }
                    int index = Integer.parseInt(l[1]) - 1;

                    // check that index is valid
                    if (index < 0 || index >= tasklist.tasklist.size()) {
                        pw.println("\nInvalid task number.\n");
                        pw.println(promptMessage);
                        pw.flush();
                        action = br.readLine().toLowerCase();
                        continue;
                    }

                    if (l[0].equals("mark")) {
                        tasklist.getTask(index).mark();
                        String output = String.format("""
                                ____________________________________________________________
                                Wooper:
                                Task %d marked as done.
                                ____________________________________________________________
                                """, index + 1);
                        pw.println(output);

                    } else if (l[0].equals("unmark")) {
                        tasklist.getTask(index).unmark();
                        String output = String.format("""
                                ____________________________________________________________
                                Wooper:
                                Task %d marked as not done.
                                ____________________________________________________________
                                """, index + 1);
                        pw.println(output);
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
                    tasklist.addTask(pw, new ToDo(description));
                
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
                        tasklist.addTask(pw, new Deadline(description, dueDate, dueTime));
                    } catch (Exception e) {
                        pw.println("\nInvalid date/time format - use YYYY-MM-DD HH:MM\n");
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
                        tasklist.addTask(pw, new Event(description, startDate, startTime, endDate, endTime));
                    } catch (Exception e) {
                        pw.println("\nInvalid date/time format - use YYYY-MM-DD HH:MM\n");
                    }

                } else {
                    pw.println("\nInvalid command.\n");
                }
            }
            tasklist.saveTasks(FILE_PATH);

            pw.flush();
            pw.println(promptMessage);
            pw.flush();
            action = br.readLine().toLowerCase();
            isRunning = !action.equals("exit");
        }
        pw.println(closingMessage);

        br.close();
        pw.close();
    }
}

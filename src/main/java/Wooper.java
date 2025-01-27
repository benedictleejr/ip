import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

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
                b. deadline <task description> /by <deadline>
                c. event <task description> /from <start time> /to <end time>
            2. View tasks:
                a. list
            3. Mark/unmark tasks as done:
                a. mark <task number>
                b. unmark <task number>
            4. Type 'exit' to exit
            ____________________________________________________________
            """;

    static String closingMessage = """
            ____________________________________________________________

            Wooper:
            Bye bye, see you soon!
            ____________________________________________________________
            """;

    static String promptMessage = "User: ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        Tasklist tasklist = new Tasklist();

        pw.println("Hello from");
        pw.println(logo);
        pw.println(openingMessage);
        pw.println(promptMessage);
        pw.flush();

        String action = br.readLine();
        boolean isRunning = true;

        while (isRunning) {
            if (action.equals("exit")) {
                break;
            }

            if (action.equals("list")) {
                tasklist.printTasklist(pw);                
                
            } else {
                // 6 possibilities - 3 tasks, mark, unmark, delete
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
                    // get the full description & deadline
                    StringBuilder descriptionBuilder = new StringBuilder();
                    int i = 1;
                    while (i < l.length && !l[i].equals("/by")) {
                        descriptionBuilder.append(l[i]).append(" ");
                        i++;
                    }
                    String description = descriptionBuilder.toString().trim();

                    StringBuilder deadlineBuilder = new StringBuilder();
                    i++; // skip the /by
                    while (i < l.length) {
                        deadlineBuilder.append(l[i]).append(" ");
                        i++;
                    }
                    String deadline = deadlineBuilder.toString().trim();

                    tasklist.addTask(pw, new Deadline(description, deadline));

                } else if (l.length >= 6 && l[0].equals("event")) {
                    // get the full description & start time & end time
                    StringBuilder descriptionBuilder = new StringBuilder();
                    int i = 1;
                    while (i < l.length && !l[i].equals("/from")) {
                        descriptionBuilder.append(l[i]).append(" ");
                        i++;
                    }
                    String description = descriptionBuilder.toString().trim();

                    StringBuilder startTimeBuilder = new StringBuilder();
                    i++; // skip the /from
                    while (i < l.length && !l[i].equals("/to")) {
                        startTimeBuilder.append(l[i]).append(" ");
                        i++;
                    }
                    String startTime = startTimeBuilder.toString().trim();

                    StringBuilder endTimeBuilder = new StringBuilder();
                    i++; // skip the /to
                    while (i < l.length) {
                        endTimeBuilder.append(l[i]).append(" ");
                        i++;
                    }
                    String endTime = endTimeBuilder.toString().trim();

                    tasklist.addTask(pw, new Event(description, startTime, endTime));
                } else {
                    pw.println("\nInvalid command.\n");
                }
            }

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

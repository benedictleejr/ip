package wooper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Ui class is responsible for handling the user interface of the application.
 * The class contains methods to print messages to the user.
 */
public class Ui {
    /**
     * ASCII art of Wooper generated
     * at https://patorjk.com/software/taag/#p=display&f=Doom&t=Wooper!
     */
    private static final String logo = """
             _    _                             _
            | |  | |                           | |
            | |  | | ___   ___  _ __   ___ _ __| |
            | |/\\| |/ _ \\ / _ \\| '_ \\ / _ \\ '__| |
            \\  /\\  / (_) | (_) | |_) |  __/ |  |_|
             \\/  \\/ \\___/ \\___/| .__/ \\___|_|  (_)
                               | |
                               |_|
            """;

    private static final String openingMessage = """
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
                c. find <keyword> - finds tasks with a specific keyword
            3. Mark/unmark tasks as done:
                a. mark <task number>
                b. unmark <task number>
            4. Delete tasks:
                a. delete <task number>
            5. Type 'exit' to exit
            ____________________________________________________________
            """;

    private static final String closingMessage = """
            ____________________________________________________________
            Wooper:
            Bye bye, see you soon!
            ____________________________________________________________
            """;

    private static final String promptMessage = "User: ";
    protected PrintWriter pw;
    protected BufferedReader br;

    /**
     * Every Ui will contain a printwriter to print to the terminal,
     * and a BufferedReader to read user inputs
     */
    public Ui() {
        this.pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Prints the opening messages to the user.
     */
    public void printOpeningMessage() {
        pw.println("Hello from");
        pw.println(logo);
        pw.println(openingMessage);
        pw.flush();
    }

    /**
     * Prints the prompting message to the user
     */
    public void printPrompt() {
        pw.println(promptMessage);
        pw.flush();
    }

    /**
     * Prints the closing messages to the user.
     */
    public void printClosingMessage() {
        pw.println(closingMessage);
        pw.flush();
    }

    /**
     * Closes the printwriter and buffered reader.
     */
    public void close() throws IOException {
        pw.close();
        br.close();
    }

    /**
     * Prints message to user, and prompts for a response
     * @param message message to be printed
     */
    public void printMessage(String message) {
        String output = String.format("""
                ____________________________________________________________
                Wooper:
                %s
                ____________________________________________________________
                """, message);
        pw.println(output);
        pw.flush();
    }

    /**
     * Prints all tasks in the lists with beautiful formatting
     * @param tasklist list of tasks to be printed
     */
    public void printTaskList(ArrayList<Task> tasklist) {
        if (tasklist.size() == 0) {
            String output = """
                    ____________________________________________________________
                    Wooper:
                    Task List:
                    No tasks in the list.
                    ____________________________________________________________
                    """;
            pw.println(output);
            return;
        }

        pw.println("____________________________________________________________\n");
        pw.println("Task List:");
        for (int i = 0; i < tasklist.size(); i++) {
            Task t = tasklist.get(i);
            pw.printf("%d.[%s][%s] %s\n", i + 1, t.getTaskType(), t.getStatusIcon(), t.getDescription());
        }
        pw.println("____________________________________________________________");
    }

    /**
     * Prints the task added message to the user, once successfully added.
     * @param task task that was added
     * @param size size of the tasklist
     */
    public void printTaskAdded(Task task, int size) {
        String output = String.format("""
                ____________________________________________________________
                Wooper:
                Woop Woop! I've added this task:
                    [%s][ ] %s
                Now you have %d tasks in the list.
                ____________________________________________________________

                """, task.getTaskType(), task.getDescription(), size);
        pw.println(output);
        pw.flush();
    }

    /**
     * Prints the task deleted message to the user, once successfully deleted.
     * @param task task that was deleted
     * @param size size of the tasklist
     */
    public void printTaskDeleted(Task task, int size) {
        String output = String.format("""
                ____________________________________________________________
                Wooper:
                Woop Woop! I've removed this task:
                    [%s][%s] %s
                Now you have %d tasks in the list.
                ____________________________________________________________

                """, task.getTaskType(), task.getStatusIcon(), task.getDescription(), size);
        pw.println(output);
        pw.flush();
    }

    /**
     * Prints the task marked message to the user, once successfully marked.
     * @param task task that was marked
     * @param index index of the task in the tasklist
     * @param markingTask boolean to indicate if the task is being marked or unmarked
     */
    public void printTaskMarked(Task task, int index, boolean markingTask) {
        if (markingTask) {
            String output = String.format("""
                    ____________________________________________________________
                    Wooper:
                    Task %d marked as done.
                    ____________________________________________________________
                    """, index + 1);
            pw.println(output);
        } else {
            String output = String.format("""
                    ____________________________________________________________
                    Wooper:
                    Task %d marked as not done.
                    ____________________________________________________________
                    """, index + 1);
            pw.println(output);
        }
        pw.flush();
        return;
    }

    /**
     * Given a list of tasks from a particular date, print them nicely
     * @param tasksOnDate list of tasks from particular date
     */
    public void printTasksOnDate(ArrayList<Task> tasksOnDate) {
        if (tasksOnDate.size() == 0) {
            String output = """
                    ____________________________________________________________
                    Wooper:
                    Task List:
                    No tasks on this date.
                    ____________________________________________________________
                    """;
            pw.println(output);
            pw.flush();
            return;
        }

        pw.println("____________________________________________________________");
        pw.println("Task List:");
        for (int i = 0; i < tasksOnDate.size(); i++) {
            Task t = tasksOnDate.get(i);
            pw.printf("%d.[%s][%s] %s\n", i + 1, t.getTaskType(), t.getStatusIcon(), t.getDescription());
        }
        pw.println("____________________________________________________________");
        pw.flush();
    }
}

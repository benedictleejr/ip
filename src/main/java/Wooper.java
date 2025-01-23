import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

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
            1. Type anything to be stored
            2. Type 'list' to view what's stored
            3. Type 'mark x' to mark task x as done
            4. Type 'unmark x' to unmark task x
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

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        Tasklist tasklist = new Tasklist();

        pw.println("Hello from");
        pw.println(logo);
        pw.println(openingMessage);
        pw.println(promptMessage);
        pw.flush();

        String action = br.readLine().toLowerCase();
        boolean isRunning = !action.equals("exit");

        while (isRunning) {
            if (action.equals("list")) {
                tasklist.printTasklist(pw);
            } else {
                String[] l = action.split(" ");
                if (l.length == 2 && (l[0].equals("mark") || l[0].equals("unmark"))) {
                    int index = Integer.parseInt(l[1]) - 1;
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

                } else {
                    tasklist.addTask(new Task(action));
                    String output = String.format("""
                            ____________________________________________________________
                                                    
                            Wooper:
                            Added: %s
                            ____________________________________________________________
                            """, action);
                    pw.println(output);
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

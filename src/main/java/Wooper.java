import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Wooper {
    // ASCII art generated at https://patorjk.com/software/taag/#p=display&f=Doom&t=Wooper!
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
                                Hello! I'm Wooper, your personal chatbot.
                                How can I help you today?
                                
                                Valid Commands:
                                1. Type anything to be stored
                                2. Type 'list' to view what's stored
                                3. Type 'exit' to exit
                                
            ____________________________________________________________
            """;

    static String closingMessage = """
            ____________________________________________________________

                                        Wooper:
                                        Bye bye, see you soon!
            ____________________________________________________________
            """;

    static String promptMessage = "User: ";

    public static void printList(PrintWriter pw, ArrayList<String> list) {
        pw.println("                                 List:");
        for (int i = 0; i < list.size(); i++) {
            pw.printf("                                 %d: %s\n", i + 1, list.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        ArrayList<String> list = new ArrayList<>();

        pw.println("Hello from");
        pw.println(logo);
        pw.println(openingMessage);
        pw.println(promptMessage);
        pw.flush();

        String message = br.readLine().toLowerCase();
        boolean isRunning = !message.equals("exit");

        while (isRunning) {

            if (message.equals("list")) {
                // String output = String.format("""
                //         ____________________________________________________________
                //                                         Wooper:
                //         """);
                pw.println("""
                        ____________________________________________________________
                                                        Wooper:
                        """);
                Wooper.printList(pw, list);
                pw.println("____________________________________________________________");
            } else {
                list.add(message);
                String output = String.format("""
                        ____________________________________________________________
                                                        Wooper:
                                                        Added: %s
                        ____________________________________________________________

                        """, message);
                pw.println(output);
            }
            pw.flush();

            pw.println(promptMessage);
            pw.flush();
            message = br.readLine().toLowerCase();
            isRunning = !message.equals("exit");
        }
        pw.println(closingMessage);

        br.close();
        pw.close();
    }
}

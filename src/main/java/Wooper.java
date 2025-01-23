import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Wooper {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        // ASCII art generated at https://patorjk.com/software/taag/#p=display&f=Doom&t=Wooper!
        String logo = """
                 _    _                             _
                | |  | |                           | |
                | |  | | ___   ___  _ __   ___ _ __| |
                | |/\\| |/ _ \\ / _ \\| '_ \\ / _ \\ '__| |
                \\  /\\  / (_) | (_) | |_) |  __/ |  |_|
                 \\/  \\/ \\___/ \\___/| .__/ \\___|_|  (_)
                                   | |
                                   |_|
                    """;

        String openingMessage = """
                ____________________________________________________________
                                 
                                    Wooper:
                                    Hello! I'm Wooper, your personal chatbot.
                                    How can I help you today?
                ____________________________________________________________
                """;

        String closingMessage = """
                ____________________________________________________________

                                            Wooper:
                                            Bye. Hope to see you again soon!
                ____________________________________________________________
                """;

        String promptMessage = "User: ";

        pw.println("Hello from");
        pw.println(logo);
        pw.println(openingMessage);
        pw.println(promptMessage);
        pw.flush();

        String message = br.readLine().toLowerCase();
        while (!message.equals("bye")) {
            String output = String.format("""
                    ____________________________________________________________
                                                    Wooper:
                                                    %s
                    ____________________________________________________________
                    """, message);
            pw.println(output);
            pw.flush();

            pw.println(promptMessage);
            pw.flush();
            message = br.readLine().toLowerCase();
        }
        pw.println(closingMessage);

        br.close();
        pw.close();
    }
}

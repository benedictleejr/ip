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
                 Hello! I'm Wooper, your personal chatbot.
                 How can I help you today?
                ____________________________________________________________
                 Bye. Hope to see you again soon!
                ____________________________________________________________

                """;

        pw.println("Hello from");
        pw.println(logo);
        pw.println(openingMessage);

        br.close();
        pw.close();
    }
}

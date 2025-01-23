public class Wooper {
    public static void main(String[] args) {
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
                 Hello! I'm Wooper.
                 What can I do for you?
                ____________________________________________________________
                 Bye. Hope to see you again soon!
                ____________________________________________________________

                """;

        System.out.println("Hello from\n" + logo);
        System.out.println(openingMessage);
    }
}

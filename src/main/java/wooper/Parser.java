package wooper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Parser class is responsible for parsing user input,
 * and returning the logic to the main program.
 * 
 */
public class Parser {
    private BufferedReader br;
    public enum CommandType {
        EXIT, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, VIEW, INVALID
    }

    public Parser() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readUserInput() {
        try {
            return br.readLine().toLowerCase();
        } catch (IOException e) {
            System.err.println("Error reading user input: " + e.getMessage());
            return "";
        }
    }

    public boolean isList(String action) {
        return action.equals("list");
    }

    public CommandType parseCommand(String action) {
        if (action.equals("exit")) {
            return CommandType.EXIT;
        }
        
        if (action.equals("list")) {
            return CommandType.LIST;
        }

        String[] l = action.split(" ");
        if (l.length == 2 && (l[0].equals("delete"))) {
            return CommandType.DELETE;
        }

        if (l.length == 2 && (l[0].equals("view"))) {
            return CommandType.VIEW;
        }

        if (l.length == 2 && (l[0].equals("mark"))) {
            return CommandType.MARK;
        }

        if (l.length == 2 && (l[0].equals("unmark"))) {
            return CommandType.UNMARK;
        }

        if (l.length >= 2 && (l[0].equals("todo"))) {
            return CommandType.TODO;
        }

        if (l.length >= 4 && (l[0].equals("deadline"))) {
            return CommandType.DEADLINE;
        }

        if (l.length >= 6 && (l[0].equals("event"))) {
            return CommandType.EVENT;
        }

        return CommandType.INVALID;
    }

}

package wooper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Parser class is responsible for parsing user input,
 * and returning the logic to the main program.
 */
public class Parser {
    protected BufferedReader br;

    /**
     * Enum for the valid command types
     */
    public enum CommandType {
        EXIT, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, VIEW, INVALID, FIND
    }

    public Parser() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Parses the user input to determine the command type.
     * @param action User input.
     * @return Returns the command type.
     */
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

        if (l.length >= 2 && (l[0].equals("find"))) {
            return CommandType.FIND;
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

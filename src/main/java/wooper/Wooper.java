package wooper;

/**
 * Main program class for the Wooper chatbot program.
 */
public class Wooper {
    protected CommandHandler cmd;
    protected Parser parser;

    /**
     * Each run of the program will have its own storage object, tasklist object,
     * as well as parser and ui objects
     */
    public Wooper() {
        this.cmd = new CommandHandler();
        this.parser = new Parser();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input
     * @return
     */
    public String getResponse(String input) {
        StringBuilder response = new StringBuilder();
        String[] l = input.split(" ");
        Parser.CommandType command = parser.parseCommand(input);
        assert command instanceof Parser.CommandType : "Command variable is not of type Parser.CommandType";

        switch (command) {
        case EXIT:
            cmd.handleExit();
            response.append("Goodbye! See you next time.");
            break;
        case LISTTASKS:
        case LISTNOTES:
            response.append(cmd.handleList(command));
            break;
        case DELTASK:
        case DELNOTE:
            response.append(cmd.handleDelete(l, command));
            break;
        case VIEW:
            response.append(cmd.handleViewOnDate(l));
            break;
        case FIND:
            response.append(cmd.handleFindTaskByKeyword(l));
            break;
        case MARK:
        case UNMARK:
            response.append(cmd.handleMarking(command, l));
            break;
        case NOTE:
            response.append(cmd.handleNewNote(l));
            break;
        case TODO:
            response.append(cmd.handleTodo(l));
            break;
        case DEADLINE:
            response.append(cmd.handleDeadline(l));
            break;
        case EVENT:
            response.append(cmd.handleEvent(l));
            break;
        default:
            response.append("Invalid command.");
            break;
        }
        cmd.saveAllInfo();
        return response.toString();
    }
}

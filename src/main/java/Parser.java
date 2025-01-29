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

    public boolean isExit(String action) {
        return action.equals("exit");
    }

    public boolean isList(String action) {
        return action.equals("list");
    }
    
}

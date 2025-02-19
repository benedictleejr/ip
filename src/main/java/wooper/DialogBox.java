package wooper;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's
 * face and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    private static final String OPENING_MESSAGE = """
            Hello! I'm Wooper, your personal chatbot. I can handle tasks and notes!
            How can I help you today?

            Valid Commands:
            1. View tasks/notes:
                a. list tasks - views all tasks
                b. list notes - views all notes
                c. view <date> - views tasks due on a specific date
                d. find <keyword> - finds tasks with a specific keyword

            2. Add tasks:
                a. todo <task description>
                b. deadline <task description> /by <due date> <due time>
                c. event <task description> /from <start date> <start time> /to <end date> <end time>
                NOTE: Date and time should be in the format YYYY-MM-DD HH:MM

            3. Add notes:
                a. note <title> /content <content>

            4. Mark/unmark tasks as done:
                a. mark <task number>
                b. unmark <task number>

            5. Delete tasks/notes:
                a. deltask <task number>
                b. delnote <note number>

            6. Type 'exit' to exit
            """;

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the
     * right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Gets the opening message for wooper program and formats it nicely to be
     * displayed
     *
     * @param img image to be displayed as wooper bot
     * @return DialogBox to be appended to display
     */
    public static DialogBox getOpeningMessage(Image img) {
        var db = new DialogBox(OPENING_MESSAGE, img);
        db.flip();
        return db;
    }

    /**
     * Converts user text and image into dialogbox to be displayed
     *
     * @param text text input from user
     * @param img  user's image
     * @return DialogBox to be displayed
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Converts output from program into dialogbox to be displayed
     *
     * @param text output text from program
     * @param img  wooper bot's image
     * @return DialogBox to be displayed
     */
    public static DialogBox getWooperDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

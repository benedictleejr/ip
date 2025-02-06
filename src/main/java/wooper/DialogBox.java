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
            Hello! I'm Wooper, your personal tasklist chatbot.
            How can I help you today?

            Valid Commands:
            1. Add tasks:
                a. todo <task description>
                b. deadline <task description> /by <due date> <due time>
                c. event <task description> /from <start date> <start time> /to <end date> <end time>
                NOTE: Date and time should be in the format YYYY-MM-DD HH:MM
            2. View tasks:
                a. list - views all tasks
                b. view <date> - views tasks due on a specific date
                c. find <keyword> - finds tasks with a specific keyword
            3. Mark/unmark tasks as done:
                a. mark <task number>
                b. unmark <task number>
            4. Delete tasks:
                a. delete <task number>
            5. Type 'exit' to exit
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

    public static DialogBox getOpeningMessage(Image img) {
        var db = new DialogBox(OPENING_MESSAGE, img);
        db.flip();
        return db;
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getWooperDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

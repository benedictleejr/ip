package wooper;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Wooper wooper;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/quagsire.png"));
    private Image wooperImage = new Image(this.getClass().getResourceAsStream("/images/wooper.png"));

    /**
     * Initializes the GUI
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getOpeningMessage(wooperImage));
    }

    /** Injects the Wooper instance */
    public void setWooper(Wooper w) {
        wooper = w;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Wooper's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = wooper.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getWooperDialog(response, wooperImage));
        userInput.clear();

        if (response.equalsIgnoreCase("Goodbye! See you next time.")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}

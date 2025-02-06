package wooper;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Wooper using FXML.
 */
public class Main extends Application {

    private Wooper wooper = new Wooper();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            fxmlLoader.<MainWindow>getController().setWooper(wooper); // inject the Wooper instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

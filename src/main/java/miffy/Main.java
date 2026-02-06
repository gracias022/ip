package miffy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Entry point for the Miffy JavaFX application.
 * <p>
 * Sets up the primary stage and initializes the main UI layout,
 * including the scrollable dialog container, user input field,
 * and send button for interacting with the chatbot.
 */
public class Main extends Application {

    private Miffy miffy = new Miffy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setMiffy(miffy); // inject the Miffy instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



package miffy;

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

    private Miffy miffy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image miffyImage = new Image(this.getClass().getResourceAsStream("/images/Miffy.png"));

    /**
     * Initializes the controller. Sets up a listener on the {@code dialogContainer}
     * height property to ensure the {@code scrollPane} automatically scrolls to the
     * bottom whenever new messages are added and the layout is updated.
     */
    @FXML
    public void initialize() {
        // This listener triggers whenever the height of the container changes (new messages)
        dialogContainer.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            scrollPane.setVvalue(1.0);
        });
    }

    /** Injects the Duke instance */
    public void setMiffy(Miffy m) {
        miffy = m;

        String welcome = miffy.getWelcomeMessage();

        dialogContainer.getChildren().add(
                DialogBox.getMiffyDialog(welcome, miffyImage, "Welcome")
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = miffy.getResponse(input);
        String commandType = miffy.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMiffyDialog(response, miffyImage, commandType)
        );
        userInput.clear();

        if (miffy.isExit()) {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> Platform.exit());
            pause.play();
        }
    }
}


package miffy;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A custom JavaFX control representing a single dialog message in the Miffy chatbot interface.
 * <p>
 * Displays a user or bot message alongside an associated profile image, laid out horizontally
 * using an {@link HBox}.
 */
public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    /**
     * Constructs a new dialog box containing the specified message text and profile image.
     *
     * @param s the text content of the dialog message
     * @param i the profile image to display alongside the message
     */
    public DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        //Styling the dialog box
        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }
}


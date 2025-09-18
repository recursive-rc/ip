package ittybotty.gui;

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

public class DialogBox extends HBox {
    private static final Image userImage = GuiUtils.getImage("weber.jpg");
    private static final Image botImage = GuiUtils.getImage("durkheim.jpg");

    @FXML
    private ImageView speakerPicture;
    @FXML
    private Label messageLabel;

    private DialogBox(String messageText, Image image) {
        try {
            FXMLLoader fxmlLoader = GuiUtils.getFxmlLoader("DialogBox.fxml");
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace(); // TODO: better error handling
        }

        this.messageLabel.setText(messageText);
        this.speakerPicture.setImage(image);
    }

    /**
     * Constructs a GUI dialog box for user text.
     * @param messageText Message sent by user.
     */
    public static DialogBox getUserDialog(String messageText) {
        return new DialogBox(messageText, DialogBox.userImage);
    }

    /**
     * Constructs a GUI dialog box for IttyBotty text.
     * @param messageText Message sent by IttyBotty.
     */
    public static DialogBox getBotDialog(String messageText) {
        DialogBox botDialog = new DialogBox(messageText, DialogBox.botImage);
        botDialog.flip();
        return botDialog;
    }

    private void flip() {
        ObservableList<Node> nodes =
                FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(nodes);
        this.getChildren().setAll(nodes);
        this.setAlignment(Pos.TOP_LEFT);
    }
}

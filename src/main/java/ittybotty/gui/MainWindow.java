package ittybotty.gui;


import static java.util.Objects.requireNonNull;

import ittybotty.IttyBotty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane dialogPane;
    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInputField;
    @FXML
    private Button sendButton;

    private IttyBotty bot;

    @FXML
    public void initialize() {
        this.dialogPane.vvalueProperty().bind(
                this.dialogContainer.heightProperty());
    }

    public void setBot(IttyBotty bot) {
        requireNonNull(bot);
        this.bot = bot;
        this.bot.setMainWindow(this);
    }

    @FXML
    private void handleUserInput() {
        String userInput = this.userInputField.getText();
        this.sendUserMessage(userInput);
        this.userInputField.clear();

        String botOutput = this.bot.handleInputAndGetOutput(userInput);
        this.sendBotMessage(botOutput);

        if (this.bot.isToExit()) {
            // TODO: add short delay before exiting, so user can see exit message
            Platform.exit();
        }
        // TODO: consider if this violates if SLAP and if it's worthwhile to change
    }

    private void sendUserMessage(String userMessage) {
        requireNonNull(this.dialogContainer);
        requireNonNull(userMessage);
        this.dialogContainer.getChildren().add(
                DialogBox.getUserDialog(userMessage));
    }

    /**
     * Shows message from bot to user in GUI.
     * @param botMessage Message for bot to send.
     */
    public void sendBotMessage(String botMessage) {
        requireNonNull(this.dialogContainer);
        requireNonNull(botMessage);
        this.dialogContainer.getChildren().add(
                DialogBox.getBotDialog(botMessage));
    }
}

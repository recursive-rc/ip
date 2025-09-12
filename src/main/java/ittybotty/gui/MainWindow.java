package ittybotty.gui;


import static java.util.Objects.requireNonNull;

import ittybotty.IttyBotty;
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
        // TODO: consider if this violates if SLAP and if it's worthwhile to change
    }

    private void sendUserMessage(String userMessage) {
        this.dialogContainer.getChildren().add(
                DialogBox.getUserDialog(userMessage));
    }

    private void sendBotMessage(String botMessage) {
        this.dialogContainer.getChildren().add(
                DialogBox.getBotDialog(botMessage));
    }
}

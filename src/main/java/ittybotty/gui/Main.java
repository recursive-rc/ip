package ittybotty.gui;

import java.io.IOException;

import ittybotty.IttyBotty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private final IttyBotty bot = new IttyBotty();

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "/view/MainWindow.fxml"));

        try {
            AnchorPane mainLayout = fxmlLoader.load();
            Scene scene = new Scene(mainLayout);
            stage.setTitle("IttyBotty");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBot(this.bot);
            stage.show();
            this.bot.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

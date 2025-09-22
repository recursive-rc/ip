package ittybotty;

import ittybotty.gui.Main;
import javafx.application.Application;

/**
 * Driver class to work around classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}

package ittybotty.gui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

/**
 * Provides utility function for GUI uses.
 *
 * <p>All functions are intentionally package-private because they are
 * only intended for use within the ittybotty.gui package.</p>
 */
public class GuiUtils {

    /**
     * Returns the JavaFX Image object for the given file name.
     * @param imageFileName Name of image file, including the file
     *                      extension, such as ".png", but excluding
     *                      "/resources/images/".
     * @throws NullPointerException If the given file name is null,
     *                              or if the image file cannot be
     *                              found or opened.
     */
    static Image getImage(String imageFileName) {
        InputStream imageStream = GuiUtils.class.getResourceAsStream(
                "/images/" + imageFileName);
        requireNonNull(imageStream);
        return new Image(imageStream);
    }

    /**
     * Retrieves parent node for a scene from a given FXML file name.
     * @param fxmlFileName Name of FXML file, including the ".fxml"
     *                     file extension, but excluding
     *                     "/resources/view/".
     * @throws IOException If error occurs when loading parent node.
     */
    static FXMLLoader getFxmlLoader(String fxmlFileName) throws IOException {
        return new FXMLLoader(GuiUtils.class.getResource(
                "/view/" + fxmlFileName));
    }
}

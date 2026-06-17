package horseracer.screens.utils;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * Utility class meant to create text objects quickly
 *
 */
public class TextLoader {

  /**
   * Create a text object for javafx and return it.
   *
   * @param display_text text that will be displayed in the text object
   * @param class_name   name of the class that the text will be styled with
   */
  public static Label create(String display_text, String class_name) {
    Label t = new Label(display_text);
    t.getStyleClass().add(class_name);

    return t;
  }
}

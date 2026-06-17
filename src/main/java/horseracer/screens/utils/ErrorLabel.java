package horseracer.screens.utils;

import javafx.scene.control.Label;

public class ErrorLabel {
  /**
   * Create an error label
   *
   * @param text
   * @return Label object
   */
  public static Label create(String text) {
    Label lb = new Label(text);
    lb.getStyleClass().add("error-label");

    return lb;
  }
}

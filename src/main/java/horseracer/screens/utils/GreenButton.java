package horseracer.screens.utils;

import javafx.scene.control.Button;

public class GreenButton {

  /**
   * Create a green button
   * 
   * @param text the text inside the button
   * @return Button object
   */
  public static Button create(String text) {
    Button button = new Button(text);
    button.setPrefWidth(260);
    button.setPrefHeight(54);
    button.getStyleClass().add("green-button");
    return button;
  }
}

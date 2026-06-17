package horseracer.screens.utils;

import javafx.scene.control.Button;

public class RedButton {

  public static Button create(String text) {
    Button button = new Button(text);
    button.setPrefWidth(260);
    button.setPrefHeight(54);
    button.getStyleClass().add("red-button");
    return button;
  }
}

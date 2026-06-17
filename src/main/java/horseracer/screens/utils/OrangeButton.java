package horseracer.screens.utils;

import javafx.scene.control.Button;

public class OrangeButton {

  public static Button create(String text) {
    Button button = new Button(text);
    button.setPrefWidth(260);
    button.setPrefHeight(50);
    button.getStyleClass().add("orange-button");
    return button;
  }
}

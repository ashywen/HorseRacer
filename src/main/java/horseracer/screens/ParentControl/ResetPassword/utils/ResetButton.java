package horseracer.screens.ParentControl.ResetPassword.utils;

import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.utils.OrangeButton;
import horseracer.screens.utils.RedButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ResetButton {
  public static void action(User user, DataManager data_manager, StackPane root, String password, PasswordField in1,
      PasswordField in2, VBox parent) {
    // Create focus overlay
    StackPane overlay = new StackPane();
    overlay.setMinSize(5000, 5000);
    overlay.getStyleClass().add("overlay");

    // Create actual box to display text
    VBox popup = new VBox(15);
    popup.getStyleClass().add("body_overlay");
    popup.setMinSize(600, 400);
    popup.setMaxSize(600, 400);
    popup.setPadding(new Insets(10, 10, 10, 10));

    // Description
    Label text = new Label("You are about to reset " + user.getUsername() + "'s password. Are you sure?");
    text.getStyleClass().add("warning-label");
    text.setWrapText(true);
    text.setTextAlignment(TextAlignment.CENTER);

    // Note
    Label note = new Label("Note: The password used to log into this account will be changed! Click yes to proceed");
    note.getStyleClass().add("note-label");
    note.setWrapText(true);
    note.setTextAlignment(TextAlignment.CENTER);

    // Confirm button
    Button confirm = RedButton.create("RESET PASSWORD");

    // Success label
    Label success = new Label("Password Successfully Reset");
    success.getStyleClass().add("success-label");

    // Reset on click
    confirm.setOnAction(click -> {

      // Reset and update
      data_manager.resetPassword(user, password);
      root.getChildren().remove(overlay);

      in1.setText("");
      in2.setText("");

      parent.getChildren().add(success);

    });

    // Cancel button
    Button close = OrangeButton.create("BACK");

    // Close the overlay on click
    close.setOnAction(click -> {
      root.getChildren().remove(overlay);
      parent.getChildren().remove(parent.getChildren().size() - 1);
    });

    // Center items
    StackPane.setAlignment(overlay, Pos.CENTER);
    popup.setAlignment(Pos.CENTER);

    popup.getChildren().addAll(text, note, confirm, close);
    overlay.getChildren().addAll(popup);
    root.getChildren().addAll(overlay);
    root.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        root.getChildren().remove(overlay);
      }
    });
  }
}

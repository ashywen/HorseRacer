package horseracer.screens.ParentControl.StudentControl.utils;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.utils.OrangeButton;
import horseracer.screens.utils.RedButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ResetButton {

  public static void resetButton(User user, StackPane root, VBox parent, GridPane grid, ScreenManager manager,
      DataManager data_manager) {

    // Create focus overlay
    StackPane overlay = new StackPane();
    // overlay.setMinSize(5000, 5000);
    overlay.getStyleClass().add("overlay");

    // Create actual box to display text
    VBox popup = new VBox(10);
    popup.getStyleClass().add("body_overlay");
    popup.setMinSize(600, 400);
    popup.setMaxSize(600, 400);
    popup.setPadding(new Insets(10, 10, 10, 10));

    // Description
    Label text = new Label("You are about to reset " + user.getUsername() + "'s stats. Are you sure?");
    text.getStyleClass().add("warning-label");
    text.setWrapText(true);
    text.setTextAlignment(TextAlignment.CENTER);

    // Note
    Label note = new Label("Note: All of your stats will be reset! Click yes to proceed");
    note.getStyleClass().add("note-label");
    note.setWrapText(true);
    note.setTextAlignment(TextAlignment.CENTER);

    // Confirm button
    Button confirm = RedButton.create("RESET STATS");

    // Success
    Label success = new Label("Stats Successfully Reset");
    success.getStyleClass().add("success-label");

    // Reset on click
    confirm.setOnAction(click -> {
      // Reset and update
      user.getStats().reset();
      data_manager.updateStats(user, user.getStats());
      data_manager.getLeaderboard().resetUser(user.getUsername());

      // Remove old grid
      root.getChildren().removeAll(overlay);
      parent.getChildren().removeAll(grid);

      // Add new Grid
      parent.getChildren().add(1, StudentData.buildGrid(user, manager, data_manager));
      parent.getChildren().add(success);

    });

    // Cancel button
    Button close = OrangeButton.create("CANCEL");

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

package horseracer.screens.ParentControl.utils;

import horseracer.data.DataManager;
import horseracer.data.exceptions.LeaderboardException;
import horseracer.screens.utils.OrangeButton;
import horseracer.screens.utils.RedButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ResetButton {
  public static void action(DataManager data_manager, StackPane root) {
    // Create focus overlay
    StackPane overlay = new StackPane();
    overlay.setMinSize(5000, 5000);
    overlay.getStyleClass().add("overlay");

    // Create actual box to display text
    VBox popup = new VBox(10);
    popup.getStyleClass().add("body_overlay");
    popup.setPrefSize(600, 400);
    popup.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    popup.setPadding(new Insets(10, 10, 10, 10));

    // Description
    Label text = new Label("You are about to reset the global leadboard! Are you sure?");
    text.getStyleClass().add("warning-label");
    text.setWrapText(true);
    text.setTextAlignment(TextAlignment.CENTER);

    // Note
    Label note = new Label("Note: All existing leaderboard scores will be removed! Click yes to proceed");
    note.getStyleClass().add("note-label");
    note.setWrapText(true);
    note.setTextAlignment(TextAlignment.CENTER);

    // Confirm button
    Button confirm = RedButton.create("RESET LEADERBOARD");

    // Reset on click
    confirm.setOnAction(click -> {

      // Reset and update
      data_manager.getLeaderboard().resetLeaderboard();
      try {
        data_manager.getLeaderboard().saveLeaderboard();
        root.getChildren().remove(overlay);

      } catch (LeaderboardException err) {

      }

    });

    // Cancel button
    Button close = OrangeButton.create("CANCEL");

    // Close the overlay on click
    close.setOnAction(click -> {
      root.getChildren().remove(overlay);
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

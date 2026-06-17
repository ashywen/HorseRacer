package horseracer.screens.GameplayScreen.utils;

import horseracer.app.ScreenManager;
import horseracer.model.Level;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EndlevelOverlay {

  /**
   * Pre-setup overlay for end level
   */
  public void create(StackPane root, ScreenManager screen_manager, Level level) {
    String overlayTitle = "LEVEL COMPLETE";
    if (level.getNumber() == 3) {
      overlayTitle = "GAME FINISHED";
    }
    Label endLevelTitle = new Label(overlayTitle);
    endLevelTitle.getStyleClass().add("overlayTitle");

    Label endLevelGameTitle = new Label("Horse Racer");
    endLevelGameTitle.getStyleClass().add("overlaySubtitle");

    Label stats_saved = new Label("Stats were saved!");
    stats_saved.getStyleClass().add("overlaySubtitle");

    Button nextLevelButton = new Button("Next Level");
    nextLevelButton.getStyleClass().add("overlayButton");
    nextLevelButton.setOnAction(e -> {
      screen_manager.showGameplay(level.nextLevel());

    });
    if (level.getNumber() == 3) {
      nextLevelButton.setVisible(false);
    }
    Button replayLevelButton = new Button("Replay Level");
    replayLevelButton.getStyleClass().add("overlayButton");
    replayLevelButton.setOnAction(e -> screen_manager.showGameplay(level));

    Button playerScreenButton = new Button("Player Screen");
    playerScreenButton.getStyleClass().add("overlayButton");
    playerScreenButton.setOnAction(e -> screen_manager.showPlayerScreen());

    // Button mainMenuButton = new Button("Return to Main Menu");
    // mainMenuButton.getStyleClass().add("overlayButton");
    // mainMenuButton.setOnAction(e -> screen_manager());

    VBox endLevelOverlay = new VBox(
        endLevelTitle,
        endLevelGameTitle,
        stats_saved,
        nextLevelButton,
        replayLevelButton,
        playerScreenButton
    // mainMenuButton
    );
    endLevelOverlay.getStyleClass().add("overlay");

    root.getChildren().add(endLevelOverlay);
  }
}

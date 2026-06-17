package horseracer.screens.GameplayScreen.utils;

import horseracer.app.ScreenManager;
import horseracer.model.Level;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameoverOverlay {

  /**
   * Pre-setup overlay for game over
   */
  public void create(StackPane root, Label scoreLabel, ScreenManager screen_manager, Level level) {
    // creates the game over screen overlay
    // Text
    Label gameOverTitle = new Label("GAME OVER"); // game over text
    gameOverTitle.getStyleClass().add("overlayTitle");
    Label gameOverGameTitle = new Label("Horse Racer"); // name of game text
    gameOverGameTitle.getStyleClass().add("overlaySubtitle");
    scoreLabel.getStyleClass().add("overlaySubtitle");

    Label stats_saved = new Label("Stats were saved!");
    stats_saved.getStyleClass().add("overlaySubtitle");

    // Buttons
    Button menuButton = new Button("Player Screen"); // main menu button
    menuButton.getStyleClass().add("overlayButton");
    menuButton.setOnAction(e -> screen_manager.showPlayerScreen());

    Button gameRestartButton = new Button("Restart Game"); // restart game button
    gameRestartButton.getStyleClass().add("overlayButton");
    gameRestartButton.setOnAction(e -> screen_manager.showGameplay(Level.easy())); // restarts the level when clicked
                                                                                   //
    Button level_restart = new Button("Restart Level"); // restart game button
    level_restart.getStyleClass().add("overlayButton");
    Level restart_level;
    if (level.getNumber() == 1) {
      restart_level = Level.easy();
    } else if (level.getNumber() == 2) {
      restart_level = Level.medium();
    } else {
      restart_level = Level.hard();
    }
    level_restart.setOnAction(e -> screen_manager.showGameplay(restart_level)); // restarts the level when clicked

    // Box for all components
    VBox gameOverOverlay = new VBox(gameOverTitle, gameOverGameTitle, scoreLabel, stats_saved, menuButton,
        level_restart,
        gameRestartButton);
    gameOverOverlay.getStyleClass().add("overlay");

    // Add to root
    root.getChildren().add(gameOverOverlay);
  }
}

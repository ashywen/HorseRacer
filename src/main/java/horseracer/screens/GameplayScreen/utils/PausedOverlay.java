package horseracer.screens.GameplayScreen.utils;

import horseracer.app.ScreenManager;
import horseracer.model.Level;
import horseracer.screens.GameplayScreen.GamePlayScreen;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PausedOverlay {

  /**
   * Pre-setup overlay for pause
   */
  public void create(StackPane root, ScreenManager screen_manager, Level level, GamePlayScreen gameplay_screen) {
    // creates the paused game overlay
    Label pausedText = new Label("Paused"); // creates the large paused text
    pausedText.getStyleClass().add("overlayTitle");

    Label pausedGameTitle = new Label("Horse Racer"); // game title text
    pausedGameTitle.getStyleClass().add("overlaySubtitle");

    Button mainMenuButton = new Button("Player Screen"); // creates the main menu button
    mainMenuButton.getStyleClass().add("overlayButton");
    mainMenuButton.setOnAction(e -> screen_manager.showPlayerScreen()); // goes back to main menu when clicked

    Button gameRestartButton = new Button("Restart Game"); // restart game button
    gameRestartButton.getStyleClass().add("overlayButton");
    gameRestartButton.setOnAction(e -> screen_manager.showGameplay(Level.easy())); // restarts the level when clicked
                                                                                   //
    Button restartLevelButton = new Button("Restart Level"); // creates the restart level button
    restartLevelButton.getStyleClass().add("overlayButton");
    restartLevelButton.setOnAction(e -> screen_manager.showGameplay(level)); // restarts the level when clicked

    Button continueButton = new Button("Continue"); // continue game button
    continueButton.getStyleClass().add("overlayButton");

    VBox pauseOverlay = new VBox(pausedText, pausedGameTitle, mainMenuButton, restartLevelButton, gameRestartButton,
        continueButton);
    pauseOverlay.getStyleClass().add("overlay");

    root.getChildren().add(pauseOverlay);

    pauseOverlay.setFocusTraversable(true);
    pauseOverlay.requestFocus();
    pauseOverlay.setOnKeyPressed(null);
    pauseOverlay.setOnKeyPressed(e -> {
      if (e.getCode().equals(KeyCode.ENTER)) {
        root.getChildren().remove(pauseOverlay);
        gameplay_screen.onContinue();
      }
    });

    // Button action
    continueButton.setOnAction(e -> {
      root.getChildren().remove(pauseOverlay);
      gameplay_screen.onContinue();
    });
  }
}

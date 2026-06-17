package horseracer.screens.GameplayScreen.utils;

import horseracer.model.PowerUpType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PowerupHUD {

  /**
   * Display PowerUp message and icon
   */
  private void showPowerUpHud(PowerUpType type, Label powerUpMessage, ImageView powerUpIcon) {
    String message;
    String path;

    switch (type) {
      case DOUBLE_POINTS:
        message = "Double Points Activated!";
        path = "/images/double-points-icon.png";
        break;
      case EXTRA_LIFE:
        message = "Extra Life Activated!";
        path = "/images/heart-life-icon.png";
        break;
      case SLOW_OBSTACLE:
        message = "Slow Obstacle Activated!";
        path = "/images/time-obstacle-icon.png";
        break;
      default:
        message = "Power-Up Activated!";
        path = "/images/doublePointsIcon.png";
    }

    Image icon = new Image(getClass().getResource(path).toExternalForm());
    powerUpIcon.setImage(icon);
    powerUpMessage.setText(message);
  }

  /**
   * Generate PowerUp UI
   */
  public HBox create(PowerUpType type) {
    ImageView powerUpIcon = new ImageView();
    powerUpIcon.setFitWidth(28);
    powerUpIcon.setFitHeight(28);
    powerUpIcon.setPreserveRatio(true);

    Label powerUpMessage = new Label("");
    powerUpMessage.getStyleClass().add("hudLabel");
    powerUpMessage.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

    showPowerUpHud(type, powerUpMessage, powerUpIcon);

    HBox powerUpHud = new HBox(8, powerUpIcon, powerUpMessage);
    powerUpHud.setAlignment(Pos.CENTER_RIGHT);
    powerUpHud.setPadding(new Insets(6, 12, 6, 12));
    powerUpHud.setStyle(
        "-fx-background-color: rgba(0,0,0,0.55);" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: gold;" +
            "-fx-border-radius: 10;");

    return powerUpHud;
  }
}

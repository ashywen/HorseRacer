package horseracer.screens;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Represents the visual horse character in the game.
 * <p>
 * This class handles rendering the horse, positioning it on the screen,
 * and providing basic actions such as jumping and landing. It also
 * exposes collision-related information for game logic.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class HorseView {

  /** The root container for the horse */
  private final Pane root;

  /** The image view displaying the horse */
  private final ImageView horseView;

  /** The x-position of the horse */
  private double x;

  /** The y-position of the horse */
  private double y;

  /**
   * Creates a HorseView and positions it relative to the scene.
   *
   * @param scene the scene used to bind the horse's position
   */
  public HorseView(StackPane scene) {
    root = new Pane();

    Image image = new Image(getClass().getResource("/images/Horse.png").toExternalForm(), true);
    horseView = new ImageView(image);
    horseView.setFitWidth(150);
    horseView.setPreserveRatio(true);

    root.getChildren().add(horseView);
    root.layoutXProperty().bind(scene.widthProperty().multiply(0.05));
    root.layoutYProperty().bind(scene.heightProperty().multiply(0.65));
  }

  /**
   * Moves the horse upward to simulate a jump.
   */
  public void jump() {
    root.setLayoutY(y - 80);
  }

  /**
   * Returns the horse to its original vertical position.
   */
  public void land() {
    root.setLayoutY(y);
  }

  /**
   * Returns the root node of the horse.
   *
   * @return the root pane
   */
  public Pane getRoot() {
    return root;
  }

  /**
   * Returns the node used for collision detection.
   *
   * @return the collision node
   */
  public Node getCollisionNode() {
    return horseView;
  }

  /**
   * Returns the bounds of the horse for collision checking.
   *
   * @return the bounds of the horse
   */
  public Bounds getBounds() {
    return horseView.getBoundsInParent();
  }
}
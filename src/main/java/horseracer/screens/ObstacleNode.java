package horseracer.screens;

import horseracer.model.Word;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an obstacle displayed during gameplay.
 * <p>
 * Each obstacle contains a fence graphic and a word shown above it.
 * The obstacle can be moved across the screen, checked for collisions,
 * and updated visually as the player types.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class ObstacleNode {

  /** The root container for the obstacle */
  private final Pane root;

  /** The node used to display the fence obstacle */
  private final Node fenceNode;

  /** The text flow used to display the word above the obstacle */
  private final TextFlow wordFlow;

  /** The list of letter nodes used for styling typed characters */
  private final List<Text> letters;

  /** The word associated with this obstacle */
  private final Word word;

  /** The x-position of the obstacle */
  private double x;

  /** The y-position of the obstacle */
  private double y;

  /** The current relative horizontal position of the obstacle */
  private double curr_pos = 1;

  /** The scene container used for positioning calculations */
  private StackPane scene;

  /**
   * Creates an obstacle node with a word, initial position, and scene reference.
   * <p>
   * The obstacle displays the given word above a fence image.
   * If the image cannot be loaded, a fallback rectangle is used instead.
   *
   * @param word the word associated with the obstacle
   * @param x the initial x-position
   * @param y the initial y-position
   * @param scene the scene used for layout calculations
   */
  public ObstacleNode(Word word, double x, double y, StackPane scene) {
    this.word = word;
    this.x = x;
    this.y = y;
    this.letters = new ArrayList<>();
    this.scene = scene;

    root = new Pane();
    root.setPrefSize(220, 170);
    root.getStylesheets().add(getClass().getResource("/style/obstacle.css").toExternalForm());

    wordFlow = new TextFlow();
    wordFlow.setLayoutX(20);
    wordFlow.setLayoutY(10);
    wordFlow.getStyleClass().add("word-flow");

    String wordText = word.getText();
    for (int i = 0; i < wordText.length(); i++) {
      Text t = new Text(String.valueOf(wordText.charAt(i)));
      t.setFill(Color.BLACK);
      t.setFont(Font.font("Consolas", FontWeight.BOLD, 28));
      letters.add(t);
      wordFlow.getChildren().add(t);
    }

    Node builtFence;
    try {
      Image image = new Image(getClass().getResource("/images/obstacle-fence.png").toExternalForm());
      ImageView fenceView = new ImageView(image);
      fenceView.setFitWidth(120);
      fenceView.setPreserveRatio(true);
      fenceView.setLayoutX(40);
      fenceView.setLayoutY(75);

      builtFence = fenceView;
    } catch (Exception e) {
      System.out.println("Obstacle image NOT found. Using fallback rectangle.");

      Rectangle fallbackFence = new Rectangle(120, 60);
      fallbackFence.setFill(Color.BLUE);
      fallbackFence.setStroke(Color.BLACK);
      fallbackFence.setStrokeWidth(3);
      fallbackFence.setLayoutX(40);
      fallbackFence.setLayoutY(85);

      builtFence = fallbackFence;
    }

    this.fenceNode = builtFence;

    root.getChildren().addAll(wordFlow, fenceNode);
    setPosition(x, y);
  }

  /**
   * Sets the obstacle position.
   *
   * @param x the new x-position
   * @param y the new y-position
   */
  public void setPosition(double x, double y) {
    this.x = x;
    this.y = y;
    root.setLayoutX(x);
    root.setLayoutY(y);
  }

  /**
   * Moves the obstacle left across the screen.
   *
   * @param dx the amount to move left
   */
  public void moveLeft(double dx) {
    this.curr_pos -= dx;
    double curr_y = scene.getHeight() * 0.6;
    double new_pos = scene.getWidth() * (curr_pos);
    setPosition(new_pos, curr_y);
  }

  /**
   * Resets the word text color to its default style.
   */
  public void resetWordStyle() {
    for (Text t : letters) {
      t.setFill(Color.BLACK);
    }
  }

  /**
   * Checks whether the obstacle has moved off the screen.
   *
   * @return true if the obstacle is off-screen, false otherwise
   */
  public boolean isOffScreen() {
    return x + root.getPrefWidth() < 0;
  }

  /**
   * Returns the word associated with this obstacle.
   *
   * @return the word
   */
  public Word getWord() {
    return word;
  }

  /**
   * Returns the root node of the obstacle.
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
    return fenceNode;
  }

  /**
   * Returns the bounds of the obstacle for collision checking.
   *
   * @return the obstacle bounds
   */
  public Bounds getBounds() {
    return fenceNode.getBoundsInParent();
  }

  /**
   * Returns the x-position of the obstacle.
   *
   * @return the x-position
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the y-position of the obstacle.
   *
   * @return the y-position
   */
  public double getY() {
    return y;
  }

  /**
   * Returns the list of text nodes representing the word letters.
   *
   * @return the list of letters
   */
  public List<Text> getLetters() {
    return letters;
  }
}
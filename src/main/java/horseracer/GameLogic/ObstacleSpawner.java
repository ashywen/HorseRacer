package horseracer.GameLogic;

import horseracer.model.Difficulty;
import horseracer.model.Level;
import horseracer.model.Word;
import horseracer.screens.GameplayScreen.*;
import horseracer.screens.ObstacleNode;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Obstacle spawner
 * <br>
 * <br>
 * Spawn and remove obstacle
 *
 * @version 1.0.0
 * @author Tianrui Xu
 */
public class ObstacleSpawner {
  /** ArrayList for all generated obstacles */
  private ArrayList<ObstacleNode> obstacleNodes;
  /** ArrayList for all on screen obstacles */
  private ArrayList<ObstacleNode> activeObstacles;

  /** width of the windows */

  /**
   * Instantiates a new Obstacle spawner.
   *
   * @param fileName the file name
   */
  public ObstacleSpawner(String fileName, double width, StackPane root) {

    this.obstacleNodes = new ArrayList<>();
    this.activeObstacles = new ArrayList<>();
    WordBank wb = new WordBank(fileName);
    for (int i = 0; i < 50; i++) {
      this.obstacleNodes.add(
          new ObstacleNode(
              wb.getWord(),
              // new Word("test", Difficulty.EASY, 50),
              root.getWidth(),
              545,
              root));
      // initialize text color
      ObstacleNode node = obstacleNodes.getLast();
      for (int j = 0; j < node.getLetters().size(); j++) {
        Text t = node.getLetters().get(j);
        t.setFill(Color.BLACK);
      }
    }
  }

  /**
   * Spawn obstacles depends on the number of obstacles
   */
  public void spawnObstacle(GamePlayScreen gamePlayScreen) {
    if (obstacleNodes.isEmpty()) {
      throw new IllegalStateException("No generated obstacles found");
    }
    gamePlayScreen.setOnRunable(() -> {
      if (obstacleNodes.isEmpty()) {
        throw new IllegalStateException("No generated obstacles found or no obstacles left to spawn");
      }
      ObstacleNode node = obstacleNodes.getFirst();
      gamePlayScreen.getRacePane().getChildren().add(node.getRoot());
      this.activeObstacles.add(node);
      obstacleNodes.removeFirst();
    });
  }

  /**
   * Update obstacle text ui.
   *
   * @param valid if the char typed is valid
   * @param index the index in the word
   */
  public void updateObstacleTextUI(boolean valid, int index) {
      ObstacleNode node = null;
      try {
          node = this.activeObstacles.getFirst();
      } catch (Exception e) {
          return;
      }

      // TODO: update UI on text on top of the obstacle
    if (valid) {
      Text t = node.getLetters().get(index);
      t.setFill(Color.GREEN);
    } else {
      Text t = node.getLetters().get(index + 1);
      t.setFill(Color.RED);
    }
  }

  /**
   * Remove the first obstacle if exist
   * 
   * @param pane the racePane (game area)
   * @return the point associate with that obstacle
   */
  public int removeObstacle(BorderPane pane) {
    if (activeObstacles.isEmpty()) {
      return 0;
    }
    pane.getChildren().remove(activeObstacles.getFirst().getRoot());
    int point = this.activeObstacles.getFirst().getWord().getPoints();
    this.activeObstacles.removeFirst();
    return point;
  }

  /**
   * Get the first obstacle on the screen
   * 
   * @return first ObstacleNode
   */
  public ObstacleNode getFocusedObstacle() {
    if (activeObstacles.isEmpty() && obstacleNodes.isEmpty()) {
      return null;
    }
    if (activeObstacles.isEmpty()) {
      return obstacleNodes.getFirst();
    } else {
      return activeObstacles.getFirst();
    }
  }

  /**
   * Method to move each obstacle on each frame
   * 
   * @param l level of the game
   */
  public void moveObstacle(Level l) {
    for (ObstacleNode node : activeObstacles) {
      node.moveLeft(l.getObstacleSpeed());
    }
  }
}

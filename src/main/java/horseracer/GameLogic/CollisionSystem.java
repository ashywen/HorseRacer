package horseracer.GameLogic;

import horseracer.screens.HorseView;
import horseracer.screens.ObstacleNode;
import javafx.geometry.Bounds;
import javafx.scene.layout.BorderPane;

/**
 * Handles collision detection and collision effects in the game.
 * <p>
 * This class determines whether the player (horse) collides with
 * obstacles and updates the game state accordingly.
 *
 * @author Tianrui Xu
 * @version 1.0.0
 */
public class CollisionSystem {

    /**
     * Checks if a collision occurs between the player and an obstacle.
     *
     * @param stationary the player (horse)
     * @param obstacle the obstacle
     * @return true if a collision occurs, false otherwise
     */
    public boolean checkCollision(HorseView stationary, ObstacleNode obstacle) {
        Bounds m = obstacle.getCollisionNode().localToScene(
                obstacle.getCollisionNode().getBoundsInLocal());
        Bounds s = stationary.getCollisionNode().localToScene(
                stationary.getCollisionNode().getBoundsInLocal());

        // Check if the bounding boxes intersect
        return s.intersects(m);
    }

    /**
     * Handles the effects of a collision.
     * <p>
     * This includes reducing the player's life and removing
     * the obstacle from the screen.
     *
     * @param lSystem the life system responsible for player lives
     * @param os the obstacle spawner managing obstacles
     * @param pane the pane containing the obstacle
     */
    public void handleCollision(LifeSystem lSystem, ObstacleSpawner os, BorderPane pane) {
        // Remove one life
        lSystem.loseLife();

        // Remove the obstacle
        os.removeObstacle(pane);
    }
}
package horseracer.model;

/**
 * Represents the different types of power-ups available in the game.
 * <p>
 * Each type provides a unique effect that helps the player during gameplay.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public enum PowerUpType {

    /** Doubles the points earned by the player */
    DOUBLE_POINTS,

    /** Grants the player an extra life */
    EXTRA_LIFE,

    /** Slows down obstacle movement */
    SLOW_OBSTACLE
}
package horseracer.GameLogic;

/**
 * Manages the player's lives in the game.
 * <p>
 * This class keeps track of the player's current lives,
 * allows lives to be gained or lost, and determines
 * whether the game is over.
 *
 * @author Tianrui Xu
 * @version 1.0.0
 */
public class LifeSystem {

    /** The maximum number of lives a player can have */
    private int maxLives;

    /** The current number of lives the player has */
    private int currentLives;

    /**
     * Creates a LifeSystem with the specified maximum lives.
     * <p>
     * The current lives are initialized to the maximum value.
     *
     * @param maxLives the maximum number of lives
     */
    public LifeSystem(int maxLives) {
        this.maxLives = maxLives;
        this.currentLives = maxLives;
    }

    /**
     * Decreases the player's lives by one.
     */
    public void loseLife() {
        currentLives--;
    }

    /**
     * Increases the player's lives by one.
     */
    public void gainLife() {
        currentLives++;
    }

    /**
     * Checks whether the game is over.
     *
     * @return true if the player has no lives remaining, false otherwise
     */
    public boolean isGameOver() {
        return currentLives == 0;
    }

    /**
     * Returns the current number of lives.
     *
     * @return the current lives
     */
    public int getCurrentLives() {
        return currentLives;
    }
}
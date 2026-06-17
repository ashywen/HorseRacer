package horseracer.model;

/**
 * Represents a power-up in the game.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class PowerUp {

    /** The type of power-up */
    private PowerUpType type;

    /** The duration of the power-up in seconds */
    private int durationSeconds;

    /**
     * Creates a PowerUp with the given type and duration.
     *
     * @param type the type of power-up
     * @param durationSeconds the duration in seconds
     */
    public PowerUp(PowerUpType type, int durationSeconds) {
        this.type = type;
        this.durationSeconds = durationSeconds;
    }

    /**
     * Returns the type of this power-up.
     *
     * @return the power-up type
     */
    public PowerUpType getType() {
        return type;
    }

    /**
     * Sets the type of this power-up.
     *
     * @param type the new power-up type
     */
    public void setType(PowerUpType type) {
        this.type = type;
    }

    /**
     * Returns the duration of this power-up.
     *
     * @return the duration in seconds
     */
    public int getDurationSeconds() {
        return durationSeconds;
    }

    /**
     * Sets the duration of this power-up.
     *
     * @param durationSeconds the new duration in seconds
     */
    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}
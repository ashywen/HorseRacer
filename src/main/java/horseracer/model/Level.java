package horseracer.model;

/**
 * Represents a game level in the typing game.
 * <p>
 * Each level has a number, duration, obstacle speed,
 * target score, and word bank file.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class Level {

    /** The level number */
    private int number;

    /** The duration of the level in seconds */
    private final int durationSeconds;

    /** The speed of obstacles in this level */
    private final double obstacleSpeed;

    /** The score required to complete the level */
    private final int targetScore;

    /** The file path for the word bank */
    private String wordBankPath;

    /**
     * Creates a Level with the given values.
     *
     * @param number the level number
     * @param durationSeconds the duration in seconds
     * @param obstacleSpeed the obstacle speed
     * @param targetScore the score needed to pass
     * @param wordBankPath the word bank file path
     */
    public Level(int number, int durationSeconds, double obstacleSpeed, int targetScore, String wordBankPath) {
        this.number = number;
        this.durationSeconds = durationSeconds;
        this.obstacleSpeed = obstacleSpeed;
        this.targetScore = targetScore;
        this.wordBankPath = wordBankPath;
    }

    /**
     * Returns the level number.
     *
     * @return the level number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the duration of the level.
     *
     * @return the duration in seconds
     */
    public int getDurationSeconds() {
        return durationSeconds;
    }

    /**
     * Returns the obstacle speed.
     *
     * @return the obstacle speed
     */
    public double getObstacleSpeed() {
        return obstacleSpeed;
    }

    /**
     * Returns the target score.
     *
     * @return the target score
     */
    public int getTargetScore() {
        return targetScore;
    }

    /**
     * Returns the word bank file path.
     *
     * @return the word bank path
     */
    public String getWordBankPath() {
        return wordBankPath;
    }

    /**
     * Creates an easy level.
     *
     * @return an easy level
     */
    public static Level easy() {
        return new Level(1, 30, 0.002, 300, "/assets/Easy.txt");
    }

    /**
     * Creates a medium level.
     *
     * @return a medium level
     */
    public static Level medium() {
        return new Level(2, 45, 0.003, 1250, "/assets/Medium.txt");
    }

    /**
     * Creates a hard level.
     *
     * @return a hard level
     */
    public static Level hard() {
        return new Level(3, 60, 0.003, 1450, "/assets/Hard.txt");
    }

    /**
     * Moves to the next level.
     *
     * @return the next level
     * @throws IllegalArgumentException if level exceeds 3
     */
    public Level nextLevel() {
        this.number += 1;
        if (number == 2) {
            return medium();
        } else if (number == 3) {
            return hard();
        } else {
            throw new IllegalArgumentException("Level can not go above 3");
        }
    }

    /**
     * Decreases the level if not completed.
     */
    public void notCompleted() {
        this.number -= 1;
    }
}
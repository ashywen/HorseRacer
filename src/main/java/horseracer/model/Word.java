package horseracer.model;

/**
 * Represents a single word used in the typing game.
 * <p>
 * Each word has a difficulty level and a base point value.
 * The final points awarded may vary depending on the difficulty.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class Word {

    /** The text of the word */
    private String text;

    /** The difficulty level of the word */
    private Difficulty difficulty;

    /** The base point value of the word */
    private int points;

    /**
     * Creates a new Word object.
     *
     * @param text the word itself
     * @param difficulty the difficulty level of the word
     * @param points the base points awarded for typing the word
     */
    public Word(String text, Difficulty difficulty, int points) {
        this.text = text;
        this.difficulty = difficulty;
        this.points = points;
    }

    /**
     * Returns the word text.
     *
     * @return the word text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the word text.
     *
     * @param text the new word text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the difficulty level of the word.
     *
     * @return the difficulty level
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the word.
     *
     * @param difficulty the new difficulty level
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Returns the point value of the word with a difficulty multiplier applied.
     * <p>
     * EASY returns base points,
     * MEDIUM applies a 1.2x multiplier,
     * HARD applies a 1.5x multiplier.
     *
     * @return the calculated point value
     */
    public int getPoints() {
        if (this.difficulty == Difficulty.EASY) {
            return points;
        } else if (this.difficulty == Difficulty.MEDIUM) {
            return (int) (points * 1.2);
        } else {
            return (int) (points * 1.5);
        }
    }

    /**
     * Decreases the base point value of the word.
     *
     * @param points the amount to subtract
     */
    public void removePoints(int points) {
        this.points -= points;
    }

    /**
     * Returns the word text as a string.
     *
     * @return the word text
     */
    @Override
    public String toString() {
        return text;
    }
}
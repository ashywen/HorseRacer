package horseracer.model;

/**
 * Represents player statistics in the typing game.
 * <p>
 * This class tracks performance metrics such as typing speed,
 * accuracy, errors, time played, and progression across levels.
 * It is updated after each round of gameplay.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class Stats {

  /** The average words per minute across all rounds */
  private double averageWpm;

  /** The highest words per minute achieved */
  private double peakWpm;

  /** The average typing accuracy (percentage) */
  private double accuracy;

  /** The total number of typing errors made */
  private int errorCount;

  /** The total time played in seconds */
  private int totalTimeSeconds;

  /** The highest score achieved */
  private int highScore;

  /** The highest level reached by the player */
  private int highestLevelReached;

  /** The total number of characters typed */
  private int charTyped;

  /** The total number of words typed */
  private int wordsTyped;

  /** The total number of rounds played */
  private int roundsPlayed;

  /**
   * Creates a new Stats object with default values.
   */
  public Stats() {
    reset();
  }

  /**
   * Updates statistics based on a completed round.
   * <p>
   * Recalculates averages and updates totals and peak values.
   *
   * @param roundWpm the WPM achieved in the round
   * @param roundAccuracy the accuracy for the round
   * @param roundErrors the number of errors made
   * @param roundSeconds the duration of the round
   * @param roundScore the score achieved
   * @param levelReached the level reached
   * @param roundWordsTyped the number of words typed
   */
  public void applyRound(double roundWpm,
                         double roundAccuracy,
                         int roundErrors,
                         int roundSeconds,
                         int roundScore,
                         int levelReached,
                         int roundWordsTyped) {

    int oldRounds = roundsPlayed;
    roundsPlayed++;

    averageWpm = ((averageWpm * oldRounds) + roundWpm) / roundsPlayed;
    accuracy = ((accuracy * oldRounds) + roundAccuracy) / roundsPlayed;

    if (roundWpm > peakWpm) {
      peakWpm = roundWpm;
    }

    errorCount += roundErrors;
    totalTimeSeconds += roundSeconds;
    wordsTyped += roundWordsTyped;

    if (roundScore > highScore) {
      highScore = roundScore;
    }

    if (levelReached > highestLevelReached) {
      highestLevelReached = levelReached;
    }
  }

  /**
   * Resets all statistics to default values.
   */
  public void reset() {
    averageWpm = 0.0;
    peakWpm = 0.0;
    accuracy = 100.0;
    errorCount = 0;
    totalTimeSeconds = 0;
    highScore = 0;
    highestLevelReached = 0;
    wordsTyped = 0;
    roundsPlayed = 0;
  }

  /**
   * Returns formatted accuracy as a percentage string.
   *
   * @return formatted accuracy (e.g., "95.5%")
   */
  public String getFormattedAccuracy() {
    return String.format("%.1f%%", accuracy);
  }

  /**
   * Returns formatted average WPM.
   *
   * @return formatted average WPM
   */
  public String getFormattedAverageWpm() {
    return String.format("%.1f", averageWpm);
  }

  /**
   * Returns formatted peak WPM.
   *
   * @return formatted peak WPM
   */
  public String getFormattedPeakWpm() {
    return String.format("%.1f", peakWpm);
  }

  /**
   * Returns total time played formatted as HH:MM:SS.
   *
   * @return formatted time played
   */
  public String getFormattedTimePlayed() {
    int hours = totalTimeSeconds / 3600;
    int minutes = (totalTimeSeconds % 3600) / 60;
    int seconds = totalTimeSeconds % 60;
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  /**
   * Returns the average WPM.
   *
   * @return average WPM
   */
  public double getAverageWpm() {
    return averageWpm;
  }

  /**
   * Sets the average WPM.
   *
   * @param averageWpm the new value
   */
  public void setAverageWpm(double averageWpm) {
    this.averageWpm = averageWpm;
  }

  /**
   * Returns the peak WPM.
   *
   * @return peak WPM
   */
  public double getPeakWpm() {
    return peakWpm;
  }

  /**
   * Sets the peak WPM.
   *
   * @param peakWpm the new value
   */
  public void setPeakWpm(double peakWpm) {
    this.peakWpm = peakWpm;
  }

  /**
   * Returns the accuracy.
   *
   * @return accuracy
   */
  public double getAccuracy() {
    return accuracy;
  }

  /**
   * Sets the accuracy.
   *
   * @param accuracy the new value
   */
  public void setAccuracy(double accuracy) {
    this.accuracy = accuracy;
  }

  /**
   * Returns the total error count.
   *
   * @return error count
   */
  public int getErrorCount() {
    return errorCount;
  }

  /**
   * Sets the error count.
   *
   * @param errorCount the new value
   */
  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

  /**
   * Returns total time played.
   *
   * @return time in seconds
   */
  public int getTotalTimeSeconds() {
    return totalTimeSeconds;
  }

  /**
   * Sets total time played.
   *
   * @param totalTimeSeconds the new value
   */
  public void setTotalTimeSeconds(int totalTimeSeconds) {
    this.totalTimeSeconds = totalTimeSeconds;
  }

  /**
   * Returns the highest score.
   *
   * @return high score
   */
  public int getHighScore() {
    return highScore;
  }

  /**
   * Sets the highest score.
   *
   * @param highScore the new value
   */
  public void setHighScore(int highScore) {
    this.highScore = highScore;
  }

  /**
   * Returns the highest level reached.
   *
   * @return highest level
   */
  public int getHighestLevelReached() {
    return highestLevelReached;
  }

  /**
   * Sets the highest level reached.
   *
   * @param highestLevelReached the new value
   */
  public void setHighestLevelReached(int highestLevelReached) {
    this.highestLevelReached = highestLevelReached;
  }

  /**
   * Returns total characters typed.
   *
   * @return character count
   */
  public int getCharTyped() {
    return charTyped;
  }

  /**
   * Sets total characters typed.
   *
   * @param charTyped the new value
   */
  public void setCharTyped(int charTyped) {
    this.charTyped = charTyped;
  }

  /**
   * Returns total words typed.
   *
   * @return word count
   */
  public int getWordsTyped() {
    return wordsTyped;
  }

  /**
   * Sets total words typed.
   *
   * @param wordsTyped the new value
   */
  public void setWordsTyped(int wordsTyped) {
    this.wordsTyped = wordsTyped;
  }

  /**
   * Returns number of rounds played.
   *
   * @return rounds played
   */
  public int getRoundsPlayed() {
    return roundsPlayed;
  }

  /**
   * Sets number of rounds played.
   *
   * @param roundsPlayed the new value
   */
  public void setRoundsPlayed(int roundsPlayed) {
    this.roundsPlayed = roundsPlayed;
  }

  /**
   * Returns a string representation of the stats.
   *
   * @return stats as a string
   */
  @Override
  public String toString() {
    return "Stats{" +
            "averageWpm=" + averageWpm +
            ", peakWpm=" + peakWpm +
            ", accuracy=" + accuracy +
            ", errorCount=" + errorCount +
            ", totalTimeSeconds=" + totalTimeSeconds +
            ", highScore=" + highScore +
            ", highestLevelReached=" + highestLevelReached +
            ", charTyped=" + charTyped +
            ", wordsTyped=" + wordsTyped +
            ", roundsPlayed=" + roundsPlayed +
            '}';
  }
}
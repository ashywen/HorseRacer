package horseracer.data;

/**
 * Represent a row in the leaderboard with name of player and their score
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class LeaderboardRow {

  private String username;
  private int score;

  /**
   * Empty constructor
   */
  public LeaderboardRow() {

    this.username = "";
    this.score = 0;
  }

  /**
   * Constructor with values
   *
   * @param new_username the username of the user
   * @param new_score    the score the user achieved
   */
  public LeaderboardRow(String new_username, int new_score) {
    username = new_username;
    score = new_score;
  }

  /**
   * Setter method
   *
   * @param new_username new username of the user
   */
  public void setUsername(String new_username) {
    username = new_username;
  }

  /**
   * Setter method
   *
   * @param new_score new updated score
   */
  public void setScore(int new_score) {
    score = new_score;
  }

  /**
   * Getter method
   * 
   * @return String representing the username of the user
   */
  public String getUsername() {
    return username;
  }

  /**
   * Getter method
   *
   */
  public int getScore() {
    return score;
  }

  /**
   * Compare two leaderboard rows to determine which is greater / lesser
   *
   * @param b second LeaderboardRow
   *
   * @return int 1 if less, 0 if same, -1 if greater
   */
  public int compareTo(LeaderboardRow b) {
    // a greater than b
    if (score < b.getScore()) {
      return 1;
    }
    // Equal case
    else if (score == b.getScore()) {
      return 0;
    }
    return -1;
  }

  /**
   * Check if two rows are equal
   *
   * @param username username of other row
   *
   * @return true if same username, false otherwise
   */
  public boolean equals(String username) {
    return this.username.equals(username);
  }
}

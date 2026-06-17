package horseracer.data;

import horseracer.data.exceptions.*;
import horseracer.model.User;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Defines the contract for managing leaderboard data.
 * <p>
 * This interface provides methods to access, update, reset,
 * and save leaderboard entries.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public interface LeaderboardInterface {

  public Iterator<LeaderboardRow> getIter();

  /**
   * Return the length of the leaderboard
   * 
   * @return {@code int} 0 <= length <= 50
   */
  public int getLength();

  /**
   * Update the leaderboard with a new user and score
   * 
   * @param newUser a user class that will be saved to the leaderboard
   * @param score   the user's score that will be saved alongside their name
   *
   */
  public void updateLeaderboard(User newUser, int score);

  /**
   * Resets the leaderboard by removing all entries.
   */
  public void resetLeaderboard();

  /**
   * Saves the leaderboard data.
   *
   * @throws LeaderboardException if saving fails
   */
  public void saveLeaderboard() throws LeaderboardException;

}

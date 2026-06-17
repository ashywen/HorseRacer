package horseracer.data;

import horseracer.data.exceptions.*;
import horseracer.data.utils.LoadData;
import horseracer.data.utils.WriteData;
import horseracer.model.User;

import java.util.ArrayList;
import java.util.Iterator;

import java.lang.Math;

/**
 * Manages the leaderboard of player scores.
 * <p>
 * This class stores leaderboard entries, updates rankings,
 * resets the leaderboard, and saves leaderboard data.
 * The leaderboard maintains only the top 5 highest scores.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class Leaderboard implements LeaderboardInterface {

  /** The list of leaderboard entries (top 5 players) */
  private ArrayList<LeaderboardRow> users;

  /** The file path used to store leaderboard data */
  private final String leaderboard_file = "data/leaderboard/leaderboard.json";

  /**
   * Creates a Leaderboard and loads saved leaderboard data.
   *
   * @throws Exception if loading leaderboard data fails
   */
  public Leaderboard() throws Exception {
    this.users = LoadData.loadData(
        leaderboard_file,
        LeaderboardRow.class,
        LeaderboardException.class);
  }

  /**
   * Returns an iterator over leaderboard rows.
   *
   * @return an iterator of leaderboard rows
   */
  public Iterator<LeaderboardRow> getIter() {
    return users.iterator();
  }

  /**
   * Returns the number of entries in the leaderboard.
   * <p>
   * The size will be between 0 and 5.
   *
   * @return the leaderboard length
   */
  public int getLength() {
    return users.size();
  }

  /**
   * Finds the index of a user in the leaderboard by username.
   *
   * @param username the username to find
   * @return the index of the user, or -1 if not found
   */
  private int findUser(String username) {
    Iterator<LeaderboardRow> iter = users.iterator();
    int counter = 0;

    while (iter.hasNext()) {
      LeaderboardRow curr = iter.next();
      if (curr.getUsername().equals(username)) {
        return counter;
      }
      counter += 1;
    }

    return -1;
  }

  /**
   * REmove a user from the leaderboard when their stats are reset
   *
   * @param username string username of the user
   */
  public void resetUser(String username) {
    int pos = findUser(username);
    if (pos != -1) {
      users.remove(pos);
      try {
        saveLeaderboard();
      } catch (LeaderboardException e) {
        e.printStackTrace();
      }
    }
    return;
  }

  /**
   * Updates the leaderboard with a user's score.
   * <p>
   * If the user already exists, their score is replaced only
   * if the new score is higher. The leaderboard maintains only
   * the top 5 highest scores.
   *
   * @param newUser the user to add or update
   * @param score   the user's score
   */
  public void updateLeaderboard(User newUser, int score) {
    int exists = findUser(newUser.getUsername());

    if (exists != -1) {
      // Remove if a new highscore
      if (users.get(exists).getScore() < score)
        users.remove(exists);
      else {
        return;
      }
    }

    LeaderboardRow new_row = new LeaderboardRow(newUser.getUsername(), score);

    if (getLength() == 0) {
      users.add(0, new_row);
      return;
    } else if (getLength() == 1) {
      if (users.get(0).getScore() < score) {
        users.add(0, new_row);
      } else {
        users.add(1, new_row);
      }
      return;
    }

    // If leaderboard already has 5 entries and score is too low, ignore
    if (getLength() == 5 && users.get(4).getScore() > score) {
      return;
    }

    int pos = binarySearch(0, getLength() - 1, score);
    users.add(pos, new_row);

    // Keep only top 5 entries
    if (getLength() == 6) {
      users.remove(5);
    }
  }

  /**
   * Resets the leaderboard by removing all entries.
   */
  public void resetLeaderboard() {
    this.users = new ArrayList<LeaderboardRow>();
  }

  /**
   * Uses binary search to find the insertion index for a score.
   *
   * @param start the starting index of the array or subarray
   * @param end   the ending index of the array or subarray
   * @param score the score to insert
   * @return the index where the score should be inserted
   */
  private int binarySearch(int start, int end, int score) {
    if (start == end) {
      return start;
    }

    int mid = Math.floorDiv((end - start), 2) + start;

    if (users.get(mid).getScore() >= score && users.get(mid + 1).getScore() <= score) {
      return mid + 1;
    }

    if (users.get(mid).getScore() < score) {
      return binarySearch(start, mid, score);
    }

    return binarySearch(mid + 1, end, score);
  }

  /**
   * Saves the leaderboard to the JSON file.
   *
   * @throws LeaderboardException if saving fails
   */
  public void saveLeaderboard() throws LeaderboardException {
    WriteData.writeData(leaderboard_file, users);
  }

  /**
   * String representation of the leaderboard
   * Follows format:
   * username : score
   * 
   * @return String of the leaderboard
   */
  public String toString() {
    String res = "";

    Iterator<LeaderboardRow> iter = users.iterator();
    while (iter.hasNext()) {
      LeaderboardRow curr = iter.next();
      res += curr.getUsername() + " : " + curr.getScore() + "\n";
    }

    return res;

  }
}

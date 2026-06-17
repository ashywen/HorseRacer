package horseracer.data;

import horseracer.model.User;
import horseracer.model.Stats;

import java.io.FileNotFoundException;
import java.io.IOException;

import horseracer.data.exceptions.*;

/**
 * Defines the contract for managing user data and authentication.
 * <p>
 * This interface provides methods for validating logins, updating
 * user statistics, creating accounts, and accessing leaderboard data.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public interface DataManagerInterface {

  /**
   * Returns the leaderboard of players.
   *
   * @return the leaderboard
   */
  public Leaderboard getLeaderboard();

  /**
   * Validates login credentials for a user.
   *
   * @param username the username entered
   * @param password the password entered
   * @return the authenticated user
   * @throws LoginInvalidException if login fails
   * @throws FileNotFoundException if data file is missing
   * @throws IOException if an error occurs during file reading
   */
  public User validateLogin(String username, String password)
          throws LoginInvalidException, FileNotFoundException, IOException;

  /**
   * Validates login credentials for a teacher.
   *
   * @param username the username entered
   * @param password the password entered
   * @return the authenticated teacher user
   * @throws LoginInvalidException if login fails
   */
  public User validateParent(String username, String password)
          throws LoginInvalidException;

  /**
   * Updates a user's statistics.
   *
   * @param newUser the user whose stats are updated
   * @param newStats the new statistics
   */
  public void updateStats(User newUser, Stats newStats);

  /**
   * Creates a new user account.
   *
   * @param newUser the user to create
   * @throws CreateAccountException if the username is already taken
   */
  public void createAccount(User newUser) throws CreateAccountException;
}
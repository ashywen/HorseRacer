
package horseracer.data;

import horseracer.model.User;
import horseracer.model.Stats;
import horseracer.data.exceptions.*;
import horseracer.data.utils.*;

import java.util.ArrayList;

/**
 * Class that manages data, such as holding the leaderboard and validating
 * user login
 *
 * @see Leaderboard
 * 
 * @author Riley Wong
 * @version 1.0
 */
public class DataManager implements DataManagerInterface {

  // Data that changes with use
  private ArrayList<User> users;
  private ArrayList<User> parents;
  private Leaderboard leaderboard;

  // Constant
  private final String user_data_file = "data/player_data/student_data.json";
  private final String parent_data_file = "data/player_data/teacher_data.json";

  /**
   * Constructor that initializes the users and leaderboard by fetching the
   * required data
   *
   */
  public DataManager() throws Exception {

    this.users = LoadData.loadData(user_data_file, User.class, LoadException.class);
    this.parents = LoadData.loadData(parent_data_file, User.class, LoadException.class);
    this.leaderboard = new Leaderboard();
  }

  /**
   * Get an arraylist representing the leaderboard of players
   *
   * @see Leaderboard
   *
   * @return {@code ArrayList<User>} representing the leaderboard.
   */
  public Leaderboard getLeaderboard() {
    return leaderboard;
  }

  public ArrayList<User> getUsers() {
    return this.users;
  }

  public ArrayList<User> getParent() {
    return this.parents;
  }

  /**
   * Attempt to log a user in by checking input credentials against stored users
   *
   * @param username username of the account the user is trying to log into
   * @param password password tied to the account the user is attempting to log
   *
   * @return {@code User} a successful login returns the user with the input
   *         credentials
   *
   * @throws LoginInvalidException when invalid login credentials
   *                               into
   */
  public User validateLogin(String username, String password)
      throws LoginInvalidException {
    // Try searching the students
    try {
      // Find the user with associated name
      User validated_user = FindUser.getUser(users, username);

      // Validate login
      if (checkCredentials(username, password, validated_user))
        return validated_user;

      // Invalid credentials
      throw new LoginInvalidException("Invalid password!");

    } catch (UserNotFoundException e) {
      // Not found, try searching the teachers
      try {
        // Get a parent with the same name
        User validated_user = FindUser.getParent(parents, username);

        // If the user has the proper username and password
        if (checkCredentials(username, password, validated_user))
          return validated_user;
        // Otherwise say password is invalid
        throw new LoginInvalidException("Invalid password!");
      } catch (UserNotFoundException e2) {
        // If no user is found in both students and teachers throw error
        throw new LoginInvalidException("Account does not Exist!");
      }
    }
  }

  /**
   * Validate a parents login, by only checking the parent database
   *
   * @param username input username
   * @param password input password
   *
   * @throws LoginInvalidException when not valid login due to no user with
   *                               username or invalid password
   */
  public User validateParent(String username, String password)
      throws LoginInvalidException {

    try {
      // Find Parent same username
      User parent = FindUser.getParent(parents, username);

      // Validate password
      if (checkCredentials(username, password, parent)) {
        return parent;
      }
      throw new LoginInvalidException("Incorrect Password");

    } catch (UserNotFoundException e) {
      throw new LoginInvalidException("User with given username does not exist");
    }

  }

  public void resetPassword(User user, String password) {
    user.setPassword(password);

    if (user.isTeacher()) {
      WriteData.writeData(parent_data_file, parents);

    } else {
      WriteData.writeData(user_data_file, users);
    }
  }

  /**
   * Update a user's stats in the data manager
   * 
   * @param user     user want to save stats to
   * @param newStats new stats for a user
   *
   * @see User
   */
  // });
  public void updateStats(User user, Stats newStats) {
    try {
      User test = getUser(user.getUsername());
      test.setStats(newStats);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }

    if (user.isTeacher()) {
      WriteData.writeData(parent_data_file, parents);

    } else {
      WriteData.writeData(user_data_file, users);
    }

  }

  /**
   * Remove a user from the database
   *
   * @param username the username of the user that will be removed
   *
   * @throws UserNotFoundException when a user with the input username couldn otb
   *                               e found
   *
   */
  public void removeUser(String username) throws UserNotFoundException {
    try {
      // Try users
      User user = FindUser.getUser(users, username);
      users.remove(user);

      // Save
      WriteData.writeData(user_data_file, users);

    } catch (UserNotFoundException e) {
      // Try parents
      User user = FindUser.getParent(parents, username);
      parents.remove(user);

      // Save
      WriteData.writeData(parent_data_file, parents);

    }

  }

  /**
   * Create an account by checking it has a unique username. Then add it to the
   * appropriate database, parent or student
   *
   * @param newUser a create user class for the new user
   * @throws CreateAccountException when username is already taken
   */
  public void createAccount(User newUser) throws CreateAccountException {
    try {
      // Search if username already exists
      User _ = FindUser.getUser(users, newUser.getUsername());
      throw new CreateAccountException("Username is taken!");

    } catch (UserNotFoundException e) {

      try {
        // Search if username already exists
        User _ = FindUser.getParent(parents, newUser.getUsername());
        throw new CreateAccountException("Username is taken!");

      } catch (UserNotFoundException err) {

        // Add to appropriate list
        if (newUser.isTeacher()) {
          parents.add(newUser);
          WriteData.writeData(parent_data_file, parents);
        } else {
          users.add(newUser);
          WriteData.writeData(user_data_file, users);
        }
      }
    }

  }

  /**
   * Get a user by their username
   * 
   * @param username string representing the user that will be returned
   *
   * @return User object
   * @throws UserNotFoundException when no user exists with the input username
   */
  public User getUser(String username) throws UserNotFoundException {
    try {
      return FindUser.getUser(users, username);
    } catch (UserNotFoundException e) {
      return FindUser.getParent(parents, username);
    }
  }

  /**
   * Function that changes the password of a given user
   *
   * @param user         the user object whos password will be changed
   * @param new_password string representing the new password
   */
  public void changePassword(User user, String new_password) {
    user.setPassword(new_password);
  }

  /**
   * Check if a given username and password match the ones associated to a user
   *
   * @see User
   *
   * @param username User's input username
   * @param password User's input password
   * @param user     User account we are checking against input credentials
   *
   * @throws LoginInvalidException when the username matches but the password is
   *                               differnet
   */
  private boolean checkCredentials(String username, String password, User user) throws LoginInvalidException {
    if (username.equals(user.getUsername()) && !password.equals(user.getPassword())) {
      throw new LoginInvalidException("Invalid Passowrd!");
    }
    return username.equals(user.getUsername()) && password.equals(user.getPassword());

  }

}

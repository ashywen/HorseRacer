package horseracer.model;

/**
 * Represents a user of the typing game system.
 * <p>
 * A user can be either a PLAYER or a TEACHER depending on their role.
 * Each user has login credentials and associated game statistics.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class User {

  /** The username used to log into the system */
  private String username;

  /** The password associated with the user's account */
  private String password;

  /** The role of the user */
  private Role role;

  /** The gameplay statistics associated with this user */
  private Stats stats;

  /**
   * Empty constructor used for serialization or frameworks
   * that require a no-argument constructor.
   */
  public User() {
    this.stats = new Stats();
  }

  /**
   * Constructs a new User with a username, password, and role.
   * A new Stats object is automatically created for the user.
   *
   * @param username the user's login username
   * @param password the user's login password
   * @param role     the role of the user
   */
  public User(String username, String password, Role role) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.stats = new Stats();
  }

  /**
   * Checks whether the user is a teacher.
   *
   * @return true if the user's role is TEACHER, false otherwise
   */
  public boolean isTeacher() {
    return role == Role.TEACHER;
  }

  /**
   * Returns the username of the user.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the password of the user.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the role of the user.
   *
   * @return the user's role
   */
  public Role getRole() {
    return role;
  }

  /**
   * Sets the role of the user.
   *
   * @param role the new role of the user
   */
  public void setRole(Role role) {
    this.role = role;
  }

  /**
   * Returns the gameplay statistics associated with this user.
   *
   * @return the Stats object for this user
   */
  public Stats getStats() {
    return stats;
  }

  /**
   * Sets the gameplay statistics for this user.
   *
   * @param stats the new Stats object
   */
  public void setStats(Stats stats) {
    this.stats = stats;
  }

  /**
   * Checks if this user has the given username.
   *
   * @param username the username to compare
   * @return true if the usernames match, false otherwise
   */
  public boolean equals(String username) {
    return this.username.equals(username);
  }
}

package horseracer.model;

/**
 * Represents the role of a user in the system.
 * <p>
 * Each role determines the permissions and functionality
 * available to the user within the game.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public enum Role {

  /** A regular player who can play the game */
  PLAYER,

  /** A teacher who can access additional controls and data */
  TEACHER
}
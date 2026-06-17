package horseracer.data.utils;

import horseracer.model.User;
import horseracer.data.exceptions.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Utility class for finding users in lists.
 * <p>
 * This class provides static methods to search for users
 * by username in different collections.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class FindUser {

  /**
   * Searches for a user in a list of players by username.
   *
   * @param users the list of users to search
   * @param username the username to find
   * @return the matching user
   * @throws UserNotFoundException if no user is found
   */
  public static User getUser(ArrayList<User> users, String username)
          throws UserNotFoundException {

    Iterator<User> iter = users.iterator();

    while (iter.hasNext()) {
      User current_user = iter.next();
      if (current_user.getUsername().equals(username)) {
        return current_user;
      }
    }

    throw new UserNotFoundException("User not found in students.");
  }

  /**
   * Searches for a user in a list of teachers by username.
   *
   * @param parents the list of teacher users
   * @param username the username to find
   * @return the matching user
   * @throws UserNotFoundException if no user is found
   */
  public static User getParent(ArrayList<User> parents, String username)
          throws UserNotFoundException {

    Iterator<User> iter = parents.iterator();

    while (iter.hasNext()) {
      User current_user = iter.next();
      if (current_user.getUsername().equals(username)) {
        return current_user;
      }
    }

    throw new UserNotFoundException("User not found in parents.");
  }

  /**
   * Finds the index of an object in a list by matching a username.
   * <p>
   * The method checks if elements in the list are equal to the
   * given username using {@code equals()}.
   *
   * @param list the list to search
   * @param username the username to match
   * @param <T> the type of elements in the list
   * @return the index of the matching element
   * @throws UserNotFoundException if no matching element is found
   */
  public static <T> int findIndex(ArrayList<T> list, String username)
          throws UserNotFoundException {

    Iterator<T> iter = list.iterator();
    int counter = 0;

    while (iter.hasNext()) {
      T curr = iter.next();
      if (curr.equals(username)) {
        return counter;
      }
      counter++;
    }

    throw new UserNotFoundException("User not found!");
  }
}
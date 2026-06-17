package horseracer.service;

import horseracer.model.User;

/**
 * Represents the session state of the application.
 * <p>
 * This class stores information about the currently logged-in user
 * and the selected level. It is used to share state across different
 * screens during gameplay.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class Session {

    /** The currently logged-in user */
    private User currentUser;

    /** The selected level for gameplay */
    private Integer selectedLevel;

    /**
     * Returns the current user.
     *
     * @return the current user, or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     *
     * @param currentUser the user to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Returns the selected level.
     *
     * @return the selected level
     */
    public Integer getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * Sets the selected level.
     *
     * @param selectedLevel the level to set
     */
    public void setSelectedLevel(Integer selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    /**
     * Clears the session state.
     * <p>
     * Resets the current user and selected level to null.
     */
    public void clear() {
        currentUser = null;
        selectedLevel = null;
    }
}
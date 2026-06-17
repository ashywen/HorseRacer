package horseracer.app;

import horseracer.data.DataManager;
import horseracer.model.Level;
import horseracer.model.User;
import horseracer.screens.ParentControl.ResetPassword.ResetPasswordScreen;
import horseracer.screens.ParentControl.ParentControlScreen;
import horseracer.screens.ParentControl.StudentControl.StudentControl;
import horseracer.screens.accountCreation.AccountCreationScreen;
import horseracer.screens.leaderboard.LeaderboardScreen;
import horseracer.screens.parentlogin.ParentLoginScreen;
import horseracer.screens.*;
import horseracer.screens.GameplayScreen.GamePlayScreen;
import horseracer.service.Session;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages screen navigation and transitions in the game.
 * <p>
 * This class controls switching between different UI screens
 * such as menus, login, gameplay, and dashboards by updating
 * the root of a single Scene.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class ScreenManager {

  /** The main application stage */
  private final Stage stage;

  /** The data manager for handling user data */
  private final DataManager dataManager;

  /** The session storing current user state */
  private final Session session;

  /** The main scene used for all screens */
  private final Scene scene;

  /**
   * Creates a ScreenManager with the given components.
   *
   * @param stage the primary stage
   * @param dataManager the data manager
   * @param session the current session
   */
  public ScreenManager(Stage stage, DataManager dataManager, Session session) {
    this.stage = stage;
    this.dataManager = dataManager;
    this.session = session;
    this.scene = new Scene(new MainMenuScreen(this, dataManager, session), 1100, 750);

    scene.getStylesheets().add(
            getClass().getResource("/style/style.css").toExternalForm()
    );
  }

  /** Displays the main menu screen. */
  public void showMainMenu() {
    scene.setRoot(new MainMenuScreen(this, dataManager, session));
  }

  /** Displays the sign-up screen. */
  public void showSignUp() {
    scene.setRoot(new SignUpScreen(this, dataManager, session));
  }

  /** Displays the login screen. */
  public void showLogin() {
    scene.setRoot(new LoginScreen(this, dataManager, session));
  }

  /** Displays the tutorial screen. */
  public void showTutorial() {
    scene.setRoot(new TutorialScreen(this, dataManager, session));
  }

  /** Displays the leaderboard screen. */
  public void showLeaderboard() {
    scene.setRoot(new LeaderboardScreen(this, dataManager, session));
  }

  /** Displays the teacher login screen. */
  public void showTeacherLogin() {
    scene.setRoot(new ParentLoginScreen(this, dataManager, session));
  }

  /** Displays the teacher dashboard. */
  public void showTeacherDashboard() {
    scene.setRoot(new ParentControlScreen(this, dataManager, session));
  }

  /** Displays the account creation screen. */
  public void showCreateAccount() {
    scene.setRoot(new AccountCreationScreen(this, dataManager, session));
  }

  /** Displays the player screen. */
  public void showPlayerScreen() {
    scene.setRoot(new PlayerScreen(this, dataManager, session));
  }

  /**
   * Displays the student control screen for a user.
   *
   * @param user the selected user
   */
  public void showStudentControl(User user) {
    scene.setRoot(new StudentControl(this, dataManager, session, user));
  }

  /**
   * Displays the reset password screen for a user.
   *
   * @param user the user whose password is reset
   */
  public void showResetPassword(User user) {
    scene.setRoot(new ResetPasswordScreen(this, dataManager, session, user));
  }

  /**
   * Displays the gameplay screen for a given level.
   *
   * @param level the level to play
   */
  public void showGameplay(Level level) {
    stage.setMaximized(true);
    scene.setRoot(new GamePlayScreen(this, dataManager, session, level));
  }

  /** Starts the application window. */
  public void start() {
    stage.setTitle("Horse Racer - JavaFX");
    stage.setScene(scene);
    stage.setMinWidth(1000);
    stage.setMinHeight(700);
    stage.show();
  }

  /** Closes the application. */
  public void exit() {
    stage.close();
  }
}
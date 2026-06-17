package horseracer.screens.leaderboard;

import horseracer.app.ScreenManager;
import horseracer.service.Session;
import horseracer.data.DataManager;
import horseracer.screens.AbstractScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import horseracer.screens.leaderboard.utils.Board;
import horseracer.screens.utils.RedButton;

/**
 * Represents the leaderboard screen of the game.
 * <p>
 * This screen displays the current leaderboard rankings and
 * provides navigation back to either the player screen or
 * the main menu, depending on whether a user is logged in.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class LeaderboardScreen extends AbstractScreen {

  /**
   * Creates the leaderboard screen UI and initializes its components.
   *
   * @param screenManager the screen manager used for navigation
   * @param data_manager the data manager used to retrieve leaderboard data
   * @param session the current session storing user state
   */
  public LeaderboardScreen(ScreenManager screenManager, DataManager data_manager, Session session) {
    super(screenManager, data_manager, session);
    getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

    VBox board = new VBox(20);
    board.getStyleClass().add("body_overlay");
    board.setMinSize(400, 400);
    board.setAlignment(Pos.CENTER);

    Label title_lbl = new Label("LEADERBOARD");
    title_lbl.getStyleClass().add("screen-title");

    VBox leaderboard = Board.create(data_manager);
    leaderboard.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

    board.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    board.setPadding(new Insets(10, 30, 10, 30));

    Button back_button = RedButton.create("BACK");
    back_button.setOnAction(e -> {
      if (session.getCurrentUser() != null) {
        screenManager.showPlayerScreen();
      } else {
        screenManager.showMainMenu();
      }
    });

    board.getChildren().addAll(title_lbl, leaderboard, back_button);
    setCenter(board);
  }
}
package horseracer.screens.leaderboard.utils;

import java.util.Iterator;
import horseracer.data.LeaderboardRow;

import horseracer.data.DataManager;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Board {

  /**
   * Create the screen that holds the user data
   *
   * @param title   the title of the screen
   * @param manager the screenmanager that handles screen changing
   * @param user    class representing the user that will be shown in the screen
   */
  public static VBox create(DataManager data_manager) {
    VBox board = new VBox();
    board.prefWidth(600);

    HBox intro_row = Row.header();
    board.getChildren().add(intro_row);
    int pos = 1;
    Iterator<LeaderboardRow> iter = data_manager.getLeaderboard().getIter();
    // Visualize rows
    while (iter.hasNext()) {
      HBox row = Row.create(iter.next(), pos);
      board.getChildren().add(row);
      pos += 1;
    }

    return board;
  }

}

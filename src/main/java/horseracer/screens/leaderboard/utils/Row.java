package horseracer.screens.leaderboard.utils;

import horseracer.data.LeaderboardRow;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Row {
  private static final int spacing = 15;
  private static final int col_spacing = 150;

  /**
   * Create the screen that holds the user data
   *
   * @param title   the title of the screen
   * @param manager the screenmanager that handles screen changing
   * @param user    class representing the user that will be shown in the screen
   */
  public static HBox create(LeaderboardRow leaderboardrow, int pos) {
    HBox row = new HBox(spacing);
    row.getStyleClass().add("leaderboard-row");
    row.setPrefWidth(500);

    // Pos
    Label pos_lbl = new Label(String.valueOf(pos));
    pos_lbl.setMinWidth(col_spacing);
    pos_lbl.setMaxWidth(col_spacing);

    // Name
    Label name_lbl = new Label(leaderboardrow.getUsername());

    // Score
    Label score_lbl = new Label(String.valueOf(leaderboardrow.getScore()));

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    row.getChildren().addAll(pos_lbl, name_lbl, spacer, score_lbl);

    return row;
  }

  public static HBox header() {

    HBox row = new HBox(spacing);
    row.getStyleClass().add("leaderboard-row");

    // Pos
    Label pos_lbl = new Label("Rank");
    pos_lbl.setMinWidth(col_spacing);
    pos_lbl.setMaxWidth(col_spacing);

    // Name
    Label name_lbl = new Label("Username");

    // Score
    Label score_lbl = new Label("Score");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    row.getChildren().addAll(pos_lbl, name_lbl, spacer, score_lbl);

    return row;
  }

}

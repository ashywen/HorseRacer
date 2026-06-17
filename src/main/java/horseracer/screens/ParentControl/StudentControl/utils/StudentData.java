package horseracer.screens.ParentControl.StudentControl.utils;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.utils.OrangeButton;
import horseracer.screens.utils.RedButton;
import horseracer.screens.utils.TextLoader;

public class StudentData {
  /**
   * Create a gui object that contains the username and the user's stats. Feature
   * buttons to reset stats or password
   *
   * @param screenManager the priamry screenmanager
   * @param data_manager  the main datamanager
   * @param user          the user whose stats are being listed
   * @param root          the stackpane root of the screen where all children are
   *                      put into
   * @return VBox containing the user's stats listed with buttons for resetting
   *         stats and password
   *
   */
  public static VBox create(ScreenManager screenManager, DataManager data_manager, User user, StackPane root) {

    // Creating a Grid Pane
    VBox gridPane = new VBox(25);

    // Setting size for the pane
    gridPane.setMinSize(400, 400);

    // Add Styling
    gridPane.getStyleClass().add("body_overlay");

    // Setting the padding
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    // Setting the Grid alignment
    gridPane.setAlignment(Pos.CENTER);

    // Name
    Label title_lbl = new Label(user.getUsername() + "'s Stats");
    title_lbl.getStyleClass().add("screen-title");

    // Form bidy
    GridPane form_body = buildGrid(user, screenManager, data_manager);

    // Buttons
    VBox buttons = new VBox(10);
    buttons.setAlignment(Pos.CENTER);

    // Backbutton
    Button back_button = OrangeButton.create("BACK");
    back_button.setOnAction(e -> {
      screenManager.showTeacherDashboard();
    });

    // Reset button you should make a reset screen!!!
    Button reset_button = OrangeButton.create("RESET STATS");

    // Display popup on click
    reset_button.setOnAction(e -> {
      ResetButton.resetButton(user, root, gridPane, form_body, screenManager, data_manager);
    });

    // Reset password button
    Button reset_pass_button = OrangeButton.create("RESET PASSWORD");
    reset_pass_button.setOnAction(e -> {
      screenManager.showResetPassword(user);
    });

    // Button layout
    buttons.getChildren().addAll(reset_pass_button, reset_button, back_button);
    buttons.setAlignment(Pos.CENTER);

    // Arranging all the nodes in the grid
    gridPane.getChildren().addAll(title_lbl, form_body, buttons);
    return gridPane;

  }

  /**
   * Create a box to showcase the selected user's data
   *
   * @param user    the user that's stats are being showcased
   * @param manager the screen manager responsible for switching screens
   *
   */

  private static void createRow(String row_name, double stat, int row_number, GridPane parent) {
    Label username_title = TextLoader.create(row_name, "stat-label");
    GridPane.setHalignment(username_title, HPos.CENTER);

    Label username_colon = TextLoader.create(":", "stat-label");
    GridPane.setHalignment(username_colon, HPos.CENTER);

    Label username = TextLoader.create(String.format("%.1f", stat), "stat-value");
    GridPane.setHalignment(username, HPos.CENTER);

    parent.add(username_title, 0, row_number);
    parent.add(username_colon, 1, row_number);
    parent.add(username, 2, row_number);
  }

  public static GridPane buildGrid(User user, ScreenManager manager, DataManager data_manager) {

    GridPane grid = new GridPane();

    int row_number = 0;

    // Username row
    Label username_title = TextLoader.create("Profile Name", "control-text");
    GridPane.setHalignment(username_title, HPos.CENTER);

    Label username_colon = TextLoader.create(":", "control-text");
    GridPane.setHalignment(username_colon, HPos.CENTER);

    Label username = TextLoader.create(user.getUsername(), "control-text");
    GridPane.setHalignment(username, HPos.CENTER);

    grid.add(username_title, 0, row_number);
    grid.add(username_colon, 1, row_number);
    grid.add(username, 2, row_number);
    row_number += 1;

    // Stats
    createRow("Average WPM", user.getStats().getAverageWpm(), row_number, grid);
    row_number += 1;

    createRow("Peak WPM", user.getStats().getPeakWpm(), row_number, grid);
    row_number += 1;

    createRow("Accuracy", user.getStats().getAccuracy(), row_number, grid);
    row_number += 1;

    createRow("Error Count", user.getStats().getErrorCount(), row_number, grid);
    row_number += 1;

    createRow("Total Time", user.getStats().getTotalTimeSeconds(), row_number, grid);
    row_number += 1;

    createRow("Highscore", user.getStats().getHighScore(), row_number, grid);
    row_number += 1;

    createRow("Highest Level", user.getStats().getHighestLevelReached(), row_number, grid);
    row_number += 1;

    createRow("Words Typed", user.getStats().getWordsTyped(), row_number, grid);
    row_number += 1;

    createRow("Rounds Played", user.getStats().getRoundsPlayed(), row_number, grid);
    row_number += 1;

    // Grid positioning
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    return grid;
  }
}

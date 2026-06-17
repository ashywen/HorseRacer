package horseracer.screens;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.model.Level;
import horseracer.model.User;
import horseracer.service.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Represents the player dashboard screen.
 * <p>
 * This screen allows the player to:
 * <ul>
 *   <li>Select and start a level</li>
 *   <li>View gameplay statistics</li>
 *   <li>Access leaderboard and tutorial</li>
 *   <li>Log out of the session</li>
 * </ul>
 * It also includes a statistics overlay displaying detailed player performance.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class PlayerScreen extends AbstractScreen {

  /** Overlay displaying player statistics */
  private VBox statsOverlay;

  /**
   * Creates the player dashboard UI and initializes all components
   * and event handlers.
   *
   * @param screenManager the screen manager used for navigation
   * @param dataManager the data manager for accessing application data
   * @param session the current session storing user state
   */
  public PlayerScreen(ScreenManager screenManager, DataManager dataManager, Session session) {
    super(screenManager, dataManager, session);

    User user = session.getCurrentUser();

    getStylesheets().add(getClass().getResource("/style/player.css").toExternalForm());
    getStyleClass().add("screen");

    try {
      Font.loadFont(getClass().getResourceAsStream("/fonts/VT323-Regular.ttf"), 10);
      Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 10);
    } catch (Exception e) {
      // fallback silently if fonts are not found
    }

    setUpStatsOverlay(user);

    VBox card = new VBox();
    card.setMaxWidth(760);
    card.setAlignment(Pos.CENTER);
    card.setSpacing(22);
    card.setPadding(new Insets(36));
    card.getStyleClass().add("card");

    Label title = new Label("Welcome, " + user.getUsername());
    title.getStyleClass().add("title");

    Label subtitle = new Label("Player Dashboard");
    subtitle.getStyleClass().add("subtitle");

    ComboBox<Integer> levelPicker = new ComboBox<>();
    int maxLevel = 3;
    int levelUnlocked = user.getStats().getHighestLevelReached();

    for (int i = 1; i <= maxLevel; i++) {
      levelPicker.getItems().add(i);
    }

    levelPicker.setCellFactory(cb -> new ListCell<>() {
      @Override
      protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
          setText(null);
          setDisable(false);
          return;
        }

        setText(item.toString());
        //should unlock the next level
        boolean disabled = item > levelUnlocked + 1;
        setDisable(disabled);

        if (disabled) {
          setStyle("-fx-background-color: lightgray; -fx-text-fill: #777;");
        }
      }
    });

    levelPicker.getSelectionModel().selectFirst();
    levelPicker.getStyleClass().add("level-picker");

    Label levelLabel = new Label("Select Level:");
    levelLabel.getStyleClass().add("level-label");

    HBox levelBox = new HBox(12, levelLabel, levelPicker);
    levelBox.setAlignment(Pos.CENTER);

    Button start = createGreenButton("START RACE  →");
    Button viewStats = createOrangeOutlineButton("VIEW STATS");
    Button leaderboard = createOrangeOutlineButton("LEADERBOARD");
    Button tutorial = createOrangeOutlineButton("TUTORIAL");
    Button logout = createOrangeOutlineButton("LOGOUT");

    VBox buttonBox = new VBox(14, levelBox, start, viewStats, leaderboard, tutorial, logout);
    buttonBox.setAlignment(Pos.CENTER);

    card.getChildren().addAll(title, subtitle, buttonBox);

    StackPane mainLayout = new StackPane(card, statsOverlay);
    StackPane.setAlignment(statsOverlay, Pos.CENTER);

    setCenter(mainLayout);

    start.setOnAction(e -> {
      Level level = getSelectedLevel(levelPicker);
      screenManager.showGameplay(level);
    });

    viewStats.setOnAction(e -> showStatsOverlay());

    leaderboard.setOnAction(e -> {
      Level level = getSelectedLevel(levelPicker);
      screenManager.showLeaderboard();
    });

    tutorial.setOnAction(e -> screenManager.showTutorial());

    logout.setOnAction(e -> {
      session.clear();
      screenManager.showMainMenu();
    });
  }

  /**
   * Returns the Level object based on the selected level number.
   *
   * @param levelPicker the combo box containing level choices
   * @return the corresponding Level
   */
  private Level getSelectedLevel(ComboBox<Integer> levelPicker) {
    int levelNum = levelPicker.getValue();

    switch (levelNum) {
      case 1:
        return Level.easy();
      case 2:
        return Level.medium();
      case 3:
        return Level.hard();
      default:
        return Level.easy();
    }
  }

  /**
   * Initializes the statistics overlay UI for the given user.
   *
   * @param user the current user whose statistics are displayed
   */
  private void setUpStatsOverlay(User user) {
    Label statsTitle = new Label("Player Statistics");
    statsTitle.getStyleClass().add("overlayTitle");

    Label statsSubtitle = new Label("Horse Racer");
    statsSubtitle.getStyleClass().add("overlaySubtitle");

    GridPane stats = new GridPane();
    stats.setHgap(24);
    stats.setVgap(14);
    stats.setAlignment(Pos.CENTER);
    stats.setPadding(new Insets(20, 0, 20, 0));

    addStatRow(stats, 0, "Average WPM:", user.getStats().getFormattedAverageWpm());
    addStatRow(stats, 1, "Peak WPM:", user.getStats().getFormattedPeakWpm());
    addStatRow(stats, 2, "Accuracy:", user.getStats().getFormattedAccuracy());
    addStatRow(stats, 3, "Time Played:", user.getStats().getFormattedTimePlayed());
    addStatRow(stats, 4, "High Score:", String.valueOf(user.getStats().getHighScore()));
    addStatRow(stats, 5, "Highest Level:", String.valueOf(user.getStats().getHighestLevelReached()));
    addStatRow(stats, 6, "Words Typed:", String.valueOf(user.getStats().getWordsTyped()));
    addStatRow(stats, 7, "Errors Typed:", String.valueOf(user.getStats().getErrorCount()));
    addStatRow(stats, 8, "Rounds Played:", String.valueOf(user.getStats().getRoundsPlayed()));

    Button closeButton = new Button("CLOSE");
    closeButton.getStyleClass().add("overlayButton");
    closeButton.setOnAction(e -> hideStatsOverlay());

    statsOverlay = new VBox(statsTitle, statsSubtitle, stats, closeButton);
    statsOverlay.getStyleClass().add("overlay");
    statsOverlay.setAlignment(Pos.CENTER);
    statsOverlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    statsOverlay.setVisible(false);
  }

  /**
   * Displays the statistics overlay.
   */
  private void showStatsOverlay() {
    statsOverlay.setVisible(true);
  }

  /**
   * Hides the statistics overlay.
   */
  private void hideStatsOverlay() {
    statsOverlay.setVisible(false);
  }

  /**
   * Adds a row of statistic labels to the grid.
   *
   * @param grid the grid pane
   * @param row the row index
   * @param labelText the label text
   * @param valueText the value text
   */
  private void addStatRow(GridPane grid, int row, String labelText, String valueText) {
    Label label = new Label(labelText);
    label.getStyleClass().add("stat-label");

    Label value = new Label(valueText);
    value.getStyleClass().add("stat-value");

    grid.add(label, 0, row);
    grid.add(value, 1, row);
  }

  /**
   * Creates a green button used for primary actions.
   *
   * @param text the button label
   * @return the configured button
   */
  private Button createGreenButton(String text) {
    Button button = new Button(text);
    button.setPrefWidth(260);
    button.setPrefHeight(54);
    button.getStyleClass().add("player-green-button");
    return button;
  }

  /**
   * Creates an outlined orange button used for secondary actions.
   *
   * @param text the button label
   * @return the configured button
   */
  private Button createOrangeOutlineButton(String text) {
    Button button = new Button(text);
    button.setPrefWidth(260);
    button.getStyleClass().add("outline-button");
    return button;
  }
}
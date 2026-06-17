package horseracer.screens.GameplayScreen;

import java.util.concurrent.ThreadLocalRandom;

import horseracer.GameLogic.*;
import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.data.exceptions.LeaderboardException;
import horseracer.model.Level;
import horseracer.model.PowerUpType;
import horseracer.model.Stats;
import horseracer.model.User;
import horseracer.screens.AbstractScreen;
import horseracer.screens.HorseView;
import horseracer.screens.ObstacleNode;
import horseracer.screens.GameplayScreen.utils.EndlevelOverlay;
import horseracer.screens.GameplayScreen.utils.GameoverOverlay;
import horseracer.screens.GameplayScreen.utils.PausedOverlay;
import horseracer.service.Session;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * Game Play Screen
 * <br>
 * <br>
 * Screen for the typing game
 *
 * @version 1.0.0
 * @author Joanna Wang, Kathy Yao, Tianrui Xu, Riley Wong
 */

public class GamePlayScreen extends AbstractScreen {

  /** Label for lives hud */
  private Label livesLabel;
  /** Label for points hud */
  private Label pointsLabel;
  /** Label for WPM */
  private Label wpmLabel;
  /** Label for socre after gameOver */
  /** The hud bar */
  private HBox hudBar;
  /** hud for powerup */
  private HBox powerUpHud;

  /** pause button */
  private Button pauseButton;

  /** main game panel */
  private BorderPane racePane = new BorderPane();
  /** wrap up ui */
  private StackPane wrapper;

  /** horse entity */
  private HorseView horseView;

  /** game loop */
  private Timeline gameLoop;
  /** frame tracker */
  private int frame = 100;
  /** Event handle method */
  private Runnable runable;

  /** track for first obstacle */
  private ObstacleNode focused;

  private boolean paused = false;

  /** obstacle spawner instance */
  private ObstacleSpawner obstacleSpawner;
  /** typing input manager instance */
  private TypingInputManager TIM;
  /** level of the game */
  private Level level;
  /** score tracker */
  private ScoreManager scoreManager;
  /** collision handle system between obstacle and horse */
  private CollisionSystem collisionSystem;
  /** current user */
  private User user;
  /** stats of current game */
  private Stats stats;
  /** timer game */
  private Timer timer;
  /** life system */
  private LifeSystem lifeSystem;
  /** music player */
  private Music music;
  /** powerup manager instance */
  private PowerUpManager PUM;
  /** icon for powerup */
  private ImageView powerUpIcon;
  /** message for powerup */
  private Label powerUpMessage;
  /** boolean indicating if attempted to generate powerup */
  private boolean attempted = false;

  // Fields for stats
  /** player word typed count */
  private int wordsTyped = 0;
  /** total input char count */
  private int totalTypedChar = 0;
  /** wrong input count */
  private int wrongCharCount = 0;
  /** Datamanager */
  private DataManager dataManager;

  /** windows width */
  private double width;

  private boolean initialized = false;

  /** game over sound effect */
  private Music gameOverSfx;

  /**
   * Constructor for GamePlayScreen
   * 
   * @param screenManager the screen manager
   * @param dataManager   the data manager
   * @param session       game session
   * @param level         of the game
   */
  public GamePlayScreen(ScreenManager screenManager, DataManager dataManager, Session session, Level level) {
    super(screenManager, dataManager, session);

    // initialize game logic instances
    this.level = level;
    this.scoreManager = new ScoreManager();
    this.collisionSystem = new CollisionSystem();
    this.user = session.getCurrentUser();
    this.stats = user.getStats();
    this.timer = new Timer();
    this.lifeSystem = new LifeSystem(3);
    this.music = new Music("/Music/PETO-Cold_Everywhere.mp3");
    this.PUM = new PowerUpManager();

    this.dataManager = dataManager;

    // setup overlay for pause and game over

    wrapper = new StackPane(racePane);
    wrapper.getStylesheets().add(getClass().getResource("/style/gamePlayScreen.css").toExternalForm());
    setCenter(wrapper);

    // initialize game logic instances
    this.level = level;
    this.scoreManager = new ScoreManager();
    this.collisionSystem = new CollisionSystem();
    this.user = session.getCurrentUser();
    this.stats = user.getStats();
    this.lifeSystem = new LifeSystem(3);

    this.PUM = new PowerUpManager();

    // Datamanager guy
    this.dataManager = dataManager;

    this.timer = new Timer();

    // Wait till screen is initialized to start loops
    wrapper.widthProperty().addListener((obs, oldVal, newVal) -> {
      // Prevent overlapping calls
      if (!initialized) {

        // UI
        generatePauseButton();
        generateLabels();
        initializeRacePane();
        addUIToRacePane();

        // initialize lives symbol
        updateLives();

        // Add horse
        this.horseView = new HorseView(wrapper);
        this.racePane.getChildren().add(horseView.getRoot());

        this.obstacleSpawner = new ObstacleSpawner(level.getWordBankPath(), width, wrapper);
        // spawn obstacle
        spawnObstacleWithTimer();

        // attach event listener to catch key input
        attachInputHandler();

        // start game
        startGameLoop();

        initialized = true;
      }
    });

  }

  /**
   * Getter for BorderPane instance
   * 
   * @return the racePane
   */
  public BorderPane getRacePane() {
    return racePane;
  }

  /**
   * Method start game loop
   */
  private void startGameLoop() {
    gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> onEachFrame()));
    this.timer.start();
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    gameLoop.play();
    this.music.play();
  }

  /**
   * Actions on each frame of the game
   */
  private void onEachFrame() {
    // to avoid invoke null instance
    if (this.focused == null) {
      return;
    }

    // End level condition
    if (this.timer.getElapsedSeconds() > this.level.getDurationSeconds()) {
      if (this.scoreManager.getScore() >= this.level.getTargetScore()) {
        onEndLevel();
      } else {
        onGameOver();
      }
      return;
    }

    powerUpActive();

    this.frame++;
    // Periodically spawn obstacles
    if (this.frame >= 130) {
      this.frame = 0;
      try {
        this.runable.run();
      } catch (IllegalStateException e) {
        // all obstacle spawned but possibly not yet removed
        return;
      }
    }
    // Move obstacle
    this.obstacleSpawner.moveObstacle(this.level);
    // Word completed
    if (this.TIM.isWordComplete()) {
      // update typed words count
      this.wordsTyped++;
      // remove typed obstacle and get points from the obstacle
      int point = this.obstacleSpawner.removeObstacle(this.racePane);
      // append points to score manager
      this.scoreManager.addPoints(point);
      // switch to next obstacle if exist
      switchFocus();
      // update score display
      updatePoints();
      // update WPM
      updateWPM();
    }
    // Collision occur
    if (this.collisionSystem.checkCollision(this.horseView, this.focused)) {
      this.collisionSystem.handleCollision(
          this.lifeSystem,
          this.obstacleSpawner,
          this.racePane);
      updateLives();
      // determine if any lives remain
      if (this.lifeSystem.isGameOver()) {
        onGameOver();
      }
      switchFocus();
    }
  }

  /**
   * Generate points and lives label
   */
  private void generateLabels() {
    // creates the top hud bar
    Label livesTextLabel = new Label("Lives:"); // creates the Lives text label
    livesTextLabel.getStyleClass().add("hudLabel");

    livesLabel = new Label("");
    livesLabel.getStyleClass().add("livesLabel");

    // Level label
    Label levelLabel = new Label("Level: " + this.level.getNumber()); // creates the level label
    levelLabel.getStyleClass().add("hudLabel");

    // Points label
    pointsLabel = new Label("Points: " + this.scoreManager.getScore()); // creates the points label
    pointsLabel.getStyleClass().add("hudLabel");

    // WPM label
    wpmLabel = new Label("WPM: 0");
    wpmLabel.getStyleClass().add("hudLabel");

    generatePowerUpHud();

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    // Lives hud
    HBox livesHud = new HBox(livesTextLabel, livesLabel); // left side of the hud contains the lives
    livesHud.getStyleClass().add("hudLeft"); // moves the text to the left
    livesHud.setAlignment(Pos.CENTER_LEFT);
    livesHud.setSpacing(6);

    // Combine level and points
    HBox levelPointsHud = new HBox(15, levelLabel, pointsLabel, wpmLabel, powerUpHud); // right side has
                                                                                       // level points
    // and pause
    levelPointsHud.getStyleClass().add("hudRight"); // moves the level and points label, and the pause button to the
                                                    // right side of the screen
    levelPointsHud.setAlignment(Pos.CENTER_RIGHT);

    // Combine with lives hud
    hudBar = new HBox(pauseButton, livesHud, levelPointsHud);
    hudBar.getStyleClass().add("hudBar");
    hudBar.setAlignment(Pos.CENTER_LEFT);
  }

  /**
   * Generate pause button with gui
   */
  private void generatePauseButton() {
    // pause button, loads the image for it
    Image pauseIcon = new Image(getClass().getResource("/images/pauseButton.png").toExternalForm()); // loads the pause
                                                                                                     // button image
    ImageView pauseImage = new ImageView(pauseIcon); // displays the pause button image
    pauseImage.setFitWidth(28); // resize image
    pauseImage.setFitHeight(28);
    pauseImage.setPreserveRatio(true);
    pauseButton = new Button(); // create a new button
    pauseButton.setGraphic(pauseImage); // puts the paused image on the button
    pauseButton.getStyleClass().add("pauseButton"); // clear the grey background
    pauseButton.setOnAction(e -> {
      if (!paused)
        onPause();
    }); // pauses the game
  }

  /**
   * Generate PowerUp UI
   */
  private void generatePowerUpHud() {
    powerUpIcon = new ImageView();
    powerUpIcon.setFitWidth(28);
    powerUpIcon.setFitHeight(28);
    powerUpIcon.setPreserveRatio(true);

    powerUpMessage = new Label("");
    powerUpMessage.getStyleClass().add("hudLabel");
    powerUpMessage.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

    powerUpHud = new HBox(8, powerUpIcon, powerUpMessage);
    powerUpHud.setAlignment(Pos.CENTER_RIGHT);
    powerUpHud.setPadding(new Insets(6, 12, 6, 12));
    powerUpHud.setStyle(
        "-fx-background-color: rgba(0,0,0,0.55);" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: gold;" +
            "-fx-border-radius: 10;");

    powerUpHud.setVisible(false);
    powerUpHud.setManaged(false);
  }

  /**
   * Initialize the game area
   */
  private void initializeRacePane() {
    try {
      setupBackgroundImage();
    } catch (Exception e) {
      // Use image background if available; otherwise use fallback color
      wrapper.getStyleClass().add("fallback-bg");
    }
  }

  /**
   * Setup background ui for game area
   */
  private void setupBackgroundImage() {
    String imgPath = "/images/track-bg" + this.level.getNumber() + ".png";

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("-fx-background-image: url('");
    stringBuilder.append(imgPath);
    stringBuilder.append("');");
    stringBuilder.append("-fx-background-repeat: repeat-x;");
    stringBuilder.append("-fx-background-size: 100% 100%;");
    stringBuilder.append("-fx-background-position: center center;");

    wrapper.setStyle(
        stringBuilder.toString());
  }

  /**
   * Add all UIs to game area
   */
  private void addUIToRacePane() {
    this.racePane.setTop(hudBar);

  }

  /**
   * Display PowerUp message and icon
   */
  private void showPowerUpHud(PowerUpType type) {
    String message;
    String path;

    switch (type) {
      case DOUBLE_POINTS:
        message = "Double Points Activated!";
        path = "/images/double-points-icon.png";
        break;
      case EXTRA_LIFE:
        message = "Extra Life Activated!";
        path = "/images/heart-life-icon.png";
        break;
      case SLOW_OBSTACLE:
        message = "Slow Obstacle Activated!";
        path = "/images/time-obstacle-icon.png";
        break;
      default:
        message = "Power-Up Activated!";
        path = "/images/doublePointsIcon.png";
    }

    Image icon = new Image(getClass().getResource(path).toExternalForm());
    powerUpIcon.setImage(icon);
    powerUpMessage.setText(message);
    powerUpHud.setVisible(true);
    powerUpHud.setManaged(true);
  }

  /**
   * Hide PowerUp UI
   */
  private void hidePowerUpHud() {
    powerUpHud.setVisible(false);
    powerUpHud.setManaged(false);
    powerUpMessage.setText("");
    powerUpIcon.setImage(null);
  }

  /**
   * Update WPM
   */
  private void updateWPM() {
    long seconds = this.timer.getElapsedSeconds();

    if (seconds == 0) {
      wpmLabel.setText("WPM: 0");
      return;
    }

    double minutes = seconds / 60.0;
    double wpm = this.wordsTyped / minutes;

    wpmLabel.setText("WPM: " + (int) wpm);
  }

  // Event handler functions

  /**
   * Pause event
   */
  private void onPause() {
    this.timer.pause();
    this.gameLoop.pause();
    this.paused = true;

    // Create the overlay
    PausedOverlay overlay = new PausedOverlay();
    overlay.create(wrapper, screenManager, level, this);

    this.music.pause();
    // attach shortcut to resume
  }

  /**
   * Method resume current game used by button
   */
  public void onContinue() {
    TIM.appendEventHandler(this.wrapper);
    this.timer.resume();
    this.paused = false;
    this.gameLoop.play();
    this.music.play();
  }

  /**
   * Game over event
   */
  private void onGameOver() {
    // Create the overlay
    Label scoreLabel = new Label("Score: " + this.scoreManager.getScore());
    GameoverOverlay overlay = new GameoverOverlay();
    overlay.create(wrapper, scoreLabel, screenManager, level);

    // Handle the game logic
    this.gameLoop.stop();
    this.level.notCompleted();
    updateStats();
    this.music.stop();
    hidePowerUpHud();

    this.gameOverSfx = new Music("/Music/game_over.wav");
    this.gameOverSfx.play();
  }

  /**
   * End level event
   */
  private void onEndLevel() {
    this.gameLoop.stop();
    this.music.stop();
    // System.out.println(this.level.getNumber());
    // this.level = level.nextLevel();
    // System.out.println(this.level.getNumber());

    updateStats();
    EndlevelOverlay overlay = new EndlevelOverlay();

    overlay.create(wrapper, screenManager, level);
    hidePowerUpHud();
  }

  /**
   * Configure method to run
   * 
   * @param r the method
   */
  public void setOnRunable(Runnable r) {
    this.runable = r;
  }

  /**
   * Spawn obstacles
   */
  public void spawnObstacleWithTimer() {
    this.obstacleSpawner.spawnObstacle(this);
    this.focused = this.obstacleSpawner.getFocusedObstacle();
  }

  /**
   * Attach event handler on user input
   */
  public void attachInputHandler() {
    this.TIM = new TypingInputManager(this.focused.getWord(), this.obstacleSpawner);
    this.TIM.appendEventHandler(wrapper);
  }

  /**
   * Update the current focus Obstacle Node
   */
  private void switchFocus() {
    this.focused = obstacleSpawner.getFocusedObstacle();
    if (this.focused == null) {
      this.gameLoop.stop();
      this.music.stop();
      updateStats();
      return;
    }
    this.TIM.updateWord(this.focused.getWord());
  }

  /**
   * Update stats after finish round
   */
  private void updateStats() {
    long seconds = this.timer.getElapsedSeconds();
    double minutes = (double) seconds / 60;
    this.totalTypedChar = this.TIM.getTotalInputChars();
    this.wrongCharCount = this.TIM.getWrongCharCount();
    double WPM = this.wordsTyped / minutes;

    // Handle error with no typed
    if (this.totalTypedChar == 0)
      this.totalTypedChar += 1;

    double accuracy = (double) (this.totalTypedChar - this.wrongCharCount) / this.totalTypedChar;

    this.stats.applyRound(
        WPM,
        accuracy * 100,
        this.wrongCharCount,
        (int) seconds,
        this.scoreManager.getScore(),
        this.level.getNumber(),
        this.wordsTyped);
    dataManager.updateStats(user, stats);

    dataManager.getLeaderboard().updateLeaderboard(user, user.getStats().getHighScore());
    try {
      dataManager.getLeaderboard().saveLeaderboard();
    } catch (LeaderboardException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update lives hud
   */
  private void updateLives() {
    String liveSymbol = "\u2764".repeat(this.lifeSystem.getCurrentLives());
    this.livesLabel.setText(liveSymbol);
  }

  /**
   * Update hud for points
   */
  private void updatePoints() {
    this.pointsLabel.setText("Points: " + this.scoreManager.getScore());
  }

  /**
   * Periodically generate powerup based on chance
   */
  private void powerUpActive() {
    if (this.timer.getElapsedSeconds() < 15) {
      return;
    }
    // since the method is called each tick instead of each second, a control is
    // required
    if (this.timer.getElapsedSeconds() % 15 == 0 && !attempted) {
      attempted = true;
      this.PUM.startPowerUpTimer();
      boolean acquired = Math.random() < (60 - this.timer.getElapsedSeconds()) / 60.0;
      if (acquired) {
        PowerUpType type = PowerUpType.values()[ThreadLocalRandom.current().nextInt(PowerUpType.values().length)];
        this.PUM.gainPowerUp(type);
        this.PUM.activePowerUp(this.scoreManager, this.lifeSystem, this.gameLoop);

        showPowerUpHud(type);
      }
    }
    boolean stillActive = this.PUM.deactivatePowerUp(this.scoreManager, this.gameLoop);
    if (!stillActive) {
      hidePowerUpHud();
    }
    attempted = stillActive;
  }

  /**
   * Get the stats instance
   * 
   * @return player stats
   */
  public Stats getStats() {
    return this.stats;
  }

}

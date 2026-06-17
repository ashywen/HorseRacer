package horseracer.screens;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.service.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Represents the main menu screen of the game.
 * <p>
 * This screen serves as the entry point for users, providing
 * navigation options such as starting the game, viewing the
 * tutorial, accessing the leaderboard, and entering the
 * teacher dashboard. It also displays helpful gameplay tips
 * and team information.
 *
 * @author Kathy Yao, Ashley Deng
 * @version 1.0.0
 */
public class MainMenuScreen extends AbstractScreen {

  /**
   * Creates the main menu UI and initializes all components
   * and event handlers.
   *
   * @param screenManager the screen manager used for navigation
   * @param dataManager the data manager for application data
   * @param session the current session storing user state
   */
  public MainMenuScreen(ScreenManager screenManager,
                        DataManager dataManager,
                        Session session) {

    super(screenManager, dataManager, session);

    getStylesheets().add(getClass().getResource("/style/mainMenu.css").toExternalForm());
    Font.loadFont(getClass().getResourceAsStream("/fonts/VT323-Regular.ttf"), 20);
    Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 20);

    // ===== TITLE SECTION =====
    Label title = new Label("HORSE RACER");
    title.getStyleClass().add("title");

    Label slogan = new Label("TYPE FAST • JUMP HIGH • WIN BIG");
    slogan.getStyleClass().add("slogan");

    VBox topSection = new VBox(20, title, slogan);
    topSection.setAlignment(Pos.CENTER);

    // ===== LEFT MENU CARD =====
    VBox menuCard = new VBox(18);
    menuCard.setAlignment(Pos.TOP_CENTER);
    menuCard.setPadding(new Insets(28));
    menuCard.setPrefWidth(430);
    menuCard.getStyleClass().add("menu-card");

    Button startRace = new Button("START RACE");
    startRace.setPrefWidth(340);
    startRace.setPrefHeight(95);
    startRace.getStyleClass().add("start-button");

    Button tutorial = createSmallMenuButton("TUTORIAL");
    Button rankings = createSmallMenuButton("RANKINGS");

    HBox smallButtons = new HBox(20, tutorial, rankings);
    smallButtons.setAlignment(Pos.CENTER);

    Button teacherDashboard = createWideMenuButton("TEACHER DASHBOARD");

    Button exitButton = new Button("EXIT");
    exitButton.setPrefWidth(340);
    exitButton.setPrefHeight(56);
    exitButton.getStyleClass().add("profile-button");

    menuCard.getChildren().addAll(startRace, smallButtons, teacherDashboard, exitButton);

    // ===== RIGHT GUIDE PANEL =====
    VBox guidePanel = new VBox(26);
    guidePanel.setPadding(new Insets(28));
    guidePanel.setPrefWidth(540);
    guidePanel.setMinWidth(540);
    guidePanel.setMaxWidth(540);
    guidePanel.setAlignment(Pos.TOP_LEFT);
    guidePanel.getStyleClass().add("guide-panel");

    Label guideTitle = new Label("RACER'S GUIDE");
    guideTitle.getStyleClass().add("guide-title");

    Region line = new Region();
    line.setPrefHeight(1);
    line.getStyleClass().add("divider");

    VBox tip1 = createTip("amber", "TIP", "Type complete words to jump over obstacles.");
    VBox tip2 = createTip("blue", "TIP", "Don't stop typing! Speed increases every jump.");
    VBox tip3 = createTip("rose", "TIP", "3 collisions = Game Over. Stay focused!");

    VBox tipsBox = new VBox(24, tip1, tip2, tip3);
    tipsBox.setAlignment(Pos.TOP_LEFT);

    guidePanel.getChildren().addAll(guideTitle, line, tipsBox);

    // ===== MIDDLE SECTION =====
    HBox middleSection = new HBox(40, menuCard, guidePanel);
    middleSection.setAlignment(Pos.CENTER);

    // ===== BOTTOM INFO PANEL =====
    VBox infoBox = new VBox(8);
    infoBox.setAlignment(Pos.CENTER);
    infoBox.setPadding(new Insets(18, 28, 18, 28));
    infoBox.setMaxWidth(1020);
    infoBox.getStyleClass().add("info-panel");

    Label teamLine = new Label("Developers: Kathy, Riley, Ashley, Joanna, Tianrui");
    teamLine.getStyleClass().add("info-text");

    Label detailsLine = new Label("Team 81  •  Winter 2026  •  CS2212 at Western University");
    detailsLine.getStyleClass().add("info-text");

    infoBox.getChildren().addAll(teamLine, detailsLine);

    // ===== MAIN LAYOUT =====
    VBox page = new VBox(35, topSection, middleSection, infoBox);
    page.setAlignment(Pos.CENTER);
    page.setPadding(new Insets(40, 30, 20, 30));

    setCenter(page);

    // ===== BUTTON ACTIONS =====
    startRace.setOnAction(e -> screenManager.showLogin());
    tutorial.setOnAction(e -> screenManager.showTutorial());
    rankings.setOnAction(e -> screenManager.showLeaderboard());
    teacherDashboard.setOnAction(e -> screenManager.showTeacherLogin());
    exitButton.setOnAction(e -> screenManager.exit());
  }

  /**
   * Creates a smaller menu button used for secondary actions.
   *
   * @param text the button label
   * @return the configured button
   */
  private Button createSmallMenuButton(String text) {
    Button button = new Button(text);
    button.setPrefWidth(160);
    button.setPrefHeight(70);
    button.getStyleClass().add("small-button");
    return button;
  }

  /**
   * Creates a wide menu button used for main actions.
   *
   * @param text the button label
   * @return the configured button
   */
  private Button createWideMenuButton(String text) {
    Button button = new Button(text);
    button.setPrefWidth(340);
    button.setPrefHeight(70);
    button.getStyleClass().add("small-button");
    return button;
  }

  /**
   * Creates a tip component displayed in the guide panel.
   *
   * @param badgeColorClass the CSS class for badge color
   * @param badgeText the label for the badge
   * @param messageText the tip message
   * @return a VBox containing the formatted tip
   */
  private VBox createTip(String badgeColorClass,
                         String badgeText,
                         String messageText) {

    Label badge = new Label(badgeText);
    badge.getStyleClass().addAll("tip-badge", badgeColorClass);
    badge.setMinWidth(48);
    badge.setPrefWidth(48);
    badge.setMaxWidth(48);
    badge.setAlignment(Pos.CENTER);

    Text message = new Text(messageText);
    message.getStyleClass().add("tip-text-node");

    TextFlow messageFlow = new TextFlow(message);
    messageFlow.setPrefWidth(410);
    messageFlow.setMinWidth(410);
    messageFlow.setMaxWidth(410);
    messageFlow.getStyleClass().add("tip-text-flow");

    HBox row = new HBox(14, badge, messageFlow);
    row.setAlignment(Pos.TOP_LEFT);

    VBox box = new VBox(row);
    box.setAlignment(Pos.TOP_LEFT);
    return box;
  }
}
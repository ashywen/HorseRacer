package horseracer.screens.parentlogin;

import horseracer.app.ScreenManager;
import horseracer.data.exceptions.LoginInvalidException;
import horseracer.model.Level;
import horseracer.model.User;
import horseracer.screens.AbstractScreen;
import horseracer.data.DataManager;
import horseracer.service.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ParentLoginScreen extends AbstractScreen {

  public ParentLoginScreen(ScreenManager screenManager, DataManager dataManager, Session session) {
    super(screenManager, dataManager, session);

    getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());

    VBox pageWrapper = new VBox();
    pageWrapper.setAlignment(Pos.CENTER);
    pageWrapper.setPadding(new Insets(35));

    StackPane cardStack = new StackPane();
    cardStack.setAlignment(Pos.CENTER);

    Region shadow = new Region();
    shadow.setMaxSize(560, 650);
    shadow.setPrefSize(560, 650);
    shadow.setTranslateX(10);
    shadow.setTranslateY(10);
    shadow.getStyleClass().add("shadow");

    VBox card = new VBox(16);
    card.setAlignment(Pos.TOP_CENTER);
    card.setMaxWidth(560);
    card.setPrefWidth(560);
    card.setPadding(new Insets(36, 42, 36, 42));
    card.getStyleClass().add("body_overlay");

    Label title = new Label("HORSE RACER");
    title.setStyle(
        "-fx-font-family: 'VT323';" +
            "-fx-font-size: 42px;" +
            "-fx-text-fill: #F97316;" +
            "-fx-letter-spacing: 2px;");

    Label subtitle = new Label("Welcome back! Enter your details.");
    subtitle.setStyle(
        "-fx-font-family: 'JetBrains Mono';" +
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #6B7280;");

    HBox tabs = new HBox(110);
    tabs.setAlignment(Pos.CENTER);

    Label loginTab = new Label("LOGIN");
    loginTab.setStyle(
        "-fx-font-family: 'VT323';" +
            "-fx-font-size: 24px;" +
            "-fx-text-fill: #F97316;");

    HBox tabLine = new HBox(0);
    tabLine.setAlignment(Pos.CENTER);

    Region orangeLine = new Region();
    orangeLine.setPrefHeight(4);
    orangeLine.setPrefWidth(235);
    orangeLine.setStyle("-fx-background-color: #F97316;");

    tabLine.getChildren().addAll(orangeLine);

    Label usernameLabel = new Label("TEACHER NAME");
    usernameLabel.setMaxWidth(Double.MAX_VALUE);
    usernameLabel.setAlignment(Pos.CENTER_LEFT);
    usernameLabel.getStyleClass().add("input-header");

    TextField username = new TextField();
    username.setPromptText("SpeedyRacer");
    username.setPrefHeight(56);
    username.setStyle(
        "-fx-font-family: 'JetBrains Mono';" +
            "-fx-font-size: 16px;" +
            "-fx-text-fill: #4B5563;" +
            "-fx-prompt-text-fill: #9CA3AF;" +
            "-fx-background-color: #F9FAFB;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #D1D5DB;" +
            "-fx-border-radius: 8;" +
            "-fx-border-width: 2;" +
            "-fx-padding: 0 14 0 14;");

    Label passwordLabel = new Label("PASSWORD");
    passwordLabel.setMaxWidth(Double.MAX_VALUE);
    passwordLabel.setAlignment(Pos.CENTER_LEFT);
    passwordLabel.getStyleClass().add("input-header");

    PasswordField password = new PasswordField();
    password.setPromptText("••••••••");
    password.setPrefHeight(56);
    password.getStyleClass().add("password-input");

    Label feedback = new Label();
    feedback.setMaxWidth(Double.MAX_VALUE);
    feedback.setAlignment(Pos.CENTER_LEFT);
    feedback.setStyle(
        "-fx-font-family: 'JetBrains Mono';" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #B91C1C;");

    Button login = new Button("MANAGE USERS  ▷");
    login.setPrefWidth(420);
    login.setPrefHeight(64);
    login.getStyleClass().add("play-button");

    Button back = new Button("BACK");
    back.setPrefWidth(420);
    back.setPrefHeight(48);
    back.getStyleClass().add("back-button");

    VBox.setMargin(login, new Insets(12, 0, 0, 0));

    card.getChildren().addAll(
        title,
        subtitle,
        tabs,
        tabLine,
        usernameLabel,
        username,
        passwordLabel,
        password,
        feedback,
        login,
        back);

    tabs.getChildren().addAll(loginTab);
    cardStack.getChildren().addAll(shadow, card);
    pageWrapper.getChildren().add(cardStack);
    setCenter(pageWrapper);

    Runnable submit = () -> {
      User user = null;

      // Validate login
      try {
        user = dataManager.validateParent(username.getText().strip(), password.getText().strip());
      } catch (LoginInvalidException e) {
        System.out.println(e);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      if (user == null) {
        feedback.setText("Invalid player username or password.");
        return;
      }
      session.setCurrentUser(user);

      Level l = Level.easy();
      screenManager.showTeacherDashboard();
    };

    login.setOnAction(e -> submit.run());
    back.setOnAction(e -> screenManager.showMainMenu());

    setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        screenManager.showMainMenu();
      }
      if (e.getCode() == KeyCode.ENTER) {
        submit.run();
      }
    });
  }
}

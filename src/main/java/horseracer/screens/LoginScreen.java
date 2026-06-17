package horseracer.screens;

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

/**
 * Represents the login screen of the application.
 * <p>
 * This screen allows users to enter their username and password
 * to authenticate. If login is successful, the user is stored in
 * the {@link Session} and redirected to the player screen.
 * Otherwise, an error message is displayed.
 *
 * @author Kathy Yao, Ashley Deng
 * @version 1.0.0
 */
public class LoginScreen extends AbstractScreen {
  
  /**
     * Creates the login screen UI and initializes all components
     * and event handlers.
     *
     * @param screenManager the screen manager used for navigation
     * @param dataManager the data manager used for validating login
     * @param session the current session storing user state
     */
  public LoginScreen(ScreenManager screenManager, DataManager dataManager, Session session) {
        super(screenManager, dataManager, session);
        getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());
        getStyleClass().add("screen");

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
        card.getStyleClass().add("card");

        Label title = new Label("HORSE RACER");
        title.getStyleClass().add("title");

        Label subtitle = new Label("Welcome back! Enter your details.");
        subtitle.getStyleClass().add("subtitle");

    HBox tabs = new HBox(110);
    tabs.setAlignment(Pos.CENTER);
        Label loginTab = new Label("LOGIN");
        loginTab.getStyleClass().add("tab-active");

        Label signUpTab = new Label("SIGN UP");
        signUpTab.getStyleClass().add("tab-inactive");

    HBox tabLine = new HBox(0);
    tabLine.setAlignment(Pos.CENTER);

        Region orangeLine = new Region();
        orangeLine.setPrefHeight(4);
        orangeLine.setPrefWidth(235);
        orangeLine.getStyleClass().add("accent-line");

        Region grayLine = new Region();
        grayLine.setPrefHeight(4);
        grayLine.setPrefWidth(235);
        grayLine.getStyleClass().add("login-gray-line");

    tabLine.getChildren().addAll(orangeLine, grayLine);

        Label usernameLabel = new Label("PLAYER NAME");
        usernameLabel.setMaxWidth(Double.MAX_VALUE);
        usernameLabel.setAlignment(Pos.CENTER_LEFT);
        usernameLabel.getStyleClass().add("field-label");

        TextField username = new TextField();
        username.setPromptText("SpeedyRacer");
        username.setPrefHeight(56);
        username.getStyleClass().add("login-text-field");

        Label passwordLabel = new Label("PASSWORD");
        passwordLabel.setMaxWidth(Double.MAX_VALUE);
        passwordLabel.setAlignment(Pos.CENTER_LEFT);
        passwordLabel.getStyleClass().add("field-label");

        PasswordField password = new PasswordField();
        password.setPromptText("••••••••");
        password.setPrefHeight(56);
        password.getStyleClass().add("login-text-field");

        Label feedback = new Label();
        feedback.setMaxWidth(Double.MAX_VALUE);
        feedback.setAlignment(Pos.CENTER_LEFT);
        feedback.getStyleClass().add("error-label");

        Button login = new Button("START PLAYING  ▷");
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

    tabs.getChildren().addAll(loginTab, signUpTab);
    signUpTab.setOnMouseClicked(e -> screenManager.showSignUp());
    cardStack.getChildren().addAll(shadow, card);
    pageWrapper.getChildren().add(cardStack);
    setCenter(pageWrapper);

    Runnable submit = () -> {
      User user = null;
      try {
        user = dataManager.validateLogin(username.getText().strip(), password.getText().strip());
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
      screenManager.showPlayerScreen();
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

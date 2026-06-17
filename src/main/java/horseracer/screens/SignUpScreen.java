package horseracer.screens;

import horseracer.app.ScreenManager;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import horseracer.model.Role;
import horseracer.model.User;
import horseracer.data.exceptions.CreateAccountException;

/**
 * Represents the sign-up screen for creating a new player account.
 * <p>
 * This screen allows users to enter a username and password,
 * validates input fields, and creates a new account using the
 * DataManager. Upon successful creation, the user is redirected
 * to the login screen.
 *
 * @author Kathy Yao, Ashley Deng
 * @version 1.0.0
 */
public class SignUpScreen extends AbstractScreen {

    /**
     * Creates the sign-up screen UI and initializes all components
     * and event handlers.
     *
     * @param screenManager the screen manager used for navigation
     * @param dataManager the data manager for account creation
     * @param session the current session storing user state
     */
    public SignUpScreen(ScreenManager screenManager, DataManager dataManager, Session session) {
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

        Label subtitle = new Label("Create your account to start racing.");
        subtitle.getStyleClass().add("subtitle");

        HBox tabs = new HBox(110);
        tabs.setAlignment(Pos.CENTER);

        Label loginTab = new Label("LOGIN");
        loginTab.getStyleClass().add("tab-inactive");

        Label signUpTab = new Label("SIGN UP");
        signUpTab.getStyleClass().add("tab-active");

        HBox tabLine = new HBox(0);
        tabLine.setAlignment(Pos.CENTER);

        Region grayLine = new Region();
        grayLine.setPrefHeight(4);
        grayLine.setPrefWidth(235);
        grayLine.getStyleClass().add("login-gray-line");

        Region orangeLine = new Region();
        orangeLine.setPrefHeight(4);
        orangeLine.setPrefWidth(235);
        orangeLine.getStyleClass().add("accent-line");

        tabLine.getChildren().addAll(grayLine, orangeLine);

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

        Label confirmPasswordLabel = new Label("CONFIRM PASSWORD");
        confirmPasswordLabel.setMaxWidth(Double.MAX_VALUE);
        confirmPasswordLabel.setAlignment(Pos.CENTER_LEFT);
        confirmPasswordLabel.getStyleClass().add("field-label");

        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("••••••••");
        confirmPassword.setPrefHeight(56);
        confirmPassword.getStyleClass().add("login-text-field");

        Label feedback = new Label();
        feedback.setMaxWidth(Double.MAX_VALUE);
        feedback.setAlignment(Pos.CENTER_LEFT);
        feedback.getStyleClass().add("error-label");

        Button signUp = new Button("CREATE ACCOUNT  ▷");
        signUp.setPrefWidth(420);
        signUp.setPrefHeight(64);
        signUp.getStyleClass().add("play-button");

        Button back = new Button("BACK");
        back.setPrefWidth(420);
        back.setPrefHeight(48);
        back.getStyleClass().add("back-button");

        VBox.setMargin(signUp, new Insets(12, 0, 0, 0));

        card.getChildren().addAll(
                title,
                subtitle,
                tabs,
                tabLine,
                usernameLabel,
                username,
                passwordLabel,
                password,
                confirmPasswordLabel,
                confirmPassword,
                feedback,
                signUp,
                back
        );

        tabs.getChildren().addAll(loginTab, signUpTab);
        loginTab.setOnMouseClicked(e -> screenManager.showLogin());
        cardStack.getChildren().addAll(shadow, card);
        pageWrapper.getChildren().add(cardStack);
        setCenter(pageWrapper);

        loginTab.setOnMouseClicked(e -> screenManager.showLogin());
        back.setOnAction(e -> screenManager.showMainMenu());

        /**
         * Handles sign-up submission.
         * <p>
         * Validates input fields, checks password confirmation,
         * creates a new user account, and navigates to login on success.
         */
        Runnable submit = () -> {
            String userText = username.getText().trim();
            String passText = password.getText();
            String confirmText = confirmPassword.getText();

            if (userText.isEmpty() || passText.isEmpty() || confirmText.isEmpty()) {
                feedback.setText("Please fill in all fields.");
                return;
            }

            if (!passText.equals(confirmText)) {
                feedback.setText("Passwords do not match.");
                return;
            }

            try {
                User newUser = new User(userText, passText, Role.PLAYER);
                dataManager.createAccount(newUser);
            } catch (CreateAccountException e) {
                feedback.setText("Username is already taken.");
                return;
            } catch (Exception e) {
                feedback.setText("Could not create account.");
                return;
            }

            feedback.setText("Account created successfully.");
            screenManager.showLogin();
        };

        signUp.setOnAction(e -> submit.run());

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
package horseracer.screens.ParentControl.ResetPassword.utils;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.utils.GreenButton;
import horseracer.screens.utils.OrangeButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoadScreen {
  public static VBox create(StackPane root, User user, DataManager dataManager, ScreenManager screenManager) {

    // Creating a Grid Pane
    VBox gridPane = new VBox(10);

    // Setting size for the pane
    gridPane.setMinSize(400, 400);

    // Add Styling
    gridPane.getStyleClass().add("body_overlay");

    // Setting the padding
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    // Setting the Grid alignment
    gridPane.setAlignment(Pos.CENTER);

    // Title
    Label screen_title = new Label("Reset " + user.getUsername() + "'s password");
    screen_title.getStyleClass().add("screen-title");

    // First password input
    Text password_txt = new Text("New Password");
    password_txt.getStyleClass().add("ac-form-text");

    PasswordField password_input = new PasswordField();
    password_input.setPrefHeight(56);
    password_input.getStyleClass().add("password-input");

    // Secondg password input
    Text confirm_password = new Text("Confirm New Password");
    confirm_password.getStyleClass().add("ac-form-text");

    PasswordField confirm_password_input = new PasswordField();
    confirm_password_input.setPrefHeight(56);
    confirm_password_input.getStyleClass().add("password-input");

    // Warning Label
    Label error_label = new Label("Passwords do not match!");
    error_label.getStyleClass().add("error-label");
    error_label.setVisible(false);

    // Enter button
    Button enter_button = GreenButton.create("RESET PASSWORD");
    enter_button.setOnAction(e -> {

      String password = password_input.getText();
      String password_confirm = confirm_password_input.getText();
      // Check matches
      if (password.length() > 0 && password.equals(password_confirm)) {

        ResetButton.action(user, dataManager, root, password, password_input, confirm_password_input, gridPane);
      } else {
        error_label.setVisible(true);
      }

    });

    // Back Button
    Button back_button = OrangeButton.create("BACK");
    back_button.setOnAction(e -> {
      screenManager.showStudentControl(user);
    });

    // Arranging all the nodes in the grid
    gridPane.getChildren().addAll(
        screen_title,
        password_txt,
        password_input,
        confirm_password,
        confirm_password_input,
        error_label,
        enter_button,
        back_button);

    return gridPane;
  }
}

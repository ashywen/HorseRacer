package horseracer.screens.accountCreation.utils;

import horseracer.screens.utils.ErrorLabel;
import horseracer.screens.utils.GreenButton;
import horseracer.screens.utils.OrangeButton;
import horseracer.screens.utils.TextLoader;

import horseracer.model.Role;
import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.data.exceptions.CreateAccountException;
import horseracer.model.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class FormBody {
  private static int spacing = 10;

  /**
   * Create the body of the form
   *
   * @param manager      the screen manager
   * @param data_manager the datamanager
   * 
   * @return VBox that contains all the form elements
   *
   */
  public static VBox CreatePane(ScreenManager manager, DataManager data_manager, VBox root) {
    VBox form_body = new VBox(spacing);

    form_body.setMinSize(300, 300);
    form_body.setAlignment(Pos.CENTER);
    form_body.setPadding(new Insets(30, 30, 30, 30));

    // Aadd Styling
    form_body.getStyleClass().add("body_overlay");

    // Title
    Label title_lbl = new Label("Create Account");
    title_lbl.getStyleClass().add("screen-title");

    // Username text
    Label username_text = TextLoader.create("Username", "input-header");
    username_text.setAlignment(Pos.CENTER_LEFT);

    // Username field
    TextField username_field = CreateTextField("");
    GridPane.setHalignment(username_field, HPos.CENTER);
    username_field.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
    username_field.setMaxWidth(Region.USE_PREF_SIZE);

    //
    Label password_text = TextLoader.create("Password", "input-header");
    password_text.setAlignment(Pos.CENTER_LEFT);

    // Password field
    TextField password_field = CreatePassword();
    GridPane.setHalignment(password_field, HPos.CENTER);
    password_field.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
    password_field.setMaxWidth(Region.USE_PREF_SIZE);

    // RAdio button group
    HBox radio_buttons = new HBox(50);
    radio_buttons.setAlignment(Pos.CENTER);

    // Need radio buttons
    final ToggleGroup group = new ToggleGroup();

    // User radio button
    RadioButton user_button = new RadioButton("User");
    user_button.getStyleClass().add("radio-button");
    user_button.setToggleGroup(group);
    user_button.setSelected(true);
    user_button.setFocusTraversable(false);
    user_button.setCursor(Cursor.HAND);

    // Parent radio button
    RadioButton parent_button = new RadioButton("Parent");
    parent_button.getStyleClass().add("radio-button");
    parent_button.setToggleGroup(group);
    parent_button.setFocusTraversable(false);
    parent_button.setCursor(Cursor.HAND);

    // Add to button row
    radio_buttons.getChildren().addAll(user_button, parent_button);

    // Error label
    Label error_label = ErrorLabel.create("Username and Password can not be empty");
    error_label.setVisible(false);

    // Submit butotn
    Button submit_button = GreenButton.create("CREATE ACCOUNT");
    submit_button.setOnAction(e -> {
      // Clear any previous error
      error_label.setVisible(false);

      // Set Role
      Role role;
      if (user_button.isSelected()) {
        role = Role.PLAYER;
      } else {
        role = Role.TEACHER;
      }

      // Get information
      String username = username_field.getText().strip();
      String password = password_field.getText().strip();

      // Check valid
      if (username.length() == 0 || password.length() == 0) {
        error_label.setText("Username and Password can not be empty");
        error_label.setVisible(true);
      } else {
        User new_user = new User(username, password, role);
        try {
          // Create account and return to studnet control screen
          data_manager.createAccount(new_user);
          manager.showTeacherDashboard();
        } catch (CreateAccountException err) {
          error_label.setText(err.toString());
          error_label.setVisible(true);
        }
      }

    });

    // Back button
    Button back_button = OrangeButton.create("BACK");
    back_button.setOnAction(e -> {
      manager.showTeacherDashboard();
    });
    Scene sc = root.getScene();
    back_button.sceneProperty().addListener((obs, oldScene, scene) -> {
      if (scene != null) {
        back_button.prefWidthProperty().bind(scene.widthProperty().multiply(0.3));
        back_button.prefHeightProperty().bind(scene.heightProperty().multiply(0.05));
      }
    });
    submit_button.sceneProperty().addListener((obs, oldScene, scene) -> {
      if (scene != null) {
        submit_button.prefWidthProperty().bind(scene.widthProperty().multiply(0.3));
        submit_button.prefHeightProperty().bind(scene.heightProperty().multiply(0.05));
      }
    });

    // Add children
    form_body.getChildren().addAll(
        title_lbl,
        username_text,
        username_field,
        password_text,
        password_field,
        radio_buttons,
        error_label,
        submit_button,
        back_button);

    return form_body;

  }

  public static PasswordField CreatePassword() {
    PasswordField pf = new PasswordField();
    pf.setPrefHeight(56);
    pf.getStyleClass().add("password-input");

    return pf;
  }

  public static TextField CreateTextField(String placeholder) {
    TextField text_field = new TextField();
    text_field.setPrefHeight(56);
    text_field.getStyleClass().add("password-input");
    return text_field;

  }

}

package horseracer.screens.ParentControl.ResetPassword;

import horseracer.app.ScreenManager;
import horseracer.service.Session;
import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.AbstractScreen;
import horseracer.screens.ParentControl.ResetPassword.utils.LoadScreen;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ResetPasswordScreen extends AbstractScreen {

  /**
   * Constructor
   */
  public ResetPasswordScreen(ScreenManager screenManager, DataManager dataManager, Session session, User user) {

    super(screenManager, dataManager, session);
    getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

    StackPane root = new StackPane();
    root.setMinSize(400, 400);

    VBox content = LoadScreen.create(root, user, dataManager, screenManager);

    content.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    root.getChildren().addAll(content);

    setCenter(root);

    root.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        screenManager.showTeacherDashboard();
      }
    });
  }

}

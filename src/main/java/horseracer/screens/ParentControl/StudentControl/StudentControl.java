package horseracer.screens.ParentControl.StudentControl;

import horseracer.app.ScreenManager;
import horseracer.service.Session;
import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.AbstractScreen;
import horseracer.screens.ParentControl.StudentControl.utils.StudentData;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StudentControl extends AbstractScreen {

  /**
   * Constructor
   *
   * @param user user the studentcontrol screen will showcase
   */
  public StudentControl(ScreenManager screenManager, DataManager data_manager, Session session, User user) {
    super(screenManager, data_manager, session);
    getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

    StackPane root = new StackPane();
    root.setMinSize(400, 400);

    VBox content = StudentData.create(screenManager, data_manager, user, root);
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

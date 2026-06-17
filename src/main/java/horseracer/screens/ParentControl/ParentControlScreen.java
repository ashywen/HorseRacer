package horseracer.screens.ParentControl;

import horseracer.screens.ParentControl.utils.LoadScreen;
import horseracer.app.ScreenManager;
import horseracer.service.Session;
import horseracer.data.DataManager;
import horseracer.screens.AbstractScreen;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ParentControlScreen extends AbstractScreen {

  /**
   * Constructor
   */
  public ParentControlScreen(ScreenManager screenManager, DataManager dataManager, Session session) {

    super(screenManager, dataManager, session);
    getStylesheets().add(getClass().getResource("/style/dashboard.css").toExternalForm());

    StackPane root = new StackPane();
    root.setMinSize(400, 400);
    // root.setPrefWidth(600);

    VBox content = LoadScreen.create(screenManager, dataManager, root, session);
    content.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

    root.getChildren().addAll(content);

    setCenter(root);
    root.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        screenManager.showMainMenu();
        session.clear();
      }
    });

  }

}

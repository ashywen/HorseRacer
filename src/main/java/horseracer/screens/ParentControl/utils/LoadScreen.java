package horseracer.screens.ParentControl.utils;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.service.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoadScreen {

  /**
   * Construct the parental control screen
   * 
   * @param title         title of thes screen
   * @param screenManager the screen manager
   *
   * @return Parent an visual object containing all frontend components relating
   *         to the partental control screen
   */
  public static VBox create(ScreenManager screenManager, DataManager data_manager, StackPane root, Session session) {

    // Creating a Grid Pane
    VBox gridPane = new VBox(50);

    // Setting size for the pane
    // gridPane.setMinSize(400, 400);

    // Add Styling
    gridPane.getStyleClass().add("body_overlay");
    gridPane.setPrefWidth(600);

    // Setting the padding
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    // Setting the Grid alignment
    gridPane.setAlignment(Pos.CENTER);

    // Title
    Label screen_title = new Label("Parent Dashboard");
    screen_title.getStyleClass().add("screen-title");

    // Form bidy
    VBox form_body = StudentList.create(screenManager, data_manager, root, session);

    // Arranging all the nodes in the grid
    gridPane.getChildren().addAll(screen_title, form_body);

    return gridPane;
  }
}

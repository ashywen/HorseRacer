package horseracer.screens.ParentControl.utils;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.ParentControl.StudentControl.StudentControl;
import horseracer.screens.utils.ImageLoader;
import horseracer.screens.utils.TextLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StudentCard {

  /**
   * build a studentcard component in javafx
   *
   * @param student a user class that the student card will showcase
   * @return VBox object that represents the student card with an image and
   *         username
   */
  public static VBox build(User student, ScreenManager manager, DataManager data_manager) {
    VBox grid_pane = new VBox();

    // Add hand
    grid_pane.setCursor(Cursor.HAND);

    // Styling
    grid_pane.getStyleClass().add("student-card");
    grid_pane.setAlignment(Pos.CENTER);
    grid_pane.setMinSize(100, 100);

    // image
    ImageView img = ImageLoader.create("/assets/Person.png");
    img.setFitWidth(60);
    img.setFitHeight(60);

    // Text
    Label name_text = TextLoader.create(student.getUsername(), "sc-text");

    // Add
    grid_pane.getChildren().addAll(img, name_text);

    grid_pane.setOnMouseClicked(e -> {
      manager.showStudentControl(student);
    });

    return grid_pane;

  }

}

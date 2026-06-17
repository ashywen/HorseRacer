package horseracer.screens.ParentControl.utils;

import java.util.ArrayList;
import java.util.Iterator;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.model.User;
import horseracer.screens.utils.OrangeButton;
import horseracer.service.Session;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StudentList {
  /**
   * Create the box that displays the list of students for a parent in JavaFX
   *
   * @param manager the screenmanager
   *
   * @return GridPane that is the visual student list
   */
  public static VBox create(ScreenManager manager, DataManager data_manager, StackPane root, Session session) {
    // StudentList
    try {
      ArrayList<User> users = data_manager.getUsers();

      VBox grid_pane = new VBox(50);

      // Grid for student cards
      GridPane student_list = new GridPane();
      student_list.setVgap(10);
      student_list.setHgap(10);

      student_list.setAlignment(Pos.CENTER);

      // Add student cards into grid
      int row = 0;
      int col = 0;
      int max_col = 4;
      Iterator<User> iter = users.iterator();
      while (iter.hasNext()) {
        User user = iter.next();
        VBox card = StudentCard.build(user, manager, data_manager);
        student_list.add(card, col, row);
        col += 1;
        if (col == max_col) {
          row += 1;
          col = 0;
        }

      }

      // Button horizontal row
      VBox button_layout = new VBox(10);
      button_layout.setAlignment(Pos.CENTER);

      // Back button
      Button back_button = OrangeButton.create("LOGOUT");
      back_button.setOnAction(e -> {
        // Return to the mainmenu
        session.clear();
        manager.showTeacherLogin();
      });
      back_button.setPrefWidth(400);

      // Create Account Button
      Button create_button = OrangeButton.create("CREATE ACCOUNT");
      create_button.setOnAction(e -> {
        manager.showCreateAccount();
      });
      create_button.setPrefWidth(400);

      // Reset Leaderboard button
      Button reset_lb = OrangeButton.create("RESET LEADERBOARD");
      reset_lb.setOnAction(e -> {
        ResetButton.action(data_manager, root);
      });
      reset_lb.setPrefWidth(400);

      button_layout.getChildren().addAll(create_button, reset_lb, back_button);

      grid_pane.getChildren().addAll(student_list, button_layout);

      return grid_pane;
    } catch (Exception e) {
      return new VBox();
    }

  }
}

package horseracer.screens.accountCreation;

import horseracer.app.ScreenManager;
import horseracer.service.Session;
import horseracer.data.DataManager;
import horseracer.screens.AbstractScreen;
import horseracer.screens.accountCreation.utils.FormBody;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Represents the account creation screen.
 * <p>
 * This screen provides a form for creating new user accounts.
 * The UI is generated using a helper utility and allows users
 * to input account details such as username and password.
 *
 * @author Riley Wong
 * @version 1.0.0
 */
public class AccountCreationScreen extends AbstractScreen {

  /**
   * Creates the account creation screen UI.
   *
   * @param screenManager the screen manager used for navigation
   * @param data_manager the data manager used to create accounts
   * @param session the current session storing user state
   */
  public AccountCreationScreen(ScreenManager screenManager,
                               DataManager data_manager,
                               Session session) {

    super(screenManager, data_manager, session);
    getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

    VBox gridPane = new VBox();
    gridPane.setMinSize(400, 200);
    gridPane.setPadding(new Insets(30, 30, 30, 30));
    gridPane.setAlignment(Pos.CENTER);

    setCenter(gridPane);

    VBox form_body = FormBody.CreatePane(screenManager, data_manager, gridPane);
    form_body.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

    gridPane.getChildren().add(form_body);
  }
}
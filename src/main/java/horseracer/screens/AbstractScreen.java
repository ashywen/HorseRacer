package horseracer.screens;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.service.Session;
import javafx.scene.layout.BorderPane;

/**
 * Abstract base class for all screens in the application.
 * <p>
 * This class provides shared access to core components such as
 * the ScreenManager, DataManager, and Session, and serves as a
 * common layout using BorderPane.
 *
 * @author Kathy Yao, Riley Wong, Ashley Deng
 * @version 1.0.0
 */
public abstract class AbstractScreen extends BorderPane {

  /** The screen manager used for navigation between screens */
  protected final ScreenManager screenManager;

  /** The data manager used for accessing and updating data */
  protected final DataManager dataManager;

  /** The session storing the current user's state */
  protected final Session session;

  /**
   * Creates an AbstractScreen with shared dependencies.
   *
   * @param screenManager the screen manager for navigation
   * @param dataManager the data manager for data operations
   * @param session the current session
   */
  protected AbstractScreen(ScreenManager screenManager, DataManager dataManager, Session session) {
    this.screenManager = screenManager;
    this.dataManager = dataManager;
    this.session = session;
  }
}
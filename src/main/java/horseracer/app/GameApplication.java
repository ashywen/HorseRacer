package horseracer.app;

import horseracer.data.DataManager;
import horseracer.service.Session;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the Horse Racer game application.
 * <p>
 * This class initializes the main components of the game,
 * including the data manager, session, and screen manager,
 * and launches the JavaFX application.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class GameApplication extends Application {

    /**
     * Starts the JavaFX application.
     * <p>
     * Initializes core components and displays the main menu.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        try {
            DataManager dataManager = new DataManager();
            Session session = new Session();
            ScreenManager screenManager = new ScreenManager(stage, dataManager, session);
            screenManager.start();
            screenManager.showMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
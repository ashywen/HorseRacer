package horseracer.GameLogic;
import javafx.application.Platform;
public class JavaFXTestBase {
    static {
        Platform.startup(() -> {});
    }

}

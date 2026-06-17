package horseracer.GameLogic;
import horseracer.model.Difficulty;
import horseracer.model.Word;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TypingInputManagerTest extends JavaFXTestBase{
    private static TypingInputManager typingInputManager;
    private static Word word;
    private static ObstacleSpawner os;
    private static StackPane root = new StackPane();

    @BeforeAll
    static void setUp(){
        word = new Word("word", Difficulty.EASY, 100);
        os = new ObstacleSpawner("/assets/Easy.txt", 1500, root);
        typingInputManager = new TypingInputManager(word, os);
    }

    @Test
    @Order(1)
    public void testCheckCharacter_correctInput(){
        assertTrue(typingInputManager.checkCharacter(KeyCode.W));
    }

    @Test
    @Order(2)
    public void testCheckCharacter_incorrectInput(){
        assertFalse(typingInputManager.checkCharacter(KeyCode.A));
    }

    @Test
    @Order(3)
    public void testWrongCharCount(){
        assertEquals(1, typingInputManager.getWrongCharCount());
    }

    @Test
    @Order(4)
    public void testInputChars(){
        assertEquals(2, typingInputManager.getTotalInputChars());
    }

    @Test
    @Order(5)
    public void testUpdateWord(){
        typingInputManager.updateWord(new Word("update", Difficulty.MEDIUM, 100));
        assertTrue(typingInputManager.checkCharacter(KeyCode.U));
    }
}
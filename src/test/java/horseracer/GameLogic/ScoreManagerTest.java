package horseracer.GameLogic;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScoreManagerTest{
    private static ScoreManager scoreManager;

    @BeforeAll
    public static void setUp(){
        scoreManager = new ScoreManager();
    }

    @Test
    @Order(1)
    public void testAddPoints(){
        scoreManager.addPoints(10);
        assertEquals(10, scoreManager.getScore());
    }

    @Test
    @Order(2)
    public void testApplyMultiplier(){
        scoreManager.applyMultiplier();
        scoreManager.addPoints(10);
        assertEquals(30, scoreManager.getScore());
    }

    @Test
    @Order(3)
    public void testRestoreMultiplier(){
        scoreManager.restoreMultiplier();
        scoreManager.addPoints(10);
        assertEquals(40, scoreManager.getScore());
    }

    @Test
    @Order(4)
    public void testRestorePoints(){
        scoreManager.resetScore();
        assertEquals(0, scoreManager.getScore());
    }
}

package horseracer.GameLogic;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LifeSystemTest{
    private static LifeSystem lifeSystem;
    @BeforeAll
    public static void setUp(){
        lifeSystem = new LifeSystem(0);
    }

    @Test
    @Order(1)
    public void isGameOverTest(){
        assertEquals(true, lifeSystem.isGameOver());
    }

    @Test
    @Order(2)
    public void gainLifeTest(){
        lifeSystem.gainLife();
        assertEquals(false, lifeSystem.isGameOver());
    }

    @Test
    @Order(3)
    public void loseLifeTest(){
        lifeSystem.loseLife();
        assertEquals(true, lifeSystem.isGameOver());
    }

}

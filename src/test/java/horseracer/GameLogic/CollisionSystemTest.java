 package horseracer.GameLogic;
 import horseracer.model.Difficulty;
 import horseracer.model.Word;
 import horseracer.screens.HorseView;
 import horseracer.screens.ObstacleNode;
 import javafx.scene.layout.StackPane;
 import org.junit.jupiter.api.*;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertTrue;

 @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 public class CollisionSystemTest extends JavaFXTestBase {
     private static CollisionSystem collisionSystem;
     private static Word word = new Word("Hello", Difficulty.EASY, 10);
     private static HorseView horseView;
     private static ObstacleNode obstacle;
     private static StackPane scene = new StackPane();

     @BeforeAll
     public static void setUp(){
         collisionSystem = new CollisionSystem();
         horseView = new HorseView(scene);
         obstacle = new ObstacleNode(word, 0, 0, scene);
     }

     @Test
     @Order(1)
     public void collisionTest_1(){
         assertEquals(true, collisionSystem.checkCollision(horseView, obstacle));
     }

     @Test
     @Order(2)
     public void collisionTest_2(){
         obstacle = new ObstacleNode(word, 111, 0, scene);
         assertEquals(false, collisionSystem.checkCollision(horseView, obstacle));
     }

     @Test
     @Order(3)
     public void collisionTest_3(){
         obstacle = new ObstacleNode(word, 110, 0, scene);
         assertTrue(collisionSystem.checkCollision(horseView, obstacle));
     }

     @Test
     @Order(4)
     public void collisionTest_4(){
         obstacle = new ObstacleNode(word, 109, 0, scene);
         assertTrue(collisionSystem.checkCollision(horseView, obstacle));
     }
 }

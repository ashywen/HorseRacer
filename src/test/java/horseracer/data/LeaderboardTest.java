package horseracer.data;

import horseracer.data.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.MethodOrderer;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import horseracer.model.User;
import java.util.Iterator;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class LeaderboardTest {

  private Leaderboard test_lb;

  static Stream<Arguments> testScores() {
    return Stream.of(
        Arguments.of("name1", 1),
        Arguments.of("name2", 10),
        Arguments.of("RedDitto", 5),
        Arguments.of("name3", 15),
        Arguments.of("name4", 12));
  }

  @Test
  @Order(1)
  void initial() {
    assertDoesNotThrow(() -> {
      test_lb = new Leaderboard();
      test_lb.resetLeaderboard();
    });
  }

  // Check leaderboard is read properly from csv
  @Test
  @Order(6)
  void loadLeaderboard() {
    assertDoesNotThrow(() -> {
      test_lb = new Leaderboard();
    });
  }

  // Leaderboard can be reset properly
  @Test
  @Order(5)
  void resetLeaderboard() {
    assertDoesNotThrow(() -> {
      Leaderboard lb = new Leaderboard();
      lb.resetLeaderboard();
    });
  }

  @ParameterizedTest
  @MethodSource("testScores")
  @Order(3)
  void addScores(String username, int score) {
    assertDoesNotThrow(() -> {
      User new_user = new User();
      new_user.setUsername(username);
      test_lb.updateLeaderboard(new_user, score);

    });

  }

  @Test
  @Order(4)
  void checkScore() {

    Iterator<LeaderboardRow> iter = test_lb.getIter();

    iter.hasNext();

    assertEquals(15, iter.next().getScore());

    iter.hasNext();
    assertEquals(12, iter.next().getScore());
    //
    iter.hasNext();
    assertEquals(10, iter.next().getScore());
    //
    iter.hasNext();
    assertEquals(5, iter.next().getScore());

    iter.hasNext();
    assertEquals(1, iter.next().getScore());
  }

}

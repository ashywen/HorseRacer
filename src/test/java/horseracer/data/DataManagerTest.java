package horseracer.data;

import horseracer.data.*;
import horseracer.data.exceptions.*;
import horseracer.model.Role;
import horseracer.model.User;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.util.Iterator;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class DataManagerTest {
  private DataManager manager;

  private static Stream<Arguments> invalidLogins() {
    return Stream.of(
        Arguments.of("name1", "pass1"),
        Arguments.of("name1", "pass1"),
        Arguments.of("RedDitto", "pass1"),
        Arguments.of("name2", "StrongPassword123"),
        Arguments.of("name1", "pass1"));
  }

  private static Stream<Arguments> validLogins() {
    return Stream.of(
        Arguments.of("RedDitto", "test123"),
        Arguments.of("Parent", "pass"),
        Arguments.of("Symm", "StrongPassword123"));
  }

  private static Stream<Arguments> validAccounts() {
    User test_user = new User("Faust", "pass", Role.PLAYER);
    User test_parent = new User("Meursault", "pass", Role.TEACHER);
    return Stream.of(
        Arguments.of(test_user),
        Arguments.of(test_parent));
  }

  private static Stream<Arguments> invalidAccounts() {
    User test_user = new User("RedDitto", "pass", Role.PLAYER);
    User test_parent = new User("Parent", "pass", Role.TEACHER);
    return Stream.of(
        Arguments.of(test_user),
        Arguments.of(test_parent));
  }

  private static Stream<Arguments> removeAccounts() {
    return Stream.of(
        Arguments.of("Faust"),
        Arguments.of("Meursault"));
  }

  @Test
  @Order(1)
  void loadManager() {
    assertDoesNotThrow(() -> {
      this.manager = new DataManager();
    });

  }

  @ParameterizedTest
  @MethodSource("invalidLogins")
  @Order(2)
  void invalidValidateLogin(String username, String password) {

    assertThrows(LoginInvalidException.class, () -> {
      manager.validateLogin(username, password);
    });

  }

  @ParameterizedTest
  @MethodSource("validLogins")
  @Order(3)
  void validValidateLogin(String username, String password) {
    assertDoesNotThrow(() -> {
      manager.validateLogin(username, password);
    });
  }

  @ParameterizedTest
  @MethodSource("validAccounts")
  @Order(4)
  void createValidAccount(User newUser) {
    assertDoesNotThrow(() -> {
      manager.createAccount(newUser);
    });
  }

  @ParameterizedTest
  @MethodSource("invalidAccounts")
  @Order(5)
  void createInvalidAccount(User newUser) {
    assertThrows(CreateAccountException.class, () -> {
      manager.createAccount(newUser);
    });
  }

  @ParameterizedTest
  @MethodSource("removeAccounts")
  @Order(6)
  void deleteAccount(String username) {
    assertDoesNotThrow(() -> {
      manager.removeUser(username);
    });
  }

  @Order(7)
  void invalidDeleteAccount(String username) {
    assertThrows(UserNotFoundException.class, () -> {
      manager.removeUser(username);
    });
  }
}

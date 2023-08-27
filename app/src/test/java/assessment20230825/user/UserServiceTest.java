package assessment20230825.user;

import static org.junit.jupiter.api.Assertions.*;

import assessment20230825.exceptions.AppException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

  @BeforeAll
  static void initDbObject() throws AppException {
    UserTableService.getInstance().createTable();
  }

  @AfterAll
  static void cleanUp() throws AppException {
    UserTableService.getInstance().dropTable();
  }

  @Test
  @Order(1)
  void userService_insertNewUser_pass() throws AppException {
    int rowsAffected = UserService.getInstance().insertUser(new User(1, "12345", "test"));
    assertEquals(1, rowsAffected);
  }

  @Test
  @Order(2)
  void userService_insertDuplication_throwException() {
    assertThrows(
        AppException.class,
        () -> UserService.getInstance().insertUser(new User(1, "12345", "test")));
  }

  @Test
  @Order(3)
  void userService_getAllUsers_pass() throws AppException {
    List<User> users = UserService.getInstance().getAllUsers();
    assertEquals(1, users.size());
    assertEquals(1, users.get(0).getUserId());
    assertEquals("12345", users.get(0).getUserGuid());
    assertEquals("test", users.get(0).getUserName());
  }

  @Test
  @Order(4)
  void userService_deleteAllUsers_pass() throws AppException {
    assertEquals(1, UserService.getInstance().deleteAllUsers());
  }
}

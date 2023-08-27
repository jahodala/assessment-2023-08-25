package assessment20230825.user;

import static org.junit.jupiter.api.Assertions.*;

import assessment20230825.database.DatabaseService;
import assessment20230825.exceptions.AppException;
import org.junit.jupiter.api.Test;

class UserTest {
  @Test
  void newUser_parse_valid() throws AppException {
    User user =
        User.fromActionString(
            "(1, \"a1\", \"Robert\")", DatabaseService.getInstance().databaseConfiguration);
    assertEquals(1, user.getUserId());
    assertEquals("a1", user.getUserGuid());
    assertEquals("Robert", user.getUserName());
  }

  @Test
  void newUser_parseWithSpaces_valid() throws AppException {
    User user =
        User.fromActionString(
            "(  2,  \"a2\",   \"Robert\"  )", DatabaseService.getInstance().databaseConfiguration);
    assertEquals(2, user.getUserId());
    assertEquals("a2", user.getUserGuid());
    assertEquals("Robert", user.getUserName());
  }

  @Test
  void newUser_parse_throwsException() {
    assertThrows(
        AppException.class,
        () ->
            User.fromActionString(
                "(1,a1,Robert)", DatabaseService.getInstance().databaseConfiguration));
  }
}

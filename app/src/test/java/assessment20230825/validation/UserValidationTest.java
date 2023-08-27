package assessment20230825.validation;

import static org.junit.jupiter.api.Assertions.*;

import assessment20230825.database.DatabaseService;
import assessment20230825.exceptions.AppException;
import assessment20230825.user.User;
import org.junit.jupiter.api.Test;

class UserValidationTest {
  @Test
  void userValidation_allValid() throws AppException {
    User user = new User(1, "guid", "Mike");
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertDoesNotThrow(() -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserIdNull() throws AppException {
    User user = new User(null, "guid", "Mike");
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserIdZero() throws AppException {
    User user = new User(0, "guid", "Mike");
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserNameNull() throws AppException {
    User user = new User(1, "guid", null);
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserNameEmpty() throws AppException {
    User user = new User(1, "guid", "");
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserNameTooLong() throws AppException {
    String longUserName = "a".repeat(11);
    User user = new User(1, "guid", longUserName);
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserGuidNull() throws AppException {
    User user = new User(1, null, "Mike");
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserGuidEmpty() throws AppException {
    User user = new User(1, "", "Mike");
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }

  @Test
  void userValidation_invalidUserGuidTooLong() throws AppException {
    String longUserGuid = "a".repeat(6);
    User user = new User(1, longUserGuid, "Mike");
    UserValidation userValidation =
        new UserValidation(DatabaseService.getInstance().databaseConfiguration);
    assertThrows(AppException.class, () -> userValidation.validate(user));
  }
}

package assessment20230825.database;

import static org.junit.jupiter.api.Assertions.*;

import assessment20230825.exceptions.AppException;
import org.junit.jupiter.api.Test;

class DatabaseServiceConfigurationTest {

  @Test
  void databaseConfiguration_defaultSetup() throws AppException {
    DatabaseConfiguration databaseConfiguration =
        DatabaseService.getInstance().databaseConfiguration;

    assertEquals(
        DatabaseConfiguration.DEFAULT_USER_GUID_SIZE, databaseConfiguration.getUserGuidSize());
    assertEquals(
        DatabaseConfiguration.DEFAULT_USER_NAME_SIZE, databaseConfiguration.getUserNameSize());
    assertEquals(DatabaseConfiguration.DEFAULT_DB_URL_PERSIST, databaseConfiguration.getDbUrl());
  }

  @Test
  void databaseConfiguration_customAll() throws AppException {
    int userGuidSize = 15;
    int userNameSize = 20;

    DatabaseConfiguration databaseConfiguration =
        new DatabaseConfiguration(userGuidSize, userNameSize);

    assertEquals(userGuidSize, databaseConfiguration.getUserGuidSize());
    assertEquals(userNameSize, databaseConfiguration.getUserNameSize());
    assertEquals(DatabaseConfiguration.DEFAULT_DB_URL_PERSIST, databaseConfiguration.getDbUrl());
  }

  @Test
  void databaseConfiguration_customSizes() throws AppException {
    int userGuidSize = 15;
    int userNameSize = 20;

    DatabaseConfiguration databaseConfiguration =
        new DatabaseConfiguration(userGuidSize, userNameSize);

    assertEquals(userGuidSize, databaseConfiguration.getUserGuidSize());
    assertEquals(userNameSize, databaseConfiguration.getUserNameSize());
    assertEquals(DatabaseConfiguration.DEFAULT_DB_URL_PERSIST, databaseConfiguration.getDbUrl());
  }

  @Test
  void databaseConfiguration_customUrl() throws AppException {
    String dbUrl = "jdbc:h2:mem:assessment";
    DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(dbUrl);
    assertEquals(dbUrl, databaseConfiguration.getDbUrl());
  }

  @Test
  void databaseConfiguration_invalidSizes() {
    assertThrows(AppException.class, () -> new DatabaseConfiguration(0, 0));
    assertThrows(AppException.class, () -> new DatabaseConfiguration(0, 1));
    assertThrows(AppException.class, () -> new DatabaseConfiguration(1, 0));
  }
}

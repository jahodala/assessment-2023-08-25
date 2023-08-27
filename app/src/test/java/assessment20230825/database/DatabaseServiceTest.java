package assessment20230825.database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DatabaseServiceTest {
  @Test
  void database_createTable_pass() {
    assertDoesNotThrow(DatabaseService::getInstance);
  }
}

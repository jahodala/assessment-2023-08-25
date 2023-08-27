package assessment20230825.user;

import assessment20230825.database.DatabaseService;
import assessment20230825.exceptions.AppException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserTableServiceTest {
  @AfterAll
  static void cleanUp() throws AppException {
    UserTableService.getInstance().dropTable();
  }

  @BeforeAll
  static void initDbObject() throws AppException {
    UserTableService.getInstance().createTable();
  }

  @Test
  void database_createTable_pass() throws AppException {
    DatabaseService service = DatabaseService.getInstance();
    service.performSelectQuery("SELECT * FROM SUSERS", resultSet -> {});
  }
}

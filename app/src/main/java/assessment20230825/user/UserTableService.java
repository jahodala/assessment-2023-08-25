package assessment20230825.user;

import assessment20230825.database.DatabaseService;
import assessment20230825.database.ResourceReader;
import assessment20230825.exceptions.AppException;
import java.util.concurrent.atomic.AtomicReference;

/** User table singleton service */
public class UserTableService {
  private static final AtomicReference<UserTableService> INSTANCE = new AtomicReference<>();
  private final DatabaseService databaseService;

  /**
   * @param databaseService database service
   */
  private UserTableService(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  /**
   * Get instance of UserTableService
   *
   * @return UserTableService instance
   * @throws AppException if unable to get instance
   */
  public static UserTableService getInstance() throws AppException {
    UserTableService instance = INSTANCE.get();
    if (instance == null) {
      instance = new UserTableService(DatabaseService.getInstance());
      if (INSTANCE.compareAndSet(null, instance)) {
        return instance;
      } else {
        return INSTANCE.get();
      }
    }
    return instance;
  }

  /**
   * Create table
   *
   * @throws AppException if unable to create table
   */
  public void createTable() throws AppException {
    String[] createTableQueries =
        ResourceReader.readFromResources("01-create-table.sql").split(";");
    for (String createTableQuery : createTableQueries) {
      if (createTableQuery.trim().isEmpty()) {
        continue;
      }
      // define column sizes
      createTableQuery = createTableQuery.replaceAll("\\?", "%s");
      createTableQuery =
          String.format(
              createTableQuery,
              databaseService.databaseConfiguration.getUserGuidSize(),
              databaseService.databaseConfiguration.getUserNameSize());

      databaseService.performUpdateQuery(createTableQuery);
    }
  }

  /**
   * Drop table
   *
   * @throws AppException if unable to drop table
   */
  public void dropTable() throws AppException {
    databaseService.performUpdateQuery(ResourceReader.readFromResources("04-drop-table.sql"));
  }
}

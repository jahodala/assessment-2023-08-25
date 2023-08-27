package assessment20230825.database;

import assessment20230825.config.Config;
import assessment20230825.exceptions.AppException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.log4j.Log4j2;

/** Utility class to perform database operations */
@Log4j2
public class DatabaseService {
  private static final AtomicReference<DatabaseService> INSTANCE = new AtomicReference<>();
  public final DatabaseConfiguration databaseConfiguration;

  /**
   * @param databaseConfiguration database configuration
   */
  private DatabaseService(DatabaseConfiguration databaseConfiguration) {
    this.databaseConfiguration = databaseConfiguration;
  }

  /**
   * Get instance of DatabaseService
   *
   * @return DatabaseService instance
   * @throws AppException if db_url is too short
   */
  public static DatabaseService getInstance() throws AppException {
    DatabaseService instance = INSTANCE.get();
    if (instance == null) {
      DatabaseConfiguration databaseConfiguration =
          new DatabaseConfiguration(DatabaseConfiguration.DEFAULT_DB_URL_PERSIST);

      try {
        databaseConfiguration =
            new DatabaseConfiguration(
                Config.getInstance().properties.getProperty("databaseService.url"));
      } catch (AppException e) {
        log.warn("Unable to apply databaseService.url from application.properties");
      }
      instance = new DatabaseService(databaseConfiguration);
      if (INSTANCE.compareAndSet(null, instance)) {
        return instance;
      } else {
        return INSTANCE.get();
      }
    }
    return instance;
  }
  /**
   * perform any update query in database with params.
   *
   * @return number of rows affected
   * @throws AppException if an error occurs while executed
   */
  public int performUpdateQuery(String updateQuery, Object... params) throws AppException {
    try (Connection connection = DriverManager.getConnection(databaseConfiguration.getDbUrl());
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

      // apply query parameters
      for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
      }

      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
      throw new AppException("Cannot execute query: " + e.getMessage());
    }
  }

  /**
   * perform any select query in database with params.
   *
   * @throws AppException if an error occurs while executed
   */
  public void performSelectQuery(
      String selectQuery, ResultSetProcessor resultSetProcessor, Object... params)
      throws AppException {
    try (Connection connection = DriverManager.getConnection(databaseConfiguration.getDbUrl());
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
      // apply query parameters
      for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
      }

      ResultSet resultSet = preparedStatement.executeQuery();
      resultSetProcessor.process(resultSet);

    } catch (SQLException e) {
      log.error(e.getMessage(), e);
      throw new AppException("Cannot execute query: " + e.getMessage());
    }
  }
}

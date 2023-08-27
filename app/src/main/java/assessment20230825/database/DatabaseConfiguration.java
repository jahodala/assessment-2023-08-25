package assessment20230825.database;

import assessment20230825.exceptions.AppException;
import lombok.Getter;

/**
 * Database configuration class. Create database configuration with custom database url and size of
 * USER_GUID and USER_NAME
 */
@Getter
public class DatabaseConfiguration {
  public static final String DEFAULT_DB_URL = "jdbc:h2:mem:assessment";
  public static final String DEFAULT_DB_URL_PERSIST = DEFAULT_DB_URL + ";DB_CLOSE_DELAY=-1";

  public static final int DEFAULT_USER_GUID_SIZE = 5;
  public static final int DEFAULT_USER_NAME_SIZE = 10;
  private String dbUrl = DEFAULT_DB_URL_PERSIST;
  private int userGuidSize = DEFAULT_USER_GUID_SIZE;
  private int userNameSize = DEFAULT_USER_NAME_SIZE;

  /**
   * Create database configuration with custom size of USER_GUID and USER_NAME
   *
   * @param userGuidSize size of USER_GUID
   * @param userNameSize size of USER_NAME
   * @throws AppException if an error occurs while at lease one params is not higher then zero
   */
  public DatabaseConfiguration(int userGuidSize, int userNameSize) throws AppException {
    if (userGuidSize > 0 && userNameSize > 0) {
      this.userGuidSize = userGuidSize;
      this.userNameSize = userNameSize;
      return;
    }
    throw new AppException("Invalid configuration - no value can be lower then 1");
  }

  /**
   * Create database configuration with custom database url
   *
   * @param dbUrl database url
   * @throws AppException if an error occurs while dbUrl is null or too short
   */
  public DatabaseConfiguration(String dbUrl) throws AppException {
    if (dbUrl != null && dbUrl.length() > 10) {
      this.dbUrl = dbUrl;
      return;
    }
    throw new AppException("Invalid configuration - dbUrl too short");
  }
}

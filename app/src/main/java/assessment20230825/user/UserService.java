package assessment20230825.user;

import assessment20230825.database.DatabaseService;
import assessment20230825.database.ResourceReader;
import assessment20230825.exceptions.AppException;
import assessment20230825.validation.UserValidation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.log4j.Log4j2;

/** User singleton service */
@Log4j2
public class UserService {
  private static final AtomicReference<UserService> INSTANCE = new AtomicReference<>();
  private final DatabaseService databaseService;

  /**
   * @param databaseService database service
   */
  private UserService(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  public static UserService getInstance() throws AppException {
    UserService instance = INSTANCE.get();
    if (instance == null) {
      instance = new UserService(DatabaseService.getInstance());
      if (INSTANCE.compareAndSet(null, instance)) {
        return instance;
      } else {
        return INSTANCE.get();
      }
    }
    return instance;
  }
  /**
   * Insert user from action string
   *
   * @param action action string
   * @throws AppException if unable to insert user
   */
  public void insertUser(String action) throws AppException {
    insertUser(User.fromActionString(action, databaseService.databaseConfiguration));
  }
  /**
   * Insert user
   *
   * @param user user to insert
   * @throws AppException if unable to insert user
   */
  public int insertUser(User user) throws AppException {
    new UserValidation(databaseService.databaseConfiguration).validate(user);
    int insertedRecords =
        databaseService.performUpdateQuery(
            ResourceReader.readFromResources("02-insert-user.sql"),
            user.getUserId(),
            user.getUserGuid(),
            user.getUserName());

    log.debug("inserted {} users", insertedRecords);
    if (insertedRecords == 0) {
      throw new AppException("No user inserted");
    }
    return insertedRecords;
  }

  /**
   * Delete all users
   *
   * @return number of deleted users
   * @throws AppException if unable to delete users
   */
  public int deleteAllUsers() throws AppException {
    int deletedRecords =
        databaseService.performUpdateQuery(
            ResourceReader.readFromResources("03-delete-all-users.sql"));
    log.debug("delete {} users", deletedRecords);
    return deletedRecords;
  }

  /**
   * Get all users
   *
   * @return list of users
   * @throws AppException if unable to get users
   */
  public List<User> getAllUsers() throws AppException {
    List<User> users = new ArrayList<>();
    databaseService.performSelectQuery(
        "select * from SUSERS order by user_id",
        resultSet -> {
          while (resultSet.next()) {
            users.add(
                new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("user_guid"),
                    resultSet.getString("user_name")));
          }
        });
    log.debug("selected {} users", users.size());
    log.debug("users: {}", users);
    return users;
  }
}

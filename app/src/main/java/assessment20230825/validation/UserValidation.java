package assessment20230825.validation;

import assessment20230825.database.DatabaseConfiguration;
import assessment20230825.exceptions.AppException;
import assessment20230825.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/** User validation */
@RequiredArgsConstructor
@Log4j2
public class UserValidation {
  private final DatabaseConfiguration databaseConfiguration;

  /**
   * Validate user
   *
   * @param user user to validate
   * @throws AppException if user is not valid
   */
  public void validate(User user) throws AppException {
    if (validateUserId(user.getUserId())
        && validateUserGuid(user.getUserGuid())
        && validateUserName(user.getUserName())) {
      log.debug("User is valid : {}", user);
    } else {
      log.error("User is not valid : {}", user);
      throw new AppException("User is not valid");
    }
  }

  /**
   * Validate user id
   *
   * @param userId user id to validate
   * @return true if user id is valid
   */
  private boolean validateUserId(Integer userId) {
    boolean result = userId != null && userId > 0;
    if (!result) {
      log.error("User id is not valid");
    }
    return result;
  }

  /**
   * Validate user name
   *
   * @param userName user name to validate
   * @return true if user name is valid
   */
  private boolean validateUserName(String userName) {
    boolean result =
        userName != null
            && !userName.isEmpty()
            && userName.length() <= databaseConfiguration.getUserNameSize();
    if (!result) {
      log.error("User name is not valid");
    }
    return result;
  }

  /**
   * Validate user guid
   *
   * @param userGuid user guid to validate
   * @return true if user guid is valid
   */
  private boolean validateUserGuid(String userGuid) {
    boolean result =
        userGuid != null
            && !userGuid.isEmpty()
            && userGuid.length() <= databaseConfiguration.getUserGuidSize();
    if (!result) {
      log.error("User guid is not valid");
    }
    return result;
  }
}

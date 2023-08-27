package assessment20230825.user;

import assessment20230825.command.CommandsActionEnum;
import assessment20230825.database.DatabaseConfiguration;
import assessment20230825.exceptions.AppException;
import assessment20230825.validation.UserValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/** User class */
@Data
@AllArgsConstructor
@Log4j2
public class User {
  private Integer userId;
  private String userGuid;
  private String userName;

  /**
   * Parse user from input
   *
   * @param input input string
   * @param databaseConfiguration database configuration
   * @return User object
   * @throws AppException if unable to parse user from input
   */
  public static User fromActionString(String input, DatabaseConfiguration databaseConfiguration)
      throws AppException {
    Matcher result = Pattern.compile(CommandsActionEnum.ADD.supportValue).matcher(input.trim());
    AppException exception = new AppException("cannot parse user from input: {},input");
    try {
      if (result.matches()) {
        int userId = Integer.parseInt(result.group(1));
        String userGuid = result.group(2);
        String userName = result.group(3);
        User user = new User(userId, userGuid, userName);
        new UserValidation(databaseConfiguration).validate(user);
        return user;
      }
    } catch (AppException e) {
      exception = e;
    }

    throw exception;
  }

  /** Override toString method */
  @Override
  public String toString() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      log.error("Cannot convert user to string", e);
      return "{userId="
          + userId
          + ", userGuid='"
          + userGuid
          + "'"
          + ", userName='"
          + userName
          + "'}";
    }
  }
}

package assessment20230825.validation;

import assessment20230825.command.CommandsActionEnum;
import assessment20230825.exceptions.AppException;
import java.util.regex.Pattern;

/** Validate input command. */
public class CommandValidation {
  /**
   * Validate input command.
   *
   * @param input input command
   * @return CommandsActionEnum or throw AppException
   */
  public static CommandsActionEnum isValid(String input) throws AppException {
    if (isValidPrintAllFormat(input)) {
      return CommandsActionEnum.PRINT_ALL;
    } else if (isValidDeleteAllFormat(input)) {
      return CommandsActionEnum.DELETE_ALL;
    } else if (isValidExitFormat(input)) {
      return CommandsActionEnum.EXIT;
    } else if (isValidAddFormat(input)) {
      return CommandsActionEnum.ADD;
    } else {
      throw new AppException("Invalid command format: " + input);
    }
  }

  /**
   * Validate ADD command.
   *
   * @param input input command
   * @return true if valid
   */
  private static boolean isValidAddFormat(String input) {
    return Pattern.compile(CommandsActionEnum.ADD.supportValue).matcher(input).matches();
  }

  /**
   * Validate PRINT_ALL command.
   *
   * @param input input command
   * @return true if valid
   */
  private static boolean isValidPrintAllFormat(String input) {
    return CommandsActionEnum.PRINT_ALL.supportValue.equals(input);
  }
  /**
   * Validate DELETE_ALL command.
   *
   * @param input input command
   * @return true if valid
   */
  private static boolean isValidDeleteAllFormat(String input) {
    return CommandsActionEnum.DELETE_ALL.supportValue.equals(input);
  }

  /**
   * Validate EXIT command.
   *
   * @param input input command
   * @return true if valid
   */
  private static boolean isValidExitFormat(String input) {
    return CommandsActionEnum.EXIT.name().equals(input);
  }
}

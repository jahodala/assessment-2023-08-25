package assessment20230825.command;

import assessment20230825.exceptions.AppException;
import assessment20230825.validation.CommandValidation;
import java.util.Optional;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/** Command DTO with customized constructor */
@Data
@Log4j2
public class Command {
  private final CommandsActionEnum action;
  private final String payload;

  /**
   * @param payload command payload
   * @throws AppException if payload is not valid
   */
  public Command(String payload) throws AppException {
    this.payload = Optional.ofNullable(payload).orElse("").trim();
    this.action = CommandValidation.isValid(this.payload);
  }
}

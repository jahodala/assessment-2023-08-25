package assessment20230825.validation;

import static org.junit.jupiter.api.Assertions.*;

import assessment20230825.exceptions.AppException;
import org.junit.jupiter.api.Test;

class CommandValidationTest {

  @Test
  public void validateCommand_add_valid() {
    assertDoesNotThrow(() -> CommandValidation.isValid("(1, \"a1\", \"Robert\")"));
  }

  @Test
  public void validateCommand_addWithVariableSpaces_valid() {
    assertDoesNotThrow(() -> CommandValidation.isValid("(  1,     \"a1\",    \"Robert\"   )"));
  }

  @Test
  public void validateCommand_addInvalid_throwException() {
    assertThrows(AppException.class, () -> CommandValidation.isValid("(1, a1, Robert)"));
  }

  @Test
  public void validateCommand_exit_valid() {
    assertDoesNotThrow(() -> CommandValidation.isValid("EXIT"));
  }

  @Test
  public void validateCommand_printAll_valid() {
    assertDoesNotThrow(() -> CommandValidation.isValid("PrintAll"));
  }

  @Test
  public void validateCommand_deleteAll_valid() {
    assertDoesNotThrow(() -> CommandValidation.isValid("DeleteAll"));
  }

  @Test
  public void validateCommand_notKnown_throwException() {
    assertThrows(AppException.class, () -> CommandValidation.isValid("()"));
    assertThrows(AppException.class, () -> CommandValidation.isValid("xit"));
    assertThrows(AppException.class, () -> CommandValidation.isValid(""));
  }
}

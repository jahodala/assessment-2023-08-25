package assessment20230825.command;

import static org.junit.jupiter.api.Assertions.*;

import assessment20230825.exceptions.AppException;
import org.junit.jupiter.api.Test;

class CommandTest {
  @Test
  void command_add_valid() throws Exception {
    String payload = "(10, \"a1\", \"Robert\")";
    Command actualCommand = new Command(payload);
    assertEquals(payload, actualCommand.getPayload());
    assertEquals(CommandsActionEnum.ADD, actualCommand.getAction());
  }

  @Test
  void command_addWithSpacesAround_valid() throws Exception {
    String payload = "(10, \"a1\", \"Robert\")";
    Command actualCommand = new Command("  (10, \"a1\", \"Robert\") ");
    assertEquals(payload, actualCommand.getPayload());
    assertEquals(CommandsActionEnum.ADD, actualCommand.getAction());
  }

  @Test
  void command_exit_valid() throws Exception {
    String payload = CommandsActionEnum.EXIT.supportValue;
    Command actualCommand = new Command(payload);
    assertEquals(payload, actualCommand.getPayload());
    assertEquals(CommandsActionEnum.EXIT, actualCommand.getAction());
  }

  @Test
  void command_printAll_valid() throws Exception {
    String payload = CommandsActionEnum.PRINT_ALL.supportValue;
    Command actualCommand = new Command(payload);
    assertEquals(payload, actualCommand.getPayload());
    assertEquals(CommandsActionEnum.PRINT_ALL, actualCommand.getAction());
  }

  @Test
  void command_deleteAll_valid() throws Exception {
    String payload = CommandsActionEnum.DELETE_ALL.supportValue;
    Command actualCommand = new Command(payload);
    assertEquals(payload, actualCommand.getPayload());
    assertEquals(CommandsActionEnum.DELETE_ALL, actualCommand.getAction());
  }

  @Test
  void command_notKnown_throwException() {
    assertThrows(AppException.class, () -> new Command(null));
    assertThrows(AppException.class, () -> new Command(""));
    assertThrows(AppException.class, () -> new Command("any"));
    assertThrows(AppException.class, () -> new Command("(any)"));
    assertThrows(AppException.class, () -> new Command("(1,2,3)"));
  }
}

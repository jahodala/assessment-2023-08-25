package assessment20230825.command;

import assessment20230825.exceptions.AppException;
import assessment20230825.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/** Class for executing a command */
@Log4j2
@AllArgsConstructor
public class CommandExecutor {
  private final Command command;
  private final String threadName;

  /** Executes a command */
  public void execute() throws AppException {
    switch (command.getAction()) {
      case ADD -> {
        sleep();
        log.info("Adding user {}", command.getPayload());
        UserService.getInstance().insertUser(command.getPayload());
      }
      case DELETE_ALL -> {
        sleep();
        log.info("Removing all users");
        UserService.getInstance().deleteAllUsers();
      }
      case PRINT_ALL -> {
        sleep();
        log.info("Listing all users: {}", UserService.getInstance().getAllUsers());
      }
      case EXIT -> {
        sleep();
        log.info("Exiting");
      }
      default -> log.error("Invalid command: {} in thread: {}", command.getAction(), threadName);
    }
  }

  /** Simulates processing delay */
  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

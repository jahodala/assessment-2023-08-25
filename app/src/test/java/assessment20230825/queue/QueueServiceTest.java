package assessment20230825.queue;

import assessment20230825.command.Command;
import assessment20230825.command.CommandsActionEnum;
import assessment20230825.exceptions.AppException;
import assessment20230825.user.UserService;
import assessment20230825.user.UserTableService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Log4j2
class QueueServiceTest {

  @AfterAll
  static void cleanUp() throws AppException {
    UserTableService.getInstance().dropTable();
  }

  @BeforeAll
  static void initDbObject() throws AppException {
    UserTableService.getInstance().createTable();
  }

  @Test
  void start() throws AppException {
    QueueService queueServiceStart = QueueService.getInstance();

    // add user
    queueServiceStart.addCommand(new Command("(1, \"a1\", \"Robert\")"));
    queueServiceStart.addCommand(new Command("(2, \"a2\", \"Martin\")"));
    log.info(UserService.getInstance().getAllUsers());
    log.info("delete {} users", UserService.getInstance().deleteAllUsers());
    log.info(UserService.getInstance().getAllUsers());

    // send exit command to all consumers
    for (int i = 0; i < QueueService.CONSUMER_COUNT; i++) {
      queueServiceStart.addCommand(new Command(CommandsActionEnum.EXIT.name()));
    }
    // shutdown consumers gracefully
    queueServiceStart.shutdown();
  }
}

package assessment20230825.queue;

import assessment20230825.command.Command;
import assessment20230825.command.CommandExecutor;
import assessment20230825.command.CommandsActionEnum;
import assessment20230825.exceptions.AppException;
import java.util.concurrent.BlockingQueue;
import lombok.extern.log4j.Log4j2;

/** Queue consumer */
@Log4j2
public class QueueConsumer implements Runnable {
  private final BlockingQueue<Command> queue;

  /**
   * @param queue queue to consume
   */
  public QueueConsumer(BlockingQueue<Command> queue) {
    this.queue = queue;
  }

  /** Consumes thread action */
  public void run() {
    log.debug(Thread.currentThread().getName() + " started");
    try {
      while (true) {
        Command command = queue.take();
        if (CommandsActionEnum.EXIT.equals(command.getAction())) {
          log.info(Thread.currentThread().getName() + " exiting");
          return;
        }
        new CommandExecutor(command, Thread.currentThread().getName()).execute();
      }
    } catch (InterruptedException e) {
      log.debug("Interrupted exception");
      Thread.currentThread().interrupt();
    } catch (AppException e) {
      log.error("Error while executing command: {}", e.getMessage());
    }
    log.info(Thread.currentThread().getName() + " exiting");
  }
}

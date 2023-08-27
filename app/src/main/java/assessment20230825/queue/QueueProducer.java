package assessment20230825.queue;

import assessment20230825.command.Command;
import java.util.concurrent.BlockingQueue;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/** Queue producer */
@Log4j2
@AllArgsConstructor
public class QueueProducer implements Runnable {
  private final BlockingQueue<Command> numbersQueue;
  private final Command command;

  /** Producer thread action */
  public void run() {
    try {
      log.debug("Adding command {}", command);
      numbersQueue.put(command);
    } catch (InterruptedException e) {
      log.debug("Interrupted exception");
      Thread.currentThread().interrupt();
    }
  }
}

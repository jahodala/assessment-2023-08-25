package assessment20230825.queue;

import assessment20230825.command.Command;
import assessment20230825.exceptions.AppException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.log4j.Log4j2;

/** Queue service */
@Log4j2
public class QueueService {
  public static final int CONSUMER_COUNT = 3;
  private static final AtomicReference<QueueService> INSTANCE = new AtomicReference<>();
  public final BlockingQueue<Command> queue;
  public final int QUEUE_SIZE = 10;
  // create thread pool for producers
  private final ExecutorService producerThreadPool = Executors.newFixedThreadPool(1);
  // create thread pool for consumers
  private final ExecutorService consumerThreadPool = Executors.newFixedThreadPool(3);

  /**
   * @param queue the queue to consume
   */
  private QueueService(BlockingQueue<Command> queue) {
    log.info("Starting queue with size {}  and {} consumers", QUEUE_SIZE, CONSUMER_COUNT);
    this.queue = queue;
    for (int i = 0; i < CONSUMER_COUNT; i++) {
      consumerThreadPool.execute(new QueueConsumer(queue));
    }
  }

  /**
   * Get instance of QueueService
   *
   * @return QueueService instance
   */
  public static QueueService getInstance() {
    QueueService instance = INSTANCE.get();
    if (instance == null) {
      instance = new QueueService(new LinkedBlockingQueue<>());
      if (INSTANCE.compareAndSet(null, instance)) {
        return instance;
      } else {
        return INSTANCE.get();
      }
    }
    return instance;
  }

  /**
   * Add command to queue
   *
   * @param commands commands to add
   */
  public void addCommand(Command... commands) {
    log.debug("Enqueuing {} commands", commands.length);
    if (commands.length > 0) {
      // enqueuing
      for (Command command : commands) {
        log.info("Enqueuing command: {}", command);
        producerThreadPool.execute(new QueueProducer(queue, command));
      }
    } else {
      log.warn("No command provided for enqueuing");
    }
  }

  /**
   * Shutdown queue
   *
   * @throws AppException if an error occurs while shutting down
   */
  public void shutdown() throws AppException {
    while (!queue.isEmpty()) {
      sleep();
    }
    log.info("Queue empty");

    producerThreadPool.shutdown();
    while (!producerThreadPool.isTerminated()) {
      sleep();
    }
    log.info("Producer thread pool shutdown");
    consumerThreadPool.shutdown();
    while (!consumerThreadPool.isTerminated()) {
      sleep();
    }
    log.info("Consumer thread pool shutdown");
  }

  /**
   * Sleep for 100ms
   *
   * @throws AppException if an error occurs while sleeping
   */
  private void sleep() throws AppException {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      throw new AppException("Interrupted exception");
    }
  }
}

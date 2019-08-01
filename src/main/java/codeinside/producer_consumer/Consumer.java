package codeinside.producer_consumer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {

	private static final Logger log = LogManager.getLogger(Consumer.class);
	private QueueManager queue;
	private RandomGenerator randomGenerator = new RandomGenerator();

	public Consumer(QueueManager queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (!queue.isConsumerFinished()) {
			if (randomGenerator.getRandomInt() > 90) {
				try {
					List<String> messages = queue.getMessages();
					messages.stream().forEach(message -> log.debug("Sending message via email: " + message));
					Thread.sleep(2000);
				} catch (Exception e) {
					throw new PCException("Cannot send messages", e);
				}
			}
		}
	}
}

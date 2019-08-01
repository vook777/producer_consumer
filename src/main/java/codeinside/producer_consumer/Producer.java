package codeinside.producer_consumer;

public class Producer implements Runnable {

	private QueueManager queue;
	private RandomGenerator randomGenerator = new RandomGenerator();
	private int messageLimit = 20;
	private int numberOfMessages = 0;

	public Producer(QueueManager queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (!queue.isProducerFinished()) {
			if (randomGenerator.getRandomInt() > 90) {
				try {
					queue.addMessage();
					numberOfMessages++;
					if (numberOfMessages == messageLimit) {
						queue.setProducerFinished(true);
					}
					Thread.sleep(100);
				} catch (Exception e) {
					throw new PCException("Cannot add message", e);
				}
			}
		}
	}
}

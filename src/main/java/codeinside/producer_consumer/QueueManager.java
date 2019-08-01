package codeinside.producer_consumer;

import java.util.List;

import codeinside.producer_consumer.dao.QueueDao;

public class QueueManager {

	private static final int QUEUE_CAPACITY = 5;
	private int queueSize = 0;
	private QueueDao dao = new QueueDao();
	private RandomGenerator messageGenerator = new RandomGenerator();
	private boolean producerFinished = false;
	private boolean consumerFinished = false;

	public synchronized void addMessage() throws InterruptedException, ClassNotFoundException {
		while (queueSize == QUEUE_CAPACITY) {
			wait();
		}
		String message = messageGenerator.getRandomString();
		dao.createMessage(message);
		System.out.println("Producer added message - " + message);
		queueSize++;
		notify();
	}

	public synchronized List<String> getMessages() throws InterruptedException, ClassNotFoundException {
		while (queueSize == 0) {
			wait();
		}
		List<String> messages = dao.retrieveUnsentMessages();
		queueSize = 0;
		notify();
		if (producerFinished) {
			consumerFinished = true;
		}
		return messages;
	}

	public boolean isProducerFinished() {
		return producerFinished;
	}

	public void setProducerFinished(boolean producerFinished) {
		this.producerFinished = producerFinished;
	}
	
	public boolean isConsumerFinished() {
		return consumerFinished;
	}
}

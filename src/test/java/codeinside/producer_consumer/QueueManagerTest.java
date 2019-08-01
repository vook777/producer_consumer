package codeinside.producer_consumer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import codeinside.producer_consumer.dao.QueueDao;

public class QueueManagerTest {

	private QueueManager queue = new QueueManager();
	private static QueueDao dao = new QueueDao();

	@BeforeClass
	public static void beforeAll() throws ClassNotFoundException {
		dao.deleteAllMessages();
	}

	@Test
	public void test() throws InterruptedException {
		new Thread(new Producer(queue)).start();
		Thread consumer = new Thread(new Consumer(queue));
		consumer.start();
		consumer.join();
	}
	
	@After
	public void checkResult() throws ClassNotFoundException {
		int allMessages = dao.countAllMessages();
		int processedMessages = dao.countProcessedMessages();
		assertEquals(allMessages, 20);
		assertEquals(processedMessages, 20);
	}
}

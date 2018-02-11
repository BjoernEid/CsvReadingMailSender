package csvReadingMailSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a mock mailing service using the IMailingService.
 * interface.
 * 
 * @author Bjoern Eid
 *
 */
public class MockMailingService implements IMailingService {
	private Logger logger;
	private ThreadPoolExecutor executor;
	private AtomicInteger mailCount;

	private Logger getLogger() {
		return this.logger;
	}

	private void setLogger(Logger logger) {
		this.logger = logger;
	}

	private ThreadPoolExecutor getExecutor() {
		return executor;
	}

	private void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public MockMailingService() {
		super();
		this.setLogger(Logger.getLogger(MockMailingService.class.getName()));
		this.setExecutor((ThreadPoolExecutor) Executors.newFixedThreadPool(1000));
		this.mailCount = new AtomicInteger(0);
	}

	/**
	 * This method is mocking the mail sending process. It waits 500ms and then logs
	 * an info message.
	 * 
	 * @param content
	 *            The content of the email to be send.
	 * @param emailAdress
	 *            The email address of the recipient.
	 * 
	 * @author Bjoern Eid
	 */
	@Override
	public void sendMail(String mailAdress, String content) {
		this.getExecutor().submit(() -> {
			Thread.sleep(500);
			this.getLogger().log(Level.INFO,
					mailCount.incrementAndGet() + ". Following mail send to " + mailAdress + ": " + content);
			mailCount.incrementAndGet();
			return null;
		});
	}

	/**
	 * Shuts down the executor and an info message is logged displaying the total
	 * number of mails sent.
	 * 
	 * @author Bjoern Eid
	 */
	public void shutdown() {
		this.getExecutor().shutdown();
		try {
			this.getExecutor().awaitTermination(10000, TimeUnit.MILLISECONDS);
			this.getLogger().log(Level.INFO, mailCount.get() + " mails sent.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

package csvReadingMailSender;

public interface IMailingService {
	
	/**
	 * This interface method is used to send an email. 
	 * 
	 * @param content
	 *            The content of the email to be send.
	 * @param emailAdress
	 *            The email address of the recipient.
	 * 
	 * @author Bjoern Eid
	 */
	public abstract void sendMail(String mailAdress, String content);

	/**
	 * This interface method has to be called after all mails have been sent to close the
	 * executor that is used to simulate asynchronous web access.
	 * 
	 * @author Bjoern Eid
	 */
	public abstract void shutdown();
}

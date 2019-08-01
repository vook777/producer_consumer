package codeinside.producer_consumer;

public class PCException extends RuntimeException {

	public PCException (String message, Exception e) {
		super(message, e);
	}
}

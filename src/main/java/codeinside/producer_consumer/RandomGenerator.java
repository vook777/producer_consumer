package codeinside.producer_consumer;

public class RandomGenerator {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public String getRandomString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public int getRandomInt() {
		return (int) (Math.random() * 100);
	}
}

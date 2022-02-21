package resources;

public class ConsoleColors {
	private ConsoleColors() {
	}

	// Reset
	private static final String RESET = "\033[0m"; // Text Reset

	/**
	 * Print a colored message in the console
	 * 
	 * @param message
	 * @param color
	 */
	public static void print(String message, Color color) {
		System.out.println(Color.valueOf(color) + message + ConsoleColors.RESET);
	}
}

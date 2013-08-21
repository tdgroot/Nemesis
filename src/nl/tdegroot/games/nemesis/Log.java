package nl.tdegroot.games.nemesis;

public class Log {

	private static final String LOG_TYPE_DEFAULT = "Log: ";
	private static final String LOG_TYPE_ERROR = "Error: ";
	private static final String LOG_TYPE_EXCEPTION = "An exception has occurred: ";

	public static void log(String msg) {
		System.out.println(LOG_TYPE_DEFAULT + msg);
	}

	public static void error(String error) {
		System.out.println(LOG_TYPE_ERROR + error);
	}

	public static void exception(String exception) {
		System.out.println(LOG_TYPE_EXCEPTION + exception);
	}

}

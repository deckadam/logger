package logger;

public class LogObject {
	private String message;
	private String senderClass;
	private String senderMethod;
	private String level;
	private String date;

	protected LogObject(String message, String senderClass, String senderMethod, int level,String date) {
		if (level < 1 || level > 6) throw new IllegalArgumentException("Unknown log level");
		if (level == LogLevels.TRACE) this.level = "TRACE";
		if (level == LogLevels.DEBUG) this.level = "DEBUG";
		if (level == LogLevels.INFO) this.level = "INFO";
		if (level == LogLevels.WARN) this.level = "WARN";
		if (level == LogLevels.ERROR) this.level = "ERROR";
		if (level == LogLevels.FATAL) this.level = "FATAL";
		this.date = date;
		this.message = message;
		this.senderMethod = senderMethod;
		this.senderClass = senderClass;
	}

	protected String getMessage() {
		return this.message;
	}

	protected String getSenderClass() {
		return this.senderClass;
	}

	protected String getSenderMethod() {
		return this.senderMethod;
	}

	protected String getLevel() {
		return this.level;
	}

	protected String getDate() {
		return this.date;
	}

}

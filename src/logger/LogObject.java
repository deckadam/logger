package logger;

public class LogObject {
	private String message;
	private String senderClass;
	private String senderMethod;
	private String level;
	private String date;
	private String fileName;
	private String moduleName;
	private String cause;
	private int lineNumber;

	protected LogObject(String message, String senderClass, String senderMethod, int level, String date,
			String fileName, String moduleName,String cause, int lineNumber) {
		this(message, senderClass, senderMethod, level, date);
		this.fileName = fileName;
		this.moduleName = moduleName;
		this.lineNumber = lineNumber;
	}

	protected LogObject(String message, String senderClass, String senderMethod, int level, String date) {
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
		this.lineNumber = -1;
	}

	public String getCause() {
		return cause;
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

	protected String getFileName() {
		return this.fileName;
	}

	protected String getModuleName() {
		return this.moduleName;
	}

	protected int getLineNumber() {
		return this.lineNumber;
	}

}

package logger;

import java.text.DateFormat;
import java.util.Date;

public class LogObject {
	private String message;
	private String senderClass;
	private String senderMethod;
	private String level;
	private Date date;

	public LogObject(String message, String senderClass, String senderMethod, int level) {
		if (level < 1 || level > 6)
			throw new IllegalArgumentException("Unknown log level");
		this.date = new Date();
		this.message = message;
		this.senderClass = senderClass;
		this.senderMethod = senderMethod;
		if (level == LogLevels.TRACE)
			this.level = "TRACE";
		if (level == LogLevels.DEBUG)
			this.level = "DEBUG";
		if (level == LogLevels.INFO)
			this.level = "INFO";
		if (level == LogLevels.WARN)
			this.level = "WARN";
		if (level == LogLevels.ERROR)
			this.level = "ERROR";
		if (level == LogLevels.FATAL)
			this.level = "FATAL";
	}

	public String returnFormattedLog(DateFormat dateFormat) {
		return level + " -> " + dateFormat.format(date) + " -> " + senderClass + " -> " + senderMethod + " -> " + message;
	}
}
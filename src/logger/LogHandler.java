package logger;

import java.text.SimpleDateFormat;
import java.util.List;

public abstract class LogHandler {
	private LogHandler alterativeHandler;
	private boolean useAlternativeHandlerOnError = false;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");

	public void setUseAlternativeHandlerOnError(boolean value) {
		this.useAlternativeHandlerOnError = value;
	}

	public boolean getUseAlternativeHandlerOnError() {
		return useAlternativeHandlerOnError;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public LogHandler getAlternativeHandler() {
		return alterativeHandler;
	}

	public void setAlternativeHandler(LogHandler alternativeHandler) {
		this.alterativeHandler = alternativeHandler;
	}

	public abstract boolean printOutLogs(List<LogInstance> logInstance, int count);
}

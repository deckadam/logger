package logger;

import java.io.OutputStream;
import java.util.List;

public abstract class LogHandler {
	protected boolean printableAtReleaseMode;
	private OutputStream handlerWriteStream;
	private LogHandler alterativeHandler;
	private boolean useAlternativeHandlerOnError = false;
	private LogFormatter formatter;
	protected LogManager logger = LogManager.getInstance();

	public LogFormatter getLogFormatter() {
		return this.formatter;
	}

	public void setLogFormatter(LogFormatter formatter) {
		this.formatter = formatter;
	}

	public void setUseAlternativeHandlerOnError(boolean value) {
		this.useAlternativeHandlerOnError = value;
	}

	public boolean getUseAlternativeHandlerOnError() {
		return useAlternativeHandlerOnError;
	}

	public LogHandler getAlternativeHandler() {
		return alterativeHandler;
	}

	public void setAlternativeHandler(LogHandler alternativeHandler) {
		this.alterativeHandler = alternativeHandler;
	}

	public OutputStream getHandlerWriteStream() {
		return this.handlerWriteStream;
	}

	public void setHandlerWriteStream(OutputStream newStream) {
		this.handlerWriteStream = newStream;
	}

	public boolean isPrintOnAtRelease() {
		return printableAtReleaseMode;
	}

	public void setPrintOnAtRelease(boolean isPrintOnAtRelease) {
		this.printableAtReleaseMode = isPrintOnAtRelease;
	}

	protected void useAlternativeHandler(List<LogInstance> logInstances, int count, LogFormatter formatter) {
		if (getUseAlternativeHandlerOnError() && getAlternativeHandler() != null) {
			getAlternativeHandler().printOutLogs(logInstances, count);
		}
		else {
			System.out.println("Alternative handler not available");
		}
	}

	protected abstract boolean printOutLogs(List<LogInstance> logInstances, int count);
}

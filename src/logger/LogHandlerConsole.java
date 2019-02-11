package logger;

import java.util.List;
import java.io.OutputStream;

public class LogHandlerConsole extends LogHandler {

	public LogHandlerConsole(OutputStream os) {
		setHandlerWriteStream(os);
	}
	
	@Override
	protected boolean printOutLogs(List<LogInstance> logInstances, int count) {
		StringBuilder outputBuilder = new StringBuilder();
		try {
			for (LogInstance tempInstance : logInstances) {
				tempInstance.getLogs(outputBuilder, getLogFormatter(), count);
				outputBuilder.append(LogManager.lineSeparator);
			}
			getHandlerWriteStream().write(outputBuilder.toString().getBytes());
		}
		catch (Exception e) {
			e.printStackTrace();
			useAlternativeHandler(logInstances, count, getLogFormatter());

		}
		return true;
	}

}

package logger;

import logger.LogObject;
import logger.LogInstance;

import java.util.List;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class LogManager {
	private static LogManager instance;
	private static boolean DEBUG;

	static {
		lineSeparator = System.lineSeparator();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
	}

	public static LogManager getInstance() {
		return getInstance(DEBUG);
	}

	public static LogManager getInstance(boolean mode) {
		if (null == instance) {
			return new LogManager(mode);
		}
		return instance;
	}

	private LogManager(boolean mode) {
		DEBUG = mode;
	}

	protected static SimpleDateFormat dateFormat;
	protected static final String lineSeparator;

	private LogHandler activeLogHandler;
	private LogHandler exceptionHandler;

	private LogInstance activeInstance;
	private LogInstance exceptionInstance;

	private int activeLoggingLevel = -1;

	private List<LogInstance> logInstances = new ArrayList<LogInstance>();

	public void addLogInstance(LogInstance newInstance) {
		if (null != newInstance) {
			logInstances.add(newInstance);
			if (activeInstance == null)
				activeInstance = newInstance;
		}
	}

	private boolean checkForPrint(LogHandler handler, LogInstance instance) {
		return (handler != null && instance != null && !instance.isInstanceEmpty());
	}

	// EXCEPTION HANDLER//////////////////////////////////////////
	public void handleException(Exception e, LogInstance[] instances) {
		StackTraceElement[] resource = e.getStackTrace();
		for (StackTraceElement element : resource) {
			String className = element.getClassName();
			if (!className.equals(this.getClass().getClass().getName())) {
				String fileName = element.getFileName();
				String methodName = element.getMethodName();
				String moduleName = element.getModuleName();
				String cause = e.getCause().toString();
				int lineNumber = element.getLineNumber();
				Date date = new Date();
				exceptionInstance.addToInstance(new LogObject(e.getMessage(), className, methodName, LogLevels.ERROR,
						dateFormat.format(date), fileName, moduleName, cause, lineNumber));
			}
		}
	}

	public void handleException(Exception e) {
		handleException(e, new LogInstance[] { activeInstance });
	}

	public boolean printExceptions() {
		if (checkForPrint(exceptionHandler, exceptionInstance))
			return exceptionHandler.printOutLogs(Arrays.asList(exceptionInstance), -1);
		else
			return false;
	}
	// EXCEPTION HANDLER//////////////////////////////////////////

	// CREATE LOG/////////////////////////////////////////////////

	private void processData(String logText, int logLevel, LogInstance[] instances) {
		this.activeLoggingLevel = logLevel;
		StackTraceElement[] currentStack = Thread.currentThread().getStackTrace();
		String callerClassName = currentStack[currentStack.length - 1].getClassName();
		String callerMethodName = currentStack[currentStack.length - 1].getMethodName();
		Date date = new Date();
		LogObject temp = new LogObject(logText, callerClassName, callerMethodName, logLevel, dateFormat.format(date));
		for (LogInstance instanceTemp : instances) {
			instanceTemp.addToInstance(temp);
		}
	}

	// RULE for log creationg
	private boolean checkDebugMode() {
		return DEBUG;
	}

	public void createLog(String logText, int logLevel, LogInstance[] instances) {
		if (checkDebugMode()) {
			processData(logText, logLevel, instances);
		}
	}

	public void createLog(String logText, int logLevel) {
		createLog(logText, logLevel, new LogInstance[] { activeInstance });
	}

	public void createLog(String logText) {
		createLog(logText, activeLoggingLevel);
	}
	// CREATE LOG////////////////////////////////////////////////

	// PRINT OUT/////////////////////////////////////////////////

	public boolean[] printOut(List<LogHandler> handlerInstances, LogInstance[] logInstances, int count) {
		if (handlerInstances.size() != 0 && logInstances.length > 0) {
			boolean[] isComplete = new boolean[handlerInstances.size()];
			int handlerCounter = 0;
			List<LogInstance> tempList = new ArrayList<LogInstance>(Arrays.asList(logInstances));
			for (LogHandler tempHandler : handlerInstances) {
				if (tempHandler != null) {
					if (checkDebugMode() || tempHandler.printableAtReleaseMode)
						isComplete[handlerCounter++] = tempHandler.printOutLogs(tempList, count);
					else
						isComplete[handlerCounter++] = false;
				}
			}
			return isComplete;
		}
		return new boolean[] { false };
	}

	public boolean[] printOut(List<LogHandler> handlerInstances, LogInstance[] logInstances) {
		return printOut(handlerInstances, logInstances, -1);
	}

	public boolean[] printOut(LogHandler handlerInstance, LogInstance[] logInstances) {
		return printOut(Arrays.asList(handlerInstance), logInstances, -1);
	}

	public boolean[] printOut(List<LogHandler> handlerInstances, int count) {
		return printOut(handlerInstances, new LogInstance[] { activeInstance }, count);
	}

	public boolean[] printOut(LogHandler handlerInstance, int count) {
		return printOut(Arrays.asList(handlerInstance), new LogInstance[] { activeInstance }, count);
	}

	public boolean[] printOut(List<LogHandler> handlerInstances) {
		return printOut(handlerInstances, -1);
	}

	public boolean[] printOut(LogHandler handlerInstance) {
		return printOut(handlerInstance, -1);
	}

	public boolean printOut(LogInstance[] logInstances, int count) {
		return printOut(Arrays.asList(activeLogHandler), logInstances, count)[0];
	}

	public boolean printOut(LogInstance[] instances) {
		return printOut(Arrays.asList(activeLogHandler), instances, -1)[0];
	}

	public boolean printOut(int count) {
		return printOut(Arrays.asList(activeLogHandler), new LogInstance[] { activeInstance }, count)[0];
	}

	public boolean printOut() {
		return printOut(Arrays.asList(activeLogHandler), new LogInstance[] { activeInstance }, -1)[0];
	}
	// PRINT OUT/////////////////////////////////////////////////

	// GETTER SETTERS////////////////////////////////////////////
	public void setActiveLogInstance(LogInstance logInstance) {
		this.activeInstance = logInstance;
	}

	public int getActiveLogInstance() {
		return this.activeInstance.getId();
	}

	public void setActiveLogHandler(LogHandler logHandler) {
		this.activeLogHandler = logHandler;
	}

	public LogHandler getActiveLogHandler() {
		return this.activeLogHandler;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat newFormat) {
		dateFormat = newFormat;
	}

	public int getActiveLoggingLevel() {
		return this.activeLoggingLevel;
	}

	public void setActiveLoggingLevel(int activeLoggingLevel) {
		this.activeLoggingLevel = activeLoggingLevel;
	}

	public static boolean getDebugMode() {
		return DEBUG;
	}

	public LogHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(LogHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public LogInstance getExceptionInstance() {
		return exceptionInstance;
	}

	public void setExceptionInstance(LogInstance exceptionInstance) {
		this.exceptionInstance = exceptionInstance;
	}
	// GETTER SETTERS////////////////////////////////////////////
}
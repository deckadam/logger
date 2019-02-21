
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
	private boolean createLogsOutOfDebugMode;

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
	private LogInstance activeInstance;
	private int activeLoggingLevel = -1;

	private List<LogInstance> logInstances = new ArrayList<LogInstance>();
	private List<LogHandler> logHandlers = new ArrayList<LogHandler>();

	public void addLogInstance(LogInstance newInstance) {
		if (null != newInstance) {
			logInstances.add(newInstance);
			if (activeInstance == null)
				activeInstance = newInstance;
		}
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
				int lineNumber = element.getLineNumber();
				Date date = new Date();
				for (LogInstance instance : instances) {
					instance.addToInstance(new LogObject(e.getMessage(), className, methodName, LogLevels.ERROR,
							dateFormat.format(date), fileName, moduleName, lineNumber));
				}
			}
		}
	}

	public void handleException(Exception e) {
		handleException(e, new LogInstance[] { activeInstance });
	}
	// EXCEPTION HANDLER//////////////////////////////////////////

	// HANDLER HOOK///////////////////////////////////////////////
	public void addLogHandlerToHook(LogHandler logHandler) {
		if (activeLogHandler == null)
			this.activeLogHandler = logHandler;
		logHandlers.add(logHandler);
	}

	public boolean removeLogHandlerFromHook(LogHandler logHandler) {
		if (logHandlers.contains(logHandler)) {

			if (logHandler == activeLogHandler) {
				if (!logHandlers.isEmpty()) {
					activeLogHandler = logHandlers.get(0);
				}
				else
					return false;
			}
			logHandlers.remove(logHandler);
			return true;
		}
		return false;
	}
	// HANDLER HOOK///////////////////////////////////////////////

	// CREATE LOG/////////////////////////////////////////////////
	public void createLog(String logText, int logLevel, LogInstance[] instances) {
		if (DEBUG || createLogsOutOfDebugMode) {
			this.activeLoggingLevel = logLevel;
			StackTraceElement[] currentStack = Thread.currentThread().getStackTrace();
			String callerClassName = currentStack[currentStack.length - 1].getClassName();
			String callerMethodName = currentStack[currentStack.length - 1].getMethodName();
			Date date = new Date();
			LogObject temp = new LogObject(logText, callerClassName, callerMethodName, logLevel,
					dateFormat.format(date));
			for (LogInstance instanceTemp : instances) {
				instanceTemp.addToInstance(temp);
			}
		}
	}

	public void createLog(String logText, int logLevel) {
		createLog(logText, logLevel, new LogInstance[] { activeInstance });
	}

	public void createLog(String logText) {
		createLog(logText, activeLoggingLevel, new LogInstance[] { activeInstance });
	}
	// CREATE LOG////////////////////////////////////////////////

	// PRINT OUT/////////////////////////////////////////////////
	public boolean[] printOut(List<LogHandler> handlerInstances, int[] logInstances, int count) {
		boolean[] isComplete = new boolean[handlerInstances.size()];
		int handlerCounter = 0;
		List<LogInstance> tempList = new ArrayList<LogInstance>();
		for (int tempInstance : logInstances) {
			tempList.add(this.logInstances.get(tempInstance));
		}
		for (LogHandler tempHandler : handlerInstances) {
			isComplete[handlerCounter] = tempHandler.printOutLogs(tempList, count);
			handlerCounter++;
		}
		return isComplete;
	}

	public boolean[] printOut(List<LogHandler> handlerInstances, int[] logInstances) {
		return printOut(handlerInstances, logInstances, -1);
	}

	public boolean[] printOut(List<LogHandler> handlerInstances, int count) {
		return printOut(handlerInstances, new int[] { activeInstance.getId() }, count);
	}

	public boolean[] printOut(List<LogHandler> handlerInstances) {
		return printOut(handlerInstances, -1);
	}

	public boolean printOut(int[] logInstances, int count) {
		return printOut(Arrays.asList(activeLogHandler), logInstances, count)[0];
	}

	public boolean printOut(int[] instances) {
		return printOut(Arrays.asList(activeLogHandler), instances, -1)[0];
	}

	public boolean printOut(int count) {
		return printOut(Arrays.asList(activeLogHandler), new int[] { activeInstance.getId() }, count)[0];
	}

	public boolean printOut() {
		return printOut(Arrays.asList(activeLogHandler), new int[] { activeInstance.getId() }, -1)[0];
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

	public boolean isCreateLogsOutOfDebugMode() {
		return createLogsOutOfDebugMode;
	}

	public void setCreateLogsOutOfDebugMode(boolean createLogsOutOfDebugMode) {
		this.createLogsOutOfDebugMode = createLogsOutOfDebugMode;
	}
	// GETTER SETTERS////////////////////////////////////////////
}
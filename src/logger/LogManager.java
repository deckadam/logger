
package logger;

import logger.LogObject;
import logger.LogInstance;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class LogManager {
	protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
	protected static final String lineSeparator;
	public final boolean DEBUG = true;
	private static LogManager instance;

	static {
		lineSeparator = System.lineSeparator();
		instance = new LogManager();
	}

	public static LogManager getInstance() {
		return instance;
	}

	private LogManager() {
	}

	private LogHandler activeLogHandler;
	private LogInstance activeInstance;
	private List<LogInstance> logInstances = new ArrayList<LogInstance>();
	private List<LogHandler> logHandlers = new ArrayList<LogHandler>();
	private int activeLoggingLevel = -1;

	public void addLogInstance(LogInstance newInstance) {
		if (null != newInstance) {
			logInstances.add(newInstance);
			if (activeInstance == null) activeInstance = newInstance;
		}
	}

	// HANDLER HOOK///////////////////////////////////////////////
	public void addLogHandlerToHook(LogHandler logHandler) {
		if (activeLogHandler == null) this.activeLogHandler = logHandler;
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

	// CREATE LOG////////////////////////////////////////////////
	public void createLog(String logText, int logLevel, int[] instances) {
		this.activeLoggingLevel = logLevel;
		StackTraceElement[] currentStack = Thread.currentThread().getStackTrace();
		String callerClassName = currentStack[currentStack.length - 1].getClassName();
		String callerMethodName = currentStack[3].getMethodName();
		Date date = new Date();
		LogObject temp = new LogObject(logText, callerClassName, callerMethodName, logLevel, dateFormat.format(date));
		for (int instanceTemp : instances) {
			logInstances.get(instanceTemp).addToLogList(temp);
		}
	}

	public void createLog(String logText, int logLevel) {
		createLog(logText, logLevel, new int[] { activeInstance.getId() });
	}

	public void createLog(String logText) {
		createLog(logText, activeLoggingLevel, new int[] { activeInstance.getId() });
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

	// FLUSH LOG INSTANCE////////////////////////////////////////
	public void flushLogInstances(int[] indexes) {
		for (int temp : indexes) {
			logInstances.get(temp).clearList();
		}
	}

	public void flushLogInstance(int index) {
		flushLogInstances(new int[] { index });
	}

	public void flushLogInstance() {
		if (activeInstance != null) flushLogInstances(new int[] { activeInstance.getId() });
	}
	// FLUSH LOG INSTANCE////////////////////////////////////////

	// REMOVE LOG////////////////////////////////////////////////
	public void removeLog(int count, int[] instances) {
		for (int temp : instances) {
			if (count == -1)
				logInstances.get(temp).clearList();
			else
				logInstances.get(temp).removeLog(count);
		}
	}

	public void removeLog(int[] instances) {
		removeLog(1, instances);
	}

	public void removeLog(int count) {
		removeLog(count, new int[] { activeInstance.getId() });
	}

	public void removeLog() {
		removeLog(1, new int[] { activeInstance.getId() });
	}
	// REMOVE LOG////////////////////////////////////////////////

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
		this.activeLoggingLevel=activeLoggingLevel;
	}
	// GETTER SETTERS////////////////////////////////////////////
}
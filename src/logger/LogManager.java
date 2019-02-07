
package logger;

import logger.LogObject;
import logger.LogInstance;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class LogManager {
	private static LogManager instance;

	public static LogManager getInstance() {
		if (instance == null) {
			instance = new LogManager();
		}
		return instance;
	}

	private LogManager() {
	}

	private LogHandler activeLogHandler;
	private LogInstance activeInstance;
	private List<LogInstance> logInstances = new ArrayList<LogInstance>();
	private List<LogHandler> logHandlers = new ArrayList<LogHandler>();
	private int instanceCounter = -1;

	public int createLogInstance() {
		LogInstance newInstance = new LogInstance(++instanceCounter);
		logInstances.add(newInstance);
		if (activeInstance == null)
			activeInstance = newInstance;
		return newInstance.getId();
	}

	public void addLogHandlerToHook(LogHandler logHandler) {
		if (activeLogHandler == null)
			this.activeLogHandler = logHandler;
		logHandlers.add(logHandler);
	}

	public boolean removeLogHandlerFromHook(LogHandler logHandler) {
		if (logHandler == activeLogHandler) {
			int temp = logHandlers.indexOf(logHandler);
			if (logHandlers.size() >= logHandlers.indexOf(logHandler) + 1) {
				activeLogHandler = logHandlers.get(temp + 1);
			} else if (logHandlers.size() == logHandlers.indexOf(logHandler) + 1) {
				activeLogHandler = logHandlers.get(temp - 1);
			} else
				activeLogHandler = null;
		}
		logHandlers.remove(logHandler);
		if (logHandlers.isEmpty())
			return false;
		return true;
	}

	public void createLog(String logText, int logLevel, int[] instances) {
		StackTraceElement[] currentStack=Thread.currentThread().getStackTrace();
		String callerClassName = currentStack[currentStack.length-1].getClassName();
		String callerMethodName = currentStack[1].getMethodName() + currentStack.length;
		LogObject temp = new LogObject(logText, callerClassName, callerMethodName, logLevel);
		for (int instanceTemp : instances) {
			logInstances.get(instanceTemp).getLogList().add(temp);
		}
	}

	public void createLog(String logText, int logLevel) {
		createLog(logText, logLevel, new int[] { activeInstance.getId() });
	}

	public boolean[] printOut(List<LogHandler> handlerInstances, int[] logInstances, int count) {
		boolean[] isComplete = new boolean[handlerInstances.size() * logInstances.length];
		int handlerCounter = 0;
		List<LogInstance> tempList = new ArrayList<LogInstance>();
		for (int tempInstance : logInstances) {
			tempList.add(this.logInstances.get(tempInstance));
		}
		for (LogHandler tempHandler : handlerInstances) {
			isComplete[handlerCounter] = tempHandler.printOutLogs(tempList, count);
			if (!isComplete[handlerCounter])
				break;
			handlerCounter++;
		}
		return isComplete;
	}
	
	public boolean[] printOut(List<LogHandler> handlerInstances , int[] logInstances) {
		return printOut(handlerInstances , logInstances , -1);
	}

	public boolean[] printOut(int[] logInstances, int count) {
		return printOut(Arrays.asList(activeLogHandler), logInstances, count);
	}

	public boolean[] printOut(List<LogHandler> handlerInstances, int count) {
		return printOut(handlerInstances, new int[] { activeInstance.getId() }, count);
	}
	
	public boolean[] printOut(List<LogHandler> handlerInstances) {
		return printOut(handlerInstances,-1);
	}

	public boolean printOut(int[] instances) {
		return printOut(Arrays.asList(activeLogHandler), instances, -1)[0];
	}

	public boolean printOut(int count) {
		return printOut(Arrays.asList(activeLogHandler), new int[] { activeInstance.getId() }, count)[0];
	}

	public boolean printOut() {
		return printOut(Arrays.asList(activeLogHandler), new int[] { activeInstance.getId() },
				activeInstance.getLogList().size())[0];
	}

	public void flushLogInstances(int[] indexes) {
		for (int temp : indexes) {
			logInstances.get(temp).clearList();
		}
	}

	public void flushLogInstance(int index) {
		flushLogInstances(new int[] { index });
	}

	public void flushLogInstance() {
		if (activeInstance != null)
			flushLogInstances(new int[] { activeInstance.getId() });
	}

	public void removeLog(int count, int[] instances) {
		for (int temp : instances) {
			if (count == -1)
				this.logInstances.get(temp).clearList();
			else
				this.logInstances.get(temp).removeLog(count);
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

	public void setLogHandler(LogHandler logHandler) {
		this.activeLogHandler = logHandler;
	}

	public LogHandler getLogHandler() {
		return activeLogHandler;
	}

	public void setActiveLogInstance(int instanceId) {
		activeInstance = logInstances.get(instanceId);
	}

	public int getActiveLogInstance() {
		return this.activeInstance.getId();
	}

}
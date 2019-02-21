package logger;

import java.util.LinkedList;
import java.util.List;

public class LogInstance {
	protected static int listCounter = 0;
	private String instanceName;
	private LinkedList<LogObject> logList = new LinkedList<LogObject>();
	private int listId;
	{
		this.listId = listCounter++;
	}

	protected void addToInstance(LogObject data) {
		this.logList.add(data);
	}

	public void removeFromBottom(int count) {
		if (checkValidity(count))
			for (int x = 0; x < count; x++) {
				logList.removeFirst();
			}

	}

	public void removeLast(int count) {
		if (checkValidity(count))
			for (int x = 0; x < count; x++) {
				logList.removeLast();
			}
	}

	protected void getLogs(StringBuilder stringBuilder, LogFormatter formatter, int count) {
		List<LogObject> tempList;
		if (count != -1)
			tempList = logList.subList(logList.size() - count, logList.size());
		else
			tempList = logList;
		for (LogObject temp : tempList) {
			stringBuilder.append(formatter.getFormattedLog(temp) + LogManager.lineSeparator);
		}

	}

	private boolean checkValidity(int count) {
		if (count > 0 || count > logList.size()) {
			return false;
		}
		else {
			return true;
		}
	}

	protected int getId() {
		return listId;
	}

	public String getInstanceName() {
		return this.instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public LogInstance() {
		this(null);
	}

	public LogInstance(String instanceName) {
		this.instanceName = instanceName;
	}

	public void clearInstance() {
		this.logList.clear();
	}
}

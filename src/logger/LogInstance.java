package logger;

import java.util.ArrayList;
import java.util.List;

public class LogInstance {
	private List<LogObject> logList = new ArrayList<LogObject>();
	private int listId;

	public LogInstance(int index) {
		this.listId = index;
	}

	public void clearList() {
		this.logList.clear();
	}

	protected void addToLogList(LogObject data) {
		this.logList.add(data);
	}

	protected void removeLog(int count) {
		if (logList.size() - count >= 0)
			this.logList = logList.subList(0, logList.size() - count);
		else
			clearList();
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

	protected int getId() {
		return listId;
	}
}

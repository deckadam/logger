package logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogInstance {
	private List<LogObject> logList = new ArrayList<LogObject>();
	private int listId;

	public LogInstance(int index) {
		this.listId = index;
	}

	public void clearInstance() {
		this.logList.clear();
	}

	protected void addToInstance(LogObject data) {
		this.logList.add(data);
	}

	public void removeFromBottom(int count) {
		Iterator<LogObject> itr = logList.iterator();
		while (itr.hasNext() && count > 0) {
			itr.next();
			itr.remove();
			count--;
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

	protected int getId() {
		return listId;
	}
}

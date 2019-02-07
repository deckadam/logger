package logger;

import java.util.ArrayList;
import java.util.List;

class LogInstance {
	private List<LogObject> logList = new ArrayList<LogObject>();
	private  int listId;	
	
	protected LogInstance(int index) {
		this.listId=index;
	}

	protected void clearList() {
		this.logList.clear();
	}
	
	protected List<LogObject> getLogList(){
		return logList;
	}

	protected void removeLog(int count) {
		if(logList.size()-count>=0)
		this.logList = logList.subList(0, logList.size() - count);
		else clearList();
	}
	
	protected int getId() {return listId;}
}

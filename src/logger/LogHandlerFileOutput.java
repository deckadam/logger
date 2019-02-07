package logger;

import java.util.List;

public class LogHandlerFileOutput extends LogHandler {

	private String outputPath;
	private String outputFileName;

	public LogHandlerFileOutput() {
		this(System.getProperty("user.home") + "//logOutputs//", "defaultFileName");
	}

	public LogHandlerFileOutput(String outputPath) {
		this(outputPath, "defaultFileName");
	}

	public LogHandlerFileOutput(String outputPath, String fileName) {
		this.outputFileName = fileName;
		this.outputPath = outputPath;
		System.out.println(this.outputFileName + this.outputPath);
	}

	@Override
	public boolean printOutLogs(List<LogInstance> logInstance, int count) {
		StringBuilder outputString = new StringBuilder();
		List<LogObject> temp;
		for (LogInstance logList : logInstance) {
			outputString.setLength(0);
			if (count != -1) {
				int size = logList.getLogList().size();
				if (logList.getLogList().size() - count >= 0)
					temp = logList.getLogList().subList(size - count, size);
				else
					temp = logList.getLogList();
			} else
				temp = logList.getLogList();
			try {
				for (LogObject instance : temp) {
					outputString.append(instance.returnFormattedLog(getDateFormat()) + "\n");
				}
				System.out.println(outputString);
			} catch (Exception e) {
				useAlternativeHandler(logInstance, count);
				return false;
			}
		}
		return true;
	}

	private boolean checkAlternativeHandler() {
		return getAlternativeHandler() != null;
	}

	private void useAlternativeHandler(List<LogInstance> logInstance, int count) {
		if (getUseAlternativeHandlerOnError() && checkAlternativeHandler()) {
			getAlternativeHandler().printOutLogs(logInstance, count);
		}
	}
}
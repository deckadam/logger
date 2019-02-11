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

		return true;
	}
}

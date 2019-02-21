package logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class LogHandlerFileOutput extends LogHandler {
	private String outputPath;
	private String outputFileName;

	public LogHandlerFileOutput(boolean isPrintOnAtRelease) {
		this(System.getProperty("user.home") + "//logOutputs//", "defaultFileName.txt");
		super.setPrintOnAtRelease(isPrintOnAtRelease);
	}

	public LogHandlerFileOutput(String outputPath) {
		this(outputPath, "defaultFileName");
	}

	public LogHandlerFileOutput(String outputPath, String fileName) {
		this.outputFileName = fileName;
		this.outputPath = outputPath;
	}

	@Override
	public boolean printOutLogs(List<LogInstance> logInstances, int count) {
		boolean[] results = new boolean[logInstances.size()];
		int resultCounter = 0;
		for (LogInstance tempInstance : logInstances) {
			StringBuilder temp = new StringBuilder();
			tempInstance.getLogs(temp, getLogFormatter(), count);
			int stringLength = temp.length();
			int chunkSize = 1024;
			char[] aChars = new char[chunkSize];
			try (BufferedWriter bw = createWriter(tempInstance.getInstanceName())) {
				for (int i = 0; i < stringLength; i += chunkSize) {
					int aPosEnd = Math.min(i + chunkSize, stringLength);
					temp.getChars(i, aPosEnd, aChars, 0);
					bw.write(aChars, 0, aPosEnd - i);
				}
				results[resultCounter++] = true;
			}
			catch (IOException e) {
				e.printStackTrace();
				logger.handleException(e);
				useAlternativeHandler(logInstances, count, getLogFormatter());
				results[resultCounter++] = true;
			}
		}
		for (boolean temp : results) {
			if (!temp)
				return false;
		}

		return true;
	}

	private int counter = 0;

	private BufferedWriter createWriter(String fileName) throws IOException {
		if (fileName != null && !fileName.equals(""))
			return new BufferedWriter(new FileWriter(outputPath + checkExtension(fileName)));
		else
			return new BufferedWriter(new FileWriter(outputPath + checkExtension(outputFileName + "-" + counter++)));
	}

	private String checkExtension(String inputName) {
		String[] dotCheck;
		dotCheck = inputName.split(Pattern.quote("."));
		if (dotCheck.length == 1)
			return inputName + ".txt";
		else if (dotCheck.length == 2)
			return inputName;
		return outputFileName;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
}
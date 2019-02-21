package testPackage;

import logger.LogFormatter;
import logger.LogHandler;
import logger.LogHandlerConsole;
import logger.LogHandlerFileOutput;
import logger.LogLevels;
import logger.LogManager;
import logger.LogInstance;
import java.util.Arrays;

public class TestClass {

	public void start() {
		LogManager logger = LogManager.getInstance(true);
		logger.setCreateLogsOutOfDebugMode(true);

		LogFormatter formatter = new LogFormatter("l,sc,sm,m,d,fn,ln,mn");
		LogInstance logInstance = new LogInstance("example");

		LogHandlerFileOutput alternativeHandler = new LogHandlerFileOutput(
				"C:\\Users\\mete han çetin\\Desktop\\logs\\");
		alternativeHandler.setLogFormatter(formatter);
		alternativeHandler.setPrintOutOfDebugMode(true);
		alternativeHandler.setUseInstanceNameIfExists(true);

		LogHandler consoleHandler = new LogHandlerConsole(System.out, true);
		consoleHandler.setAlternativeHandler(alternativeHandler);
		consoleHandler.setLogFormatter(formatter);
		consoleHandler.setUseAlternativeHandlerOnError(true);
		consoleHandler.setPrintOutOfDebugMode(false);

		logger.addLogHandlerToHook(consoleHandler);
		logger.addLogHandlerToHook(alternativeHandler);
		logger.addLogInstance(logInstance);

		logger.setActiveLoggingLevel(LogLevels.DEBUG);
		logger.createLog("heloooo");
		logger.createLog("FOR DELETE1");
		logger.createLog("FOR DELETE2", LogLevels.WARN);
		logger.createLog("FOR DELETE3", LogLevels.ERROR);
		logInstance.removeFromBottom(-2);

		logger.handleException(new Exception("test exception"));
		logger.printOut(Arrays.asList(consoleHandler, alternativeHandler));
	}

	public static void main(String[] args) {
		TestClass t = new TestClass();
		t.start();
	}

}

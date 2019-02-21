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

		LogFormatter formatter = new LogFormatter("l,sc,sm,m,d,fn,ln,c,mn");
		LogInstance logInstance = new LogInstance();
		LogInstance logInstance2 = new LogInstance("text");
		LogInstance exceptionInstance = new LogInstance();

		LogHandlerFileOutput fileHandler = new LogHandlerFileOutput("C:\\Users\\mete han çetin\\Desktop\\logs\\");
		fileHandler.setLogFormatter(formatter);

		LogHandler consoleHandler = new LogHandlerConsole(System.out, true);
		consoleHandler.setAlternativeHandler(fileHandler);
		consoleHandler.setLogFormatter(formatter);
		consoleHandler.setUseAlternativeHandlerOnError(true);
		logger.setExceptionHandler(consoleHandler);
		logger.setExceptionInstance(exceptionInstance);
		logger.addLogInstance(logInstance);

		logger.setActiveLoggingLevel(LogLevels.DEBUG);
		logger.createLog("heloooo");
		logger.createLog("FOR DELETE1");
		logger.createLog("FOR DELETE2", LogLevels.WARN);
		logger.createLog("FOR DELETE3", LogLevels.ERROR);
		logInstance.removeFromBottom(-2);
		Throwable e = new Throwable("IOException");
		logger.handleException(new Exception("test exception", e));
		logger.printExceptions();
		logger.printOut(Arrays.asList(consoleHandler, fileHandler), new LogInstance[] { logInstance, logInstance2 });
	}

	public static void main(String[] args) {

		TestClass t = new TestClass();
		t.start();
	}

}

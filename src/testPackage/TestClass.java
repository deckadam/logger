package testPackage;

import logger.*;

public class TestClass {

	
	private static LogManager logger;
	public static void main(String[] args) {
		logger = LogManager.getInstance();

		LogHandler consoleHandler = new LogHandlerConsole();
		LogHandler fileHandler=new LogHandlerFileOutput();
		
		
		logger.addLogHandlerToHook(consoleHandler);
		logger.setLogHandler(consoleHandler);

		int logInstance1, logInstance2, logInstance3;
		logInstance1 = logger.createLogInstance();
		logInstance2 = logger.createLogInstance();
		logInstance3 = logger.createLogInstance();

		//logger.removeLogHandlerFromHook(consoleHandler1);

		logger.setActiveLogInstance(logInstance1);


		LogHandlerConsole alternativeHandler = new LogHandlerConsole();
		consoleHandler.setUseAlternativeHandlerOnError(true);
		consoleHandler.setAlternativeHandler(alternativeHandler);

		logger.createLog("Hello world!!", LogLevels.INFO);
		logger.createLog("heloooo", LogLevels.INFO);
		logger.createLog("FOR DELETE1", LogLevels.INFO);
		logger.createLog("FOR DELETE2", LogLevels.WARN);
		logger.createLog("FOR DELETE3", LogLevels.ERROR);
		testMethod1();

		logger.setActiveLogInstance(logInstance2);
		logger.createLog("Hello world!!", LogLevels.INFO);
		logger.createLog("heloooo", LogLevels.INFO);
		logger.createLog("FOR DELETE1", LogLevels.INFO);
		logger.createLog("FOR DELETE2", LogLevels.INFO);

		logger.setActiveLogInstance(logInstance3);
		logger.createLog("Hello world!!", LogLevels.INFO);
		logger.createLog("heloooo", LogLevels.INFO);
		logger.createLog("FOR DELETE1", LogLevels.INFO);
		logger.createLog("FOR DELETE2", LogLevels.INFO);
		logger.createLog("FOR DELETE3", LogLevels.INFO);

		logger.printOut(new int[] {logInstance1,logInstance2,logInstance3});

	}
	
	private static void testMethod1() {

		logger.createLog("FOR DELETE4", LogLevels.FATAL);
	}
}

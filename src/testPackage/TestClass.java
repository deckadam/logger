package testPackage;

import java.util.Arrays;

import logger.LogFormatter;
import logger.LogHandler;
import logger.LogHandlerConsole;
import logger.LogLevels;
import logger.LogManager;
import logger.LogInstance;

public class TestClass {

	private int instanceCounter=0;
	
	public void start() {
		LogManager logger = LogManager.getInstance();
		LogHandler consoleHandler= new LogHandlerConsole(System.out);
		LogFormatter formatter = new LogFormatter ("l,sc,sm,m,d");
		LogInstance logInstance = new LogInstance(instanceCounter++);
		LogHandler alternativeHandler= new LogHandlerConsole(System.err);
		
		consoleHandler.setAlternativeHandler(alternativeHandler);
		consoleHandler.setLogFormatter(formatter);
		consoleHandler.setUseAlternativeHandlerOnError(true);
		alternativeHandler.setLogFormatter(formatter);
		
		logger.addLogHandlerToHook(consoleHandler);
		logger.addLogHandlerToHook(alternativeHandler);
		logger.addLogInstance(logInstance);
		
		
		logger.setActiveLoggingLevel(LogLevels.DEBUG);
		logger.createLog("Hello world!!");
		logger.createLog("heloooo");
		logger.createLog("FOR DELETE1");
		logger.createLog("FOR DELETE2", LogLevels.WARN);
		logger.createLog("FOR DELETE3", LogLevels.ERROR);
		
		logger.printOut();
		
		
	}
	
	public static void main(String[] args) {
		TestClass t = new TestClass();
		t.start();
	}
}

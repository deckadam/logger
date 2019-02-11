package testPackage;

import logger.LogFormatter;
import logger.LogHandler;
import logger.LogHandlerConsole;
import logger.LogLevels;
import logger.LogManager;
import logger.LogInstance;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class TestClass {

	private int instanceCounter = 0;

	public void start() {
		LogManager logger = LogManager.getInstance(true);
		LogHandler consoleHandler = new LogHandlerConsole(System.out);
		LogFormatter formatter = new LogFormatter("l,sc,sm,m,d");
		LogInstance logInstance = new LogInstance(instanceCounter++);
		LogHandler alternativeHandler = new LogHandlerConsole(System.err);

		consoleHandler.setAlternativeHandler(alternativeHandler);
		consoleHandler.setLogFormatter(formatter);
		consoleHandler.setUseAlternativeHandlerOnError(true);
		alternativeHandler.setLogFormatter(formatter);

		logger.addLogHandlerToHook(consoleHandler);
		logger.addLogHandlerToHook(alternativeHandler);
		logger.addLogInstance(logInstance);

		logger.setActiveLoggingLevel(LogLevels.DEBUG);
		logger.createLog("heloooo");
		
		logger.createLog("FOR DELETE1");
		logger.createLog("FOR DELETE2", LogLevels.WARN);
		logger.createLog("FOR DELETE3", LogLevels.ERROR);
		logInstance.removeLog(1);

		logger.printOut();

	}

	public void test() {
		List<Integer> testList = new ArrayList<Integer>();

		for (int x = 0; x < 500000; x++) {
			testList.add(x);
		}

		List<Integer> tempList = new ArrayList<>(testList);

		long start = System.nanoTime();
		for (int x = 0; x < 500000; x++) {
			tempList.remove(0);
		}
		long end = System.nanoTime();
		System.out.println(end - start);
		Iterator<Integer> itr = testList.iterator();
		start = System.nanoTime();
		while (itr.hasNext()) {
			itr.next();
			itr.remove();
		}
		end = System.nanoTime();

		System.out.println(end - start);
	}

	public static void main(String[] args) {
		TestClass t = new TestClass();
		t.start();
	}
}

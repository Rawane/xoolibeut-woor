package org.xoolibeut.woor.organize.photo;

public class ConsoleLogger {
	private ApplicationInfo applicationInfo;
	private static ConsoleLogger consoleLogger = new ConsoleLogger();

	private ConsoleLogger() {

	}

	public static ConsoleLogger getInstance(ApplicationInfo applicationInfo) {
		consoleLogger.applicationInfo = applicationInfo;
		return consoleLogger;

	}

	public void println(String msg) {
		if (applicationInfo.isViewLogConsole()) {
			System.out.println(msg);
		}
	}
}

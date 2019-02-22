package org.xoolibeut.woor.organize.photo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TraceLog {
	private ApplicationInfo applicationInfo;
	private ConsoleLogger consoleLogger;
	private static TraceLog traceLog = new TraceLog();

	private TraceLog() {

	}

	public static TraceLog getInstance(ApplicationInfo applicationInfo, ConsoleLogger consoleLogger) {
		traceLog.applicationInfo = applicationInfo;
		traceLog.consoleLogger = consoleLogger;
		return traceLog;

	}

	public void trace(TraceInfo traceInfo) {
		if (applicationInfo.getApplicationLog() != null) {
			try {
				if (!Files.exists(Paths.get(applicationInfo.getApplicationLog()))) {
					Files.createFile(Paths.get(applicationInfo.getApplicationLog()));
				}

				try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(applicationInfo.getApplicationLog()),
						StandardOpenOption.APPEND)) {
					writer.write(traceInfo.toString() + "\n");
					consoleLogger.println(traceInfo.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

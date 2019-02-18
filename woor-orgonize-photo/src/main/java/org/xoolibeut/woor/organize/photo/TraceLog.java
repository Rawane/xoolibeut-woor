package org.xoolibeut.woor.organize.photo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TraceLog {
	private ApplicationInfo applicationInfo;
	private static TraceLog traceLog = new TraceLog();

	private TraceLog() {

	}

	public static TraceLog getInstance(ApplicationInfo applicationInfo) {
		traceLog.applicationInfo = applicationInfo;
		return traceLog;

	}

	public void trace(TraceInfo traceInfo) {
		try {
			if (!Files.exists(Paths.get(applicationInfo.getApplicationLog()))) {
				Files.createFile(Paths.get(applicationInfo.getApplicationLog()));
			}

			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(applicationInfo.getApplicationLog()),
					StandardOpenOption.APPEND)) {
				writer.write(traceInfo.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

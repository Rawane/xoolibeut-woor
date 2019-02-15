package org.xoolibeut.woor.organize.photo;

public class TraceLog {
	private ApplicationInfo applicationInfo;
	private static TraceLog traceLog = new TraceLog();

	private TraceLog() {

	}

	public static TraceLog getInstance(ApplicationInfo applicationInfo) {
		traceLog.applicationInfo = applicationInfo;
		return traceLog;

	}

	public void trace() {
		
	}
}

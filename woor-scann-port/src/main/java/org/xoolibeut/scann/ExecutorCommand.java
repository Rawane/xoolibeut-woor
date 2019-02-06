package org.xoolibeut.scann;

import java.io.IOException;

public class ExecutorCommand {
	public static void execute(Sys sys, String cmdLine, ScannResultCommand<? extends ResultCommand> resultCommand)
			throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		String interP = null;
		String arg1 = null;
		switch (sys) {
		case Win:
			interP = "cmd.exe";
			arg1 = "/c";
			break;
		case Linux:
			interP = "/bin/bash";
			arg1 = "-c";
			break;
		default:
			break;
		}
		processBuilder.command(interP, arg1, cmdLine);
		Process process = processBuilder.start();
		resultCommand.doResult(process.getInputStream());
		int exitCode = process.waitFor();
		resultCommand.setSatatus(exitCode);
	}
}

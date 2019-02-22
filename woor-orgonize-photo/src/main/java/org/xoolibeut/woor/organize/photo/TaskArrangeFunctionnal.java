package org.xoolibeut.woor.organize.photo;

import java.nio.file.Path;

@FunctionalInterface
public interface TaskArrangeFunctionnal {
	/**
	 * 
	 * @param path
	 */
	public void doAfterMoveFile(Path path);

}

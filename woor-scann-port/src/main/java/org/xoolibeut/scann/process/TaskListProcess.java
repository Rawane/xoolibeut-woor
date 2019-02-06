package org.xoolibeut.scann.process;

import java.util.List;

public interface TaskListProcess {
	/**
	 * lister tous les process actifs.
	 * 
	 * @return
	 */
	List<Process> listAllProcess();
/**
 * Lister les process qui ne sont pas dans la liste entréée
 * @param procs
 * @return
 */
	List<Process> listAllProcessNotIn(List<String> procs);
}

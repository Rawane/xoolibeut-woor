package org.xoolibeut.scann.port;

import java.io.IOException;
import java.util.List;

public interface NetstatParsing {
	/**
	 * vérifier tous les ports et les process.
	 * 
	 * @return
	 */
	List<TagInfo> retrieveAllInfostraffic() throws IOException, InterruptedException;

	/**
	 * Lister les ports servers.
	 * 
	 * @return
	 */
	List<TagInfo> retrieveListenningPort() throws IOException, InterruptedException;

}

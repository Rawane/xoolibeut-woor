package org.xoolibeut.scann;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public interface ScannResultCommand<E> {
	/**
	 * traitement resultat commande.
	 * 
	 * @param inputStream
	 * 
	 */
	void doResult(InputStream inputStream);

	void setSatatus(int code);

	static void showConsole(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		reader.lines().forEach(System.out::println);
		try {
			reader.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}
}

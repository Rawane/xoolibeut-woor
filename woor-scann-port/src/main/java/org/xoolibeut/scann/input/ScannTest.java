package org.xoolibeut.scann.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

public class ScannTest {

	public static void mainT(String[] args) {
		Properties props = System.getProperties();
		Set<Object> setKey = props.keySet();
		String osName = System.getProperty("os.name");
		String osArch = System.getProperty("os.arch");
		String osVersion = System.getProperty("os.version");
		System.out.println(osName);
		System.out.println(osArch);
		System.out.println(osVersion);
		for (Object key : setKey) {
			System.out.println(key + ": " + props.get(key));
		}

	}
	public static void main(String[] args) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Windows
        processBuilder.command("cmd.exe", "/c","netstat -b");

        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            System.out.println("---------------------");
            int compteur=1;
            while ((line = reader.readLine()) != null) {
            	 System.out.println("compteur "+compteur+" nbr line "+line.length());
            	
                System.out.println(line);
                compteur++;
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

package org.xoolibeut.scann.port;

import org.xoolibeut.scann.ResultCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Information sur port et processus.
 * 
 * @author AC75094508
 *
 */
public class TagInfo implements ResultCommand {
	private String portLocal;
	private String portDistante;
	private String adresseIplocal;
	private String adresseIpDistante;
	private String processus;
	private String pathExe;
	private String typeConnection;
	private String protocole;

	public TagInfo() {

	}

	public TagInfo(String line) {
		System.out.println("----------------- " + line);
		line = line.trim();
		if (line.startsWith("[")) {
			this.processus = line.substring(1, line.length() - 1);
		} else {
			String[] terms = line.trim().split("\\s+");
			if (terms.length > 3) {
				this.protocole = terms[0];
				if (terms[1].contains(":")) {
					String[] ipAndPort = terms[1].split(":");
					this.adresseIplocal = ipAndPort[0];
					this.portLocal = ipAndPort[1];
				}
				if (terms[2].contains(":")) {
					String[] ipAndPortDistant = terms[2].split(":");
					this.adresseIpDistante = ipAndPortDistant[0];
					if ("http".equals(ipAndPortDistant[1])) {
						this.portDistante = "80";
					} else {
						if ("https".equals(ipAndPortDistant[1])) {
							this.portDistante = "443";
						} else {
							this.portDistante = ipAndPortDistant[1];
						}
					}

				}
				this.typeConnection = terms[3];
			} 
		}

	}

	public String getPortLocal() {
		return portLocal;
	}

	public void setPortLocal(String portLocal) {
		this.portLocal = portLocal;
	}

	public String getPortDistante() {
		return portDistante;
	}

	public void setPortDistante(String portDistante) {
		this.portDistante = portDistante;
	}

	public String getAdresseIplocal() {
		return adresseIplocal;
	}

	public void setAdresseIplocal(String adresseIplocal) {
		this.adresseIplocal = adresseIplocal;
	}

	public String getAdresseIpDistante() {
		return adresseIpDistante;
	}

	public void setAdresseIpDistante(String adresseIpDistante) {
		this.adresseIpDistante = adresseIpDistante;
	}

	public String getProcessus() {
		return processus;
	}

	public void setProcessus(String processus) {
		this.processus = processus;
	}

	public String getPathExe() {
		return pathExe;
	}

	public void setPathExe(String pathExe) {
		this.pathExe = pathExe;
	}

	public String getTypeConnection() {
		return typeConnection;
	}

	public void setTypeConnection(String typeConnection) {
		this.typeConnection = typeConnection;
	}

	public String getProtocole() {
		return protocole;
	}

	public void setProtocole(String protocole) {
		this.protocole = protocole;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException exception) {
			exception.printStackTrace();
		}
		return this.toString();
	}
}

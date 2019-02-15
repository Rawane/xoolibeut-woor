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
	private String portLocal="";
	private String portDistante="";
	private String adresseIplocal="";
	private String adresseIpDistante="";
	private String processus="";
	private String pathExe="";
	private String typeConnection="";
	private String protocole="";

	public TagInfo() {

	}

	public TagInfo(String line) {
		line = line.trim();
		if (line.startsWith("[")) {
			this.processus = line.substring(1, line.length() - 1);
		} else {
			String[] terms = line.trim().split("\\s+");
			if (terms.length > 3) {
				this.protocole = terms[0];
				if (terms[1].contains(":")) {
					if (!terms[1].startsWith("[::]")) {
						String[] ipAndPort = terms[1].split(":");
						this.adresseIplocal = ipAndPort[0];
						this.portLocal = ipAndPort[1];
					}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresseIpDistante == null) ? 0 : adresseIpDistante.hashCode());
		result = prime * result + ((adresseIplocal == null) ? 0 : adresseIplocal.hashCode());
		result = prime * result + ((portDistante == null) ? 0 : portDistante.hashCode());
		result = prime * result + ((portLocal == null) ? 0 : portLocal.hashCode());
		result = prime * result + ((protocole == null) ? 0 : protocole.hashCode());
		result = prime * result + ((typeConnection == null) ? 0 : typeConnection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagInfo other = (TagInfo) obj;
		if (adresseIpDistante == null) {
			if (other.adresseIpDistante != null)
				return false;
		} else if (!adresseIpDistante.equals(other.adresseIpDistante))
			return false;
		if (adresseIplocal == null) {
			if (other.adresseIplocal != null)
				return false;
		} else if (!adresseIplocal.equals(other.adresseIplocal))
			return false;
		if (portDistante == null) {
			if (other.portDistante != null)
				return false;
		} else if (!portDistante.equals(other.portDistante))
			return false;
		if (portLocal == null) {
			if (other.portLocal != null)
				return false;
		} else if (!portLocal.equals(other.portLocal))
			return false;
		if (protocole == null) {
			if (other.protocole != null)
				return false;
		} else if (!protocole.equals(other.protocole))
			return false;
		if (typeConnection == null) {
			if (other.typeConnection != null)
				return false;
		} else if (!typeConnection.equals(other.typeConnection))
			return false;
		return true;
	}
}

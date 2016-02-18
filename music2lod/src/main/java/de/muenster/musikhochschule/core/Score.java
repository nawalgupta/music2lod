package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Score {
	
	
	private ArrayList<Part> parts;
	private String version;
	private Identification identification;
	private String rights;
	
	public Score() {
		super();
		
		this.parts = new ArrayList<Part>();
				
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<Part> getParts() {
		
		return parts;
		
	}

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}


	
	
}

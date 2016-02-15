package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Score {

	PartList partList;
	ArrayList<Part> parts;
	String version;
	ArrayList<Identification> identification;
	String rights;
	
	public Score() {
		super();
		
		this.partList = new PartList();
		this.parts = new ArrayList<Part>();
		this.identification = new ArrayList<Identification>();
		
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public PartList getPartList() {
		return partList;
	}

	public ArrayList<Part> getParts() {
		
		return parts;
		
	}

	public ArrayList<Identification> getIdentification() {
		return identification;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

		
	
	
	
}

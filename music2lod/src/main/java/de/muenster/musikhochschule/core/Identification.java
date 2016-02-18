package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Identification {

	private String workTitle;
	private String workNumber;
	private ArrayList<Creator> creators; 
	
	public Identification() {

		super();
		this.creators = new ArrayList<Creator>();
	}

	
	
	public String getWorkTitle() {
		return workTitle;
	}



	public void setWorkTitle(String work) {
		this.workTitle = work;
	}



	public String getWorkNumber() {
		return workNumber;
	}



	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}



	public ArrayList<Creator> getCreators() {
		return creators;
	}

	
	
}

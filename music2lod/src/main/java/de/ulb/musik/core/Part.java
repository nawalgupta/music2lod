package de.ulb.musik.core;

import java.util.ArrayList;

public class Part {

	String id;
	String name;
	
	ArrayList<Measure> measures;
	
	public Part() {
		super();
		this.measures = new ArrayList<Measure>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Measure> getMeasures() {
		return measures;
	}

	
	
}

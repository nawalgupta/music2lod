package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Measure {

	private String id;	
	private int divisions;
	private Key key;
	private TimeSignature time;
	private Clef clef;
	private ArrayList<Note> notes;
	
	public Measure() {
		super();

		this.notes = new ArrayList<Note>();
		this.key = new Key();
		this.time = new TimeSignature();
		this.clef = new Clef();
	
	}

	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	public int getDivisions() {
		return divisions;
	}

	public void setDivisions(int divisions) {
		this.divisions = divisions;
	}



	public ArrayList<Note> getRhythmic() {
		return notes;
	}



	public Key getKey() {
		return key;
	}



	public TimeSignature getTime() {
		return time;
	}



	public Clef getClef() {
		return clef;
	}	
	
	
}

package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Measure {

	String id;	
	int divisions;
	Key key;
	TimeSignature time;
	Clef clef;
	ArrayList<Direction> direction;
	ArrayList<Note> notes;
	
	public Measure() {
		super();

		this.notes = new ArrayList<Note>();
		this.key = new Key();
		this.time = new TimeSignature();
		this.clef = new Clef();
		this.direction = new ArrayList<Direction>();
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



	public ArrayList<Direction> getDirection() {
		
		return direction;
	}
	
	
	
	
}

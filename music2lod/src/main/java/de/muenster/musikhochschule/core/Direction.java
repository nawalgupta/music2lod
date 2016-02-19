package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Direction {

	private ArrayList<Dynamic> dynamic;
	private ArrayList<Word> word;
	private ArrayList<Wedge> wedge;
	private int staff;
	
	
	
	public Direction() {
		super();
	
		this.dynamic = new ArrayList<Dynamic>();
		this.word = new ArrayList<Word>();
		this.wedge = new ArrayList<Wedge>();
	}
	
	public int getStaff() {
		return staff;
	}
	
	public void setStaff(int staff) {
		this.staff = staff;
	}
	
	public ArrayList<Dynamic> getDynamic() {
		return dynamic;
	}

	public ArrayList<Word> getWord() {
		return word;
	}

	public ArrayList<Wedge> getWedge() {
		return wedge;
	}
	
	
}

package de.ulb.musik.core;

import java.util.ArrayList;

public class Direction {

	ArrayList<Dynamic> dynamic;
	ArrayList<Word> word;
	ArrayList<Wedge> wedge;
	int staff;
	
	
	
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

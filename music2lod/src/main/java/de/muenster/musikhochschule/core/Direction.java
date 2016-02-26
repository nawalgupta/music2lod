package de.muenster.musikhochschule.core;

public class Direction {

	private Word word;
	private Wedge wedge;
	private int staff;
	
	
	
	public Direction() {
		super();
	
		this.word = new Word();
		this.wedge = new Wedge();
	}
	
	public int getStaff() {
		return staff;
	}
	
	public void setStaff(int staff) {
		this.staff = staff;
	}
	
	public Word getWord() {
		return word;
	}

	public Wedge getWedge() {
		return wedge;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public void setWedge(Wedge wedge) {
		this.wedge = wedge;
	}
	
	
}

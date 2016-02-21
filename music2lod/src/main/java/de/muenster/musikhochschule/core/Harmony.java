package de.muenster.musikhochschule.core;

public class Harmony {

	/**
	 * @param args
	 */

	private int value;
	private String note;
		
	public Harmony() {
		super();
	}
	
	
	
	public Harmony(int value, String note) {
		super();
		this.value = value;
		this.note = note;
	}



	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}

package de.muenster.musikhochschule.core;

public class Key {

	int fifths;
	String mode;
	
	public Key() {
		super();
	}

	public Key(int fifths, String mode) {
	
		super();
		this.fifths = fifths;
		this.mode = mode;
	}

	public int getFifths() {
		return fifths;
	}

	public void setFifths(int fifths) {
		this.fifths = fifths;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	
	
}

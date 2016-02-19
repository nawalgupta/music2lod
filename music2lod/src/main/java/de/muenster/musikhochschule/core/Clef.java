package de.muenster.musikhochschule.core;

public class Clef {

	private String sign;
	private int line;
	
	public Clef() {
		super();
	}
	
	public Clef(String sign, int line) {
		super();
		this.sign = sign;
		this.line = line;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	
	
}

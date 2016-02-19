package de.muenster.musikhochschule.core;

public class TimeSignature {

	private int beats;
	private int beatType;
			
	public TimeSignature() {

		super();

	}

	public TimeSignature(int beats, int beatType) {
		super();
		this.beats = beats;
		this.beatType = beatType;
	}

	public int getBeats() {
		return beats;
	}

	public void setBeats(int beats) {
		this.beats = beats;
	}

	public int getBeatType() {
		return beatType;
	}

	public void setBeatType(int beatType) {
		this.beatType = beatType;
	}
	
	
}

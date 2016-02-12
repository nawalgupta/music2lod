package de.ulb.musik.core;

public class Time {

	int beats;
	int beatType;
			
	public Time() {

		super();

	}

	public Time(int beats, int beatType) {
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

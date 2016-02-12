package de.ulb.musik.core;

public class Pitch {

	String step;
	int octave;
	
	public Pitch() {
		super();
		
	}

	public Pitch(String step, int octave) {
		super();
		this.step = step;
		this.octave = octave;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}
	
	
}


package de.muenster.musikhochschule.core;

public class Pitch {

	private String step;
	private int octave;
	
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


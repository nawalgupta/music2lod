package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Note {

	Pitch pitch;
	Lyric lyric;
	ArrayList<Beam> beam;
	Notation notation;
	
	int duration;
	String accidental;
	String type;
	int voice;
	String stem;
	int staff;
	boolean chord;
	
	public Note() {
		
		super();		
		this.pitch = new Pitch();
		this.lyric = new Lyric();
		this.notation = new Notation();
		this.beam = new ArrayList<Beam>();
		chord = false;
	}

	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}


	public Lyric getLyric() {
		return lyric;
	}


	public ArrayList<Beam> getBeam() {
		return beam;
	}


	public int getStaff() {
		return staff;
	}


	public void setStaff(int staff) {
		this.staff = staff;
	}


	public Notation getNotation() {
		return notation;
	}


	public String getAccidental() {
		return accidental;
	}


	public void setAccidental(String accidental) {
		this.accidental = accidental;
	}


	public boolean isChord() {
		return chord;
	}


	public void setChord(boolean chord) {
		this.chord = chord;
	}

	
	

	
	
}

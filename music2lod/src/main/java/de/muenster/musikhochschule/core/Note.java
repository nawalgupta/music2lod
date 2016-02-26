package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Note {

	private Pitch pitch;
	private Lyric lyric;
	private Notation notation;	
	private int duration;
	private String accidental;
	private String type;
	private int voice;
	private String stem;
	private int staff;
	private boolean chord;

	private ArrayList<Beam> beam;
	private ArrayList<Direction> direction;
	private ArrayList<Dynamic> dynamic;
	
	public Note() {
		
		super();		
		this.pitch = new Pitch();
		this.lyric = new Lyric();
		this.notation = new Notation();
		this.beam = new ArrayList<Beam>();
		this.direction = new ArrayList<Direction>();
		this.dynamic = new ArrayList<Dynamic>();
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


	public ArrayList<Direction> getDirection() {
		return direction;
	}


	public ArrayList<Dynamic> getDynamic() {
		return dynamic;
	}

	
	

	
	
}

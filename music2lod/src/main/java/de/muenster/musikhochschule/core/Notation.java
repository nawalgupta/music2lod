package de.muenster.musikhochschule.core;

import java.util.ArrayList;

public class Notation {

	private ArrayList<Slur> slur;
	private ArrayList<Articulation> articulation;
	
	public Notation() {
		super();
		slur = new ArrayList<Slur>();
		articulation = new ArrayList<Articulation>();
	}

	public ArrayList<Slur> getSlur() {
		return slur;
	}

	public ArrayList<Articulation> getArticulation() {
		return articulation;
	}
	
	
	
}

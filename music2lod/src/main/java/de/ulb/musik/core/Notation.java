package de.ulb.musik.core;

import java.util.ArrayList;

public class Notation {

	ArrayList<Slur> slur;
	ArrayList<Articulation> articulation;
	
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

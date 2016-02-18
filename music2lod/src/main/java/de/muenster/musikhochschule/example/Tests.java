package de.muenster.musikhochschule.example;

import java.io.File;

import de.muenster.musikhochschule.converter.MusicXMLParser;

public class Tests {


	public static void main(String[] args) {

		MusicXMLParser parser = new MusicXMLParser();
		parser.loadMusicXML(new File("scores/xmlsamples/BeetAnGeSample.xml"));

		parser.setExportFolder("nt/");
		parser.setVerbose(false);

		parser.exportAsNTriples();

	}



}

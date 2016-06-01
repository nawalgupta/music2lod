package de.muenster.musikhochschule.example;

import java.io.File;

import de.muenster.musikhochschule.parser.MusicXMLParser;

public class Tests {

	public static void main(String[] args) {

		MusicXMLParser parser = new MusicXMLParser();

		parser.setExportFolder("nt/");
		parser.setVerbose(false);

//		parser.loadMusicXML(new File("scores/xmlsamples/BeetAnGeSample.xml"));
//		parser.printScoreDetails();
//		
//		parser.exportAsNTriples();

		
		File[] files = new File("scores/tests/").listFiles();
		
		for (File file : files) {

			if(file.getName().endsWith(".xml")){

				parser.loadMusicXML(file);
				parser.exportAsNTriples();
				
			}
			
		}
	
	}



}

package de.muenster.musikhochschule.example;

import java.io.File;

import de.muenster.musikhochschule.parser.MusicXMLParser;

public class Tests {

	public static void main(String[] args) {

		MusicXMLParser parser = new MusicXMLParser();

		parser.setExportFolder("nt/");
		parser.setVerbose(false);

		
//		parser.loadMusicXML(new File("scores/xmlsamples/Elgar_cello_Concerto_Op.85.xml"));
//		parser.printScoreDetails();
//		
//		parser.exportAsNTriples();

		
		File[] files = new File("scores/sammlung/").listFiles();
		
		for (File file : files) {

			if(file.getName().endsWith(".xml")){

				parser.loadMusicXML(file);
				parser.exportAsNTriples();
				
			}
			
		}
	
	}



}

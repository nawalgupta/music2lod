package de.muenster.musikhochschule.example;

import java.io.File;
import de.muenster.musikhochschule.converter.MusicXMLParser;

public class Tests {


	public static void main(String[] args) {

		MusicXMLParser parser = new MusicXMLParser();

		parser.setExportFolder("nt/");
		parser.setVerbose(false);

		//parser.loadMusicXML(new File("scores/xmlsamples/BeetAnGeSample.xml"));
		//parser.exportAsNTriples();
		
		File[] files = new File("scores/xmlsamples/").listFiles();
		
		for (File file : files) {

			if(file.getName().endsWith(".xml")){
				
				System.out.println(file.getName());
				parser.loadMusicXML(file);
				parser.exportAsNTriples();
				
			}
			
		}

	}



}

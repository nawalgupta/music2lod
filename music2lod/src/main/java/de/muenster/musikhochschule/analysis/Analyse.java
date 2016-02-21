package de.muenster.musikhochschule.analysis;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import de.muenster.musikhochschule.core.Harmony;
import de.muenster.musikhochschule.core.Measure;
import de.muenster.musikhochschule.core.Score;
import de.muenster.musikhochschule.parser.MusicXMLParser;

public class Analyse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		MusicXMLParser parser = new MusicXMLParser();
		parser.loadMusicXML(new File("scores/xmlsamples/Cirandinha.xml"));


		harmonicAnalysis(parser.getScore());

	}


	public static void harmonicAnalysis(Score score){

		for (int i = 0; i < score.getParts().size(); i++) {

			for (int j = 0; j < score.getParts().get(i).getMeasures().size(); j++) {

				analyseMeasure(score.getParts().get(i).getMeasures().get(j));

			}

		}

	}


	public static void analyseMeasure(Measure measure){

		ArrayList<String> notesOfMeasure = new ArrayList<String>();
		ArrayList<String> notesAnalysis = new ArrayList<String>();

		System.out.println("===== Measure: " + measure.getId());

		String[] notesOrder = {"C", "D", "E", "F", "G", "A", "B"};

		for (int i = 0; i < measure.getRhythmic().size(); i++) {

			if(!notesOfMeasure.contains(measure.getRhythmic().get(i).getPitch().getStep()) && measure.getRhythmic().get(i).getPitch().getStep()!="rest" ){

				if(measure.getRhythmic().get(i).getAccidental()!=null) {
					
					notesOfMeasure.add(measure.getRhythmic().get(i).getPitch().getStep() + measure.getRhythmic().get(i).getAccidental());
					
				} else {
					
					notesOfMeasure.add(measure.getRhythmic().get(i).getPitch().getStep());
					
				}
			}


		}




		for (int i = 0; i < notesOrder.length; i++) {

			for (int j = 0; j < notesOfMeasure.size(); j++) {

				if (notesOrder[i].equals(notesOfMeasure.get(j))) {

					if (!notesAnalysis.contains(notesOfMeasure.get(j))) {

						notesAnalysis.add(notesOfMeasure.get(j));

					}



				}

			}

		}


		ArrayList<Harmony> escadinha = new ArrayList<Harmony>();

		if (notesAnalysis.contains("C") &&	notesAnalysis.contains("E") && notesAnalysis.contains("G") ){

			System.out.println("CM");
			escadinha = GlobalVariables.getCMajor();

		} else if (notesAnalysis.contains("A") &&	notesAnalysis.contains("C") && notesAnalysis.contains("E") ) {

			System.out.println("Am");
			escadinha = GlobalVariables.getAMinor();

		} else if (notesAnalysis.contains("D") &&	notesAnalysis.contains("Fsharp") && notesAnalysis.contains("A") ) {

			escadinha = GlobalVariables.getDMajor();
			System.out.println("DM");

		} else if (notesAnalysis.contains("G") &&	notesAnalysis.contains("B") && notesAnalysis.contains("D") ) {

			escadinha = GlobalVariables.getGMajor();
			System.out.println("GM");

		} else if (notesAnalysis.contains("B") &&	notesAnalysis.contains("D") && notesAnalysis.contains("F") ) {

			escadinha = GlobalVariables.getBMinor();
			System.out.println("Bm");

		} else if (notesAnalysis.contains("E") &&	notesAnalysis.contains("Gsharp") && notesAnalysis.contains("B") ) {

			escadinha = GlobalVariables.getEMajor();
			System.out.println("EM");

		} else if (notesAnalysis.contains("Csharp") &&	notesAnalysis.contains("E") && notesAnalysis.contains("Gsharp") ) {

			escadinha = GlobalVariables.getCSharpMinor();
			System.out.println("C#m");

		} else if (notesAnalysis.contains("F") &&	notesAnalysis.contains("A") && notesAnalysis.contains("C") ) {

			escadinha = GlobalVariables.getFMajor();
			System.out.println("FM");

		} else if (notesAnalysis.contains("D") &&	notesAnalysis.contains("F") && notesAnalysis.contains("A") ) {

			escadinha = GlobalVariables.getDMinor();
			System.out.println("Dm");

		} else if (notesAnalysis.contains("E") &&	notesAnalysis.contains("G") && notesAnalysis.contains("B") ) {

			escadinha = GlobalVariables.getEMinor();
			System.out.println("Em");

		} else if (notesAnalysis.contains("A") &&	notesAnalysis.contains("Csharp") && notesAnalysis.contains("E") ) {

			escadinha = GlobalVariables.getAMajor();
			System.out.println("AM");

		} else if (notesAnalysis.contains("Fsharp") &&	notesAnalysis.contains("A") && notesAnalysis.contains("Csharp") ) {

			escadinha = GlobalVariables.getFSharpMinor();
			System.out.println("F#m");

		} else if (notesAnalysis.contains("B") &&	notesAnalysis.contains("Dsharp") && notesAnalysis.contains("Fsharp") ) {

			//TODO include function!!!
			System.out.println("BM");

		} else if (notesAnalysis.contains("Gsharp") &&	notesAnalysis.contains("B") && notesAnalysis.contains("Dsharp") ) {

			escadinha = GlobalVariables.getGSharpMinor();
			System.out.println("G#m");

		} else if (notesAnalysis.contains("Fsharp") &&	notesAnalysis.contains("Asharp") && notesAnalysis.contains("Csharp") ) {

			escadinha = GlobalVariables.getDSharpMinor();
			System.out.println("F#M");

		} else if (notesAnalysis.contains("Csharp") &&	notesAnalysis.contains("Esharp") && notesAnalysis.contains("Gsharp") ) {

			escadinha = GlobalVariables.getCSharpMajor();
			System.out.println("C#M");

		} else if (notesAnalysis.contains("Cflat") &&	notesAnalysis.contains("Eflat") && notesAnalysis.contains("Gflat") ) {

			escadinha = GlobalVariables.getCFlatMajor();
			System.out.println("CbM");

		} else if (notesAnalysis.contains("Bflat") &&	notesAnalysis.contains("D") && notesAnalysis.contains("F") ) {

			escadinha = GlobalVariables.getBFlatMajor();
			System.out.println("BbM");

		} else if (notesAnalysis.contains("G") &&	notesAnalysis.contains("Bflat") && notesAnalysis.contains("Eflat") ) {

			escadinha = GlobalVariables.getGMinor();
			System.out.println("Gm");

		} else if (notesAnalysis.contains("Eflat") &&	notesAnalysis.contains("G") && notesAnalysis.contains("Bflat") ) {

			escadinha = GlobalVariables.getCFlatMajor();
			System.out.println("EbM");

		} else if (notesAnalysis.contains("C") &&	notesAnalysis.contains("Eflat") && notesAnalysis.contains("G") ) {

			escadinha = GlobalVariables.getCMinor();
			System.out.println("Cm");

		} else if (notesAnalysis.contains("Aflat") &&	notesAnalysis.contains("C") && notesAnalysis.contains("Eflat") ) {

			escadinha = GlobalVariables.getAFlatMajor();
			System.out.println("AbM");
			
		} else if (notesAnalysis.contains("F") &&	notesAnalysis.contains("Aflat") && notesAnalysis.contains("C") ) {

			escadinha = GlobalVariables.getFMinor();
			System.out.println("Fm");
			
		} else if (notesAnalysis.contains("Dflat") &&	notesAnalysis.contains("F") && notesAnalysis.contains("Aflat") ) {

			escadinha = GlobalVariables.getDFlatMajor();
			System.out.println("DbM");
			
		} else if (notesAnalysis.contains("Bflat") &&	notesAnalysis.contains("Dflat") && notesAnalysis.contains("F") ) {

			escadinha = GlobalVariables.getBFlatMinor();
			System.out.println("Bbm");
			
		} else if (notesAnalysis.contains("Gflat") &&	notesAnalysis.contains("Bflat") && notesAnalysis.contains("Eflat") ) {

			escadinha = GlobalVariables.getGFlatMajor();
			System.out.println("GbM");
			
		} else if (notesAnalysis.contains("Eflat") &&	notesAnalysis.contains("Gflat") && notesAnalysis.contains("Bflat") ) {

			escadinha = GlobalVariables.getEFlatMinor();
			System.out.println("Ebm");
			
		}
		
		
		ArrayList<Harmony> result = new ArrayList<Harmony>();

		for (int i = 0; i < notesAnalysis.size(); i++) {

			for (int j = 0; j < escadinha.size(); j++) {

				if(notesAnalysis.get(i).equals(escadinha.get(j).getNote())){

					result.add(escadinha.get(j));
				}

			}

		}

		
				
		int major = 0;

		for (int i = 0; i < result.size(); i++) {

			if(result.get(i).getValue() > major) major = result.get(i).getValue();
			
			//if(result.get(i).getValue()>=7)	
			System.out.println(result.get(i).getValue() + ": " + result.get(i).getNote());
	
		}


		


	}
		

}

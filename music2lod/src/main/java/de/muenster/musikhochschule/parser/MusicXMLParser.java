package de.muenster.musikhochschule.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import de.muenster.musikhochschule.core.Articulation;
import de.muenster.musikhochschule.core.Beam;
import de.muenster.musikhochschule.core.Creator;
import de.muenster.musikhochschule.core.Direction;
import de.muenster.musikhochschule.core.Dynamic;
import de.muenster.musikhochschule.core.Identification;
import de.muenster.musikhochschule.core.Measure;
import de.muenster.musikhochschule.core.Note;
import de.muenster.musikhochschule.core.Part;
import de.muenster.musikhochschule.core.Score;
import de.muenster.musikhochschule.core.Slur;
import de.muenster.musikhochschule.core.Wedge;
import de.muenster.musikhochschule.core.Word;

public class MusicXMLParser {

	private String outputFolder;
	private File inputFile;
	private boolean verbose;
	private Score score;

	public MusicXMLParser() {
		super();

		this.verbose = false;
		this.score = new Score();

	}

	public String exportAsNTriples(){

		Score score = this.getScore();
		String rdfTypeURI = " <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ";
		String rdfLabelURI = " <http://www.w3.org/2000/01/rdf-schema#label> ";
		String rdfIdURI = " <http://www.w3.org/1999/02/22-rdf-syntax-ns#ID> ";
		String scoreOntologySequence = " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasSequence> ";

		System.out.println("Generating N-Triples for [" + this.getInputFile().getName() + "] ...");

		UUID scoreID = UUID.randomUUID();

		StringBuffer ttl = new StringBuffer();		
		String scoreSubject = "<http://musik.uni-muenster.de/scores/"+scoreID.toString()+">";

		ttl.append(scoreSubject + rdfTypeURI + " <http://dbpedia.org/ontology/MusicalWork> .\n");
		ttl.append(scoreSubject + " <http://purl.org/dc/terms/title> \"" + score.getIdentification().getWorkTitle() + " | " + score.getIdentification().getWorkNumber() + "\"^^<http://www.w3.org/2001/XMLSchema#string> .\n");

		for (int i = 0; i < score.getIdentification().getCreators().size(); i++) {

			String person = "<http://musik.uni-muenster.de/persons/PERSON_"+score.getIdentification().getCreators().get(i).getName().toLowerCase().hashCode()+"> ";

			if(score.getIdentification().getCreators().get(i).getType().toLowerCase().equals("composer")){
				ttl.append(person + " <http://dbpedia.org/property/occupation> <http://dbpedia.org/resource/Composer> .\n");
			}

			if(score.getIdentification().getCreators().get(i).getType().toLowerCase().equals("lyricist")){
				ttl.append(person + " <http://dbpedia.org/property/occupation> <http://dbpedia.org/resource/Lyricist> .\n");
			}

			if(score.getIdentification().getCreators().get(i).getType().toLowerCase().equals("arranger")){
				ttl.append(person + " <http://dbpedia.org/property/occupation> <http://dbpedia.org/resource/Arranger> .\n");
			}


			ttl.append(scoreSubject + " <http://dbpedia.org/ontology/composer> " + person + " .\n");
			ttl.append(person + rdfTypeURI +" <http://xmlns.com/foaf/0.1/Person> .\n");
			ttl.append(person + " <http://xmlns.com/foaf/0.1/name> \"" + score.getIdentification().getCreators().get(i).getName() + "\"^^<http://www.w3.org/2001/XMLSchema#string> .\n");

		}

		for (int i = 0; i < score.getParts().size(); i++) {

			String part = "<http://musik.uni-muenster.de/node/"+scoreID.toString()+"/PART_"+score.getParts().get(i).getId() + ">";
			String partID = score.getParts().get(i).getId();
			String key = "";
			String keyType ="";
			String time = "";
			int beats = 0;
			int beatType = 0;


			ttl.append(scoreSubject + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasScorePart> " + part + ".\n" );
			ttl.append(part + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#ScorePart> .\n" );
			ttl.append(part + rdfLabelURI + "\"" +score.getParts().get(i).getName().replaceAll("[\n\r]", " ") + "\"^^<http://www.w3.org/2001/XMLSchema#string> .\n");
			ttl.append(part + rdfIdURI + "\"" + score.getParts().get(i).getId() + "\"^^<http://www.w3.org/2001/XMLSchema#string> . \n" );

			for (int j = 0; j < score.getParts().get(i).getMeasures().size(); j++) {

				String measure = "<http://musik.uni-muenster.de/node/"+scoreID.toString()+"/MEASURE_" + score.getParts().get(i).getId() + "_" +score.getParts().get(i).getMeasures().get(j).getId() + ">";
				String measureID = score.getParts().get(i).getMeasures().get(j).getId();

				ttl.append(part + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasMeasure> " + measure + " .\n");
				ttl.append(measure + scoreOntologySequence + "\"" + score.getParts().get(i).getMeasures().get(j).getId() + "\"^^<http://www.w3.org/2001/XMLSchema#int> .\n");

				for (int k = 0; k < score.getParts().get(i).getMeasures().get(j).getDirection().size(); k++) {

					String direction = "<http://musik.uni-muenster.de/"+scoreID.toString()+"/DIRECTION_"+score.getParts().get(i).getId() + "_M" + score.getParts().get(i).getMeasures().get(j).getId() + "_" + k + ">";

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getDynamic().size(); l++) {

						
						String dynamic = "<http://musik.uni-muenster.de/node/DYNAMIC_"+ score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getDynamic().get(l).getType() + ">";
						ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasDynamic> " + dynamic + " . \n");
						ttl.append(dynamic + rdfTypeURI + "<http://linkeddata.uni-muenster.de/ontology/musicscore#"+score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getDynamic().get(l).getType() + "> . \n ");
					}

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWord().size(); l++) {

						ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasInstruction> \"" + score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWord().get(l).getWordText()  + "\"^^<http://www.w3.org/2001/XMLSchema#string>. \n");

					}

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWedge().size(); l++) {

						ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasDirection> " + direction + " .\n");

						String wedge = score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWedge().get(l).getType();
						wedge = wedge.substring(0, 1).toUpperCase() + wedge.substring(1);

						ttl.append(direction + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#" + wedge  +"> .\n" );

					}

				}


				if(score.getParts().get(i).getMeasures().get(j).getClef().getSign()!=null){

					String clef = "<http://musik.uni-muenster.de/node/"+scoreID.toString()+"/CLEF_" +score.getParts().get(i).getId() + "_M" + score.getParts().get(i).getMeasures().get(j).getId() + ">";

					ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasClef> " + clef + " .\n");

					String clefType = "";

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==3 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("C")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#Alto>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==5 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("C")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#BaritoneC>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==3 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("F")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#BaritoneF>";

					}


					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==4 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("F")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#Bass>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==1 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("G")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#FrenchViolin>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==2 || score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("C")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#MezzoSoprano>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==1 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("C")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#Soprano>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==5 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("F")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#SubBass>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==4 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("C")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#Tenor>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getLine()==4 && score.getParts().get(i).getMeasures().get(j).getClef().getSign().equals("G")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#Trebble>";

					}

					if (score.getParts().get(i).getMeasures().get(j).getClef().getSign().toLowerCase().equals("percussion")){

						clefType = "<http://linkeddata.uni-muenster.de/ontology/musicscore#Percussion>";
					}

					ttl.append(clef + rdfTypeURI + clefType + " . \n");





				}


				if(score.getParts().get(i).getMeasures().get(j).getTime().getBeats()!=0 &&
						score.getParts().get(i).getMeasures().get(j).getTime().getBeatType()!=0){

					time = "<http://musik.uni-muenster.de/node/"+scoreID.toString()+"/TIME_" +score.getParts().get(i).getId() + "_M" + score.getParts().get(i).getMeasures().get(j).getId()+">";
					beats = score.getParts().get(i).getMeasures().get(j).getTime().getBeats();
					beatType = score.getParts().get(i).getMeasures().get(j).getTime().getBeatType();

					ttl.append(time + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#TimeSignature> . \n");					
					ttl.append(time + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasBeats> \"" + beats + "\"^^<http://www.w3.org/2001/XMLSchema#int> . \n");
					ttl.append(time + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasBeatType> \"" + beatType + "\"^^<http://www.w3.org/2001/XMLSchema#int> . \n");

					ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasTime> " + time + ". \n");					

				} else {

					if(!time.equals("")){

						ttl.append(time + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#TimeSignature> . \n");					
						ttl.append(time + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasBeats> \"" + beats + "\"^^<http://www.w3.org/2001/XMLSchema#int> . \n");
						ttl.append(time + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasBeatType> \"" + beatType + "\"^^<http://www.w3.org/2001/XMLSchema#int> . \n");

						ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasTime> " + time + ". \n");					


					}

				}







				//String keyType = "";


				if(score.getParts().get(i).getMeasures().get(j).getKey().getMode()!=null){

					key = "<http://musik.uni-muenster.de/node/KEY_" +score.getParts().get(i).getId() + "_M" + score.getParts().get(i).getMeasures().get(j).getId()+">";
					ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasKey> " + key + ". \n");

					if(score.getParts().get(i).getMeasures().get(j).getKey().getMode().equals("major")){

						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==0) keyType = "<http://purl.org/NET/c4dm/keys.owl#CMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==1) keyType = "<http://purl.org/NET/c4dm/keys.owl#GMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==2) keyType = "<http://purl.org/NET/c4dm/keys.owl#DMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==3) keyType = "<http://purl.org/NET/c4dm/keys.owl#AMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==4) keyType = "<http://purl.org/NET/c4dm/keys.owl#EMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==5) keyType = "<http://purl.org/NET/c4dm/keys.owl#BMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==6) keyType = "<http://purl.org/NET/c4dm/keys.owl#FShparpMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==7) keyType = "<http://purl.org/NET/c4dm/keys.owl#CSharpMajor>";

						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-1) keyType = "<http://purl.org/NET/c4dm/keys.owl#FMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-2) keyType = "<http://purl.org/NET/c4dm/keys.owl#BFlatMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-3) keyType = "<http://purl.org/NET/c4dm/keys.owl#EFlatMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-4) keyType = "<http://purl.org/NET/c4dm/keys.owl#AFlatMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-5) keyType = "<http://purl.org/NET/c4dm/keys.owl#DFlatMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-6) keyType = "<http://purl.org/NET/c4dm/keys.owl#GFlatMajor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-7) keyType = "<http://purl.org/NET/c4dm/keys.owl#CFlatMajor>";

					}

					if(score.getParts().get(i).getMeasures().get(j).getKey().getMode().equals("minor")){

						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==0) keyType = "<http://purl.org/NET/c4dm/keys.owl#AMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==1) keyType = "<http://purl.org/NET/c4dm/keys.owl#EMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==2) keyType = "<http://purl.org/NET/c4dm/keys.owl#BMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==3) keyType = "<http://purl.org/NET/c4dm/keys.owl#FSharpMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==4) keyType = "<http://purl.org/NET/c4dm/keys.owl#CSharpMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==5) keyType = "<http://purl.org/NET/c4dm/keys.owl#GSharpMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==6) keyType = "<http://purl.org/NET/c4dm/keys.owl#DSharpMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==7) keyType = "<http://purl.org/NET/c4dm/keys.owl#AFlatMinor>";

						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-1) keyType = "<http://purl.org/NET/c4dm/keys.owl#DMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-2) keyType = "<http://purl.org/NET/c4dm/keys.owl#GMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-3) keyType = "<http://purl.org/NET/c4dm/keys.owl#CMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-4) keyType = "<http://purl.org/NET/c4dm/keys.owl#FMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-5) keyType = "<http://purl.org/NET/c4dm/keys.owl#BFlatMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-6) keyType = "<http://purl.org/NET/c4dm/keys.owl#EFlatMinor>";
						if (score.getParts().get(i).getMeasures().get(j).getKey().getFifths()==-7) keyType = "<http://purl.org/NET/c4dm/keys.owl#GSharpMinor>";

					}

					ttl.append(key + rdfTypeURI + keyType + " . \n");

				} else {

					ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasKey> " + key + ". \n");
					ttl.append(key + rdfTypeURI + keyType + " . \n");
				}





				int chordAnchor = 0;
				String chordAnchorObj="";

				for (int k = 0; k < score.getParts().get(i).getMeasures().get(j).getRhythmic().size(); k++) {

					String rhythmic = "<http://musik.uni-muenster.de/node/"+scoreID.toString()+"/RHYTHMIC_" +score.getParts().get(i).getId() + "_M" + score.getParts().get(i).getMeasures().get(j).getId() + "_" + k + ">";
					String rhythmicType = getCapital(score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getType());
					
						
					int rhythmicVoice = score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getVoice();
					int rhythmicDuration = score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getDuration();
					int rhythmicStaff = +score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getStaff();


					ttl.append(rhythmic + scoreOntologySequence + "\""+k+"\"^^<http://www.w3.org/2001/XMLSchema#int> . \n");
					ttl.append(measure + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasRhythm> " + rhythmic + " . \n");

					String rhythmicNote = "<http://musik.uni-muenster.de/node/"+scoreID.toString()+"/NOTE_" + partID + "_M" + measureID + "_N" + k + ">";
					String staff = "<http://musik.uni-muenster.de/node/STAFF/" + scoreID + "_" + partID  +"_S"+ rhythmicStaff +">";
					int noteOctave = score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getPitch().getOctave();

					ttl.append(staff + rdfTypeURI + "<http://linkeddata.uni-muenster.de/ontology/musicscore#Staff> . \n");
					ttl.append(rhythmic + " <http://linkeddata.uni-muenster.de/ontology/musicscore#belongsToStaff> "+ staff+ " . \n");


					if(rhythmicType == null){

						ttl.append(rhythmic + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#Whole> . \n");
						ttl.append(rhythmic + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasNote> " + rhythmicNote + " . \n");
						ttl.append(rhythmicNote + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#Rest> . \n");

					} else {

						if(rhythmicType.toLowerCase().equals("16th")) rhythmicType = "Sixteenth";
						if(rhythmicType.toLowerCase().equals("32nd")) rhythmicType = "Thirtysecond";
						if(rhythmicType.toLowerCase().equals("64th")) rhythmicType = "Sixtyfourth";
						
						ttl.append(rhythmic + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#" + rhythmicType + "> . \n");						
						ttl.append(rhythmic + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasVoice> \"" + rhythmicVoice + "\"^^<http://www.w3.org/2001/XMLSchema#int> . \n");					
						ttl.append(rhythmic + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasDuration> \"" + rhythmicDuration + "\"^^<http://www.w3.org/2001/XMLSchema#int> .\n");


						String accidentalValue = "";

						if(score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getAccidental()!=null){

							accidentalValue = this.getCapital(score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getAccidental().toLowerCase());

						}

						ttl.append(rhythmic + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasNote> " + rhythmicNote + " . \n");	
						String noteType = "<http://purl.org/NET/c4dm/keys.owl#"+score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getPitch().getStep().toUpperCase() + accidentalValue+">";

						if(!score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getPitch().getStep().toUpperCase().equals("REST")){

							ttl.append(rhythmicNote +  rdfTypeURI + noteType + ". \n");
							ttl.append(rhythmicNote + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasOctave> \"" + noteOctave + "\"^^<http://www.w3.org/2001/XMLSchema#int> .\n");

						} else {

							ttl.append(rhythmicNote +  rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#Rest> . \n");

						}
					}



					//TODO: correct triple redundancy (Chord type and anchor are being duplicated)  


					String chord = "";

					if(!score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).isChord()){

						chordAnchor = k;
						chordAnchorObj = rhythmicNote;

					} else {

						chord = "<http://musik.uni-muenster.de/node/"+scoreID.toString()+"/CHORD_" +score.getParts().get(i).getId() + "_M" + score.getParts().get(i).getMeasures().get(j).getId() + "_" + chordAnchor+">"; 
						ttl.append(chord + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasNote> " + rhythmicNote + " . \n" );

						ttl.append(chord + " <http://linkeddata.uni-muenster.de/ontology/musicscore#hasNote> " + chordAnchorObj + " . \n");
						ttl.append(chord + rdfTypeURI + " <http://linkeddata.uni-muenster.de/ontology/musicscore#Chord> . \n");

					}


					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getBeam().size(); l++) {

						/*						
						System.out.println("------Note "+ (k+1) +" Beam Number: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getBeam().get(l).getNumber());
						System.out.println("------Note "+ (k+1) +" Beam Type: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getBeam().get(l).getType());
						 */
					}
					/*
					System.out.println("------Note "+ (k+1) +" Syllabic: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getLyric().getSyllabic());
					System.out.println("------Note "+ (k+1) +" Text: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getLyric().getText());
					 */					
					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getNotation().getSlur().size(); l++) {
						/*
						System.out.println("------Note "+ (k+1) +" Slur Number: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getSlur().get(l).getNumber());
						System.out.println("------Note "+ (k+1) +" Slur Type: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getSlur().get(l).getType());
						 */
					}

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getNotation().getArticulation().size(); l++) {

						//System.out.println("------Note "+ (k+1) +" Articulation Type: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getArticulation().get(l).getType());					

					}

				}

			}

		}


		if(verbose) System.out.println(ttl.toString());

		this.generateRDFTriples(ttl.toString());

		return "";
	}

	private String getCapital(String string){

		if(string != null) string = string.substring(0, 1).toUpperCase() + string.substring(1);

		return string;

	}

	public void printScoreDetails(){

		Score score = this.getScore();

		for (int i = 0; i < score.getParts().size(); i++) {

			System.out.println("Part: " + score.getParts().get(i).getId());

			for (int j = 0; j < score.getParts().get(i).getMeasures().size(); j++) {

				System.out.println("--Measure: " + score.getParts().get(i).getMeasures().get(j).getId());


				for (int k = 0; k < score.getParts().get(i).getMeasures().get(j).getDirection().size(); k++) {

					System.out.println("----Direction Staff: " + score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getStaff());

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getDynamic().size(); l++) {

						System.out.println("----Direction Dynamic: " + score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getDynamic().get(l).getType());

					}

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWord().size(); l++) {

						System.out.println("----Direction Word: " + score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWord().get(l).getWordText());

					}

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWedge().size(); l++) {

						System.out.println("----Direction Wedge Spread: " + score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWedge().get(l).getSpread());
						System.out.println("----Direction Wedge Type: " + score.getParts().get(i).getMeasures().get(j).getDirection().get(k).getWedge().get(l).getType());

					}

				}



				if(score.getParts().get(i).getMeasures().get(j).getClef().getSign()!=null){

					System.out.println("----Clef Line: " + score.getParts().get(i).getMeasures().get(j).getClef().getLine());
					System.out.println("----Clef Sign: " + score.getParts().get(i).getMeasures().get(j).getClef().getSign());
					System.out.println("----Divisions: " + score.getParts().get(i).getMeasures().get(j).getDivisions());
					System.out.println("----Key Fifths: " + score.getParts().get(i).getMeasures().get(j).getKey().getFifths());
					System.out.println("----Key Mode: " + score.getParts().get(i).getMeasures().get(j).getKey().getMode());
					System.out.println("----Time Beats: " + score.getParts().get(i).getMeasures().get(j).getTime().getBeats());
					System.out.println("----Time Beat-Type: " + score.getParts().get(i).getMeasures().get(j).getTime().getBeatType());




				}

				for (int k = 0; k < score.getParts().get(i).getMeasures().get(j).getRhythmic().size(); k++) {

					System.out.println("------Note "+ (k+1) +" Pitch (Step): " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getPitch().getStep());
					System.out.println("------Note "+ (k+1) +" Pitch (Octave): " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getPitch().getOctave());
					System.out.println("------Note "+ (k+1) +" Duration: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getDuration());
					System.out.println("------Note "+ (k+1) +" Type: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getType());
					System.out.println("------Note "+ (k+1) +" Voice: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getVoice());
					System.out.println("------Note "+ (k+1) +" Stem: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getStem());
					System.out.println("------Note "+ (k+1) +" Staff: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getStaff());
					System.out.println("------Note "+ (k+1) +" Accidental: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getAccidental());

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getBeam().size(); l++) {

						System.out.println("------Note "+ (k+1) +" Beam Number: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getBeam().get(l).getNumber());
						System.out.println("------Note "+ (k+1) +" Beam Type: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getBeam().get(l).getType());

					}

					System.out.println("------Note "+ (k+1) +" Syllabic: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getLyric().getSyllabic());
					System.out.println("------Note "+ (k+1) +" Text: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getLyric().getText());

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getNotation().getSlur().size(); l++) {

						System.out.println("------Note "+ (k+1) +" Slur Number: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getNotation().getSlur().get(l).getNumber());
						System.out.println("------Note "+ (k+1) +" Slur Type: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getNotation().getSlur().get(l).getType());

					}

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getNotation().getArticulation().size(); l++) {

						System.out.println("------Note "+ (k+1) +" Articulation Type: " + score.getParts().get(i).getMeasures().get(j).getRhythmic().get(k).getNotation().getArticulation().get(l).getType());					

					}

				}

			}

		}


		for (int i = 0; i < score.getIdentification().getCreators().size(); i++) {

			System.out.println("\nCreator: " + score.getIdentification().getCreators().get(i).getName());
			System.out.println("Creator Type: " + score.getIdentification().getCreators().get(i).getType());

		}

		System.out.println("\nCopy Rights: "+score.getRights());

	}

	private void generateRDFTriples(String triples){

		try {

			StringBuffer buffer = new StringBuffer();
			buffer.append(triples);

			String fileName = "";

			int index = this.getInputFile().getName().lastIndexOf('.');
			if (index != -1) {
				fileName = this.getInputFile().getName().substring(0, index);
			} else {

				fileName = this.getInputFile().getName();
			}


			@SuppressWarnings("static-access")
			FileOutputStream fileStream = new FileOutputStream(new File(this.getOutputFolder() + this.getInputFile().separator + fileName +".nt"),false);
			OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF8");

			writer.append(buffer.toString());
			writer.close();

			System.out.println("N-Triples for [" + this.getInputFile().getName() + "] generated at: " + this.getOutputFolder() + "\n");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();		
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();		
		}

	}

	public Score loadMusicXML(File file) {

		Score score = new Score();
		this.setInputFile(file);

		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(false);
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

			builder.setEntityResolver(new EntityResolver() {

				@Override
				public InputSource resolveEntity(String publicId, String systemId)
						throws SAXException, IOException {
					//System.out.println("Ignoring " + publicId + ", " + systemId);
					return new InputSource(new StringReader(""));
				}
			});

			Document document = builder.parse(this.getInputFile());       

			/**
			 * Loading score version
			 */

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			NodeList subfields = (NodeList) xpath.evaluate("//score-partwise/@version", document,XPathConstants.NODESET);

			if (subfields.getLength() != 0) {
				Node currentItem = subfields.item(0);   
				score.setVersion(currentItem.getTextContent());                                     
			}

			//System.out.println("MusicXML Version: " + score.getVersion());

			/**
			 * Loading Header Info
			 */

			Identification identification = new Identification();			

			NodeList nodeIdentification = (NodeList) xpath.evaluate("//score-partwise/identification", document,XPathConstants.NODESET);

			Element elementIdentification = (Element) nodeIdentification.item(0);

			if (nodeIdentification.getLength() != 0) {

				for (int i = 0; i < elementIdentification.getElementsByTagName("creator").getLength(); i++) {

					Creator creator = new Creator();
					creator.setName(elementIdentification.getElementsByTagName("creator").item(i).getTextContent());
					creator.setType(elementIdentification.getElementsByTagName("creator").item(i).getAttributes().getNamedItem("type").getTextContent());

					identification.getCreators().add(creator);


				}

				NodeList nodeWork = (NodeList) xpath.evaluate("//score-partwise/work", document,XPathConstants.NODESET);

				if (nodeWork.getLength() != 0 && nodeWork != null) {

					Element elementWork = (Element) nodeWork.item(0);

					if(elementWork.getElementsByTagName("work-number").item(0)!=null)identification.setWorkNumber(elementWork.getElementsByTagName("work-number").item(0).getTextContent());					
					if(elementWork.getElementsByTagName("work-title").item(0)!=null)identification.setWorkTitle(elementWork.getElementsByTagName("work-title").item(0).getTextContent());

				}


				score.setIdentification(identification);

			}



			NodeList nodeRights = (NodeList) xpath.evaluate("//score-partwise/identification/rights", document,XPathConstants.NODESET);

			if (nodeRights.getLength() != 0) {

				Element elementRights = (Element) nodeRights.item(0);
				score.setRights(elementRights.getTextContent());                                     

			}


			/**
			 * Loading parts
			 */

			subfields = (NodeList) xpath.evaluate("//score-partwise/part-list/score-part/@id", document,XPathConstants.NODESET);

			if (subfields.getLength() != 0) {

				for (int i = 0; i < subfields.getLength(); i++) {

					Node currentItem = subfields.item(i);   
					Part part = new Part();
					part.setId(currentItem.getTextContent());

					NodeList nodePartName = (NodeList) xpath.evaluate("//score-partwise/part-list/score-part[@id='"+part.getId()+"']", document,XPathConstants.NODESET);

					if (nodePartName.getLength() != 0) {

						Element elementNotes = (Element) nodePartName.item(0);
						part.setName(elementNotes.getElementsByTagName("part-name").item(0).getTextContent());


					}

					score.getParts().add(part);

				}

			}


			//TODO: add instrument name

			/**
			 * Loading measures 
			 */

			System.out.println("\nParsing MusicXML file [" + this.getInputFile().getName() + "], it might take a while ...");

			for (int i = 0; i < score.getParts().size(); i++) {

				NodeList nodesMeasuresIDs = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure", document,XPathConstants.NODESET);

				if (nodesMeasuresIDs.getLength() != 0) {


					/**
					 * Loading measure data
					 */

					for (int j = 0; j < nodesMeasuresIDs.getLength(); j++) {

						Measure measure = new Measure();

						NodeList nodesMeasures = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure/@number", document,XPathConstants.NODESET);
						measure.setId(nodesMeasures.item(j).getTextContent());

						NodeList nodesMeasuresDivision = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+measure.getId()+"']/attributes/divisions", document,XPathConstants.NODESET);						
						if(nodesMeasuresDivision.getLength()!=0) measure.setDivisions(Integer.parseInt(nodesMeasuresDivision.item(0).getTextContent()));

						NodeList nodesMeasuresFifths = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+measure.getId()+"']/attributes/key/fifths", document,XPathConstants.NODESET);
						if(nodesMeasuresFifths.getLength()!=0) measure.getKey().setFifths(Integer.parseInt(nodesMeasuresFifths.item(0).getTextContent()));

						NodeList nodesMeasuresMode = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+measure.getId()+"']/attributes/key/mode", document,XPathConstants.NODESET);
						if(nodesMeasuresMode.getLength()!=0) measure.getKey().setMode(nodesMeasuresMode.item(0).getTextContent());

						NodeList nodesMeasuresBeats = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+measure.getId()+"']/attributes/time/beats", document,XPathConstants.NODESET);
						if(nodesMeasuresBeats.getLength()!=0) measure.getTime().setBeats(Integer.parseInt(nodesMeasuresBeats.item(0).getTextContent()));

						NodeList nodesMeasuresBeatType = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+measure.getId()+"']/attributes/time/beat-type", document,XPathConstants.NODESET);
						if(nodesMeasuresBeatType.getLength()!=0) measure.getTime().setBeatType(Integer.parseInt(nodesMeasuresBeatType.item(0).getTextContent()));

						NodeList nodesMeasuresClef = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+measure.getId()+"']/attributes/clef/sign", document,XPathConstants.NODESET);
						if(nodesMeasuresClef.getLength()!=0) measure.getClef().setSign(nodesMeasuresClef.item(0).getTextContent());

						NodeList nodesMeasuresClefLine = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+measure.getId()+"']/attributes/clef/line", document,XPathConstants.NODESET);
						if(nodesMeasuresClefLine.getLength()!=0) measure.getClef().setLine(Integer.parseInt(nodesMeasuresClefLine.item(0).getTextContent()));


						/**
						 * Adding loaded measures.
						 */

						score.getParts().get(i).getMeasures().add(measure);


					}








					for (int j = 0; j < score.getParts().get(i).getMeasures().size(); j++) {


						/**
						 * Loading all Notes of a single measure
						 */

						NodeList nodeNotes = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+score.getParts().get(i).getMeasures().get(j).getId()+"']/note", document,XPathConstants.NODESET);


						if (nodeNotes.getLength() != 0) {


							for (int k = 0; k < nodeNotes.getLength(); k++) {

								Element elementNotes = (Element) nodeNotes.item(k);


								/**
								 * Loading note Pitch (Step)
								 */	
								Note note = new Note();


								if(elementNotes.getElementsByTagName("step").item(0)!=null){

									note.getPitch().setStep(elementNotes.getElementsByTagName("step").item(0).getTextContent());

								} else {

									note.getPitch().setStep("rest");
								}

								/**
								 * Loading note Pitch (Octave)
								 */


								if(elementNotes.getElementsByTagName("octave").item(0)!=null){

									note.getPitch().setOctave(Integer.parseInt(elementNotes.getElementsByTagName("octave").item(0).getTextContent()));
								} else {

									note.getPitch().setOctave(0);
								}

								/**
								 * Loading Note Duration
								 */


								if(elementNotes.getElementsByTagName("duration").item(0)!=null){

									note.setDuration(Integer.parseInt(elementNotes.getElementsByTagName("duration").item(0).getTextContent()));

								}


								/**
								 * Loading Note Chord Flag
								 */


								if(elementNotes.getElementsByTagName("chord").item(0)!=null){

									note.setChord(true);

								}

								/**
								 * Loading Note Type
								 */


								if(elementNotes.getElementsByTagName("type").item(0)!=null){

									note.setType(elementNotes.getElementsByTagName("type").item(0).getTextContent());

								}


								/**
								 * Loading Note Voice
								 */


								if(elementNotes.getElementsByTagName("voice").item(0)!=null){

									note.setVoice(Integer.parseInt(elementNotes.getElementsByTagName("voice").item(0).getTextContent()));

								}

								/**
								 * Loading Note Stem
								 */
								if(elementNotes.getElementsByTagName("stem").item(0)!=null){

									note.setStem(elementNotes.getElementsByTagName("stem").item(0).getTextContent());

								} else {

									note.setStem("-");

								}


								/**
								 * Loading Note Staff
								 */


								if(elementNotes.getElementsByTagName("staff").item(0)!=null){

									note.setStaff(Integer.parseInt(elementNotes.getElementsByTagName("staff").item(0).getTextContent()));

								}

								/**
								 * Loading Beam Type
								 */
								if(elementNotes.getElementsByTagName("beam").item(0)!=null){

									for (int l = 0; l < elementNotes.getElementsByTagName("beam").getLength(); l++) {

										Beam beam = new Beam();
										beam.setNumber(Integer.parseInt(elementNotes.getElementsByTagName("beam").item(l).getAttributes().getNamedItem("number").getTextContent()));
										beam.setType(elementNotes.getElementsByTagName("beam").item(l).getTextContent());

										note.getBeam().add(beam);

									}


								}


								/**
								 * Loading Note Accidental
								 */


								if(elementNotes.getElementsByTagName("accidental").item(0)!=null){

									note.setAccidental(elementNotes.getElementsByTagName("accidental").item(0).getTextContent());

								}


								/**
								 * Loading Note Lyric (Syllabic)
								 */
								if(elementNotes.getElementsByTagName("syllabic").item(0)!=null){

									note.getLyric().setSyllabic(elementNotes.getElementsByTagName("syllabic").item(0).getTextContent());

								}

								/**
								 * Loading Note Lyric (Text)
								 */
								if(elementNotes.getElementsByTagName("text").item(0)!=null){

									note.getLyric().setText(elementNotes.getElementsByTagName("text").item(0).getTextContent());

								}

								/**
								 * Loading Note Notation Slur
								 */
								if(elementNotes.getElementsByTagName("slur").item(0)!=null){

									for (int l = 0; l < elementNotes.getElementsByTagName("slur").getLength(); l++) {

										Slur slur = new Slur();
										slur.setNumber(Integer.parseInt(elementNotes.getElementsByTagName("slur").item(l).getAttributes().getNamedItem("number").getTextContent()));
										slur.setType(elementNotes.getElementsByTagName("slur").item(l).getAttributes().getNamedItem("type").getTextContent());

										note.getNotation().getSlur().add(slur);

									}


								}




								/**
								 * Loading Note Notation Articulation Accent
								 */
								if(elementNotes.getElementsByTagName("accent").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("accent");
									note.getNotation().getArticulation().add(articulation);

								}								



								/**
								 * Loading Note Notation Articulation Breath-mark
								 */
								if(elementNotes.getElementsByTagName("breath-mark").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("breath-mark");
									note.getNotation().getArticulation().add(articulation);

								}								

								/**
								 * Loading Note Notation Articulation Caesura
								 */
								if(elementNotes.getElementsByTagName("caesura").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("caesura");
									note.getNotation().getArticulation().add(articulation);

								}									

								/**
								 * Loading Note Notation Articulation detached-legato
								 */
								if(elementNotes.getElementsByTagName("detached-legato").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("detached-legato");
									note.getNotation().getArticulation().add(articulation);

								}		



								/**
								 * Loading Note Notation Articulation Doit
								 */
								if(elementNotes.getElementsByTagName("doit").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("doit");
									note.getNotation().getArticulation().add(articulation);

								}	


								/**
								 * Loading Note Notation Articulation fallof
								 */
								if(elementNotes.getElementsByTagName("fallof").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("fallof");
									note.getNotation().getArticulation().add(articulation);

								}	


								/**
								 * Loading Note Notation Articulation plop
								 */
								if(elementNotes.getElementsByTagName("plop").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("plop");
									note.getNotation().getArticulation().add(articulation);

								}	


								/**
								 * Loading Note Notation Articulation scoop
								 */
								if(elementNotes.getElementsByTagName("scoop").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("scoop");
									note.getNotation().getArticulation().add(articulation);

								}	


								/**
								 * Loading Note Notation Articulation spiccato
								 */
								if(elementNotes.getElementsByTagName("spiccato").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("spiccato");
									note.getNotation().getArticulation().add(articulation);

								}	

								/**
								 * Loading Note Notation Articulation staccatissimo 
								 */
								if(elementNotes.getElementsByTagName("staccatissimo").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("staccatissimo");
									note.getNotation().getArticulation().add(articulation);

								}	


								/**
								 * Loading Note Notation Articulation staccato  
								 */
								if(elementNotes.getElementsByTagName("staccato").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("staccato");
									note.getNotation().getArticulation().add(articulation);

								}	


								/**
								 * Loading Note Notation Articulation stress   
								 */
								if(elementNotes.getElementsByTagName("stress").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("stress");
									note.getNotation().getArticulation().add(articulation);

								}	

								/**
								 * Loading Note Notation Articulation strong-accent   
								 */
								if(elementNotes.getElementsByTagName("strong-accent").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("strong-accent");
									note.getNotation().getArticulation().add(articulation);

								}	

								/**
								 * Loading Note Notation Articulation tenuto    
								 */
								if(elementNotes.getElementsByTagName("tenuto").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("tenuto");
									note.getNotation().getArticulation().add(articulation);

								}	


								/**
								 * Loading Note Notation Articulation unstress    
								 */
								if(elementNotes.getElementsByTagName("unstress").item(0)!=null){

									Articulation articulation = new Articulation();										
									articulation.setType("unstress");
									note.getNotation().getArticulation().add(articulation);

								}	

								score.getParts().get(i).getMeasures().get(j).getRhythmic().add(note);


							}
						}

						NodeList nodesMeasuresDirections = (NodeList) xpath.evaluate("//score-partwise/part[@id='"+score.getParts().get(i).getId()+"']/measure[@number='"+score.getParts().get(i).getMeasures().get(j).getId()+"']/direction", document,XPathConstants.NODESET);


						for (int l = 0; l < nodesMeasuresDirections.getLength(); l++) {


							Direction directions = new Direction();							

							Word word = new Word();
							Dynamic dynamic = new Dynamic();

							Element elementDirections = (Element) nodesMeasuresDirections.item(l);

							if(elementDirections.getElementsByTagName("p").item(0) !=null){

								dynamic.setType("p");
								directions.getDynamic().add(dynamic);

							}

							if(elementDirections.getElementsByTagName("pp").item(0) !=null){

								dynamic.setType("pp");
								directions.getDynamic().add(dynamic);

							}

							if(elementDirections.getElementsByTagName("ppp").item(0) !=null){

								dynamic.setType("ppp");
								directions.getDynamic().add(dynamic);

							}

							if(elementDirections.getElementsByTagName("pppp").item(0) !=null){

								dynamic.setType("pppp");
								directions.getDynamic().add(dynamic);

							}


							if(elementDirections.getElementsByTagName("f").item(0) !=null){


								dynamic.setType("f");
								directions.getDynamic().add(dynamic);

							}

							if(elementDirections.getElementsByTagName("ff").item(0) !=null){

								dynamic.setType("ff");
								directions.getDynamic().add(dynamic);

							}

							if(elementDirections.getElementsByTagName("fff").item(0) !=null){

								dynamic.setType("fff");
								directions.getDynamic().add(dynamic);

							}

							if(elementDirections.getElementsByTagName("ffff").item(0) !=null){

								dynamic.setType("ffff");
								directions.getDynamic().add(dynamic);

							}


							if(elementDirections.getElementsByTagName("words").item(0)!=null){

								word.setWordText(elementDirections.getElementsByTagName("words").item(0).getTextContent());
								directions.getWord().add(word);							

							}


							if(elementDirections.getElementsByTagName("staff").item(0) !=null){

								directions.setStaff(Integer.parseInt(elementDirections.getElementsByTagName("staff").item(0).getTextContent()));

							}


							if(elementDirections.getElementsByTagName("wedge").item(0) !=null){

								Wedge wedge = new Wedge();
								wedge.setType(elementDirections.getElementsByTagName("wedge").item(0).getAttributes().getNamedItem("type").getTextContent());

								if(elementDirections.getElementsByTagName("wedge").item(0).getAttributes().getNamedItem("spread")!=null){

									wedge.setSpread(Integer.parseInt(elementDirections.getElementsByTagName("wedge").item(0).getAttributes().getNamedItem("spread").getTextContent()));

								}

								directions.getWedge().add(wedge);

							}

							score.getParts().get(i).getMeasures().get(j).getDirection().add(directions);

						}



					}	









				}


			}

			/**
			 * Loading measures attributes
			 */



		} catch (MalformedURLException ex) {                   
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (XPathExpressionException ex) {
			ex.printStackTrace();
		}


		this.score = score;

		return score;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setExportFolder(String outputFile) {
		this.outputFolder = outputFile;
	}

	public File getInputFile() {
		return inputFile;
	}

	private void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public Score getScore() {
		return this.score;
	}


}

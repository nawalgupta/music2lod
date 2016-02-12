package de.ulb.musik.converter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
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
import de.ulb.musik.core.Articulation;
import de.ulb.musik.core.Beam;
import de.ulb.musik.core.Direction;
import de.ulb.musik.core.Dynamic;
import de.ulb.musik.core.Identification;
import de.ulb.musik.core.Measure;
import de.ulb.musik.core.Note;
import de.ulb.musik.core.Part;
import de.ulb.musik.core.Score;
import de.ulb.musik.core.Slur;
import de.ulb.musik.core.Wedge;
import de.ulb.musik.core.Word;

public class MusicXML2LOD {

	public static void main(String[] args) {

		MusicXML2LOD instance = new MusicXML2LOD();
		instance.printScoreDetails(instance.parseMusicXML("scores/xmlsamples/MozaVeilSample.xml"));
		//instance.printScoreDetails(t.parseMusicXML("scores/xmlsamples/DebuMandSample.xml"));
		

	}

	private void printScoreDetails(Score score){

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

				for (int k = 0; k < score.getParts().get(i).getMeasures().get(j).getNotes().size(); k++) {

					System.out.println("------Note "+ (k+1) +" Pitch (Step): " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getPitch().getStep());
					System.out.println("------Note "+ (k+1) +" Pitch (Octave): " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getPitch().getOctave());
					System.out.println("------Note "+ (k+1) +" Duration: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getDuration());
					System.out.println("------Note "+ (k+1) +" Type: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getType());
					System.out.println("------Note "+ (k+1) +" Voice: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getVoice());
					System.out.println("------Note "+ (k+1) +" Stem: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getStem());
					System.out.println("------Note "+ (k+1) +" Staff: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getStaff());
					System.out.println("------Note "+ (k+1) +" Accidental: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getAccidental());
					
					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getBeam().size(); l++) {

						System.out.println("------Note "+ (k+1) +" Beam Number: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getBeam().get(l).getNumber());
						System.out.println("------Note "+ (k+1) +" Beam Type: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getBeam().get(l).getType());

					}

					System.out.println("------Note "+ (k+1) +" Syllabic: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getLyric().getSyllabic());
					System.out.println("------Note "+ (k+1) +" Text: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getLyric().getText());
					
					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getSlur().size(); l++) {

						System.out.println("------Note "+ (k+1) +" Slur Number: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getSlur().get(l).getNumber());
						System.out.println("------Note "+ (k+1) +" Slur Type: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getSlur().get(l).getType());

					}

					for (int l = 0; l < score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getArticulation().size(); l++) {

						System.out.println("------Note "+ (k+1) +" Articulation Type: " + score.getParts().get(i).getMeasures().get(j).getNotes().get(k).getNotation().getArticulation().get(l).getType());					

					}
					
				}

			}

		}


		for (int i = 0; i < score.getIdentification().size(); i++) {
			
			System.out.println("\nCreator: " + score.getIdentification().get(i).getCreator());
			System.out.println("Creator Type: " + score.getIdentification().get(i).getCreatorType());
			
		}
		
		System.out.println("\nCopy Rights: "+score.getRights());
	}


	private Score parseMusicXML(String path) {

		Score score = new Score();

		try {

			File file = new File(path);

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(false);
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

			builder.setEntityResolver(new EntityResolver() {

				@Override
				public InputSource resolveEntity(String publicId, String systemId)
						throws SAXException, IOException {
					System.out.println("Ignoring " + publicId + ", " + systemId);
					return new InputSource(new StringReader(""));
				}
			});

			Document document = builder.parse(file);       

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

			System.out.println("MusicXML Version: " + score.getVersion());

			/**
			 * Loading Header Info
			 */
			NodeList nodeCreator = (NodeList) xpath.evaluate("//score-partwise/identification/creator", document,XPathConstants.NODESET);

			if (nodeCreator.getLength() != 0) {
			
				for (int i = 0; i < nodeCreator.getLength(); i++) {
					
					Identification identification = new Identification();			
					Element elementCreator = (Element) nodeCreator.item(i);
					
					identification.setCreator(elementCreator.getTextContent());
					identification.setCreatorType(elementCreator.getAttributes().getNamedItem("type").getTextContent());
					
					score.getIdentification().add(identification);                                     

				}

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

					score.getParts().add(part);

				}

			}


			/**
			 * Loading measures 
			 */
			
			System.out.println("\nParsing MusicXML file, it might take a while ... \n");
			
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
						 * Loading Notes of a single measure
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
								
								score.getParts().get(i).getMeasures().get(j).getNotes().add(note);


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
								wedge.setSpread(Integer.parseInt(elementDirections.getElementsByTagName("wedge").item(0).getAttributes().getNamedItem("spread").getTextContent()));
								wedge.setType(elementDirections.getElementsByTagName("wedge").item(0).getAttributes().getNamedItem("type").getTextContent());
								
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




		return score;
	}



}

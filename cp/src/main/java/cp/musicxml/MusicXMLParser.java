package cp.musicxml;

import cp.midi.MidiDevicesUtil;
import cp.model.note.BeamType;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


public class MusicXMLParser {
	public org.jsoup.nodes.Document doc;
	HashMap<Integer, Integer> divMultiplier;
	public int divisions;
	private Map<String, List<Note>> notesPerInstrument;
	private final List<Note> notes = new ArrayList<>();

	private static MidiDevicesUtil midiDevicesUtil = new MidiDevicesUtil();
	
	 public static void main (String[]args) throws IOException, InvalidMidiDataException {
         MusicXMLParser parser = new MusicXMLParser("cp/src/main/resources/test.xml");
         parser.parseMusicXML();
         List<Note> notes = parser.getNotes();
         notes.forEach(n -> System.out.println(n));
         Map<String, List<Note>> notesPerInstrument = parser.getNotesPerInstrument();
         notesPerInstrument.forEach((k,v) -> System.out.println(k + ":" + v));
         parser.getNotesForVoice(2).forEach(n -> System.out.println(n));
     }

	
	public Map<String, List<Note>> getNotesPerInstrument() {
		return notesPerInstrument;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public List<Note> getNotesForVoice(int voice){
		return notes.stream().filter(n -> n.getVoice() == voice).sorted(comparing(Note::getPosition)).collect(toList());
	}

	public MusicXMLParser(String filename) throws IOException {
		final Resource resource = new FileSystemResource(filename);
		InputStream is = resource.getInputStream();
		doc = Jsoup.parse(is, "UTF-8", "", Parser.xmlParser());
	}

	public void parseMusicXML() {
		parseNotes();
		mapNotesPerInstrument();
	}

	private void parseNotes() {
		Integer currentVoice;
		int duration = 0;
		Integer position;
		Integer lastDuration;
		Elements parts = this.doc.select("part");
		for (Element part : parts) {
			position = 0;
			lastDuration = 0;
			divisions = 1;
			for (Element partElement : part.getElementsByTag("measure")) {
				String measure = "0";
				if (!partElement.getElementsByTag("divisions").isEmpty()) {
					divisions = Integer.valueOf(partElement.getElementsByTag("divisions").text());
				}
				measure = partElement.attr("number");
				for (Element measureElement : partElement.children()) {
					Note note = new Note();
					if (!measureElement.getElementsByTag("direction").isEmpty() && !measureElement.getElementsByTag("direction-type").isEmpty()) {
						Element dynamics = measureElement.getElementsByTag("dynamics").first();
						if (dynamics != null) {
							String dynamic = dynamics.tagName();
							note.setDynamic(Dynamic.getDynamic(dynamic));
						}
					}
					if (measureElement.tagName().equals("note")) {
						if (!measureElement.getElementsByTag("voice").isEmpty()) {
							currentVoice = Integer.valueOf(measureElement.getElementsByTag("voice").text());
							note.setVoice(currentVoice);
						}
						// get the pitch
						if (!measureElement.getElementsByTag("pitch").isEmpty()) {
							for (Element thispitch : measureElement.getElementsByTag("pitch")) {
								String step = thispitch.getElementsByTag("step").text();
								int pitch = getPitchFromStep(step);
								String octave = thispitch.getElementsByTag("octave").text()
										.replaceAll("^\\s+|\\s+$|\\s*(\n)\\s*|(\\s)\\s*", "$1$2");
								int octaveInt = Integer.parseInt(octave);
								note.setOctave(octaveInt);
								String alter = String.valueOf(thispitch.getElementsByTag("alter").text());
								int alterValue = 0;
								if (!thispitch.getElementsByTag("alter").isEmpty()) {
									if (alter.equals("1")) {
										alterValue = 1;
									} else if (alter.equals("-1")) {
										alterValue = -1;
									} else if (alter.equals("2")) {
										alterValue = 2;
									} else if (alter.equals("-2")) {
										alterValue = -2;
									}
								}
								int pitchClass = pitch + alterValue;
								note.setPitchClass(pitchClass);
								note.setPitch(pitchClass + 12 * octaveInt);
							}

						} else {
							note.setPitch(cp.model.note.Note.REST);
						}
						if(measureElement.getElementsByTag("time-modification").isEmpty()){
							switch (measureElement.getElementsByTag("type").text()) {
							case "16th":
								duration = DurationConstants.SIXTEENTH;
								if(!measureElement.getElementsByTag("dot").isEmpty()){
									//TODO
								}
								break;
							case "eighth":
								duration = DurationConstants.EIGHT;
								if(!measureElement.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.EIGHT + DurationConstants.SIXTEENTH;
								}
								break;
							case "quarter":
								duration = DurationConstants.QUARTER;
								if(!measureElement.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.QUARTER + DurationConstants.EIGHT;
								}
								break;
							case "half":
								duration = DurationConstants.HALF;
								if(!measureElement.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.HALF + DurationConstants.QUARTER;
								}
								break;
							case "whole":
								duration = DurationConstants.WHOLE;
								if(!measureElement.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.WHOLE + DurationConstants.HALF;
								}
								break;
							default:
								break;
							}
						}else{
							if(measureElement.getElementsByTag("actual-notes").text().equals("3")){
								note.setTriplet(true);
								note.setTimeModification(measureElement.getElementsByTag("normal-type").text());
								switch (measureElement.getElementsByTag("type").text()) {
								case "16th":
									duration = DurationConstants.SIXTEENTH_TRIPLET;
									break;
								case "eighth":
									duration = DurationConstants.EIGHT_TRIPLET;
									if(!measureElement.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.EIGHT_TRIPLET + DurationConstants.SIXTEENTH_TRIPLET;
									}
									break;
								case "quarter":
									duration = DurationConstants.QUARTER_TRIPLET;
									if(!measureElement.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.QUARTER_TRIPLET + DurationConstants.EIGHT_TRIPLET;
									}
									break;
								case "half":
									duration = DurationConstants.HALF_TRIPLET;
									if(!measureElement.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.HALF_TRIPLET + DurationConstants.QUARTER_TRIPLET;
									}
									break;
								default:
									break;
								}
							}else if(measureElement.getElementsByTag("actual-notes").text().equals("6")){
								note.setSextuplet(true);
								note.setTimeModification(measureElement.getElementsByTag("normal-type").text());
								switch (measureElement.getElementsByTag("type").text()) {
								case "16th":
									duration = DurationConstants.SIXTEENTH_TRIPLET;
									break;
								case "eighth":
									duration = DurationConstants.EIGHT_TRIPLET;
									if(!measureElement.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.EIGHT_TRIPLET + DurationConstants.SIXTEENTH_TRIPLET;
									}
									break;
								case "quarter":
									duration = DurationConstants.QUARTER_TRIPLET;
									if(!measureElement.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.QUARTER_TRIPLET + DurationConstants.EIGHT_TRIPLET;
									}
									break;
								case "half":
									duration = DurationConstants.HALF_TRIPLET;
									if(!measureElement.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.HALF_TRIPLET + DurationConstants.QUARTER_TRIPLET;
									}
									break;
								default:
									break;
								}
							}else if(measureElement.getElementsByTag("actual-notes").text().equals("5")){
								note.setQuintuplet(true);
								note.setTimeModification(measureElement.getElementsByTag("normal-type").text());
								switch (measureElement.getElementsByTag("type").text()) {
								case "16th":
									duration = DurationConstants.SIXTEENTH_QUINTUPLET;
									break;
								case "eighth":
									duration = DurationConstants.EIGHT_QUINTUPLET;
									if(!measureElement.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.EIGHT_QUINTUPLET + DurationConstants.SIXTEENTH_QUINTUPLET;
									}
									break;
								default:
									break;
								}
							}
						}
						
//						duration = Integer.valueOf(measureElement.getElementsByTag("duration").text()); // *
//																									// divMultiplier.get(divisions);			
//						duration =  duration * Note.DEFAULT_LENGTH / MusicXMLWriter.DIVISIONS;
						
						note.setLength(duration);
						note.setDisplayLength(duration);
						
		
						// now check if it is a chord
						if (!measureElement.getElementsByTag("chord").isEmpty()) {
//							note.setStartTime(position);
							// retract previous duration
							note.setPosition(position - lastDuration);

						} else {
							// increment start time of the current voice
							// System.out.print(" start: " + position);
							note.setPosition(position);
							position = position + duration;
						}

						lastDuration = duration;
						note.setInstrument(measureElement.getElementsByTag("instrument").attr("id"));

						if(!measureElement.getElementsByTag("tied").isEmpty()){
							if(measureElement.getElementsByTag("tied").attr("type").equals("start")){
								note.setTieStart(true);
							}
							if(measureElement.getElementsByTag("tied").attr("type").equals("stop")){
								note.setTieEnd(true);
							}
						}
						
						if(!measureElement.getElementsByTag("tuplet").isEmpty()){
							Element tuplet = measureElement.getElementsByTag("tuplet").first();
							if(tuplet.attr("type").equals("start")){
								note.setTupletType(TupletType.START);
							}
							if(tuplet.attr("type").equals("stop")){
								note.setTupletType(TupletType.STOP);
							}
							if(tuplet.attr("bracket").equals("yes")){
								note.setBracket(true);
							}
						}
						
						if(!measureElement.getElementsByTag("beam").isEmpty()){
							if(measureElement.getElementsByTag("beam").size() == 1) {
								if( "begin".equals(measureElement.getElementsByTag("beam").text())){
									note.setBeamType(BeamType.BEGIN);	
								}else if("continue".equals(measureElement.getElementsByTag("beam").text())){
									note.setBeamType(BeamType.CONTINUE);	
								}else if("end".equals(measureElement.getElementsByTag("beam").text())){
									note.setBeamType(BeamType.END);	
								}
							}
							if(measureElement.getElementsByTag("beam").size() == 2){
								Element firsBeam = measureElement.getElementsByTag("beam").get(0);
								Element secondBeam = measureElement.getElementsByTag("beam").get(1);
								if("begin".equals(firsBeam.text()) && "begin".equals(secondBeam.text())){
									note.setBeamType(BeamType.BEGIN_BEGIN);	
								}else if("continue".equals(firsBeam.text()) && "continue".equals(secondBeam.text())){
									note.setBeamType(BeamType.CONTINUE_CONTINUE);	
								}else if("end".equals(firsBeam.text()) && "end".equals(secondBeam.text())){
									note.setBeamType(BeamType.END_END);	
								}
								else if("continue".equals(firsBeam.text()) && "begin".equals(secondBeam.text())){
									note.setBeamType(BeamType.CONTINUE_BEGIN);	
								}
								else if("continue".equals(secondBeam.text()) && "end".equals(secondBeam.text())){
									note.setBeamType(BeamType.CONTINUE_END);	
								}
							}
						}

						if (!measureElement.getElementsByTag("notations").isEmpty()) {
							Element articulations = measureElement.getElementsByTag("articulations").first();
							if (articulations != null) {
								String articulation = articulations.tagName();
								note.setArticulation(Articulation.getArticulation(articulation));
							}
						}
						
						notes.add(note);

					} else if (measureElement.tagName().equals("forward")) {
						position = position + Integer.valueOf(measureElement.getElementsByTag("duration").text());
								//* divMultiplier.get(divisions);

					} else if (measureElement.tagName().equals("backup")) {
						// System.out.println("BACKUP" +
						// Integer.valueOf(measureElement.getElementsByTag("duration").text())
						// * divMultiplier.get(divisions));
						position = position - Integer.valueOf(measureElement.getElementsByTag("duration").text());// *
																											// divMultiplier.get(divisions);
					}
				}
			}
		}
	}
	
	private void mapNotesPerInstrument(){
		notesPerInstrument =  notes.stream().collect(groupingBy(Note::getInstrument, TreeMap::new, Collectors.toList()));
	}

	private int getPitchFromStep(String step) {
		int pitch = 0;
		switch (step) {
		case "C":
			pitch = 0;
			break;
		case "D":
			pitch = 2;
			break;
		case "E":
			pitch = 4;
			break;
		case "F":
			pitch = 5;
			break;
		case "G":
			pitch = 7;
			break;
		case "A":
			pitch = 9;
			break;
		case "B":
			pitch = 11;
			break;
		default:
			break;
		}
		return pitch;
	}

}
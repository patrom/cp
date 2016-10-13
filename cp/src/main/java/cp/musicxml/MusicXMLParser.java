package cp.musicxml;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.model.rhythm.DurationConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
	
	 public static void main (String[]args)throws IOException {
         MusicXMLParser parser = new MusicXMLParser("src/main/java/resources/cello3.xml");
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
			for (Element thismeasure : part.getElementsByTag("measure")) {
				String measure = "0";
				if (!thismeasure.getElementsByTag("divisions").isEmpty()) {
					divisions = Integer.valueOf(thismeasure.getElementsByTag("divisions").text());
				}
				measure = thismeasure.attr("number");
				for (Element thisnote : thismeasure.children()) {
					if (thisnote.tagName().equals("note")) {
						Note note = new Note();
						if (!thisnote.getElementsByTag("voice").isEmpty()) {
							currentVoice = Integer.valueOf(thisnote.getElementsByTag("voice").text());
							note.setVoice(currentVoice);
						}
						// get the pitch
						if (!thisnote.getElementsByTag("pitch").isEmpty()) {
							for (Element thispitch : thisnote.getElementsByTag("pitch")) {
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
						if(thisnote.getElementsByTag("time-modification").isEmpty()){
							switch (thisnote.getElementsByTag("type").text()) {
							case "16th":
								duration = DurationConstants.SIXTEENTH;
								if(!thisnote.getElementsByTag("dot").isEmpty()){
									//TODO
								}
								break;
							case "eighth":
								duration = DurationConstants.EIGHT;
								if(!thisnote.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.EIGHT + DurationConstants.SIXTEENTH;
								}
								break;
							case "quarter":
								duration = DurationConstants.QUARTER;
								if(!thisnote.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.QUARTER + DurationConstants.EIGHT;
								}
								break;
							case "half":
								duration = DurationConstants.HALF;
								if(!thisnote.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.HALF + DurationConstants.QUARTER;
								}
								break;
							case "whole":
								duration = DurationConstants.WHOLE;
								if(!thisnote.getElementsByTag("dot").isEmpty()){
									duration = DurationConstants.WHOLE + DurationConstants.HALF;
								}
								break;
							default:
								break;
							}
						}else{
							if(thisnote.getElementsByTag("actual-notes").text().equals("3")){
								note.setTriplet(true);
								note.setTimeModification(thisnote.getElementsByTag("normal-type").text());
								switch (thisnote.getElementsByTag("type").text()) {
								case "16th":
									duration = DurationConstants.SIXTEENTH_TRIPLET;
									break;
								case "eighth":
									duration = DurationConstants.EIGHT_TRIPLET;
									if(!thisnote.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.EIGHT_TRIPLET + DurationConstants.SIXTEENTH_TRIPLET;
									}
									break;
								case "quarter":
									duration = DurationConstants.QUARTER_TRIPLET;
									if(!thisnote.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.QUARTER_TRIPLET + DurationConstants.EIGHT_TRIPLET;
									}
									break;
								case "half":
									duration = DurationConstants.HALF_TRIPLET;
									if(!thisnote.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.HALF_TRIPLET + DurationConstants.QUARTER_TRIPLET;
									}
									break;
								default:
									break;
								}
							}else if(thisnote.getElementsByTag("actual-notes").text().equals("6")){
								note.setSextuplet(true);
								note.setTimeModification(thisnote.getElementsByTag("normal-type").text());
								switch (thisnote.getElementsByTag("type").text()) {
								case "16th":
									duration = DurationConstants.SIXTEENTH_TRIPLET;
									break;
								case "eighth":
									duration = DurationConstants.EIGHT_TRIPLET;
									if(!thisnote.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.EIGHT_TRIPLET + DurationConstants.SIXTEENTH_TRIPLET;
									}
									break;
								case "quarter":
									duration = DurationConstants.QUARTER_TRIPLET;
									if(!thisnote.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.QUARTER_TRIPLET + DurationConstants.EIGHT_TRIPLET;
									}
									break;
								case "half":
									duration = DurationConstants.HALF_TRIPLET;
									if(!thisnote.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.HALF_TRIPLET + DurationConstants.QUARTER_TRIPLET;
									}
									break;
								default:
									break;
								}
							}else if(thisnote.getElementsByTag("actual-notes").text().equals("5")){
								note.setQuintuplet(true);
								note.setTimeModification(thisnote.getElementsByTag("normal-type").text());
								switch (thisnote.getElementsByTag("type").text()) {
								case "16th":
									duration = DurationConstants.SIXTEENTH_QUINTUPLET;
									break;
								case "eighth":
									duration = DurationConstants.EIGHT_QUINTUPLET;
									if(!thisnote.getElementsByTag("dot").isEmpty()){
										duration = DurationConstants.EIGHT_QUINTUPLET + DurationConstants.SIXTEENTH_QUINTUPLET;
									}
									break;
								default:
									break;
								}
							}
						}
						
//						duration = Integer.valueOf(thisnote.getElementsByTag("duration").text()); // *
//																									// divMultiplier.get(divisions);			
//						duration =  duration * Note.DEFAULT_LENGTH / MusicXMLWriter.DIVISIONS;
						
						note.setLength(duration);
						note.setDisplayLength(duration);
						
		
						// now check if it is a chord
						if (!thisnote.getElementsByTag("chord").isEmpty()) {
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
						note.setInstrument(thisnote.getElementsByTag("instrument").attr("id"));

						if(!thisnote.getElementsByTag("tied").isEmpty()){
							if(thisnote.getElementsByTag("tied").attr("type").equals("start")){
								note.setTieStart(true);
							}
							if(thisnote.getElementsByTag("tied").attr("type").equals("stop")){
								note.setTieEnd(true);
							}
						}
						
						if(!thisnote.getElementsByTag("tuplet").isEmpty()){
							Element tuplet = thisnote.getElementsByTag("tuplet").first();
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
						
						if(!thisnote.getElementsByTag("beam").isEmpty()){
							if(thisnote.getElementsByTag("beam").size() == 1) {
								if( "begin".equals(thisnote.getElementsByTag("beam").text())){
									note.setBeamType(BeamType.BEGIN);	
								}else if("continue".equals(thisnote.getElementsByTag("beam").text())){
									note.setBeamType(BeamType.CONTINUE);	
								}else if("end".equals(thisnote.getElementsByTag("beam").text())){
									note.setBeamType(BeamType.END);	
								}
							}
							if(thisnote.getElementsByTag("beam").size() == 2){
								Element firsBeam = thisnote.getElementsByTag("beam").get(0);
								Element secondBeam = thisnote.getElementsByTag("beam").get(1);
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
						
						
						notes.add(note);

					} else if (thisnote.tagName().equals("forward")) {
						position = position + Integer.valueOf(thisnote.getElementsByTag("duration").text());
								//* divMultiplier.get(divisions);

					} else if (thisnote.tagName().equals("backup")) {
						// System.out.println("BACKUP" +
						// Integer.valueOf(thisnote.getElementsByTag("duration").text())
						// * divMultiplier.get(divisions));
						position = position - Integer.valueOf(thisnote.getElementsByTag("duration").text());// *
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
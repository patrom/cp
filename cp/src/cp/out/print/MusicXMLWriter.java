package cp.out.print;

import static cp.model.note.NoteBuilder.note;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import cp.out.print.note.Key;
import cp.out.print.note.NoteDisplay;

@Component
public class MusicXMLWriter {

	private static final int DIVISIONS = 256;
	private Integer[] nonTieLengths = {48,24,12,9,8,6,4,3};
	
	XMLStreamWriter xmlStreamWriter;
	
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private NoteDisplay noteDisplay;
	
//	public void generateMusicXML(List<MelodyInstrument> melodies, String id) throws Exception {
//		Map<Instrument, List<Note>> melodiesForInstrument = new HashMap<>();
//		for (MelodyInstrument melody : melodies) {
//			List<Note> notes = melody.getNotes();
//			addLeadingRest(notes);
//			Instrument instrument = getInstrumentForVoice(melody.getVoice());
//			melodiesForInstrument.compute(instrument, (k, v) -> {
//					if (v == null) {
//						List<Note> list = new ArrayList<>();
//						list.addAll(notes);
//						return list;
//					}else {
//						v.addAll(notes);
//						return v;
//					}
//				}
//			);
//		}
//		createXML(id, melodiesForInstrument);
//	}

	protected void addLeadingRest(List<Note> notes) {
		if (!notes.isEmpty()) {
			Note firsNote = notes.get(0);
			if (firsNote.getPosition() != 0){
				Note rest = note().pitch(Note.REST).pos(0).len(firsNote.getPosition()).voice(firsNote.getVoice()).build();
				notes.add(0, rest);
			}
		}
	}

	public void generateMusicXMLForMelodies(List<MelodyBlock> melodies, String id) throws Exception {
		Map<Instrument, List<Note>> melodiesForInstrument = getMelodyNotesForInstrument(melodies);
		createXML(id, melodiesForInstrument);
	}
	
//	public void generateMusicXMLForNotes(List<Note> notes, Instrument instrument, String id) throws Exception {
//		List<Instrument> instruments = new ArrayList<Instrument>();
//		instruments.add(instrument);
//		musicProperties.setInstruments(instruments);
//		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
//		generateMusicXML(Collections.singletonList(melodyInstrument), id);
//	}

	public void createXML(String id, Map<Instrument, List<Note>> melodiesForInstrument) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException {
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream("resources/xml/" + id + ".xml"), "UTF-8");

		// Generate the XML
		// Write the default XML declaration
		xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
		xmlStreamWriter.writeCharacters("\n");
		xmlStreamWriter.writeDTD("<!DOCTYPE score-partwise PUBLIC '-//Recordare//DTD MusicXML 3.0 Partwise//EN' 'http://www.musicxml.org/dtds/partwise.dtd'>");
		xmlStreamWriter.writeCharacters("\n");

		// Write the root element
		xmlStreamWriter.writeStartElement("score-partwise");
		xmlStreamWriter.writeAttribute("version", "3.0");
		xmlStreamWriter.writeCharacters("\n");

		createElement("work");
		createElement("identification");
		createElement("defaults");

		// part-list element
		xmlStreamWriter.writeStartElement("part-list");
		xmlStreamWriter.writeCharacters("\n");
		
		Set<Instrument> distinctInstruments = melodiesForInstrument.keySet();
		for (Instrument instrument : distinctInstruments) {
			createScorePartElement(instrument);
		}
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
		
		//part element
		for (Entry<Instrument, List<Note>> entries : melodiesForInstrument.entrySet()) {
			createPartElement(entries.getValue(), entries.getKey());
		}
		
		// End the root element
		xmlStreamWriter.writeEndElement();

		// End the XML document
		xmlStreamWriter.writeEndDocument();

		// Close the XMLStreamWriter to free up resources
		xmlStreamWriter.close();
	}
	
	private List<Note> addRests(List<Note> notes){
		List<Note> notesRestsIncluded = new ArrayList<Note>();
		int size = notes.size() - 1;
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			notesRestsIncluded.add(note);
			Note nextNote = notes.get(i + 1);
			int endNote = note.getPosition() + note.getLength();
			if (endNote < nextNote.getPosition()) {
				int length = nextNote.getPosition() - endNote;
				Note rest = note().pitch(Note.REST).pos(endNote).len(length).voice(note.getVoice()).build();
				notesRestsIncluded.add(rest);
			}
		}
		return notesRestsIncluded;
	}

	private Map<Instrument, List<Note>> getMelodyNotesForInstrument(
			List<MelodyBlock> melodies) {
		Map<Instrument, List<Note>> melodiesForInstrument = new TreeMap<>();
		for (MelodyBlock melody : melodies) {
			List<Note> notes = melody.getMelodyBlockNotesWithRests();
			addLeadingRest(notes);
			melodiesForInstrument.compute(melody.getInstrument(), (k, v) -> {
					if (v == null) {
						List<Note> list = new ArrayList<>();
						list.addAll(notes);
						return list;
					}else {
						v.addAll(notes);
						return v;
					}
				}
			);
		}
		return melodiesForInstrument;
	}
	
	private void createBackupElement(int duration) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("backup");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("duration", String.valueOf(duration));
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

//	private Instrument getInstrumentForVoice(int voice){
//		List<Instrument> instruments = musicProperties.getInstruments();
//		Optional<Instrument> instrument = instruments.stream().filter(instr -> (instr.getVoice()) == voice).findFirst();
//		if (instrument.isPresent()) {
//			return instrument.get();
//		}else{
//			throw new IllegalArgumentException("Instrument for voice " + voice + " is missing!");
//		}
//	}

	private void createPartElement(List<Note> list, Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("part");
		xmlStreamWriter.writeAttribute("id", "P" + instrument.getVoice());
		xmlStreamWriter.writeCharacters("\n");
//		addLeadingRest(list);
		createMeasureElements(list, instrument);
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

//	private void createMeasureElements(List<List<Note>> notesLists, Instrument instrument) throws XMLStreamException {
//		// only 1 melody
//		if (notesLists.size() == 1) {
////			List<Note> tiedNotes = updateTies(notesLists.get(0), musicProperties.getMelodyBeatValue());
//			List<Measure> measures = getMeasures(notesLists.get(0), musicProperties.getMelodyBeatValue());
//			for (int i = 0; i < measures.size(); i++) {
//				Measure measure = measures.get(i);
//				xmlStreamWriter.writeComment("====== Part: " + instrument.getVoice() + " , Measure: " + i + " =======");
//				xmlStreamWriter.writeCharacters("\n");
//				xmlStreamWriter.writeStartElement("measure");
//				xmlStreamWriter.writeAttribute("number", String.valueOf(i));
//				xmlStreamWriter.writeCharacters("\n");
//				if (i == 0) {
//					createAttributes(instrument);
//					createDirectionElement(instrument);
//				}
//				List<Note> notes = addTies(measure.getNotes());
//				updateTripletNotes(notes);
//				updateBeat(instrument, notes);
//				xmlStreamWriter.writeEndElement();
//				xmlStreamWriter.writeCharacters("\n");
//			}
//		} else {
//			//multi voice
//			List<Measure> multiVoiceMeasures = new ArrayList<>();
//			for (List<Note> melodyNotes : notesLists) {
////				List<Note> tiedNotes = updateTies(melodyNotes, 12);
//				List<Measure> measures = getMeasures(melodyNotes, 12);
//				multiVoiceMeasures.addAll(measures);
//			}
//			Map<Integer, List<Measure>> multiVoiceMeasureStartPositions = multiVoiceMeasures.stream()
//					.collect(Collectors.groupingBy(Measure::getStart, TreeMap::new, Collectors.toList()));
//			int start = 0;
//			for (Entry<Integer, List<Measure>> startPositions : multiVoiceMeasureStartPositions.entrySet()) {
//				xmlStreamWriter.writeComment("====== Part: " + instrument.getVoice() + " , Measure: " + start + " =======");
//				xmlStreamWriter.writeCharacters("\n");
//				xmlStreamWriter.writeStartElement("measure");
//				xmlStreamWriter.writeAttribute("number", String.valueOf(start));
//				xmlStreamWriter.writeCharacters("\n");
//				if (start==0) {
//					createAttributes(instrument);
//					createDirectionElement(null);
//				}
//				List<Measure> measures = startPositions.getValue();
//				int m = 1;
//				int split = notesLists.size()/2;
//				int lastMeasure = measures.size();
//				boolean isChordNote = false;
//				for (Measure measure : measures) {
//					List<Note> notes = addTies(measure.getNotes());
//					updateTripletNotes(notes);
//					int position = -1;
//					for (Note note : notes) {
//						if (position == note.getPosition()) {
//							isChordNote = true;
//						} else {
//							isChordNote = false;
//						}
//						position = note.getPosition();
//						if (m <= split) {
//							createNoteElement(note, instrument, isChordNote, m, 1);
//						}else{
//							createNoteElement(note, instrument, isChordNote, m, 2);
//						}
//					}
//					if (m != lastMeasure) {
//						int duration = getDuration(notes);
//						createBackupElement(duration);
//					}
//					m++;
//				}
//				xmlStreamWriter.writeEndElement();
//				xmlStreamWriter.writeCharacters("\n");
//				start++;
//			}
//		}
//	}

//	private int getDuration(List<Note> notes) {
//		int duration = notes.stream().mapToInt(note -> note.getLength()).sum();
//		return (duration * DIVISIONS) / 12;
//	}

	private int getStaff(Instrument instrument, Note note) {
		if (instrument instanceof Piano) {
			if (note.getPitch() < 60) {
				return 2;
			}
		}
		return 1;
	}

	private void createDirectionElement(Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("direction");
		xmlStreamWriter.writeCharacters("\n");
		
		createDirectionTypeElement();
//		createElementWithValue(xmlStreamWriter, "voice", String.valueOf(instrument.getVoice()));
//		createElementWithValue(xmlStreamWriter, "staff", String.valueOf(instrument.getVoice());
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createDirectionTypeElement() throws XMLStreamException {
		xmlStreamWriter.writeStartElement("direction-type");
		xmlStreamWriter.writeCharacters("\n");
		
		createMetronomeElement();

		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createMetronomeElement() throws XMLStreamException {
		xmlStreamWriter.writeStartElement("metronome");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("beat-unit", "quarter");
		createElementWithValue("per-minute", String.valueOf(musicProperties.getTempo()));
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	protected List<Measure> getMeasures(List<Note> notes, int beat) {
		Note lastNote = notes.get(notes.size() - 1);
		int totalLength = lastNote.getPosition() + lastNote.getDisplayLength();
		int measureSize = musicProperties.getNumerator() * beat;
		int totalMeasures = (totalLength + measureSize - 1) / measureSize;
		List<Measure> measures = new ArrayList<>();
		for (int i = 0; i < totalMeasures; i++) {
			Measure measure = new Measure();
			measure.setStart(i * measureSize);
			measure.setEnd((i + 1) * measureSize);
			measures.add(measure);
		}
		int measureIndex = 0;
		Measure measure = measures.get(measureIndex);
		for (Note note : notes) {
			if (note.getPosition() >= measure.getEnd()) {
				measure = measures.get(++measureIndex);
			}
			int length = note.getPosition() + note.getDisplayLength();
			if (length > measure.getEnd()) {
				//split
				Note firstNote = note.clone();
				firstNote.setLength(measure.getEnd() - note.getPosition());
				firstNote.setTieStart(true);
				measure.addNote(firstNote);
				Note secondNote = note.clone();
				secondNote.setPosition(measure.getEnd());
				secondNote.setLength(length - measure.getEnd());
				secondNote.setTieEnd(true);
				//add split note to next measure
				measure = measures.get(++measureIndex);
				measure.addNote(secondNote);
			} else {
				measure.addNote(note);
			}
		}
		return measures;
	}

	private void createNoteElement(Note note, Instrument instrument, boolean isChordNote, int voice, int staff) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("note");
		xmlStreamWriter.writeCharacters("\n");
		if (isChordNote) {
			xmlStreamWriter.writeEmptyElement("chord");
			xmlStreamWriter.writeCharacters("\n");
		}
		if (note.isRest()) {
			xmlStreamWriter.writeEmptyElement("rest");
		} else {
			createPitchElement(note);
		}
		int length =  note.getDisplayLength() * DIVISIONS / Note.DEFAULT_LENGTH;
		createElementWithValue("duration", String.valueOf(length));
		createElementWithAttributeValue("instrument", "id", "P" + instrument.getVoice() + "-I" + instrument.getVoice());
		if (voice > 0) {
			createElementWithValue("voice", String.valueOf(voice));
		}
		NoteType noteType = NoteType.getNoteType(length);
		createElementWithValue("type", noteType.getName());
		if (noteType.isDot() || (noteType.equals(NoteType.eighth) && note.isSextuplet())) {
			xmlStreamWriter.writeEmptyElement("dot");
			xmlStreamWriter.writeCharacters("\n");
		}
		if (note.isTriplet() || note.isSextuplet() || note.isQuintuplet()) {
			createTimeModification(note);
		}
		createElementWithValue("staff", String.valueOf(staff));
		if (note.hasBeamType()) {
			if (note.hasDoubleBeaming()) {
				createElementBeamType(note.getBeamType().getLabel(), "1");
				createElementBeamType(note.getBeamType().getSecondLabel(), "2");
			}else{
				createElementBeamType(note.getBeamType().getLabel(), "1");
			}
		}
		if (note.hasArticulation()|| note.isTieStart() || note.isTieEnd() || note.isTriplet() || note.isSextuplet()) {
			createNotationsElement(note);
		}
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}
	
	private void createElementBeamType(String label, String number) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("beam");
		xmlStreamWriter.writeAttribute("number", number);
		xmlStreamWriter.writeCharacters(label);
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createNotationsElement(Note note) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("notations");
		xmlStreamWriter.writeCharacters("\n");
		if (note.isTieStart()) {
			createElementWithAttributeValue("tied", "type", "start");
		}else if (note.isTieEnd()) {
			createStopElement();
		}
		if (note.hasArticulation()) {
			createArticulationElement(note);
		}
		if (note.hasTupletType()) {
			if (note.getTupletType().equals(TupletType.START)) {
				createTripletElementWithAttributeValues("start", note.getDisplayLength());
			}else if (note.getTupletType().equals(TupletType.STOP)) {
				createTripletElementWithAttributeValues("stop", note.getDisplayLength());
			}
		}
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createArticulationElement(Note note) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("articulations");
		xmlStreamWriter.writeCharacters("\n");
		
		xmlStreamWriter.writeEmptyElement(note.getArticulation().getMusicXmlLabel());
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
		
	}

	private void createTimeModification(Note note) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("time-modification");
		xmlStreamWriter.writeCharacters("\n");
		
		if (note.isSextuplet()) {
			createElementWithValue("actual-notes", "6");
			createElementWithValue("normal-notes", "4");
			createElementWithValue("normal-type", "16th");
		} else if(note.isTriplet()){
			createElementWithValue("actual-notes", "3");
			createElementWithValue("normal-notes", "2");
			createElementWithValue("normal-type", "eighth");
		} else if(note.isQuintuplet()){
			createElementWithValue("actual-notes", "5");
			createElementWithValue("normal-notes", "4");
			createElementWithValue("normal-type", "16th");
		}
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createStopElement() throws XMLStreamException {
		xmlStreamWriter.writeStartElement("slur");
		xmlStreamWriter.writeAttribute("color", "#000000");
		xmlStreamWriter.writeAttribute("type", "stop");
		xmlStreamWriter.writeAttribute("orientation", "over");
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithAttributeValue("tied", "type", "stop");
	}

	private void createPitchElement(Note note) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("pitch");
		xmlStreamWriter.writeCharacters("\n");
		
		Key noteStep = noteDisplay.getNoteStep(note.getPitchClass());
		createElementWithValue("step", noteStep.getStep());
		if (StringUtils.isNotEmpty(noteStep.getAlter())) {
			createElementWithValue("alter", noteStep.getAlter());
		}
		createElementWithValue("octave", String.valueOf(note.getOctave() - 1));
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createAttributes(Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("attributes");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("divisions", String.valueOf(DIVISIONS));
		createKeyElement(xmlStreamWriter);
		createTimeElement();
		if (instrument instanceof Piano) {
			createElementWithValue("staves", String.valueOf(2));
			createPianoClefElement();
		} else{
			createElementWithValue("staves", String.valueOf(1));
			createClefElement(instrument);
		}
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}
	
	private void createPianoClefElement() throws XMLStreamException {
		xmlStreamWriter.writeStartElement("clef");
		xmlStreamWriter.writeAttribute("number", "1");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("sign", "G");
		createElementWithValue("line", "2");

		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
		
		xmlStreamWriter.writeStartElement("clef");
		xmlStreamWriter.writeAttribute("number", "2");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("sign", "F");
		createElementWithValue("line", "4");

		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createClefElement(Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("clef");
		
		if (instrument instanceof Piano) {
			xmlStreamWriter.writeAttribute("number", "1");
			xmlStreamWriter.writeCharacters("\n");
			
			createElementWithValue("sign", "G");
			createElementWithValue("line", "2");

			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n");
			
			xmlStreamWriter.writeStartElement("clef");
			xmlStreamWriter.writeAttribute("number", "2");
			xmlStreamWriter.writeCharacters("\n");
			
			createElementWithValue("sign", "F");
			createElementWithValue("line", "4");
		}else{
			xmlStreamWriter.writeCharacters("\n");
			if (instrument.getClef().equals("F")) {
				createElementWithValue("sign", "F");
				createElementWithValue("line", "4");
			} if (instrument.getClef().equals("C")) {
				createElementWithValue("sign", "C");
				createElementWithValue("line", "3");
			} else {
				createElementWithValue("sign", "G");
				createElementWithValue("line", "2");
			}
		}
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createTimeElement() throws XMLStreamException {
		xmlStreamWriter.writeStartElement("time");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("beats", String.valueOf(musicProperties.getNumerator()));
		createElementWithValue("beat-type", String.valueOf(musicProperties.getDenominator()));
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createKeyElement(XMLStreamWriter xmlStreamWriter) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("key");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("fifths", String.valueOf(musicProperties.getKeySignature()));
		createElementWithValue("mode", "major");
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createScorePartElement(Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("score-part");
		xmlStreamWriter.writeAttribute("id", "P" + instrument.getVoice());
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("part-name", instrument.getInstrumentName());
		createInstrumentScoreElement(instrument);
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createInstrumentScoreElement(Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("score-instrument");
		xmlStreamWriter.writeAttribute("id", "P" + instrument.getVoice() + "-I" + instrument.getVoice());
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("instrument-name", instrument.getInstrumentName());
		createElementWithValue("instrument-sound", instrument.getInstrumentSound());
		
		xmlStreamWriter.writeEmptyElement("solo");
		createVirtualInstrumentElement(instrument);
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
		
	}

	private void createVirtualInstrumentElement(Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("virtual-instrument");
		xmlStreamWriter.writeCharacters("\n");
		createElementWithValue("virtual-library", instrument.getVirtualLibrary());
		createElementWithValue("virtual-name", instrument.getVirtualName());
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
		
	}

	private void createElement(String name) throws XMLStreamException {
		xmlStreamWriter.writeStartElement(name);
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}
	
	private void createElementWithValue(String name, String value) throws XMLStreamException {
		xmlStreamWriter.writeStartElement(name);
		xmlStreamWriter.writeCharacters(value);
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}
	
	private void createElementWithAttributeValue(String elementName, String attributeName, String attributeValue) throws XMLStreamException {
		xmlStreamWriter.writeStartElement(elementName);
		xmlStreamWriter.writeAttribute(attributeName, attributeValue);
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}
	
	private void createTripletElementWithAttributeValues(String type, int length) throws XMLStreamException {
//		<tuplet type="start" bracket="no" number="1" default-y="-20" placement="below" />
		xmlStreamWriter.writeStartElement("tuplet");
		xmlStreamWriter.writeAttribute("type", type);
		if (length <= Note.DEFAULT_LENGTH) {
			xmlStreamWriter.writeAttribute("bracket", "no");
		}else{
			xmlStreamWriter.writeAttribute("bracket", "yes");
		}
		xmlStreamWriter.writeAttribute("number", "1");
		xmlStreamWriter.writeAttribute("default-y", "-20");
		xmlStreamWriter.writeAttribute("placement", "below");
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}
	
	protected int findNoteTypeLength(int length){
		Optional<Integer> optionalLength = Stream.of(nonTieLengths).filter(i -> i <= length).findFirst();
		if (optionalLength.isPresent()) {
			return optionalLength.get();
		} 
		return length;
	}
	
//	protected List<Note> addTies(List<Note> notes){
//		List<Note> tiedNotes = new ArrayList<Note>();
//		for (Note note : notes) {
//			int length = findNoteTypeLength(note.getDisplayLength());
//			if (length == note.getDisplayLength()) {
//				tiedNotes.add(note);
//			} else {
//				int rest = note.getDisplayLength() - length;
//				note.setLength(length);
//				note.setTieStart(true);
//				tiedNotes.add(note);
//				//tied
//				int restLength = findNoteTypeLength(rest);
//				Note restNote = note.clone();
//				restNote.setPosition(note.getPosition() + length);
//				restNote.setLength(restLength);
//				restNote.setTieEnd(true);
//				tiedNotes.add(restNote);
//			}
//		}
//		return tiedNotes;
//	}
	
//	protected void updateTripletNotes(List<Note> notes){
//		Map<Integer, List<Note>> map = notesForBeat(notes, 12);
//		for (List<Note> melodyNotes : map.values()) {
//			if(hasTriplet(melodyNotes)){
//				melodyNotes.forEach(n -> n.setTriplet(true));
//			}
//			if (hasTriplet(melodyNotes) && hasSextuplet(melodyNotes)) {
//				melodyNotes.forEach(n -> n.setSextuplet(true));
//			}
//		}
//	}
	
	protected void createMeasureElements(List<Note> allNotes, Instrument instrument) throws XMLStreamException{
		int measureSize = getMeasureSize();
		BeatMap notesPerMeasureBeat = new BeatMap();
		notesPerMeasureBeat.createBeatMap(allNotes, measureSize);
		for (int i = 0; i <= notesPerMeasureBeat.size(); i++) {
			xmlStreamWriter.writeComment("====== Part: " + instrument.getVoice() + " , Measure: " + i + " =======");
			xmlStreamWriter.writeCharacters("\n");
			xmlStreamWriter.writeStartElement("measure");
			xmlStreamWriter.writeAttribute("number", String.valueOf(i));
			xmlStreamWriter.writeCharacters("\n");
			if (i == 0) {
				createAttributes(instrument);
				createDirectionElement(instrument);
			}
			notesPerMeasureBeat.createTies();
			
			List<Note> measureNotes = notesPerMeasureBeat.getNoteForBeat(i);
			if (measureNotes != null) {
				updateBeat(instrument, measureNotes);
			}
			
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n");
		}
	}

	private int getMeasureSize() {
		int numerator = musicProperties.getNumerator();
		switch (musicProperties.getDenominator()) {
		case 4:
			return numerator * Note.DEFAULT_LENGTH;
		case 8:
			return numerator * (Note.DEFAULT_LENGTH / 2);
		case 2:
			return numerator * (Note.DEFAULT_LENGTH * 2);
		}
		throw new IllegalStateException("Denominator unknown; " + musicProperties.getDenominator());
	}

	private void updateBeat(Instrument instrument, List<Note> notes)
			throws XMLStreamException {
		int position = -1;
		for (Note note : notes) {
//							if (note.hasDynamic()) {
//								<direction>
//								<direction-type>
//									<dynamics default-x="9" default-y="-118" color="#000000" font-family="Opus Text Std" font-style="normal" font-size="11.9365" font-weight="normal">
//										<p />
//									</dynamics>
//								</direction-type>
//								<voice>1</voice>
//								<staff>1</staff>
//							</direction>
//							}
			int staff = getStaff(instrument, note);
			if (position == note.getPosition()) {
				createNoteElement(note, instrument, true, -1 , staff);
			} else {
				createNoteElement(note, instrument, false, -1, staff);
			}
			position = note.getPosition();
		}
	}

}

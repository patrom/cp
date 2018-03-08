package cp.out.print;

import cp.composition.voice.Voice;
import cp.config.InstrumentConfig;
import cp.generator.MusicProperties;
import cp.model.melody.MelodyBlock;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.model.texture.Texture;
import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;
import cp.out.instrument.keyboard.Piano;
import cp.out.play.InstrumentMapping;
import cp.out.print.note.Key;
import cp.out.print.note.NoteDisplay;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;

@Component
public class MusicXMLWriter {

	private static final int DIVISIONS = 256;
	private final Integer[] nonTieLengths = {48,24,12,9,8,6,4,3};

	private XMLStreamWriter xmlStreamWriter;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private NoteDisplay noteDisplay;
	@Autowired
	private InstrumentConfig instrumentConfig;
	@Autowired
	private Texture texture;

	public void generateMusicXMLForMelodies(List<MelodyBlock> melodies, OutputStream outputStream) throws Exception {
		Map<InstrumentMapping, List<Note>> melodiesForInstrument = getMelodyNotesForInstrument(melodies);
		createXML(outputStream, melodiesForInstrument);
	}


	public void createXML(OutputStream outputStream , Map<InstrumentMapping, List<Note>> melodiesForInstrument) throws FactoryConfigurationError, XMLStreamException {
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(outputStream, "UTF-8");

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
		
		Set<InstrumentMapping> distinctInstruments = melodiesForInstrument.keySet();
		for (InstrumentMapping instrumentMapping : distinctInstruments) {
			createScorePartElement(instrumentMapping);
		}
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
		
		//part element
		for (Entry<InstrumentMapping, List<Note>> entries : melodiesForInstrument.entrySet()) {
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
		List<Note> notesRestsIncluded = new ArrayList<>();
		int size = notes.size() - 1;
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			notesRestsIncluded.add(note);
			Note nextNote = notes.get(i + 1);
			int endNote = note.getPosition() + note.getLength();
			if (endNote < nextNote.getPosition()) {
				int length = nextNote.getPosition() - endNote;
				Note rest = note().rest().pos(endNote).len(length).voice(note.getVoice()).build();
				notesRestsIncluded.add(rest);
			}
		}
		return notesRestsIncluded;
	}

	private Map<InstrumentMapping, List<Note>> getMelodyNotesForInstrument(
			List<MelodyBlock> melodies) {
		Map<InstrumentMapping, List<Note>> melodiesForInstrument = new TreeMap<>();
		for (MelodyBlock melody : melodies) {
			List<Note> notes = melody.getMelodyBlockNotesWithRests();
			addLeadingRest(notes);
			InstrumentMapping instrumentMapping = instrumentConfig.getInstrumentMappingForVoice(melody.getVoice());
			melodiesForInstrument.compute(instrumentMapping, (k, v) -> {
					if (v == null) {
						return new ArrayList<>(notes);
					}else {
						v.addAll(notes);
						return v;
					}
				}
			);
		}
		return melodiesForInstrument;
	}
	
	protected void addLeadingRest(List<Note> notes) {
		if (!notes.isEmpty()) {
			Note firsNote = notes.get(0);
			if (firsNote.getPosition() != 0){
				Note rest = note().rest().pos(0).len(firsNote.getPosition()).voice(firsNote.getVoice()).build();
				notes.add(0, rest);
			}
		}
	}
	
	private void createBackupElement(int duration) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("backup");
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("duration", String.valueOf(duration));
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createPartElement(List<Note> notes, InstrumentMapping instrumentMapping) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("part");
		xmlStreamWriter.writeAttribute("id", "P" + instrumentMapping.getScoreOrder());
		xmlStreamWriter.writeCharacters("\n");
//		addLeadingRest(list);
		createMeasureElements(notes, instrumentMapping);
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

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

	private void createNoteDirectionElement(Note note, Instrument instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("direction");
		xmlStreamWriter.writeCharacters("\n");

		createNoteDirectionTypeElement(note);
        createElementWithValue("voice", String.valueOf(note.getVoice()));
//        createElementWithValue("staff", String.valueOf(getStaff(instrument, note)));
        createElementWithValue("staff", "1");

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

	private void createNoteDirectionTypeElement(Note note) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("direction-type");
		xmlStreamWriter.writeCharacters("\n");

        if (note.isPrintDynamic()) {
            createDynamicElement(note.getDynamic());
        }
        if (note.isPrintTechnical()) {
            createWordsElement(note.getTechnical());
        }

        xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createWordsElement(Technical technical) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("words");
		xmlStreamWriter.writeAttribute("justify", "left");
		xmlStreamWriter.writeAttribute("valign", "middle");
		xmlStreamWriter.writeAttribute("font-family", "Plantin MT Std");
		xmlStreamWriter.writeAttribute("font-style", "normal");
		xmlStreamWriter.writeAttribute("font-size", "11.9365");
		xmlStreamWriter.writeAttribute("font-weight", "normal");

		xmlStreamWriter.writeCharacters(technical.getTechnicalLabel());

		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

    private void createDynamicElement(Dynamic dynamic) throws XMLStreamException {
        xmlStreamWriter.writeStartElement("dynamics");
        xmlStreamWriter.writeAttribute("color", "#000000");
        xmlStreamWriter.writeAttribute("font-family", "Opus Text Std");
        xmlStreamWriter.writeAttribute("font-style", "normal");
        xmlStreamWriter.writeAttribute("font-size", "11.9365");
        xmlStreamWriter.writeAttribute("font-weight", "normal");
        xmlStreamWriter.writeCharacters("\n");

        xmlStreamWriter.writeEmptyElement(dynamic.name().toLowerCase());

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

	private void createNoteElement(Note note, InstrumentMapping instrumentMapping, boolean isChordNote) throws XMLStreamException {
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
		int length =  note.getDisplayLength() * DIVISIONS / Voice.DEFAULT_LENGTH;
		createElementWithValue("duration", String.valueOf(length));
		createElementWithAttributeValue("instrument", "id", "P" + instrumentMapping.getScoreOrder() + "-I" + instrumentMapping.getScoreOrder());
		if (note.getVoice() > -1) {
			createElementWithValue("voice", String.valueOf(note.getVoice()));
		}
		NoteType noteType = NoteType.getNoteType(length);
		createElementWithValue("type", noteType.getName());
		if (noteType.isDot() || (noteType.equals(NoteType.eighth) && note.isSextuplet())) {
			xmlStreamWriter.writeEmptyElement("dot");
			xmlStreamWriter.writeCharacters("\n");
		}
		if (note.isTriplet() || note.isSextuplet() || note.isQuintuplet() || note.isSepttuplet()) {
			createTimeModification(note, noteType);
		}
		createElementWithValue("staff", String.valueOf(getStaff(instrumentMapping.getInstrument(), note)));
		if (note.hasBeamType()) {
			if (note.hasDoubleBeaming()) {
				createElementBeamType(note.getBeamType().getLabel(), "1");
				createElementBeamType(note.getBeamType().getSecondLabel(), "2");
			}else{
				createElementBeamType(note.getBeamType().getLabel(), "1");
			}
		}
		if (note.hasArticulation()|| note.isTieStart() || note.isTieEnd() || note.isTriplet() || note.isSextuplet() || note.isQuintuplet() || note.isSepttuplet()) {
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
				createTripletElementWithAttributeValues("start", note);
			}else if (note.getTupletType().equals(TupletType.STOP)) {
				createTripletElementWithAttributeValues("stop", note);
			}
		}
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createArticulationElement(Note note) throws XMLStreamException {
		if (StringUtils.isNotBlank(note.getArticulation().getMusicXmlLabel())) {
			xmlStreamWriter.writeStartElement("articulations");
			xmlStreamWriter.writeCharacters("\n");

			xmlStreamWriter.writeEmptyElement(note.getArticulation().getMusicXmlLabel());

			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n");
		}

	}

	private void createTimeModification(Note note, NoteType noteType) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("time-modification");
		xmlStreamWriter.writeCharacters("\n");
		if (note.isSextuplet()) {
			createElementWithValue("actual-notes", "6");
			createElementWithValue("normal-notes", "4");
		} else if(note.isTriplet()){
			createElementWithValue("actual-notes", "3");
			createElementWithValue("normal-notes", "2");
		} else if(note.isQuintuplet()){
			createElementWithValue("actual-notes", "5");
			createElementWithValue("normal-notes", "4");
		} else if(note.isSepttuplet()){
			createElementWithValue("actual-notes", "7");
			createElementWithValue("normal-notes", "4");
		}
//		} else if(note.11){
//			createElementWithValue("actual-notes", "11");
//			createElementWithValue("normal-notes", "8");
//		}
		createElementWithValue("normal-type", note.getTimeModification());
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

	private void createScorePartElement(InstrumentMapping instrumentMapping) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("score-part");
		xmlStreamWriter.writeAttribute("id", "P" + instrumentMapping.getScoreOrder());
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("part-name", instrumentMapping.getInstrument().getInstrumentName());
		createInstrumentScoreElement(instrumentMapping);
		
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeCharacters("\n");
	}

	private void createInstrumentScoreElement(InstrumentMapping instrument) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("score-instrument");
		xmlStreamWriter.writeAttribute("id", "P" + instrument.getScoreOrder() + "-I" + instrument.getScoreOrder());
		xmlStreamWriter.writeCharacters("\n");
		
		createElementWithValue("instrument-name", instrument.getInstrument().getInstrumentName());
		createElementWithValue("instrument-sound", instrument.getInstrument().getInstrumentName());
		
		xmlStreamWriter.writeEmptyElement("solo");
		createVirtualInstrumentElement(instrument.getInstrument());
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
	
	private void createTripletElementWithAttributeValues(String type, Note note) throws XMLStreamException {
//		<tuplet type="start" bracket="no" number="1" default-y="-20" placement="below" />
		xmlStreamWriter.writeStartElement("tuplet");
		xmlStreamWriter.writeAttribute("type", type);
		if (note.isBracket()) {
			xmlStreamWriter.writeAttribute("bracket", "yes");
		}else{
			xmlStreamWriter.writeAttribute("bracket", "no");
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
	
	protected void createMeasureElements(List<Note> allNotes, InstrumentMapping instrumentMapping) throws XMLStreamException{
        updatePrintDirectionTypeNote(allNotes);
        int measureSize = getMeasureSize();
		BeatMap notesPerMeasureBeat = new BeatMap();
		notesPerMeasureBeat.createBeatMap(allNotes, measureSize);
		for (int i = 0; i <= notesPerMeasureBeat.size(); i++) {
			xmlStreamWriter.writeComment("====== Part: " + instrumentMapping.getScoreOrder() + " , Measure: " + i + " =======");
			xmlStreamWriter.writeCharacters("\n");
			xmlStreamWriter.writeStartElement("measure");
			xmlStreamWriter.writeAttribute("number", String.valueOf(i));
			xmlStreamWriter.writeCharacters("\n");
			if (i == 0) {
				createAttributes(instrumentMapping.getInstrument());
				createDirectionElement(instrumentMapping.getInstrument());
			}
			notesPerMeasureBeat.createTies();
			
			List<Note> measureNotes = notesPerMeasureBeat.getNoteForBeat(i);
			if (measureNotes != null) {
				updateBeat(instrumentMapping, measureNotes);
			}
			
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeCharacters("\n");
		}
	}

    private void updatePrintDirectionTypeNote(List<Note> allNotes) {
        List<Note> allNotesNoRests = allNotes.stream().filter(n -> !n.isRest()).sorted().collect(Collectors.toList());
        int size = allNotesNoRests.size() -1;
        Note firstNote = allNotesNoRests.get(0);
        firstNote.setPrintDynamic(true);
        firstNote.setPrintTechnical(true);
        for (int i = 0; i < size; i++) {
            Note note = allNotesNoRests.get(i);
            Note nextNote = allNotesNoRests.get(i + 1);
            if(note.getDynamic() != nextNote.getDynamic()){
                nextNote.setPrintDynamic(true);
            }
            if(note.getTechnical() != null && !note.getTechnical().equals(nextNote.getTechnical())){
                nextNote.setPrintTechnical(true);
            }
        }
    }

    private int getMeasureSize() {
		int numerator = musicProperties.getNumerator();
		switch (musicProperties.getDenominator()) {
		case 4:
			return numerator * Voice.DEFAULT_LENGTH;
		case 8:
			return numerator * (Voice.DEFAULT_LENGTH / 2);
		case 2:
			return numerator * (Voice.DEFAULT_LENGTH * 2);
		}
		throw new IllegalStateException("Denominator unknown; " + musicProperties.getDenominator());
	}

	private void updateBeat(InstrumentMapping instrumentMapping, List<Note> notes)
			throws XMLStreamException {
		for (Note note : notes) {
			if (note.isPrintDynamic() || note.isPrintTechnical()) {
				createNoteDirectionElement(note, instrumentMapping.getInstrument());
			}
			createNoteElement(note, instrumentMapping, false);
            //texture
            List<Note> textureNotes = texture.getTextureForNote(note);
            if (!textureNotes.isEmpty()) {
                for (Note textureNote : textureNotes) {
                    createNoteElement(textureNote, instrumentMapping, true);
                }
            }
        }
	}

}

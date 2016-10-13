package cp.out.orchestration;

import cp.DefaultConfig;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.brass.BassTrombone;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.quality.*;
import cp.out.print.MusicXMLWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class OrchestrationGeneratorTest {
	
	@Autowired
	private Brilliant brilliant;
	@Autowired
	private Bright bright;
	@Autowired
	private Pleasant pleasant;
	@Autowired
	private Rich rich;
	@Autowired
	private Golden golden;
	@Autowired
	private Glowing glowing;
	@Autowired
	private Mellow mellow;
	@Autowired
	private Warm warm;
	
	@Autowired
	private OrchestrationGenerator orchestrationGenerator;
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	@Autowired
	private VerticalRelations verticalRelations;

//	private Orchestra orchestra = new WindOrchestra();
	private final Orchestra orchestra = new ClassicalOrchestra();
//	private Orchestra orchestra = new FullOrchestra();
//	private Orchestra orchestra = new Percussion();
	
	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testPerfectCombinations() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int position = 0;
		int noteLength = 48;
		for (int i = 2; i < 8; i++) {
			int[] chord = new int[]{12, 9, 6, 2};
			int[] chordPitchClasses = getChordPitchclasses(i, chord);
			List<OrchestralQuality> orchestralQualities = orchestrationGenerator.getOrchestralQualities();
			for (OrchestralQuality orchestralQuality : orchestralQualities) {
				System.out.println("-----------------");
				System.out.println(orchestralQuality.getQuality());
//				List<Instrument> instruments = orchestralQuality.getBasicInstruments();
				List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.STRINGS);
				
				List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::overlapping2);
				List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length + 1).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::enclosing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength,  orchestratedChords);

				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::crossing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);

//				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::oneOrchestralQuality);
//				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
//				position = updateOrchestra(position, noteLength, orchestratedChords);
			
				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::superpositionSplit2);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength,  orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::superpositionSplit1);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
			}
		}
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testCloseCombinationsWhiteYellow() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{11,6,2};
		closeCombinationsWhiteYellow(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	
	@Test
	public void testCloseCombinationsYellowGreen() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{11,6,2};
		closeCombinationsYellowGreen(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testCloseCombinationsGreenBlue() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{12,9,5};
		closeCombinationsGreenBlue(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testCloseCombinationsBluePurple() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{11,6,2};
		closeCombinationsBluePurple(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testCloseCombinationsYellowOrange() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{14,11,6,2};
		closeCombinationsYellowOrange(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testCloseCombinationsOrangeRed() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{11,6,2};
		closeCombinationsOrangeRed(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testCloseCombinationsRedBrown() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{11,6,2};
		closeCombinationsRedBrown(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testCloseCombinationsBrownPurple() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int[] chord = new int[]{11,6,2};
		closeCombinationsBrownPurple(chord, InstrumentGroup.STRINGS);
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	private int combineCloseCombinations(int[] chord, List<Instrument> orchestralQuality1, List<Instrument> orchestralQuality2, int position) {
		int noteLength = 48;
		for (int i = 2; i < 8; i++) {
			int[] chordPitchClasses = getChordPitchclasses(i, chord);				
//			List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, orchestralQuality1, orchestralQuality2, verticalRelations::overlapping2);
//			List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length + 1).collect(toList());
//			position = updateOrchestra(position, noteLength, orchestratedChords);
			
			List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, orchestralQuality1, orchestralQuality2, verticalRelations::enclosing);
			List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, orchestralQuality1, orchestralQuality2, verticalRelations::crossing);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, orchestralQuality1, orchestralQuality2, verticalRelations::superpositionSplit2);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);
			
//			noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, orchestralQuality1, orchestralQuality2, verticalRelations::superpositionSplit1);
//			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
//			position = updateOrchestra(position, noteLength, orchestratedChords);
		}
		return position;
	}
	
	private int combineCloseCombinations(int[] pitches, List<Instrument> orchestralQuality1, List<Instrument> orchestralQuality2, int position, int noteLength) {
			
//			List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, orchestralQuality1, orchestralQuality2, verticalRelations::overlapping2);
//			List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length + 1).collect(toList());
//			position = updateOrchestra(position, noteLength, orchestratedChords);
			
			List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(pitches, orchestralQuality1, orchestralQuality2, verticalRelations::enclosing);
			List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = orchestrationGenerator.combine(pitches, orchestralQuality1, orchestralQuality2, verticalRelations::crossing);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = orchestrationGenerator.combine(pitches, orchestralQuality1, orchestralQuality2, verticalRelations::superpositionSplit2);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);
			
//			noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, orchestralQuality1, orchestralQuality2, verticalRelations::superpositionSplit1);
//			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
//			position = updateOrchestra(position, noteLength, orchestratedChords);

			return position;
	}

	private int updateOrchestra(int position, int noteLength, 
			List<InstrumentNoteMapping> orchestratedChords) {
		for (InstrumentNoteMapping instrumentNoteMapping : orchestratedChords) {
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				addToOrchestra(position, entry);
			}
			position = position + noteLength;
		}
		return position;
	}

	private void addToOrchestra(int position, Entry<Instrument, List<Note>> entry) {
		System.out.println(entry.getKey().getInstrumentName());
		List<Note> notes = entry.getValue();
		notes.forEach(n -> n.setPosition(position));
		System.out.println(notes);
		orchestra.updateInstrument(entry.getKey(), notes);
	}

	private int[] getChordPitchclasses(int octave, int[] chord) {
		int[] pcs = new int[chord.length];
		for (int i = 0; i < chord.length; i++) {
			pcs[i] = chord[i] + (octave * 12);
		}
		return pcs;
	}

	@Test
	public void testOrchestrate1Instrument() {
		List<Note> notes = orchestrationGenerator.orchestrate1Instrument(new int[]{60,57,54}, new ViolinsI());
		for (Note note: notes) {
			System.out.println(note);
		}
	}
	

	
	@Test
	public void testCombine() {
		List<OrchestralQuality> orchestralQualities = orchestrationGenerator.getOrchestralQualities();
		for (OrchestralQuality orchestralQuality : orchestralQualities) {
//			List<Instrument> instruments = orchestralQuality.getBasicInstruments();
			List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.WOODWINDS);
			System.out.println(orchestralQuality.getQuality());
			orchestrationGenerator.combine(new int[]{67, 64,60}, instruments, instruments, verticalRelations::overlapping2);
		}
	}
	
	public void closeCombinationsWhiteYellow(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> white = brilliant.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> yellow = bright.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches, white, yellow, 0);
	}
	
	public void closeCombinationsYellowGreen(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> yellow = bright.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> green = pleasant.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches, yellow, green, 0);
	}
	
	public void closeCombinationsGreenBlue(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> green = pleasant.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> blue = rich.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches,green, blue, 0);
	}
	
	public void closeCombinationsBluePurple(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> blue = rich.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> purple = mellow.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches, blue, purple, 0);
	}
	
	public void closeCombinationsYellowOrange(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> yellow = bright.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> orange = golden.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches, yellow, orange, 0);
	}
	
	public void closeCombinationsOrangeRed(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> orange = golden.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> red = glowing.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches, orange, red, 0);
	}
	
	public void closeCombinationsRedBrown(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> red = glowing.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> brown = warm.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches, red, brown, 0);
	}
	
	public void closeCombinationsBrownPurple(int[] pitches, InstrumentGroup instrumentGroup){
		List<Instrument> brown = warm.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> purple = mellow.getBasicInstrumentsByGroup(instrumentGroup);
		combineCloseCombinations(pitches, brown, purple, 0);
	}
	
	@Test
	public void testOrchestrateChord() {
		List<OrchestralQuality> orchestralQualities = orchestrationGenerator.getOrchestralQualities();
		for (OrchestralQuality orchestralQuality : orchestralQualities) {
			List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.WOODWINDS);
			System.out.println(orchestralQuality.getQuality());
			orchestrationGenerator.orchestrateChord(new int[]{67, 64,60}, 48, instruments, instruments);
		}
	}
	
	@Test
	public void testAllCloseCombinations() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException{
		List<InstrumentGroup> instrumentGroup = new ArrayList<>();
		instrumentGroup.add(InstrumentGroup.WOODWINDS);
		instrumentGroup.add(InstrumentGroup.BRASS);
		
//		InstrumentGroup instrumentGroup = InstrumentGroup.STRINGS;
		
		List<Instrument> yellow = bright.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> orange = golden.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> red = glowing.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> brown = warm.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> purple = mellow.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> white = brilliant.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> blue = rich.getBasicInstrumentsByGroup(instrumentGroup);
		List<Instrument> green = pleasant.getBasicInstrumentsByGroup(instrumentGroup);
		
		int position = 0;
		int[] chord = new int[]{16,7,0};
		int noteLength = 48;
		for (int i = 2; i < 8; i++) {
			int[] pitches = getChordPitchclasses(i, chord);
			position = combineCloseCombinations(pitches, white, yellow, position, noteLength);
			position = combineCloseCombinations(pitches, yellow, green, position, noteLength);
			position = combineCloseCombinations(pitches,green, blue, position, noteLength);
			position = combineCloseCombinations(pitches, blue, purple, position, noteLength);
			position = combineCloseCombinations(pitches, yellow, orange, position, noteLength);
			position = combineCloseCombinations(pitches, orange, red, position, noteLength);
			position = combineCloseCombinations(pitches, red, brown, position, noteLength);
			position = combineCloseCombinations(pitches, brown, purple, position, noteLength);
		}
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testOrchestrateChordPerfectAndCloseCombinations() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int noteLength = 48;
		int[] pitches = new int[]{68,64,61,54,47};
		int position = 0;
		List<OrchestralQuality> orchestralQualities = orchestrationGenerator.getOrchestralQualities();
		for(InstrumentGroup instrumentGroup: InstrumentGroup.values()){
			//perfect combinations
			System.out.println("----PERFECT-----" + instrumentGroup);
			for (OrchestralQuality orchestralQuality : orchestralQualities) {
				System.out.println("-----------------");
				System.out.println(orchestralQuality.getQuality());
//					List<Instrument> instruments = orchestralQuality.getBasicInstruments();
				List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(instrumentGroup);
				
				List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::overlapping2);
				List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length + 1).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::enclosing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength,  orchestratedChords);

				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::crossing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);

				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::oneOrchestralQuality);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
			
				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::superpositionSplit2);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength,  orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::superpositionSplit1);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
			}
			//close Combinations
			System.out.println("----CLOSE-----");
			List<Instrument> yellow = bright.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> orange = golden.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> red = glowing.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> brown = warm.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> purple = mellow.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> white = brilliant.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> blue = rich.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> green = pleasant.getBasicInstrumentsByGroup(instrumentGroup);
			
			position = combineCloseCombinations(pitches, white, yellow, position, noteLength);
			position = combineCloseCombinations(pitches, yellow, green, position, noteLength);
			position = combineCloseCombinations(pitches,green, blue, position, noteLength);
			position = combineCloseCombinations(pitches, blue, purple, position, noteLength);
			position = combineCloseCombinations(pitches, yellow, orange, position, noteLength);
			position = combineCloseCombinations(pitches, orange, red, position, noteLength);
			position = combineCloseCombinations(pitches, red, brown, position, noteLength);
			position = combineCloseCombinations(pitches, brown, purple, position, noteLength);
		}
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testOneInstrument() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException{
		int noteLength = 48;
		int[] pitches = new int[]{67,64,60};
		int position = 0;
		List<OrchestralQuality> orchestralQualities = orchestrationGenerator.getOrchestralQualities();
		for(InstrumentGroup instrumentGroup: InstrumentGroup.values()){
			//perfect combinations
			System.out.println("----PERFECT-----" + instrumentGroup);
			for (OrchestralQuality orchestralQuality : orchestralQualities) {
				System.out.println("-----------------");
//				List<Instrument> instruments = orchestralQuality.getBasicInstruments();
				List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(instrumentGroup);
				for (Instrument instrument : instruments) {
					InstrumentNoteMapping instrumentNoteMapping = verticalRelations.oneOrchestralQuality(pitches, instrument, null);
					if (instrumentNoteMapping.getChordSize() == pitches.length) {
						position = updateOrchestra(position, noteLength, Collections.singletonList(instrumentNoteMapping));
					}
				}
			}
			//close Combinations
//			System.out.println("----CLOSE-----");
//			List<Instrument> yellow = bright.getBasicInstrumentsByGroup(instrumentGroup);
//			List<Instrument> orange = golden.getBasicInstrumentsByGroup(instrumentGroup);
//			List<Instrument> red = glowing.getBasicInstrumentsByGroup(instrumentGroup);
//			List<Instrument> brown = warm.getBasicInstrumentsByGroup(instrumentGroup);
//			List<Instrument> purple = mellow.getBasicInstrumentsByGroup(instrumentGroup);
//			List<Instrument> white = brilliant.getBasicInstrumentsByGroup(instrumentGroup);
//			List<Instrument> blue = rich.getBasicInstrumentsByGroup(instrumentGroup);
//			List<Instrument> green = pleasant.getBasicInstrumentsByGroup(instrumentGroup);
//			
//			position = combineCloseCombinations(pitches, white, yellow, position, noteLength);
//			position = combineCloseCombinations(pitches, yellow, green, position, noteLength);
//			position = combineCloseCombinations(pitches,green, blue, position, noteLength);
//			position = combineCloseCombinations(pitches, blue, purple, position, noteLength);
//			position = combineCloseCombinations(pitches, yellow, orange, position, noteLength);
//			position = combineCloseCombinations(pitches, orange, red, position, noteLength);
//			position = combineCloseCombinations(pitches, red, brown, position, noteLength);
//			position = combineCloseCombinations(pitches, brown, purple, position, noteLength);
		}
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testOrchestrateChordPerfectAndCloseCombinationsMultipleGroups() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int noteLength = 48;
		int[] pitches = new int[]{68,64,61,54,47};
		int position = 0;
		List<OrchestralQuality> orchestralQualities = orchestrationGenerator.getOrchestralQualities();
		List<InstrumentGroup> instrumentGroup = new ArrayList<>();
		instrumentGroup.add(InstrumentGroup.WOODWINDS);
		instrumentGroup.add(InstrumentGroup.BRASS);
		
			//perfect combinations
			System.out.println("----PERFECT-----" + instrumentGroup);
			for (OrchestralQuality orchestralQuality : orchestralQualities) {
				System.out.println("-----------------");
				System.out.println(orchestralQuality.getQuality());
//					List<Instrument> instruments = orchestralQuality.getBasicInstruments();
				List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(instrumentGroup);
				
				List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::overlapping2);
				List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length + 1).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::enclosing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength,  orchestratedChords);

				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::crossing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);

				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::oneOrchestralQuality);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
			
				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::superpositionSplit2);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength,  orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(pitches, instruments, instruments, verticalRelations::superpositionSplit1);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestratedChords);
			}
			//close Combinations
			System.out.println("----CLOSE-----");
			List<Instrument> yellow = bright.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> orange = golden.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> red = glowing.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> brown = warm.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> purple = mellow.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> white = brilliant.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> blue = rich.getBasicInstrumentsByGroup(instrumentGroup);
			List<Instrument> green = pleasant.getBasicInstrumentsByGroup(instrumentGroup);
			
			position = combineCloseCombinations(pitches, white, yellow, position, noteLength);
			position = combineCloseCombinations(pitches, yellow, green, position, noteLength);
			position = combineCloseCombinations(pitches,green, blue, position, noteLength);
			position = combineCloseCombinations(pitches, blue, purple, position, noteLength);
			position = combineCloseCombinations(pitches, yellow, orange, position, noteLength);
			position = combineCloseCombinations(pitches, orange, red, position, noteLength);
			position = combineCloseCombinations(pitches, red, brown, position, noteLength);
			position = combineCloseCombinations(pitches, brown, purple, position, noteLength);
		
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testOrchestrateMultipleRegisterChord() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException{
		List<Instrument> instruments = Stream.of(
				new Trumpet(),	
				new FrenchHorn(),
				new Trumpet(),	
				new FrenchHorn(),
				new FrenchHorn(),
				new Trombone(),
				new BassTrombone()
				).collect(Collectors.toList());
		int[] pitches = new int[]{68,64,61,52, 40};
		List<InstrumentNoteMapping> orchestrateLargeChord = orchestrationGenerator.orchestrateMultipleRegisterChord(pitches, instruments);
		for (InstrumentNoteMapping instrumentNoteMapping : orchestrateLargeChord) {
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				addToOrchestra(0, entry);
			}
		}
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testOrchestrateMultipleRegisterChord2() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException{
		List<Instrument> instruments = Stream.of(
				new ViolinsI(),
				new ViolinsI(),
				new Viola(),
				new Viola(),
				new Cello(),
				new Doublebass()
				).collect(Collectors.toList());
		int[] pitches = new int[]{68,64,61,52, 40};
		List<InstrumentNoteMapping> orchestrateLargeChord = orchestrationGenerator.orchestrateMultipleRegisterChord(pitches, instruments);
		for (InstrumentNoteMapping instrumentNoteMapping : orchestrateLargeChord) {
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				addToOrchestra(0, entry);
			}
		}
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}
	
	@Test
	public void testFindOrchestralBasicAndComplementaryQuality(){
		Optional<OrchestralQuality> quality = orchestrationGenerator.findOrchestralBasicQuality(64, warm, new FrenchHorn());
		assertEquals(warm, quality.get());
	}
	
	@Test
	public void testFindInstrumentWithQuality(){
		List<Instrument> instruments = Stream.of(
				new Trumpet(),
				new Trumpet(),
				new FrenchHorn(),
				new FrenchHorn(),
				new Trombone(),
				new BassTrombone()
				).collect(Collectors.toList());
		Optional<Instrument> instrument = orchestrationGenerator.findBasicInstrumentWithQuality(instruments, mellow);
		assertEquals(instruments.get(2), instrument.get());
	}
	
	@Test
	public void testFindInstrumentWithQuality2(){
		List<Instrument> instruments = Stream.of(
				new BassTrombone()
				).collect(Collectors.toList());
		Optional<Instrument> instrument = orchestrationGenerator.findBasicInstrumentWithQuality(instruments, glowing);
		assertEquals(new BassTrombone().getInstrumentName(), instrument.get().getInstrumentName());
	}
	
	@Test
	public void testFindInstrumentWithQualityForPitch(){
		List<Instrument> instruments = Stream.of(
				new Trumpet(),
				new Trumpet(),
				new FrenchHorn(),
				new FrenchHorn(),
				new Trombone(),
				new BassTrombone()
				).collect(Collectors.toList());
		Instrument instrument = orchestrationGenerator.findInstrumentInBasicQualityForPitch(49, instruments, warm);
		assertEquals(new Trombone().getInstrumentName(), instrument.getInstrumentName());
	}
	
	@Test
	public void testFindInstrumentWithQualityForPitch2(){
		List<Instrument> instruments = Stream.of(
				new BassTrombone()
				).collect(Collectors.toList());
		Instrument instrument = orchestrationGenerator.findInstrumentInBasicQualityForPitch(40, instruments, glowing);
		assertNull(instrument);
	}
	
}
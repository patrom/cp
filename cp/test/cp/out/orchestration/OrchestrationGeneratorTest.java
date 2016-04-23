package cp.out.orchestration;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.orchestra.WindOrchestra;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.print.MusicXMLWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class OrchestrationGeneratorTest {
	
	@Autowired
	private OrchestrationGenerator orchestrationGenerator;	
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	@Autowired
	private VerticalRelations verticalRelations;

	private Orchestra orchestra = new ClassicalOrchestra();
	
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
//				List<Instrument> instruments = orchestralQuality.getBasicInstruments();
				List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.STRINGS);
				Collections.sort(instruments , new OrderComparator());
				
				List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::overlapping2);
				List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length + 1).collect(toList());
				position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::enclosing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);

				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::crossing);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);

//				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::oneOrchestralQuality);
//				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
//				position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);
			
				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::superpositionSplit2);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);
				
				noteForInstrument = orchestrationGenerator.combine(chordPitchClasses, instruments, instruments, verticalRelations::superpositionSplit1);
				orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == chord.length).collect(toList());
				position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);
			}
		}
		musicXMLWriter.createXML("orchestra", orchestra.getOrchestra());
	}

	private int updateOrchestra(int position, int noteLength, OrchestralQuality orchestralQuality,
			List<InstrumentNoteMapping> orchestratedChords) {
		for (InstrumentNoteMapping instrumentNoteMapping : orchestratedChords) {
			System.out.println("-----------------");
			System.out.println(orchestralQuality.getQuality());
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
	
	@Test
	public void testOverlayInstruments2() {
		List<Instrument> instruments = new ArrayList<>();
		instruments.add(new ViolinsI());
		instruments.add(new Viola());
		instruments.add(new Cello());
		List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.overlayAllInstruments(new int[]{67,64,60}, instruments);
		for (InstrumentNoteMapping instrumentNoteMapping : noteForInstrument) {
			System.out.println("-----------------");
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				System.out.println(entry.getKey().getInstrumentName());
				System.out.println(entry.getValue());
			}
		}
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
	public void testEnclosureAllInstruments() {
		List<Instrument> instruments = new ArrayList<>();
		instruments.add(new ViolinsI());
		instruments.add(new Viola());
		instruments.add(new Cello());
		List<InstrumentNoteMapping> orchestratedChords = orchestrationGenerator.enclosureAllInstruments(new int[]{67, 64,60,57}, instruments);
		for (InstrumentNoteMapping instrumentNoteMapping : orchestratedChords) {
			System.out.println("-----------------");
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				System.out.println(entry.getKey().getInstrumentName());
				System.out.println(entry.getValue());
			}
		}
	}
	
	@Test
	public void testCrossingAllInstruments() {
		List<Instrument> instruments = new ArrayList<>();
		instruments.add(new ViolinsI());
		instruments.add(new Viola());
		instruments.add(new Cello());
		List<InstrumentNoteMapping> orchestratedChords = orchestrationGenerator.crossingAllInstruments(new int[]{67, 64,60,57}, instruments);
		for (InstrumentNoteMapping instrumentNoteMapping : orchestratedChords) {
			System.out.println("-----------------");
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				System.out.println(entry.getKey().getInstrumentName());
				System.out.println(entry.getValue());
			}
		}
	}
	
	@Test
	public void testSuperposition() {
		InstrumentNoteMapping instrumentNoteMapping = orchestrationGenerator.superpositionSplit2(new int[]{67, 64,60,57}, new ViolinsI(), new Viola());
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 64);
		notes = map.get(new Viola());
		assertEquals(notes.get(0).getPitch(), 60);
		assertEquals(notes.get(1).getPitch(), 57);
	}
	
	@Test
	public void testEnclosing() {
		InstrumentNoteMapping instrumentNoteMapping = orchestrationGenerator.enclosing(new int[]{67, 64,60,57}, new ViolinsI(), new Viola());
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 57);
		notes = map.get(new Viola());
		assertEquals(notes.get(0).getPitch(), 64);
		assertEquals(notes.get(1).getPitch(), 60);
	}
	
	@Test
	public void testCrossing() {
		InstrumentNoteMapping instrumentNoteMapping = orchestrationGenerator.crossing(new int[]{67, 64,60,57}, new ViolinsI(), new Viola());
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 60);
		notes = map.get(new Viola());
		assertEquals(notes.get(0).getPitch(), 64);
		assertEquals(notes.get(1).getPitch(), 57);
	}
	
	@Test
	public void testOverlapping() {
		InstrumentNoteMapping instrumentNoteMapping = orchestrationGenerator.overlapping2(new int[]{67, 64,60}, new ViolinsI(), new Viola());
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 64);
		notes = map.get(new Viola());
		assertEquals(notes.get(0).getPitch(), 64);
		assertEquals(notes.get(1).getPitch(), 60);
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
}
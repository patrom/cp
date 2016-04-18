package cp.out.orchestration;

import static java.util.stream.Collectors.toList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.print.MusicXMLWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class OrchestrationGeneratorTest {
	
	@Autowired
	private OrchestrationGenerator orchestrationGenerator;	
	@Autowired
	private MusicXMLWriter musicXMLWriter;

	private ClassicalOrchestra classicalOrchestra = new ClassicalOrchestra();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOverlay2AllInstruments() throws FileNotFoundException, FactoryConfigurationError, XMLStreamException {
		int position = 0;
		int noteLength = 48;
		for (int i = 2; i < 8; i++) {
			int[] chord = new int[]{9, 7, 4, 1};
			int[] chordPitchClasses = getChordPitchclasses(i, chord);
			List<OrchestralQuality> orchestralQualities = orchestrationGenerator.getOrchestralQualities();
			for (OrchestralQuality orchestralQuality : orchestralQualities) {
				List<Instrument> instruments = orchestralQuality.getBasicInstruments();
//				List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.BRASS);
//				List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.overlay2AllInstruments(chordPitchClasses, instruments, 2);
				List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.enclosureAllInstruments(chordPitchClasses, instruments);
//				List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.crossingAllInstruments(chordPitchClasses, instruments);
				List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() > chord.length - 1 ).collect(toList());
				for (InstrumentNoteMapping instrumentNoteMapping : orchestratedChords) {
					System.out.println("-----------------");
					System.out.println(orchestralQuality.getQuality());
					for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
						addToOrchestra(position, entry);
					}
					position = position + noteLength;
				}
			}
		}
		musicXMLWriter.createXML("orchestra", classicalOrchestra.getOrchestra());
	}

	private void addToOrchestra(int position, Entry<Instrument, List<Note>> entry) {
		System.out.println(entry.getKey().getInstrumentName());
		List<Note> notes = entry.getValue();
		notes.forEach(n -> n.setPosition(position));
		System.out.println(notes);
		classicalOrchestra.updateInstrument(entry.getKey(), notes);
	}
	
	@Test
	public void testOverlay2Instruments2() {
		List<Instrument> instruments = new ArrayList<>();
		instruments.add(new ViolinsI());
		instruments.add(new Viola());
		instruments.add(new Cello());
		List<InstrumentNoteMapping> noteForInstrument = orchestrationGenerator.overlay2AllInstruments(new int[]{67,64,60}, instruments, 2);
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
}
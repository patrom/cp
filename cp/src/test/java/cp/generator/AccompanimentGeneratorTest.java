package cp.generator;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.VariationConfig;
import cp.midi.MelodyInstrument;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class AccompanimentGeneratorTest extends AbstractTest {
	
	@Autowired
	private AccompanimentGenerator accompanimentGenerator;

	@Test
	public void testFourFourSingleNote() {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).build());
		Instrument piano = new Piano(0, 0);
		List<Note> accompanimentNotes = accompanimentGenerator.fourFourSingleNote(chordNotes, 3, piano);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ":" + n.getPosition() + ", "));
		
		playOnKontakt(accompanimentNotes, piano, 90, 5000);
	}
	
	@Test
	public void testFourFourSingleNoteTexture() {
		List<MelodyInstrument> melodyInstruments = new ArrayList<>();
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).build());
		Instrument piano = new Piano(0, 0);
		List<Note> accompanimentNotes = accompanimentGenerator.fourFourTexture(chordNotes, 6, piano, 1);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ":" + n.getPosition() + ", "));
		
		MelodyInstrument melodyInstrument = new MelodyInstrument(accompanimentNotes, piano.getVoice());
		melodyInstrument.setInstrument(piano);
		melodyInstruments.add(melodyInstrument);
		
		chordNotes = new ArrayList<>();
		
		chordNotes.add(note().pc(4).pitch(76).ocatve(6).build());
		chordNotes.add(note().pc(7).pitch(97).ocatve(6).build());
		chordNotes.add(note().pc(0).pitch(72).ocatve(6).build());
		accompanimentNotes = accompanimentGenerator.fourFourTexture(chordNotes, DurationConstants.QUARTER, piano, 1);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ":" + n.getPosition() + ", "));
		melodyInstrument = new MelodyInstrument(accompanimentNotes, piano.getVoice());
		melodyInstrument.setInstrument(piano);
		melodyInstruments.add(melodyInstrument);
		playOnKontakt(melodyInstruments, 90, 5000);
	}
	
	@Test
	public void testFourFourSingleNote2() {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).build());
		chordNotes.add(note().pc(2).pitch(62).ocatve(5).build());
		Instrument piano = new Piano(0, 0);
		//TODO fixed 2 note contour
		List<Note> accompanimentNotes = accompanimentGenerator.fourFourSingleNote(chordNotes, 6, piano);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ", " ));
		
		playOnKontakt(accompanimentNotes, piano, 90, 5000);
	}

}

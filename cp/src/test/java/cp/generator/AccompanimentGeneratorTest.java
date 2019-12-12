package cp.generator;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.midi.MelodyInstrument;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import cp.out.play.InstrumentMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
public class AccompanimentGeneratorTest extends AbstractTest {
	
	@Autowired
	private AccompanimentGenerator accompanimentGenerator;

	@Test
	public void testFourFourSingleNote() {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).build());
		Instrument piano = new Piano();
		List<Note> accompanimentNotes = accompanimentGenerator.fourFourSingleNote(chordNotes, 3, piano,0);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ":" + n.getPosition() + ", "));
		
		playOnKontakt(accompanimentNotes, new InstrumentMapping(new Piano(),0,0), 90, 5000);
	}
	
	@Test
	public void testFourFourSingleNoteTexture() {
		List<MelodyInstrument> melodyInstruments = new ArrayList<>();
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).build());
		Instrument piano = new Piano();
		List<Note> accompanimentNotes = accompanimentGenerator.fourFourTexture(chordNotes, 6, piano, 1,0);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ":" + n.getPosition() + ", "));
		
		MelodyInstrument melodyInstrument = new MelodyInstrument(accompanimentNotes,0);
		melodyInstrument.setInstrumentMapping(new InstrumentMapping(piano,0,0));
		melodyInstruments.add(melodyInstrument);
		
		chordNotes = new ArrayList<>();
		
		chordNotes.add(note().pc(4).pitch(76).octave(6).build());
		chordNotes.add(note().pc(7).pitch(97).octave(6).build());
		chordNotes.add(note().pc(0).pitch(72).octave(6).build());
		accompanimentNotes = accompanimentGenerator.fourFourTexture(chordNotes, DurationConstants.QUARTER, piano, 1, 0);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ":" + n.getPosition() + ", "));
		melodyInstrument = new MelodyInstrument(accompanimentNotes, 0);
		melodyInstrument.setInstrumentMapping(new InstrumentMapping(piano,0,0));
		melodyInstruments.add(melodyInstrument);
		playOnKontakt(melodyInstruments, 90, 5000);
	}
	
	@Test
	public void testFourFourSingleNote2() {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).build());
		chordNotes.add(note().pc(2).pitch(62).octave(5).build());
		Instrument piano = new Piano();
		//TODO fixed 2 note contour
		List<Note> accompanimentNotes = accompanimentGenerator.fourFourSingleNote(chordNotes, 6, piano, 0);
		accompanimentNotes.forEach(n -> System.out.print(n.getPitch() + ", " ));
		
		playOnKontakt(accompanimentNotes, new InstrumentMapping(new Piano(),0,0), 90, 5000);
	}

}

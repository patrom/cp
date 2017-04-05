package cp.out.orchestration;

import cp.DefaultConfig;
import cp.combination.even.TwoNoteEven;
import cp.model.note.Note;
import cp.out.instrument.Articulation;
import cp.out.orchestration.notetemplate.TwoNoteTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class ChordOrchestrationTest {
	
	private final ChordOrchestration chordOrchestration = new ChordOrchestration(0, 48, 5);
	
	@Autowired
	private TwoNoteEven twoNoteEven;
	@Autowired
	private TwoNoteTemplate twoNoteTemplate;

	@Test
	public void testApplyRhythmCombination() {
		List<Note> rhythmNotes = chordOrchestration.applyRhythmCombination(twoNoteEven::pos12, 12);
		rhythmNotes.forEach(n -> System.out.println(n.getPosition()));
		assertEquals(0, rhythmNotes.get(0).getPosition());
		assertEquals(3, rhythmNotes.get(1).getPosition());
		assertEquals(12, rhythmNotes.get(2).getPosition());
		assertEquals(15, rhythmNotes.get(3).getPosition());
	}
	
	@Test
	public void testOrchestrate() {
		int[] pitchClasses = {0, 4, 7};
		List<Note> notes = chordOrchestration.orchestrate(pitchClasses, twoNoteTemplate::note01, twoNoteEven::pos12, 12);
		notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getPosition()));
	}
	
	@Test
	public void testApplyNoteTemplate() {
		int[] pitchClasses = {0, 4};
		Note[] notes = chordOrchestration.applyNoteTemplate(twoNoteTemplate::note01, pitchClasses);
		
		assertEquals(2, notes.length);
		assertEquals(0, notes[0].getPitchClass());
		assertEquals(4, notes[1].getPitchClass());
		Arrays.stream(notes).forEach(n -> System.out.println(n.getPitchClass()));
	}
	
	@Test
	public void testApplyArticulation() {
		List<Note> rhythmNotes = chordOrchestration.applyRhythmCombination(twoNoteEven::pos13, 12);
		
		List<Note> articulationNotes = chordOrchestration.applyRhythmCombination(twoNoteEven::pos13, 24);
		articulationNotes.forEach(n -> n.setArticulation(Articulation.MARCATO));
		
		List<Note> notes = chordOrchestration.applyArticulation(rhythmNotes, articulationNotes);
		assertEquals(Articulation.MARCATO, notes.get(0).getArticulation());
		assertEquals(Articulation.STACCATO, notes.get(1).getArticulation());
		notes.forEach(n -> System.out.println(n.getArticulation() + ", " + n.getPosition()));
	}
	
	@Test
	public void testOrchestrateArticulation() {
		int[] pitchClasses = {0, 4, 7};
		List<Note> notes = chordOrchestration.orchestrate(pitchClasses, twoNoteTemplate::note01, twoNoteEven::pos12, 12, twoNoteEven::pos13, 24, Articulation.MARCATO);
		notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getPosition() + ", " + n.getArticulation()));
	}

}

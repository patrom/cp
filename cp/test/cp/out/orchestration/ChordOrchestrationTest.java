package cp.out.orchestration;

import static cp.model.note.NoteName.C;
import static cp.model.note.NoteName.E;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.combination.even.TwoNoteEven;
import cp.model.note.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class ChordOrchestrationTest {
	
	private ChordOrchestration chordOrchestration = new ChordOrchestration(0, 48);
	
	@Autowired
	private TwoNoteEven twoNoteEven;
	

	@Test
	public void testOrchestrateChord() {
		List<Note> rhythmNotes = chordOrchestration.getRhythmNotes(twoNoteEven::pos12, 12);
		List<Note> notes = chordOrchestration.orchestrateChord(rhythmNotes, C(5), E(5));
		notes.forEach(n -> System.out.println(n.getPitchClass() + ", " + n.getPosition()));
	}
	
	@Test
	public void testOrchestrate() {
		List<Note> notes = chordOrchestration.orchestrate(twoNoteEven::pos12, 24, C(5), E(5));
		notes.forEach(n -> System.out.println(n.getPitchClass() + ", " + n.getPosition()));
	}


}

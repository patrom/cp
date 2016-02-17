package cp.out.orchestration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.combination.RhythmCombination;
import cp.combination.even.TwoNoteEven;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class ChordOrchestrationTest {
	
	@Autowired
	private ChordOrchestration chordOrchestration;
	
	@Autowired
	private TwoNoteEven twoNoteEven;
	

	@Test
	public void testOrchestrateChord() {
		List<Note> rhythmNotes = chordOrchestration.getRhythmNotes(twoNoteEven::pos12, 0, 48, 12);
		
		List<Note> chordNotes = new ArrayList<Note>();
		chordNotes.add(NoteBuilder.note().pc(0).pitch(60).ocatve(5).build());
		chordNotes.add(NoteBuilder.note().pc(4).pitch(64).ocatve(5).build());
		chordNotes.add(NoteBuilder.note().pc(7).pitch(67).ocatve(5).build());
		
		List<Note> notes = chordOrchestration.orchestrateChord(rhythmNotes, chordNotes);
		
		notes.forEach(n -> System.out.println(n.getPitchClass() + ", " + n.getPosition()));
	}

}

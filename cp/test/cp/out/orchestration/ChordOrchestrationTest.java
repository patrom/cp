package cp.out.orchestration;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
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
import cp.out.orchestration.notetemplate.TwoNoteTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class ChordOrchestrationTest {
	
	private ChordOrchestration chordOrchestration = new ChordOrchestration(0, 48, 5);
	
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
		List<Note> notes = chordOrchestration.orchestrate(twoNoteTemplate::getNoteTemplate,twoNoteEven::pos12, pitchClasses, 12);
		notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getPosition()));
	}
	
	@Test
	public void testApplyNoteTemplate() {
		int[] pitchClasses = {0, 4};
		Note[] notes = chordOrchestration.applyNoteTemplate(twoNoteTemplate::getNoteTemplate, pitchClasses);
		
		assertEquals(3, notes.length);
		Arrays.stream(notes).forEach(n -> System.out.println(n.getPitchClass()));
	}
	
	


}

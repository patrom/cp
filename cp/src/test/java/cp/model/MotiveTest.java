package cp.model;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
public class MotiveTest extends AbstractTest {
	
	private Motive motive;

	//	@Test
//	public void testGetMelodies(){
//		motive.extractMelodies();
//		List<Melody> melodies = motive.getMelodies();
//		assertFalse(melodies.isEmpty());
//		assertEquals(2, melodies.get(0).getMelodieNotes().size());
//		assertEquals(5, melodies.get(0).getMelodieNotes().get(1).getPitchClass());
//	}
//	
//	@Test
//	public void updateInnerMetricWeightNotes(){
//		musicProperties.setMinimumLength(6);
//		List<Note> notes = new ArrayList<>();
//		notes.add(NoteBuilder.note().pos(6).build());
//		notes.add(NoteBuilder.note().pos(DurationConstants.QUARTER).build());
//		notes.add(NoteBuilder.note().pos(DurationConstants.THREE_EIGHTS).build());
////		notes.add(NoteBuilder.note().pos(DurationConstants.HALF).build());
//		notes.add(NoteBuilder.note().pos(DurationConstants.SIX_EIGHTS).build());
//		
////		List<Note> notes = new ArrayList<>();
////		notes.add(NoteBuilder.note().pos(0).build());
////		notes.add(NoteBuilder.note().pos(6).build());
////		notes.add(NoteBuilder.note().pos(9).build());
////		notes.add(NoteBuilder.note().pos(DurationConstants.QUARTER).build());
////		notes.add(NoteBuilder.note().pos(DurationConstants.THREE_EIGHTS).build());
////		notes.add(NoteBuilder.note().pos(30).build());
////		notes.add(NoteBuilder.note().pos(DurationConstants.SIX_EIGHTS).build());
//		motive.updateInnerMetricWeightNotes(notes);
//		
////		INFO: {0=17.0, 2=17.0, 3=8.0, 4=13.0, 6=21.0, 10=4.0, 12=4.0}
//		System.out.println(notes);
//	}
//	
//	@Test
//	public void extractMelodies(){
//		motive.extractMelodies();
//		List<Melody> melodies = motive.getMelodies();
//		assertFalse(melodies.isEmpty());
//		assertEquals(2, melodies.get(0).getMelodieNotes().size());
//		assertEquals(5, melodies.get(0).getMelodieNotes().get(1).getPitchClass());
//	}
	
	@Test
	public void testExtractRhythmProfile() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		
		notes.add(note().pos(DurationConstants.HALF).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(2.0).build());
		CpMelody melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		MelodyBlock melodyBlock = new MelodyBlock(5, 1);
		melodyBlock.addMelodyBlock(melody);
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		melodyBlocks.add(melodyBlock);
		motive = new Motive(melodyBlocks);
		Map<Integer, Double> profiles = motive.extractRhythmProfile();
		assertEquals(4.0 , profiles.get(0), 0);
		assertEquals(1.0 , profiles.get(DurationConstants.QUARTER), 0);
		assertEquals(3.0 , profiles.get(DurationConstants.HALF), 0);
		assertEquals(6.0 , profiles.get(DurationConstants.SIX_EIGHTS), 0);
	}

}

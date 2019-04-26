package cp.model.rhythm;

import cp.DefaultConfig;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class RhythmWeightTest {
	
	@Autowired
	private RhythmWeight rhythmWeight;

	@BeforeEach
	public void setUp() throws Exception {
	}
	
	@Test
	public void testUpdateNotesLength() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(60).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(60).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateNotesLength();
        assertEquals(DurationConstants.EIGHT , notes.get(1).getLength());
	}

	@Test
	public void testGetMinimumNoteValue() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(60).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(60).len(DurationConstants.HALF).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(60).len(DurationConstants.EIGHT).build());
		rhythmWeight.setNotes(notes);
		double min = rhythmWeight.getMinimumNoteValue();
		assertEquals(DurationConstants.EIGHT , min, 0);
	}

	@Test
	public void testUpdateRhythmWeightSounds() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(60).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(60).len(DurationConstants.HALF).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(60).len(DurationConstants.EIGHT).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightSounds(DurationConstants.EIGHT);
		assertEquals(2 , notes.get(0).getPositionWeight(), 0);
		assertEquals(1 , notes.get(1).getPositionWeight(), 0);
		assertEquals(1 , notes.get(2).getPositionWeight(), 0);
		assertEquals(4 , notes.get(3).getPositionWeight(), 0);
	}
	
	@Test
	public void testExtractDifferentPitches() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(61).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).build());
		rhythmWeight.setNotes(notes);
		List<Note> pitches = rhythmWeight.extractDifferentPitches();
		assertEquals(4 , pitches.size());
		assertEquals(60 , pitches.get(0).getPitch());
		assertEquals(62 , pitches.get(1).getPitch());
		assertEquals(61 , pitches.get(2).getPitch());
		assertEquals(60 , pitches.get(3).getPitch());
	}

	@Test
	public void testUpdateRhythmWeightPitch() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(61).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).build());
		rhythmWeight.setNotes(notes);
		List<Note> pitches = rhythmWeight.extractDifferentPitches();
		rhythmWeight.updateRhythmWeightPitch(pitches, DurationConstants.EIGHT);
		assertEquals(3 , notes.get(0).getPositionWeight(), 0);
		assertEquals(0 , notes.get(1).getPositionWeight(), 0);
		assertEquals(1 , notes.get(2).getPositionWeight(), 0);
		assertEquals(6 , notes.get(3).getPositionWeight(), 0);
	}
	
	@Test
	public void testUpdateRhythmWeightPitch2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(64).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.SIXTEENTH).pitch(64).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.EIGHT).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightPitch(notes, DurationConstants.QUARTER);
		assertEquals(1 , notes.get(0).getPositionWeight(), 0);
		assertEquals(0.5 , notes.get(1).getPositionWeight(), 0);
		assertEquals(0.5 , notes.get(2).getPositionWeight(), 0);
		assertEquals(0.25 , notes.get(3).getPositionWeight(), 0);
		assertEquals(0.25 , notes.get(3).getPositionWeight(), 0);
	}

	@Test
	public void testUpdateRhythmWeightDiastematy() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).build());
		rhythmWeight.setNotes(notes);
		List<Note> pitches = rhythmWeight.extractDifferentPitches();
		rhythmWeight.updateRhythmWeightDiastematy(pitches);
		assertEquals(1 , pitches.get(0).getPositionWeight(), 0);
		assertEquals(1 , pitches.get(1).getPositionWeight(), 0);
		assertEquals(1 , pitches.get(3).getPositionWeight(), 0);
		
		assertTrue(notes.get(0).isKeel());
		assertFalse(notes.get(1).isKeel());
		assertFalse(notes.get(1).isCrest());
		assertTrue(notes.get(2).isCrest());
		assertFalse(notes.get(3).isKeel());
		assertFalse(notes.get(3).isCrest());
		assertTrue(notes.get(4).isKeel());
	}

	@Test
	public void testUpdateRhythmWeightDynamics() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).dyn(Dynamic.F).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).dyn(Dynamic.MP).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightDynamics();
		assertEquals(1 , notes.get(0).getPositionWeight(), 0);
		assertEquals(1 , notes.get(1).getPositionWeight(), 0);
		assertEquals(0 , notes.get(2).getPositionWeight(), 0);
		assertEquals(1 , notes.get(3).getPositionWeight(), 0);
		assertEquals(1 , notes.get(4).getPositionWeight(), 0);
		assertEquals(0 , notes.get(5).getPositionWeight(), 0);
	}

	@Test
	public void testUpdateRhythmWeightTechnical() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).tech(Technical.LEGATO).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).tech(Technical.STACCATO).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).tech(Technical.STACCATO).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).tech(Technical.LEGATO).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightTechnical();
		assertEquals(1 , notes.get(0).getPositionWeight(), 0);
		assertEquals(0 , notes.get(1).getPositionWeight(), 0);
		assertEquals(0 , notes.get(2).getPositionWeight(), 0);
		assertEquals(1 , notes.get(3).getPositionWeight(), 0);
		assertEquals(0 , notes.get(4).getPositionWeight(), 0);
		assertEquals(1 , notes.get(5).getPositionWeight(), 0);
	}

	@Test
	public void testUpdateRhythmWeightArticulation() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).art(Articulation.MARCATO).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).art(Articulation.STACCATO).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).build());//change to default mf
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightArticulation();
		assertEquals(1 , notes.get(0).getPositionWeight(), 0);
		assertEquals(0 , notes.get(1).getPositionWeight(), 0);
		assertEquals(0 , notes.get(2).getPositionWeight(), 0);
		assertEquals(1 , notes.get(3).getPositionWeight(), 0);
		assertEquals(0 , notes.get(4).getPositionWeight(), 0);
		assertEquals(0 , notes.get(5).getPositionWeight(), 0);
	}
	
	@Test
	public void testUpdateRhythmWeight() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).len(DurationConstants.EIGHT).dyn(Dynamic.P).build());
		notes.add(note().pos(DurationConstants.EIGHT).pitch(62).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(64).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(64).len(DurationConstants.EIGHT).art(Articulation.STACCATO).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.EIGHT).pitch(65).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(65).len(DurationConstants.QUARTER).build());
		
		notes.add(note().pos(DurationConstants.WHOLE).pitch(67).dyn(Dynamic.F).len(DurationConstants.HALF).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pitch(65).len(DurationConstants.EIGHT).art(Articulation.STACCATO).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pitch(65).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pitch(64).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(0).len(DurationConstants.QUARTER).build());
		
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeight();
		
		assertEquals(5 , notes.get(0).getPositionWeight(), 0);
		assertEquals(3 , notes.get(1).getPositionWeight(), 0);
		assertEquals(5 , notes.get(2).getPositionWeight(), 0);
		assertEquals(2 , notes.get(3).getPositionWeight(), 0);
		assertEquals(4 , notes.get(4).getPositionWeight(), 0);
		assertEquals(2 , notes.get(5).getPositionWeight(), 0);
		assertEquals(10 , notes.get(6).getPositionWeight(), 0);
		assertEquals(5 , notes.get(7).getPositionWeight(), 0);
		assertEquals(1 , notes.get(8).getPositionWeight(), 0);
		assertEquals(4 , notes.get(9).getPositionWeight(), 0);
	}
	
	@Test
	public void testUpdateRhythmWeight2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(4).pitch(62).build());
		notes.add(note().pos(6).pitch(64).build());
		notes.add(note().pos(8).pitch(63).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(64).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(65).build());
		notes.add(note().pos(21).pitch(66).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(67).build());
		
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightMinimum(12);
		
		notes.forEach(n -> System.out.println(n.getPositionWeight()));
//		assertEquals(4 , notes.get(0).getPositionWeight(), 0);
//		assertEquals(2 , notes.get(1).getPositionWeight(), 0);
//		assertEquals(5 , notes.get(2).getPositionWeight(), 0);
//		assertEquals(2 , notes.get(3).getPositionWeight(), 0);
//		assertEquals(4 , notes.get(4).getPositionWeight(), 0);
//		assertEquals(2 , notes.get(5).getPositionWeight(), 0);
//		assertEquals(10 , notes.get(6).getPositionWeight(), 0);
//		assertEquals(4 , notes.get(7).getPositionWeight(), 0);
//		assertEquals(1 , notes.get(8).getPositionWeight(), 0);
//		assertEquals(4 , notes.get(9).getPositionWeight(), 0);
	}
	
	@Test
	public void testFilterRhythmWeight() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).dyn(Dynamic.F).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).dyn(Dynamic.MP).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).len(DurationConstants.QUARTER).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeight();
		List<Note> filteredNotes = rhythmWeight.filterRhythmWeigths(4.0);
		assertEquals(2 , filteredNotes.size());
		assertEquals(0 , filteredNotes.get(0).getPosition());
	}
	
}

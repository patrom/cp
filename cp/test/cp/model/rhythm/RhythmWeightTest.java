package cp.model.rhythm;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jm.constants.Dynamics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.out.instrument.Articulation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class RhythmWeightTest {
	
	@Autowired
	private RhythmWeight rhythmWeight;

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testUpdateNotesLength() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(60).build());
		notes.add(note().pos(24).pitch(60).build());
		notes.add(note().pos(48).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateNotesLength();
		assertEquals(6 , notes.get(1).getLength());
	}

	@Test
	public void testGetMinimumNoteValue() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(60).build());
		notes.add(note().pos(24).pitch(60).build());
		notes.add(note().pos(48).pitch(60).build());
		rhythmWeight.setNotes(notes);
		double min = rhythmWeight.getMinimumNoteValue();
		assertEquals(6 , min, 0);
	}

	@Test
	public void testUpdateRhythmWeightSounds() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(60).build());
		notes.add(note().pos(24).pitch(60).build());
		notes.add(note().pos(48).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightSounds(6);
		assertEquals(2 , notes.get(0).getPositionWeight(), 0);
		assertEquals(1 , notes.get(1).getPositionWeight(), 0);
		assertEquals(1 , notes.get(2).getPositionWeight(), 0);
		assertEquals(4 , notes.get(3).getPositionWeight(), 0);
	}
	
	@Test
	public void testExtractDifferentPitches() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(62).build());
		notes.add(note().pos(24).pitch(61).build());
		notes.add(note().pos(48).pitch(61).build());
		notes.add(note().pos(60).pitch(60).build());
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
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(62).build());
		notes.add(note().pos(24).pitch(61).build());
		notes.add(note().pos(48).pitch(61).build());
		notes.add(note().pos(60).pitch(60).build());
		rhythmWeight.setNotes(notes);
		List<Note> pitches = rhythmWeight.extractDifferentPitches();
		rhythmWeight.updateRhythmWeightPitch(pitches, 6);
		assertEquals(3 , notes.get(0).getPositionWeight(), 0);
		assertEquals(0 , notes.get(1).getPositionWeight(), 0);
		assertEquals(1 , notes.get(2).getPositionWeight(), 0);
		assertEquals(6 , notes.get(3).getPositionWeight(), 0);
	}

	@Test
	public void testUpdateRhythmWeightDiastematy() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(62).build());
		notes.add(note().pos(24).pitch(61).build());
		notes.add(note().pos(48).pitch(59).build());
		notes.add(note().pos(60).pitch(60).build());
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
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(62).build());
		notes.add(note().pos(24).pitch(61).dyn(Dynamic.MP).build());
		notes.add(note().pos(48).pitch(59).build());
		notes.add(note().pos(60).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeightDynamics();
		assertEquals(1 , notes.get(0).getPositionWeight(), 0);
		assertEquals(0 , notes.get(1).getPositionWeight(), 0);
		assertEquals(0 , notes.get(2).getPositionWeight(), 0);
		assertEquals(1 , notes.get(3).getPositionWeight(), 0);
		assertEquals(0 , notes.get(4).getPositionWeight(), 0);
		assertEquals(0 , notes.get(5).getPositionWeight(), 0);
	}

	@Test
	public void testUpdateRhythmWeightArticulation() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).art(Articulation.MARCATO).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(18).pitch(62).build());
		notes.add(note().pos(24).pitch(61).art(Articulation.STACCATO).build());
		notes.add(note().pos(48).pitch(59).build());//change to default mf
		notes.add(note().pos(60).pitch(60).build());
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
		notes.add(note().pos(0).pitch(60).dyn(Dynamic.P).build());
		notes.add(note().pos(6).pitch(62).build());
		notes.add(note().pos(12).pitch(64).build());
		notes.add(note().pos(24).pitch(64).art(Articulation.STACCATO).build());
		notes.add(note().pos(30).pitch(65).build());
		notes.add(note().pos(36).pitch(65).build());
		
		notes.add(note().pos(48).pitch(67).dyn(Dynamic.F).build());
		notes.add(note().pos(72).pitch(65).art(Articulation.STACCATO).build());
		notes.add(note().pos(78).pitch(65).build());
		notes.add(note().pos(84).pitch(64).build());
		notes.add(note().pos(96).pitch(0).build());
		
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeight();
		
		assertEquals(4 , notes.get(0).getPositionWeight(), 0);
		assertEquals(2 , notes.get(1).getPositionWeight(), 0);
		assertEquals(5 , notes.get(2).getPositionWeight(), 0);
		assertEquals(2 , notes.get(3).getPositionWeight(), 0);
		assertEquals(4 , notes.get(4).getPositionWeight(), 0);
		assertEquals(2 , notes.get(5).getPositionWeight(), 0);
		assertEquals(10 , notes.get(6).getPositionWeight(), 0);
		assertEquals(4 , notes.get(7).getPositionWeight(), 0);
		assertEquals(1 , notes.get(8).getPositionWeight(), 0);
		assertEquals(4 , notes.get(9).getPositionWeight(), 0);
	}
	
	@Test
	public void testFilterRhythmWeight() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).dyn(Dynamic.F).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(24).pitch(62).build());
		notes.add(note().pos(36).pitch(61).dyn(Dynamic.MP).build());
		notes.add(note().pos(48).pitch(59).build());
		notes.add(note().pos(60).pitch(60).build());
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeight();
		List<Note> filteredNotes = rhythmWeight.filterRhythmWeigths(4.0);
		assertEquals(1 , filteredNotes.size());
		assertEquals(0 , filteredNotes.get(0).getPosition());
	}
	
}

package cp.model.harmony;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.model.dissonance.Dissonance;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class HarmonyExtractorTest {
	
	@Autowired
	@InjectMocks
	private HarmonyExtractor harmonyExtractor;
	@Mock
	private Dissonance dissonance;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testExtractHarmony() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).pc(2).voice(1).positionWeight(6.0).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(8, harmonies.size());
		assertEquals(2, harmonies.get(1).getNotes().size());
		assertEquals(2, harmonies.get(2).getNotes().size());
		assertEquals(60, harmonies.get(2).getNotes().get(0).getPitch());
		assertEquals(58, harmonies.get(2).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(3).getNotes().size());
		assertEquals(2, harmonies.get(4).getNotes().size());
	}
	

	@Test
	public void testMapNotesForPosition() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).positionWeight(6.0).build());
		Map<Integer, List<Note>> map = harmonyExtractor.mapNotesForPosition(notes);
		assertEquals(2, map.get(0).size());
		assertEquals(1, map.get(DurationConstants.QUARTER).size());
		assertEquals(2, map.get(DurationConstants.SIX_EIGHTS).size());
	}
	
	@Test
	public void testExtractHarmonyDifferentStart() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(6).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).pc(2).voice(1).positionWeight(6.0).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(7, harmonies.size());
		assertEquals(60, harmonies.get(0).getNotes().get(0).getPitch());
		assertEquals(64, harmonies.get(0).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(0).getNotes().size());
		assertEquals(2, harmonies.get(1).getNotes().size());
		assertEquals(60, harmonies.get(1).getNotes().get(0).getPitch());
		assertEquals(58, harmonies.get(1).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(2).getNotes().size());
		assertEquals(2, harmonies.get(3).getNotes().size());
	}
	
	
	@Test
	public void testExtractRest() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(61).pc(1).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).build());
		
		notes.add(note().pos(6).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).positionWeight(3.0).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(6, harmonies.size());
		assertEquals(60, harmonies.get(0).getNotes().get(0).getPitch());
		assertEquals(ChordType.CH2_KLEINE_TERTS, harmonies.get(1).getChord().getChordType());
		assertEquals(ChordType.CH2_GROTE_TERTS, harmonies.get(2).getChord().getChordType());
		assertEquals(ChordType.CH2_KLEINE_SECONDE, harmonies.get(3).getChord().getChordType());
	}
	
	
	

}

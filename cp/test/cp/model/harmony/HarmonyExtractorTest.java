package cp.model.harmony;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.model.note.Note;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class HarmonyExtractorTest {
	
	@Autowired
	private HarmonyExtractor harmonyExtractor;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExtractHarmony() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(12).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(24).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(36).pitch(61).pc(1).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(48).pitch(59).pc(11).voice(0).positionWeight(3.0).build());
		notes.add(note().pos(60).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(18).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(36).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
		notes.add(note().pos(48).pitch(59).pc(11).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(96).pitch(62).pc(2).voice(1).positionWeight(6.0).build());
		harmonyExtractor.extractHarmony(notes, 2);
	}

	@Test
	public void testMapNotesForPosition() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(12).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(24).pitch(62).positionWeight(2.0).build());
		notes.add(note().pos(36).pitch(61).positionWeight(4.0).build());
		notes.add(note().pos(48).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(60).pitch(60).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).positionWeight(3.0).build());
		notes.add(note().pos(18).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(36).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(48).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(96).pitch(62).positionWeight(6.0).build());
		Map<Integer, List<Note>> map = harmonyExtractor.mapNotesForPosition(notes);
		assertEquals(2, map.get(0).size());
		assertEquals(1, map.get(12).size());
		assertEquals(2, map.get(36).size());
	}

}

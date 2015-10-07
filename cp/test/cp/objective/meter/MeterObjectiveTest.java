package cp.objective.meter;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class MeterObjectiveTest {
	
	@Autowired
	private MeterObjective meterObjective;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEvaluate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProfileMergedMelodies() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(12).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(24).pitch(62).positionWeight(2.0).build());
		notes.add(note().pos(36).pitch(61).positionWeight(4.0).build());
		notes.add(note().pos(48).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(60).pitch(60).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).positionWeight(3.0).build());
		notes.add(note().pos(18).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(24).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(48).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(96).pitch(62).positionWeight(6.0).build());
		double profileAverage = meterObjective.getProfileMergedMelodiesAverage(notes, 6.0);
		assertEquals(1.0 , profileAverage, 0);
	}

	@Test
	public void testExtractRhythmProfile() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(12).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(24).pitch(62).positionWeight(2.0).build());
		notes.add(note().pos(36).pitch(61).positionWeight(4.0).build());
		notes.add(note().pos(48).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(60).pitch(60).positionWeight(1.0).build());
		
		notes.add(note().pos(24).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(36).pitch(61).positionWeight(2.0).build());
		Map<Integer, Double> profiles = meterObjective.extractRhythmProfile(notes);
		assertEquals(4.0 , profiles.get(0).doubleValue(), 0);
		assertEquals(1.0 , profiles.get(12).doubleValue(), 0);
		assertEquals(3.0 , profiles.get(24).doubleValue(), 0);
		assertEquals(6.0 , profiles.get(36).doubleValue(), 0);
	}

}

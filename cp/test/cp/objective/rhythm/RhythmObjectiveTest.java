package cp.objective.rhythm;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.out.print.ScoreUtilities;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class RhythmObjectiveTest extends JFrame {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RhythmObjectiveTest.class.getName());
	
	@Autowired
	private RhythmObjective rhythmObjective;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private MusicProperties musicProperties;

	@Before
	public void setUp() throws Exception {
		musicProperties.setMinimumLength(12);
	}

	@Test
	public void testGetProfile() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(12).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(24).pitch(62).positionWeight(4.0).build());
		notes.add(note().pos(36).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(48).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(60).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(96).pitch(62).positionWeight(4.0).build());
		CpMelody melody = new CpMelody(notes, 0, 0, 96);
		MelodyBlock melodyBlock = new MelodyBlock(5, 0);
		melodyBlock.addMelodyBlock(melody);
		double profileAverage = rhythmObjective.getProfileAverage(melodyBlock, 3.0, 12);
		assertEquals(0.75 , profileAverage, 0);
	}

}

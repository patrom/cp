package cp.objective.rhythm;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.RhythmWeight;
import cp.objective.meter.InnerMetricWeight;
import cp.objective.meter.InnerMetricWeightFunctions;
import cp.objective.meter.InnerMetricWeightTest;
import cp.out.print.ScoreUtilities;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
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
	public void testEvaluate() {
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
		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		double profileAverage = rhythmObjective.getProfileAverage(melody, 3.0, 12);
		assertEquals(0.75 , profileAverage, 0);
	}

}

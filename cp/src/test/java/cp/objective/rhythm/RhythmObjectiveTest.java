package cp.objective.rhythm;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.composition.timesignature.TimeConfig;
import cp.composition.voice.MelodyVoice;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.print.ScoreUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class RhythmObjectiveTest extends JFrame {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RhythmObjectiveTest.class.getName());
	
	@Autowired
	@InjectMocks
	private RhythmObjective rhythmObjective;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;
	@Mock
	private Composition composition;
	@Autowired
	private MelodyVoice melodyVoice;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetProfile() {
		Mockito.when(composition.getVoiceConfiguration(Mockito.anyInt())).thenReturn(melodyVoice);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).positionWeight(4.0).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE * 2);
		MelodyBlock melodyBlock = new MelodyBlock(5, 0);
		melodyBlock.addMelodyBlock(melody);
		double profileAverage = rhythmObjective.getProfileAverage(melodyBlock);
		assertEquals(0.75 , profileAverage, 0);
	}

}

package cp.objective.rhythm;

import cp.DefaultConfig;
import cp.composition.timesignature.TimeConfig;
import cp.composition.voice.MelodyVoice;
import cp.config.VoiceConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.print.ScoreUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class RhythmObjectiveTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RhythmObjectiveTest.class.getName());
	
	@Autowired
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
	@MockBean
	private VoiceConfig voiceConfig;
	@Autowired
	private MelodyVoice melodyVoice;
	
	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testGetProfile() {
		Mockito.when(voiceConfig.getVoiceConfiguration(Mockito.anyInt())).thenReturn(melodyVoice);
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

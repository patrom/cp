package cp.evaluation;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.Composition;
import cp.composition.timesignature.TimeConfig;
import cp.composition.voice.MelodyVoice;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.strings.ViolinSolo;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class FitnessEvaluationTemplateTest extends JFrame{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FitnessEvaluationTemplateTest.class);
	
	@Autowired
	@InjectMocks
	private FitnessEvaluationTemplate fitnessEvaluationTemplate;
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;
	
	private List<MelodyBlock> melodies;
	@Mock
	private Composition composition;
	@Autowired
	private MelodyVoice melodyVoice;

	@Before
	public void setUp() throws Exception {
		melodies = new ArrayList<>();
		MockitoAnnotations.initMocks(this);
		Mockito.when(composition.getVoiceConfiguration(Mockito.anyInt())).thenReturn(melodyVoice);
	}

	@Test
	public void testEvaluate() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.HALF).pc(2).pitch(62).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(4).pitch(64).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(5).pitch(65).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(2).pitch(62).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pc(7).pitch(67).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}

	protected void evaluate() {
		melodies.forEach(m -> m.setInstrument(new ViolinSolo()));
		Motive motive = new Motive(melodies);
		FitnessObjectiveValues objectives = fitnessEvaluationTemplate.evaluate(motive);
		LOGGER.info(objectives.toString());
//		Score score = new Score();
//		for (MelodyBlock cpMelody : melodies) {
//			Phrase phrase = scoreUtilities.createPhrase(cpMelody.getMelodyBlockNotesWithRests());	
//			Part part = new Part(phrase);
//			score.add(part);
//		}
//		View.notate(score);
//		Play.midi(score, true);
	}
	
	@Test
	public void testPassingNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).pitch(62).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(4).pitch(52).build());
		notes.add(note().pos(DurationConstants.HALF).pc(0).pitch(48).build());
		melody = new CpMelody(notes, 0, 0 , 60);
		melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}
	
	@Test
	public void testPassingNoteOnBeat() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(2).pitch(62).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(4).pitch(52).build());
		notes.add(note().pos(DurationConstants.HALF).pc(0).pitch(48).build());
		melody = new CpMelody(notes, 0, 0 , 60);
		melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}
	
	@Test
	public void testNeigborNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(4).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(0).build());
		melody = new CpMelody(notes, 0, 0 , 60);
		melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}
	
	@Test
	public void testSuspension() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(72).build());
		notes.add(note().pos(DurationConstants.HALF).pc(0).pitch(72).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(11).pitch(71).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(0).pitch(72).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(5).pitch(65).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(4).pitch(64).build());
		melody = new CpMelody(notes, 0, 0 , 60);
		melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}
	
	@Test
	public void testAppoggiatura() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(2).pitch(62).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(9).pitch(69).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(11).pitch(59).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).build());
		melody = new CpMelody(notes, 0, 0 , 60);
		melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}
	
	@Test
	public void test() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(11).pitch(71).build());
		notes.add(note().pos(6).pc(9).pitch(69).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(7).pitch(67).build());
//		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(0).pitch(60).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(65).build());
//		notes.add(note().pos(30).pc(4).pitch(64).build());
//		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(4).pitch(64).build());
//		notes.add(note().pos(42).pc(0).pitch(60).build());
//		notes.add(note().pos(DurationConstants.WHOLE).pc(5).pitch(65).len(DurationConstants.EIGHT).build());

		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).build());
		notes.add(note().pos(6).pc(2).pitch(50).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(52).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(53).build());
//		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(7).pitch(55).build());
//		notes.add(note().pos(42).pc(11).pitch(59).build());
//		notes.add(note().pos(DurationConstants.WHOLE).pc(9).pitch(57).len(DurationConstants.EIGHT).build());
//		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(0).pitch(48).build());
		melody = new CpMelody(notes, 0, 0 , 60);
		melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}
	
	@Test
	public void testMelody() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(11).pitch(71).build());
		notes.add(note().pos(6).pc(0).pitch(72).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(7).pitch(67).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(9).pitch(69).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).build());
		notes.add(note().pos(30).pc(4).pitch(64).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(4).pitch(64).build());
		notes.add(note().pos(42).pc(2).pitch(62).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(5).pitch(65).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		
		evaluate();
	}
	
}

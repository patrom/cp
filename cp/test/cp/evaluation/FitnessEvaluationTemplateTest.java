package cp.evaluation;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.Ensemble;
import cp.out.print.ScoreUtilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class FitnessEvaluationTemplateTest extends JFrame{
	
	private static Logger LOGGER = LoggerFactory.getLogger(FitnessEvaluationTemplateTest.class);
	
	@Autowired
	private FitnessEvaluationTemplate fitnessEvaluationTemplate;
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private ScoreUtilities scoreUtilities;
	
	private List<CpMelody> melodies;

	@Before
	public void setUp() throws Exception {
		musicProperties.setInstruments(Ensemble.getPiano(4));
		melodies = new ArrayList<>();
	}

	@Test
	public void testEvaluate() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(12).pc(0).pitch(60).build());
		notes.add(note().pos(24).pc(2).pitch(62).build());
		notes.add(note().pos(36).pc(4).pitch(64).build());
		notes.add(note().pos(48).pc(5).pitch(65).build());
		notes.add(note().pos(60).pc(2).pitch(62).build());
		notes.add(note().pos(96).pc(7).pitch(67).build());
		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melodies.add(melody);
		
		evaluate();
	}

	protected void evaluate() {
		Motive motive = new Motive(melodies);
		FitnessObjectiveValues objectives = fitnessEvaluationTemplate.evaluate(motive);
		LOGGER.info(objectives.toString());
		Score score = new Score();
		for (CpMelody cpMelody : melodies) {
			Phrase phrase = scoreUtilities.createPhrase(cpMelody.getNotes());	
			Part part = new Part(phrase);
			score.add(part);
		}
		View.notate(score);
		Play.midi(score, true);
	}
	
	@Test
	public void testPassingNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(12).pc(2).pitch(62).build());
		notes.add(note().pos(24).pc(4).pitch(64).build());
		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melodies.add(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(4).pitch(52).build());
		notes.add(note().pos(24).pc(0).pitch(48).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melodies.add(melody);
		
		evaluate();
	}
	
	@Test
	public void testPassingNoteOnBeat() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(2).pitch(62).build());
		notes.add(note().pos(12).pc(0).pitch(60).build());
		notes.add(note().pos(24).pc(4).pitch(64).build());
		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melodies.add(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(4).pitch(52).build());
		notes.add(note().pos(24).pc(0).pitch(48).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melodies.add(melody);
		
		evaluate();
	}
	
	@Test
	public void testNeigborNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(4).build());
		notes.add(note().pos(12).pc(5).build());
		notes.add(note().pos(24).pc(4).build());
		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melodies.add(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(24).pc(0).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melodies.add(melody);
		
		evaluate();
	}
	
	@Test
	public void testSuspension() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(72).build());
		notes.add(note().pos(24).pc(0).pitch(72).build());
		notes.add(note().pos(36).pc(11).pitch(71).build());
		notes.add(note().pos(48).pc(0).pitch(72).build());
		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melodies.add(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(5).pitch(65).build());
		notes.add(note().pos(24).pc(7).pitch(67).build());
		notes.add(note().pos(48).pc(4).pitch(64).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melodies.add(melody);
		
		evaluate();
	}
	
	@Test
	public void testAppoggiatura() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(2).pitch(62).build());
		notes.add(note().pos(12).pc(9).pitch(69).build());
		notes.add(note().pos(24).pc(7).pitch(67).build());
		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melodies.add(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(11).pitch(59).build());
		notes.add(note().pos(24).pc(4).pitch(64).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melodies.add(melody);
		
		evaluate();
	}
	
	@Test
	public void testRandom(){
//		for (int i = 0; i < 2; i++) {
			CpMelody melody = melodyGenerator.generateMelody(Scale.MAJOR_SCALE, new int[]{0,60}, 6, 1);
			melody.updatePitches(5);
			melodies.add(melody);
			
//			List<Note> notes = new ArrayList<>();
//			notes.add(note().pos(0).pc(0).pitch(48).build());
//			notes.add(note().pos(12).pc(2).pitch(50).build());
//			notes.add(note().pos(24).pc(5).pitch(53).build());
//			notes.add(note().pos(36).pc(7).pitch(55).build());
//			notes.add(note().pos(48).pc(0).pitch(48).build());
//			CpMelody melody2 = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
//			melody2.setMutable(false);
//			melody2.setRhythmMutable(false);
			CpMelody melody2 = melodyGenerator.generateMelody(Scale.MAJOR_SCALE, new int[]{0,60}, 6, 0);
			melody2.updatePitches(4);
			melodies.add(melody2);
//		}
			evaluate();
	}
	
	@Test
	public void test() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(11).pitch(71).build());
		notes.add(note().pos(6).pc(9).pitch(69).build());
		notes.add(note().pos(12).pc(7).pitch(67).build());
//		notes.add(note().pos(18).pc(0).pitch(60).build());
		notes.add(note().pos(24).pc(5).pitch(65).build());
//		notes.add(note().pos(30).pc(4).pitch(64).build());
//		notes.add(note().pos(36).pc(4).pitch(64).build());
//		notes.add(note().pos(42).pc(0).pitch(60).build());
//		notes.add(note().pos(48).pc(5).pitch(65).len(6).build());

		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melodies.add(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).build());
		notes.add(note().pos(6).pc(2).pitch(50).build());
		notes.add(note().pos(12).pc(4).pitch(52).build());
		notes.add(note().pos(24).pc(5).pitch(53).build());
//		notes.add(note().pos(36).pc(7).pitch(55).build());
//		notes.add(note().pos(42).pc(11).pitch(59).build());
//		notes.add(note().pos(48).pc(9).pitch(57).len(6).build());
//		notes.add(note().pos(60).pc(0).pitch(48).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melodies.add(melody);
		
		evaluate();
	}
	
}

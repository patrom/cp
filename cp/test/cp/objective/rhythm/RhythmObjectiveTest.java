package cp.objective.rhythm;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.generator.MelodyGenerator;
import cp.model.Motive;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.objective.meter.InnerMetricWeight;
import cp.objective.meter.InnerMetricWeightFunctions;
import cp.objective.meter.InnerMetricWeightTest;
import cp.out.print.ScoreUtilities;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class RhythmObjectiveTest extends JFrame {
	
	private static Logger LOGGER = Logger.getLogger(RhythmObjectiveTest.class.getName());
	
	@Autowired
	private RhythmObjective rhythmObjective;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MelodyGenerator melodyGenerator;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEvaluate() {
	}

	@Test
	public void testGetProfile() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).build());
		notes.add(note().pos(12).pitch(60).build());
		notes.add(note().pos(24).pitch(62).build());
		notes.add(note().pos(36).pitch(61).build());
		notes.add(note().pos(48).pitch(59).build());
		notes.add(note().pos(60).pitch(60).build());
		notes.add(note().pos(96).pitch(62).build());
		double profileAverage = rhythmObjective.getProfileAverage(notes, 3.0);
		assertEquals(1.0 , profileAverage, 0);
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
		notes.add(note().pos(36).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(48).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(96).pitch(62).positionWeight(6.0).build());
		double profileAverage = rhythmObjective.getProfileMergedMelodiesAverage(notes, 6.0);
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
		Map<Integer, Double> profiles = rhythmObjective.extractRhythmProfile(notes);
		assertEquals(4.0 , profiles.get(0).doubleValue(), 0);
		assertEquals(1.0 , profiles.get(12).doubleValue(), 0);
		assertEquals(3.0 , profiles.get(24).doubleValue(), 0);
		assertEquals(6.0 , profiles.get(36).doubleValue(), 0);
	}
	
	@Test
	public void testGenerateMelodies() {
		List<Note> chordNotes = new ArrayList<Note>();
		chordNotes.add(NoteBuilder.note().pitch(60).build());
		chordNotes.add(NoteBuilder.note().pitch(65).build());
		int[] harmony = {0, 192};
		int max = 32;
		
		for (int i = 0; i < 100000; i++) {
			System.out.println("loop: " + i);
			int[] rhythmPattern = melodyGenerator.generateMelodyPositions(harmony, 6, max);
//			int[] rhythmPattern = new int[]{0, 24, 48, 72, 96};
			List<Note> notesVoice1 = melodyGenerator.generateMelodyChordNotes(rhythmPattern, chordNotes);
			
			double profileAverage1 = rhythmObjective.getProfileAverage(notesVoice1, 2.0);
			System.out.println(profileAverage1);
			
			int[] rhythmPattern2 = melodyGenerator.generateMelodyPositions(harmony, 6, max);
//			int[] rhythmPattern2 = new int[]{0, 12, 24, 36, 48, 60, 72, 84, 96};
			List<Note> notesVoice2 = melodyGenerator.generateMelodyChordNotes(rhythmPattern2, chordNotes);
			double profileAverage2 = rhythmObjective.getProfileAverage(notesVoice2, 2.0);
			System.out.println(profileAverage2);
			
			List<Note> mergedMelodies = new ArrayList<>();
			mergedMelodies.addAll(notesVoice1);
			mergedMelodies.addAll(notesVoice2);
			double profileAverage = rhythmObjective.getProfileMergedMelodiesAverage(mergedMelodies, 4.0);
			System.out.println(profileAverage);
			
			double total = (profileAverage1 + profileAverage2 + (profileAverage * 2 )) / 4;
			
			if (total > 0.8) {
				Score score = new Score();
				Phrase phrase = scoreUtilities.createPhrase(notesVoice1);	
				Part part = new Part(phrase);
				score.add(part);
				notesVoice2.forEach(n -> n.setPitch(n.getPitch() - 12));
				Phrase phrase2 = scoreUtilities.createPhrase(notesVoice2);	
				Part part2 = new Part(phrase2);
				score.add(part2);
				score.setTempo(120);
				View.notate(score);
				Play.midi(score, true);
				break;
			}
		}
		
		
		
		
	}
	
	protected List<Note> getSounds(int harmonyPosition, Integer[] positions) {
		List<Note> sounds = new ArrayList<>();
		for (int i = 0; i < positions.length - 1; i++) {
			Note note = new Note();
			note.setPosition(positions[i] + harmonyPosition);
			note.setLength(positions[i + 1] - positions[i]);
			sounds.add(note);
		}
		return sounds;
	}

}

package cp.generator;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.objective.melody.MelodicObjective;
import cp.objective.rhythm.RhythmObjective;
import cp.out.print.ScoreUtilities;
import cp.variation.Embellisher;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class MelodyGeneratorTest extends JFrame{
	
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private Embellisher embellisher;
	@Autowired
	private RhythmObjective rhythmObjective;
	@Autowired
	private MelodicObjective melodicObjective;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGenerateMelody() {
		int[] harmony = {0, 48};
		int max = 4;
		int[] positions = melodyGenerator.generateMelodyPositions(harmony, 12, max);
		System.out.println(Arrays.toString(positions));
	}
	
	@Test
	public void testGenerateMelodyNotes() {
		int[] beginEndPosition = {0, 96};
		int minimumNoteValue = 6;
		List<Note> scaleNotes = new ArrayList<>();
		scaleNotes.add(NoteBuilder.note().pc(0).build());
		scaleNotes.add(NoteBuilder.note().pc(2).build());
		scaleNotes.add(NoteBuilder.note().pc(4).build());
		scaleNotes.add(NoteBuilder.note().pc(5).build());
		scaleNotes.add(NoteBuilder.note().pc(7).build());
		scaleNotes.add(NoteBuilder.note().pc(9).build());
		scaleNotes.add(NoteBuilder.note().pc(11).build());
		
		List<Note> melodyNotes = melodyGenerator.generateMelody(scaleNotes, beginEndPosition, minimumNoteValue);
		melodyNotes.forEach(note -> note.setPitch(note.getPitchClass() + 60));
		Score score = scoreUtilities.createMelody(melodyNotes);
		View.notate(score);
		Play.midi(score, true);
	}
	
	@Test
	public void testGenerate() {
		int[] harmony = {0, 48};
		int max = 8;
		int[] positions = melodyGenerator.generateMelodyPositions(harmony, 6, max);
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(NoteBuilder.note().pc(0).voice(3).build());
		chordNotes.add(NoteBuilder.note().pc(4).voice(3).build());
		chordNotes.add(NoteBuilder.note().pc(7).voice(3).build());
		List<Note> melodyChordNotes = melodyGenerator.generateMelodyChordNotes(positions, chordNotes);
		for (Note note : melodyChordNotes) {
			note.setPitch(note.getPitchClass() + 60);
		}
		System.out.println(melodyChordNotes);
		List<Note> embellishedNotes = embellisher.embellish(melodyChordNotes);
		Score score = new Score();
		Phrase phrase = scoreUtilities.createPhrase(melodyChordNotes);	
		Part part = new Part(phrase);
		score.add(part);
		phrase = scoreUtilities.createPhrase(embellishedNotes);	
		part = new Part(phrase);
		score.add(part);
		View.notate(score);
		Play.midi(score, true);
		
	}

}

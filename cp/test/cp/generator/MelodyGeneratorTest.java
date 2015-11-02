package cp.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
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
import cp.midi.MidiDevicesUtil;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.objective.melody.MelodicObjective;
import cp.objective.rhythm.RhythmObjective;
import cp.out.instrument.Articulation;
import cp.out.instrument.KontaktLibFlute;
import cp.out.instrument.KontaktLibViolin;
import cp.out.instrument.MidiDevice;
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
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;

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
	public void testGenerateMelodyNotes() throws InvalidMidiDataException, InterruptedException {
		int[] beginEndPosition = {12, 96};
		int minimumNoteValue = 6;
		
//		CpMelody melody = melodyGenerator.generateMelody(Scale.MAJOR_SCALE, beginEndPosition, minimumNoteValue, 0);
//		melody.updatePitches(6);
//		Score score = scoreUtilities.createMelody(melody.getNotes());
//		View.notate(score);
//		Play.midi(score, true);
		
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().len(12).pc(4).pitch(64).ocatve(4).pos(0).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(24).pc(2).pitch(62).ocatve(4).pos(12).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(24).pc(4).pitch(64).ocatve(4).pos(36).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(6).pc(5).pitch(65).ocatve(4).pos(60).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(6).pc(7).pitch(67).ocatve(4).pos(66).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(12).pc(9).pitch(69).ocatve(4).pos(72).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(24).pc(11).pitch(71).ocatve(4).pos(84).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(24).pc(0).pitch(60).ocatve(4).pos(108).art(Articulation.STACCATO).build());
//		notes.forEach(note -> note.setArticulation(Articulation.STACCATO));
		Score score = scoreUtilities.createMelody(notes);
		View.notate(score);
		Sequence seq = midiDevicesUtil.createSequenceNotes(notes, new  KontaktLibViolin(3, 0));
		midiDevicesUtil.playOnDevice(seq, 60, MidiDevice.KONTAKT);
		Thread.sleep(10000);
	}
	
	@Test
	public void testGenerate() {
		int[] harmony = {0, 48};
		int max = 8;
		int[] positions = melodyGenerator.generateMelodyPositions(harmony, 6, max);
		List<Note> melodyNotes = melodyGenerator.generateMelodyNotes(positions, Scale.MAJOR_SCALE.getPitchClasses());
		melodyNotes.forEach(note -> note.setPitch(note.getPitchClass() + 60));
		System.out.println(melodyNotes);
		Score score = new Score();
		Phrase phrase = scoreUtilities.createPhrase(melodyNotes);	
		Part part = new Part(phrase);
		score.add(part);
		View.notate(score);
		Play.midi(score, true);
	}
	
	@Test
	public void testGenerateMelodyTactus() {
		CpMelody melody = melodyGenerator.generateMelodyTactus(Scale.MAJOR_SCALE, new int[]{0,48}, 12, 0, 5);
		melody.updatePitchesFromContour();
		List<Note> melodyNotes = melody.getNotes();
//		melodyNotes.forEach(note -> note.setPitch(note.getPitchClass() + 60));
		System.out.println(melodyNotes);
		Score score = new Score();
		Phrase phrase = scoreUtilities.createPhrase(melodyNotes);	
		Part part = new Part(phrase);
		score.add(part);
		View.notate(score);
		Play.midi(score, true);
	}
	
	@Test
	public void testGeneratePositions() {
		List<Note> melodyNotes = melodyGenerator.generatePositions(0, 12, 6, new Integer[]{1,1});
//		melodyNotes.forEach(note -> note.setPitch(note.getPitchClass() + 60));
		melodyNotes.forEach(n -> System.out.print(n.getPosition()  + ", "));
//		Score score = new Score();
//		Phrase phrase = scoreUtilities.createPhrase(melodyNotes);	
//		Part part = new Part(phrase);
//		score.add(part);
//		View.notate(score);
//		Play.midi(score, true);
	}
}

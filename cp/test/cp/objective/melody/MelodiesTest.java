package cp.objective.melody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.InvalidMidiDataException;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.evaluation.FitnessEvaluationTemplate;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;
import cp.midi.MidiConverter;
import cp.midi.MidiInfo;
import cp.midi.MidiParser;
import cp.model.dissonance.Dissonance;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.Rhythm;
import cp.model.rhythm.RhythmWeight;
import cp.objective.rhythm.RhythmObjective;
import cp.out.print.ScoreUtilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
@Ignore
public class MelodiesTest extends AbstractTest{
	
	private static Logger LOGGER = LoggerFactory.getLogger(FitnessEvaluationTemplate.class.getName());
	
	private List<File> midiFiles;
	@Autowired
	private MidiParser midiParser;
	@Autowired
	@Qualifier(value="TonalDissonance")
	private Dissonance dissonance;
	@Autowired
	private MelodicObjective melodicObjective;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private RhythmWeight rhythmWeight;
	@Autowired
	private RhythmObjective rhythmObjective;
	@Autowired
	private MelodyGenerator melodyGenerator;
	
	@Before
	public void setUp() throws IOException, InvalidMidiDataException {
		
	}

	@Test
	public void testMelodies() throws InvalidMidiDataException, IOException {
		midiFiles = Files.list(new File(MelodiesTest.class.getResource("/melodies").getPath()).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			MidiInfo midiInfo = midiParser.readMidi(file);
			LOGGER.debug(file.getName());
			List<MelodyInstrument> melodies = midiInfo.getMelodies();
			MidiConverter.updatePositionNotes(melodies, midiInfo.getTimeSignature());
			for (MelodyInstrument melodyInstrument : melodies) {
				List<Note> notes = melodyInstrument.getNotes();
				double value = melodicObjective.evaluateMelody(notes, 1);
				LOGGER.info("Intervals : " + value);
				value = melodicObjective.evaluateTriadicValueMelody(notes);
				LOGGER.info("Triadic value: " + value);
				List<Note> filteredNotes = melodicObjective.filterNotesWithWeightEqualToOrGreaterThan(notes, 0.5);
				double filteredValue = melodicObjective.evaluateMelody(filteredNotes, 1);
				LOGGER.info("filteredValue : " + filteredValue);
				List<Note> notesLevel2 = melodicObjective.extractNotesOnLevel(notes, 2);
				LOGGER.info("notesLevel2 : " + notesLevel2);
//				Score score = scoreUtilities.createScoreFromMelodyInstrument(melodies, (double) midiInfo.getTempo());
//				View.notate(score);
//				jm.util.Play.midi(score, false);
			}
		}
	}
	
	@Test
	public void readMelodies() throws InvalidMidiDataException, IOException {
		midiFiles = Files.list(new File(MelodiesTest.class.getResource("/melodies/solo").getPath()).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			MidiInfo midiInfo = midiParser.readMidi(file);
			LOGGER.info(file.getName());
			List<MelodyInstrument> melodies = midiInfo.getMelodies();
			List<Note> notes = melodies.get(0).getNotes();
			rhythmWeight.setNotes(notes);
			rhythmWeight.updateRhythmWeight();
			double value = melodicObjective.evaluateMelody(notes, 1);
			LOGGER.info("Intervals : " + value);
			double value2 = melodicObjective.evaluateMelody(notes, 2);
			LOGGER.info("Intervals 2: " + value2);
			
			List<Note> crestNotes = melodicObjective.filterCrestNotes(notes);
			double crestValue = melodicObjective.evaluateMelody(crestNotes, 1);
			LOGGER.info("crestValue : " + crestValue);
			
			List<Note> keelNotes = melodicObjective.filterKeelNotes(notes);
			double keelValue = melodicObjective.evaluateMelody(keelNotes, 1);
			LOGGER.info("keelValue : " + keelValue);
			
			List<Note> filteredNotes = rhythmWeight.filterRhythmWeigths(3.0);
			double filtered = melodicObjective.evaluateMelody(filteredNotes, 1);
			LOGGER.info("filtered : " + filtered);
			CpMelody melody = new CpMelody(notes, 0, 0, 200);
			MelodyBlock melodyBlock = new MelodyBlock(5,0);
			melodyBlock.addMelodyBlock(melody);
			double profile = rhythmObjective.getProfileAverage(melodyBlock, 3.0, 12);
			LOGGER.info("profile 12: " + profile);
			double profile2 = rhythmObjective.getProfileAverage(melodyBlock, 3.0, 6);
			LOGGER.info("profile 6: " + profile2);
			double profile3 = rhythmObjective.getProfileAverage(melodyBlock, 3.0, 24);
			LOGGER.info("profile 24: " + profile3);
			Score score = scoreUtilities.createScoreFromMelodyInstrument(melodies, (double) midiInfo.getTempo());
			View.notate(score);
			jm.util.Play.midi(score, true);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void generateMelodies() throws InvalidMidiDataException, IOException {
		for (int i = 0; i < 10; i++) {
			CpMelody melody = melodyGenerator.generateMelody(0, 0, 12);
			MelodyBlock melodyBlock = new MelodyBlock(5,0);
			melodyBlock.addMelodyBlock(melody);
			List<Note> notes = melody.getNotes();
			notes.forEach(n -> n.setPitch(n.getPitchClass() + 60));
			rhythmWeight.setNotes(notes);
			rhythmWeight.updateRhythmWeight();
			double value = melodicObjective.evaluateMelody(notes, 1);
			LOGGER.info("Intervals : " + value);
			List<Note> filteredNotes = rhythmWeight.filterRhythmWeigths(3.0);
			double filtered = melodicObjective.evaluateMelody(filteredNotes, 1);
			LOGGER.info("filtered : " + filtered);
			double profile = rhythmObjective.getProfileAverage(melodyBlock, 3.0, 12);
			LOGGER.info("profile : " + profile);
			Phrase phrase = scoreUtilities.createPhrase(notes);
			Score score = new Score();
			Part part = new Part();
			part.add(phrase);
			score.add(part);
			View.notate(score);
			jm.util.Play.midi(score, true);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	

}

package cp.objective.melody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.sound.midi.InvalidMidiDataException;

import jm.music.data.Score;
import jm.util.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.evaluation.FitnessEvaluationTemplate;
import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;
import cp.midi.MidiConverter;
import cp.midi.MidiInfo;
import cp.midi.MidiParser;
import cp.model.dissonance.Dissonance;
import cp.model.note.Note;
import cp.out.print.ScoreUtilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class MelodiesTest extends AbstractTest{
	
	private static Logger LOGGER = Logger.getLogger(FitnessEvaluationTemplate.class.getName());
	
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
	
	@Before
	public void setUp() throws IOException, InvalidMidiDataException {
		
	}

	@Test
	public void testMelodies() throws InvalidMidiDataException, IOException {
		midiFiles = Files.list(new File(MelodiesTest.class.getResource("/melodies").getPath()).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			MidiInfo midiInfo = midiParser.readMidi(file);
			LOGGER.fine(file.getName());
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
			LOGGER.fine(file.getName());
			List<MelodyInstrument> melodies = midiInfo.getMelodies();
			Score score = scoreUtilities.createScoreFromMelodyInstrument(melodies, (double) midiInfo.getTempo());
			View.notate(score);
			jm.util.Play.midi(score, true);
		}
	}

}

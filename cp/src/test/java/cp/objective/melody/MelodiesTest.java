package cp.objective.melody;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.combination.RhythmCombination;
import cp.composition.voice.MelodyVoice;
import cp.config.VoiceConfig;
import cp.evaluation.FitnessEvaluationTemplate;
import cp.generator.MelodyGenerator;
import cp.midi.MidiParser;
import cp.model.dissonance.Dissonance;
import cp.model.rhythm.RhythmWeight;
import cp.objective.rhythm.RhythmObjective;
import cp.out.print.ScoreUtilities;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
@Ignore
public class MelodiesTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FitnessEvaluationTemplate.class.getName());
	
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
	@Resource(name="defaultUnevenCombinations")
	private List<RhythmCombination> defaultUnEvenCombinations;
	@MockBean
	private VoiceConfig voiceConfig;
	@Autowired
	private MelodyVoice melodyVoice;
	@Autowired
	private MelodyDefaultDissonance melodyDefaultDissonance;

	@BeforeEach
	public void setUp() throws Exception {
		Mockito.when(voiceConfig.getVoiceConfiguration(Mockito.anyInt())).thenReturn(melodyVoice);
	}

//	@Test
//	public void testMelodies() throws InvalidMidiDataException, IOException {
//		midiFiles = Files.list(new File(MelodiesTest.class.getResource("/melodies").getPath()).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
//		for (File file : midiFiles) {
//			MidiInfo midiInfo = midiParser.readMidi(file);
//			LOGGER.debug(file.getName());
//			List<MelodyInstrument> melodies = midiInfo.getMelodies();
//			MidiConverter.updatePositionNotes(melodies, midiInfo.getTimeSignature());
//			for (MelodyInstrument melodyInstrument : melodies) {
//				List<Note> notes = melodyInstrument.getNotes();
//				double value = melodicObjective.evaluateMelody(notes, 1, melodyDefaultDissonance);
//				LOGGER.info("Intervals : " + value);
//				value = melodicObjective.evaluateTriadicValueMelody(notes);
//				LOGGER.info("Triadic value: " + value);
//				List<Note> filteredNotes = melodicObjective.filterNotesWithWeightEqualToOrGreaterThan(notes, 0.5);
//				double filteredValue = melodicObjective.evaluateMelody(filteredNotes, 1, melodyDefaultDissonance);
//				LOGGER.info("filteredValue : " + filteredValue);
//				List<Note> notesLevel2 = melodicObjective.extractNotesOnLevel(notes, 2);
//				LOGGER.info("notesLevel2 : " + notesLevel2);
////				Score score = scoreUtilities.createScoreFromMelodyInstrument(melodies, (double) midiInfo.getTempo());
////				View.notate(score);
////				jm.util.Play.midi(score, false);
//			}
//		}
//	}
	
//	@Test
//    @Ignore
//	public void readMelodies() throws InvalidMidiDataException, IOException {
//		midiFiles = Files.list(new File(MelodiesTest.class.getResource("/melodies/solo").getPath()).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
//		for (File file : midiFiles) {
//			MidiInfo midiInfo = midiParser.readMidi(file);
//			LOGGER.info(file.getName());
//			List<MelodyInstrument> melodies = midiInfo.getMelodies();
//			List<Note> notes = melodies.get(0).getNotes();
//			rhythmWeight.setNotes(notes);
//			rhythmWeight.updateRhythmWeight();
//			double value = melodicObjective.evaluateMelody(notes, 1, melodyDefaultDissonance);
//			LOGGER.info("Intervals : " + value);
//			double value2 = melodicObjective.evaluateMelody(notes, 2, melodyDefaultDissonance);
//			LOGGER.info("Intervals 2: " + value2);
//
//			List<Note> crestNotes = melodicObjective.filterCrestNotes(notes);
//			double crestValue = melodicObjective.evaluateMelody(crestNotes, 1, melodyDefaultDissonance);
//			LOGGER.info("crestValue : " + crestValue);
//
//			List<Note> keelNotes = melodicObjective.filterKeelNotes(notes);
//			double keelValue = melodicObjective.evaluateMelody(keelNotes, 1, melodyDefaultDissonance);
//			LOGGER.info("keelValue : " + keelValue);
//
//			List<Note> filteredNotes = rhythmWeight.filterRhythmWeigths(3.0);
//			double filtered = melodicObjective.evaluateMelody(filteredNotes, 1, melodyDefaultDissonance);
//			LOGGER.info("filtered : " + filtered);
//			CpMelody melody = new CpMelody(notes, 0, 0, 200);
//			MelodyBlock melodyBlock = new MelodyBlock(5,0);
//			melodyBlock.addMelodyBlock(melody);
//			double profile = rhythmObjective.getProfileAverage(melodyBlock);//quarter
//			LOGGER.info("profile 12: " + profile);
//			double profile2 = rhythmObjective.getProfileAverage(melodyBlock);//eigth
//			LOGGER.info("profile 6: " + profile2);
//			double profile3 = rhythmObjective.getProfileAverage(melodyBlock); //half
//			LOGGER.info("profile 24: " + profile3);
//			Score score = scoreUtilities.createScoreFromMelodyInstrument(melodies, (double) midiInfo.getTempo());
//			View.notate(score);
//			jm.util.Play.midi(score, true);
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}

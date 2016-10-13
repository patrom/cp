package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombination;
import cp.composition.Composition;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupStrategy;
import cp.composition.beat.BeatGroupTwo;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.midi.MidiDevicesUtil;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import cp.out.instrument.MidiDevice;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.print.ScoreUtilities;
import cp.out.print.note.Key;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class MelodyGeneratorTest extends JFrame{
	
	private static Logger LOGGER = LoggerFactory.getLogger(MelodyGeneratorTest.class);
	
	@Autowired
	@InjectMocks
	private MelodyGenerator melodyGenerator;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	@Autowired
	private Key C;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private RandomPitchClasses randomPitchClasses;
	@Mock
	private PitchClassGenerator pitchClassGenerator;
	@Mock
	private Composition compostion;
	@Mock
	private BeatGroupStrategy beatGroupStrategy;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;
	@Resource(name = "fixedEven")
	private List<RhythmCombination> fixedEven;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		musicProperties.setKey(C);
		melodyGenerator.setPitchClassGenerator(randomPitchClasses::randomPitchClasses);
	}

	@Test
	public void testGenerateMelody() {
		int[] harmony = {0, DurationConstants.WHOLE};
		int max = 4;
		int[] positions = melodyGenerator.generateMelodyPositions(harmony, DurationConstants.QUARTER, max);
		System.out.println(Arrays.toString(positions));
	}
	
	@Test
	public void testGenerateMelodyNotes() throws InvalidMidiDataException, InterruptedException {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(4).pos(0).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(2).pitch(62).ocatve(4).pos(DurationConstants.QUARTER).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(4).pitch(64).ocatve(4).pos(DurationConstants.HALF + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.EIGHT).pc(5).pitch(65).ocatve(4).pos(DurationConstants.WHOLE + DurationConstants.QUARTER).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.EIGHT).pc(7).pitch(67).ocatve(4).pos(DurationConstants.WHOLE + DurationConstants.THREE_EIGHTS).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(9).pitch(69).ocatve(4).pos(DurationConstants.WHOLE + DurationConstants.HALF).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(11).pitch(71).ocatve(4).pos(DurationConstants.WHOLE + DurationConstants.SIX_EIGHTS).art(Articulation.LEGATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(0).pitch(60).ocatve(4).pos(2 * DurationConstants.WHOLE + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
//		notes.forEach(note -> note.setArticulation(Articulation.STACCATO));
		Score score = scoreUtilities.createMelody(notes);
		View.notate(score);
		Sequence seq = midiDevicesUtil.createSequenceNotes(notes, new  ViolinSolo(3, 0));
		midiDevicesUtil.playOnDevice(seq, 60, MidiDevice.KONTAKT);
		Thread.sleep(10000);
	}
	
	@Test
	public void testGenerate() {
		int[] harmony = {0, DurationConstants.WHOLE};
		int max = 8;
		int[] positions = melodyGenerator.generateMelodyPositions(harmony, DurationConstants.EIGHT, max);
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
	public void testGenerateMelodyBlock() {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pos(0).build());
		when(compostion.getStart()).thenReturn(0);
		when(compostion.getEnd()).thenReturn(DurationConstants.WHOLE);
		when(compostion.getTimeConfig()).thenReturn(time44);
		List<BeatGroup> beatGroups = new ArrayList<>();
		beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, fixedEven));
		when(beatGroupStrategy.getBeatGroups()).thenReturn(beatGroups);
		when(pitchClassGenerator.updatePitchClasses(notes)).thenReturn(notes);
		List<Integer> beats = new ArrayList<>();
		beats.add(12);
		MelodyBlock melody = melodyGenerator.generateMelodyBlock(1, 5);
		assertEquals(1, melody.getVoice());
		assertEquals(2, melody.getMelodyBlocks().size());
	}
	
}

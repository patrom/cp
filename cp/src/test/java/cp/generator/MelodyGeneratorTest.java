package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombination;
import cp.combination.even.FourNoteEven;
import cp.composition.Composition;
import cp.composition.accomp.AccompGroup;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupStrategy;
import cp.composition.beat.BeatGroupTwo;
import cp.composition.timesignature.TimeConfig;
import cp.composition.voice.FixedVoice;
import cp.composition.voice.MelodyVoice;
import cp.config.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.midi.MidiDevicesUtil;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.ScoreUtilities;
import cp.out.print.note.Key;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class})
public class MelodyGeneratorTest extends JFrame{
	
	private static Logger LOGGER = LoggerFactory.getLogger(MelodyGeneratorTest.class);
	
	@Autowired
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
	@MockBean(name = "pitchClassGenerator")
	private PitchClassGenerator pitchClassGenerator;
	@MockBean(name = "beatGroupStrategy")
	private BeatGroupStrategy beatGroupStrategy;
	@MockBean(name = "timeLine")
	private TimeLine timeLine;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;
	@Resource(name = "fixedEven")
	private List<RhythmCombination> fixedEven;
	@MockBean(name = "voiceConfig")
	private VoiceConfig voiceConfig;
	@Autowired
	private MelodyVoice melodyVoice;
	@Autowired
	private FixedVoice fixedVoice;
	@Autowired
	private FourNoteEven fourNoteEven;

	@MockBean
	@Qualifier(value ="fourVoiceComposition" )
	private Composition composition;

	@Before
	public void setUp() throws Exception {
		musicProperties.setKey(C);
	}

	@Test
	public void testGenerateMelody() {
		int[] harmony = {0, DurationConstants.WHOLE};
		int max = 4;
		int[] positions = melodyGenerator.generateMelodyPositions(harmony, DurationConstants.QUARTER, max);
		System.out.println(Arrays.toString(positions));
	}

	
	@Test
	public void testGenerateMelodyBlock() {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pos(0).build());
		when(composition.getStart()).thenReturn(0);
		when(composition.getEnd()).thenReturn(DurationConstants.WHOLE);
		when(composition.getTimeConfig()).thenReturn(time44);
		List<BeatGroup> beatGroups = new ArrayList<>();
		beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER));
		when(beatGroupStrategy.getBeatGroups()).thenReturn(beatGroups);
		when(pitchClassGenerator.updatePitchClasses(notes)).thenReturn(notes);
		when(voiceConfig.getVoiceConfiguration(anyInt())).thenReturn(melodyVoice);
		when(voiceConfig.getRandomPitchClassGenerator(anyInt())).thenReturn(pg -> new ArrayList<>());
		MelodyBlock melody = melodyGenerator.generateMelodyBlockConfig(1, 5);
		assertEquals(1, melody.getVoice());
		assertTrue(melody.getMelodyBlocks().size() > 1);
	}

	@Test
	@Ignore
	public void testGenerateMelodyBlockConfig() {
		when(composition.getStart()).thenReturn(0);
		when(composition.getEnd()).thenReturn(DurationConstants.WHOLE);
		ArrayList<Integer> contour = new ArrayList<>();
		contour.add(1);
		contour.add(1);
		contour.add(1);
		contour.add(-1);
		AccompGroup accompGroup = new AccompGroup(melodyVoice, contour);
		MelodyBlock melody = melodyGenerator.generateMelodyBlockWithoutPitchClassGenerator(1, accompGroup,  0);
		List<Note> melodyBlockNotes = melody.getMelodyBlockNotes();
		melodyBlockNotes.forEach(System.out::println);
		assertEquals(8, melodyBlockNotes.size());
		for (Note melodyBlockNote : melodyBlockNotes) {
			assertEquals(DurationConstants.EIGHT, melodyBlockNote.getLength());
		}
	}


	@Test
	public void testGenerateDependantMelodyBlock() {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pos(0).build());
		when(composition.getStart()).thenReturn(0);
		when(composition.getEnd()).thenReturn(2 * DurationConstants.WHOLE);
		when(composition.getTimeConfig()).thenReturn(time44);
		List<BeatGroup> beatGroups = new ArrayList<>();
		beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER));
		when(beatGroupStrategy.getBeatGroups()).thenReturn(beatGroups);
		when(pitchClassGenerator.updatePitchClasses(notes)).thenReturn(notes);
		when(voiceConfig.getVoiceConfiguration(anyInt())).thenReturn(melodyVoice);
		when(voiceConfig.getRandomPitchClassGenerator(anyInt())).thenReturn(pitchClassGenerator);

		List<Integer> beats = new ArrayList<>();
		beats.add(12);
		MelodyBlock dependingBlock = melodyGenerator.generateMelodyBlockConfig(1, 5);

		when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, 2 * DurationConstants.WHOLE));

		MelodyBlock melodyBlock = melodyGenerator.generateDependantMelodyBlock(0, 5, dependingBlock);
//		assertEquals(dependingBlock.getMelodyBlocks().size(), melodyBlock.getMelodyBlocks().size());
        for (CpMelody cpMelody : dependingBlock.getMelodyBlocks()) {
            cpMelody.getNotes().forEach(n -> System.out.println(n.toString()));
            System.out.println("________________");
        }
        System.out.println("-- -- -- -- -- -- -- --");
        for (CpMelody cpMelody : melodyBlock.getMelodyBlocks()) {
            cpMelody.getNotes().forEach(n -> System.out.println(n.toString()));
            System.out.println("________________");
        }
	}
	
}

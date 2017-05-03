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
import cp.composition.voice.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.midi.MidiDevicePlayer;
import cp.midi.MidiDevicesUtil;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.print.ScoreUtilities;
import cp.out.print.note.Key;
import jm.music.data.Score;
import jm.util.View;
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

import javax.annotation.Resource;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
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
	private BeatGroupStrategy beatGroupStrategy;
	@Mock
	private TimeLine timeLine;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;
	@Resource(name = "fixedEven")
	private List<RhythmCombination> fixedEven;
	@Mock
	private Composition composition;
	@Mock
	private VoiceConfig voiceConfig;
	@Autowired
	private MelodyVoice melodyVoice;
	@Autowired
	private FixedVoice fixedVoice;
	@Autowired
	private FourNoteEven fourNoteEven;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
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
	public void testGenerateMelodyNotes() throws InvalidMidiDataException, InterruptedException {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).pitch(64).octave(4).pos(0).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(2).pitch(62).octave(4).pos(DurationConstants.QUARTER).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(4).pitch(64).octave(4).pos(DurationConstants.HALF + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.EIGHT).pc(5).pitch(65).octave(4).pos(DurationConstants.WHOLE + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.EIGHT).pc(7).pitch(67).octave(4).pos(DurationConstants.WHOLE + DurationConstants.THREE_EIGHTS).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(9).pitch(69).octave(4).pos(DurationConstants.WHOLE + DurationConstants.HALF).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(11).pitch(71).octave(4).pos(DurationConstants.WHOLE + DurationConstants.SIX_EIGHTS).art(Articulation.STACCATO).build());
		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(0).pitch(60).octave(4).pos(2 * DurationConstants.WHOLE + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
//		notes.forEach(note -> note.setArticulation(Articulation.STACCATO));
		Score score = scoreUtilities.createMelody(notes);
		View.notate(score);
		Sequence seq = midiDevicesUtil.createSequenceNotes(notes, new  ViolinSolo(), 0);
		midiDevicesUtil.playOnDevice(seq, 60, MidiDevicePlayer.KONTAKT);
		Thread.sleep(10000);
	}
	
	@Test
	public void testGenerateMelodyBlock() {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pos(0).build());
		when(composition.getStart()).thenReturn(0);
		when(composition.getEnd()).thenReturn(DurationConstants.WHOLE);
		when(composition.getTimeConfig()).thenReturn(time44);
		List<BeatGroup> beatGroups = new ArrayList<>();
		beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, fixedEven));
		when(beatGroupStrategy.getBeatGroups()).thenReturn(beatGroups);
		when(pitchClassGenerator.updatePitchClasses(notes)).thenReturn(notes);
		when(voiceConfig.getVoiceConfiguration(Mockito.anyInt())).thenReturn(melodyVoice);
		when(voiceConfig.getRandomPitchClassGenerator(Mockito.anyInt())).thenReturn(new PitchClassGenerator() {
			@Override
			public List<Note> updatePitchClasses(List<Note> notes) {
				return new ArrayList<>();
			}
		});
		MelodyBlock melody = melodyGenerator.generateMelodyBlockConfig(1, 5);
		assertEquals(1, melody.getVoice());
		assertTrue(melody.getMelodyBlocks().size() > 1);
	}

	@Test
	public void testGenerateMelodyBlockConfig() {
		when(composition.getStart()).thenReturn(0);
		when(composition.getEnd()).thenReturn(DurationConstants.WHOLE);
		ArrayList<Integer> contour = new ArrayList<>();
		contour.add(1);
		contour.add(1);
		contour.add(1);
		contour.add(-1);
		BeatGroup beatGroup = new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(fourNoteEven::pos1234));
		AccompGroup accompGroup = new AccompGroup(beatGroup, contour);
		MelodyBlock melody = melodyGenerator.generateMelodyBlockWithoutPitchClassGenerator(1, accompGroup,  0);
		List<Note> melodyBlockNotes = melody.getMelodyBlockNotes();
		melodyBlockNotes.forEach(n -> System.out.println(n));
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
		beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, fixedEven));
		when(beatGroupStrategy.getBeatGroups()).thenReturn(beatGroups);
		when(pitchClassGenerator.updatePitchClasses(notes)).thenReturn(notes);
		when(voiceConfig.getVoiceConfiguration(Mockito.anyInt())).thenReturn(melodyVoice);
		when(voiceConfig.getRandomPitchClassGenerator(Mockito.anyInt())).thenReturn(pitchClassGenerator);

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

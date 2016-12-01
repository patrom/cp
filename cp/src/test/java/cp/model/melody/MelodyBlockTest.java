package cp.model.melody;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroupFactory;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.print.note.Key;
import cp.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class MelodyBlockTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MelodyBlockTest.class);
	
	private MelodyBlock melodyBlock;
	private CpMelody melody;
	
	@Autowired
	private Key D;
	@Autowired
	private Key Csharp;
	@Autowired
	private Key C;
	@Autowired
	private Key G;
	@Autowired
	private TimeLine timeLine;
	@Autowired
	private BeatGroupFactory beatGroupFactory;

	@Before
	public void setUp() throws Exception {
		melodyBlock = new MelodyBlock(5, 1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.getContour().set(1, 3);
		melodyBlock.addMelodyBlock(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(65).ocatve(5).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.getContour().set(0, 4);
		melody.getContour().set(1, 5);
		melodyBlock.addMelodyBlock(melody);
	}

//	@Test
//	public void testUpdateRandomNote() {
//		melodyBlock.updateRandomNote();
//		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockNotes());
//		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockContour());
//	}
	
	
//	@Test
//	public void testReplaceMelody() {
//		List<Note> notes = new ArrayList<>();
//		melodyBlock = new MelodyBlock(5,1);
//		CpMelodyBuilder cpMelodyBuilder = new CpMelodyBuilder();
//		CpMelody melody = cpMelodyBuilder.start(0).build();
//		melodyBlock.addMelodyBlock(melody);
//		melody = cpMelodyBuilder.start(24).build();
//		melodyBlock.addMelodyBlock(melody);
//		melody  = new CpMelody(notes, 0, 0 , DurationConstants.QUARTER);
//		melodyBlock.replaceMelody(melody);
////		assertEquals(50, melodyBlock.getMelodyBlockNotes().get(3).getPitch());
//		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockNotes());
//		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockContour());
//	}
	
	@Test
	public void testUpdatePitchesFromContour() {
		melodyBlock = new MelodyBlock(4,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.getContour().set(0, 1);
		melody.getContour().set(1, -1);
		melodyBlock.addMelodyBlock(melody);
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.HALF).pc(5).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(7).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.getContour().set(0, 1);
		melody.getContour().set(1, -1);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.updatePitchesFromContour();
		assertEquals(48, melodyBlock.getMelodyBlockNotes().get(0).getPitch());
		assertEquals(52, melodyBlock.getMelodyBlockNotes().get(1).getPitch());
		assertEquals(41, melodyBlock.getMelodyBlockNotes().get(2).getPitch());
		assertEquals(43, melodyBlock.getMelodyBlockNotes().get(3).getPitch());
		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockNotes());
		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockContour());
	}
	
//	@Test
//	public void testTransformDependingOn() {
//		melodyBlock = new MelodyBlock(5,1);
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
//		melody = new CpMelody(notes, 1, 0 , DurationConstants.HALF);
//		melodyBlock.addMelodyBlock(melody);
//
//		notes = new ArrayList<>();
//		notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(65).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, 1, DurationConstants.HALF , DurationConstants.SIX_EIGHTS);
//		melodyBlock.addMelodyBlock(melody);
//
//		MelodyBlock melodyBlockCopy = new MelodyBlock(5,1);
//		melodyBlockCopy.setOffset(DurationConstants.HALF);
//		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.T);
//		melodyBlockCopy.setOperatorType(operatorType);
//		melodyBlockCopy.setInstrument(new ViolinSolo(0, 0));
//		List<TimeLineKey> keys = new ArrayList<>();
//		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.HALF));
//		keys.add(new TimeLineKey(D, Scale.MAJOR_SCALE, DurationConstants.HALF, DurationConstants.WHOLE));
//		timeLine.addKeysForVoice(keys, 0);
//		melodyBlockCopy.transformDependingOn(melodyBlock, timeLine);
//		assertEquals(1, melodyBlockCopy.getMelodyBlockNotes().size());
//		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockNotes());
//		LOGGER.info("Notes: " + melodyBlock.getMelodyBlockContour());
//		LOGGER.info("Notes: " + melodyBlockCopy.getMelodyBlockNotes());
//		LOGGER.info("Notes: " + melodyBlockCopy.getMelodyBlockContour());
//	}
	
	@Test
	public void testUpdatePitches() {
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(7).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.updatePitchesFromContour();
		assertEquals(0, notes.get(0).getPitch() % 12);
		assertEquals(2, notes.get(1).getPitch() % 12);
		assertEquals(11, notes.get(2).getPitch() % 12);
		assertEquals(5, notes.get(3).getPitch() % 12);
		assertEquals(7, notes.get(4).getPitch() % 12);
	}
	

	
	@Test
	public void testUpdateMelodyBetween() {
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).ocatve(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(89).ocatve(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(7).pitch(55).ocatve(4).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		Instrument instrument = new Instrument();
		instrument.setLowest(60);
		instrument.setHighest(80);
		melodyBlock.setInstrument(instrument);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.updateMelodyBetween();
		assertEquals(60, notes.get(0).getPitch());
		assertEquals(71, notes.get(1).getPitch());
		assertEquals(77, notes.get(2).getPitch());
		assertEquals(6, notes.get(2).getOctave());
		assertEquals(67, notes.get(3).getPitch());
		assertEquals(5, notes.get(3).getOctave());
	}
	
	@Test
	public void testCalculateInterval(){
		melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes,0,0, DurationConstants.QUARTER);
		int interval = Util.calculateInterval(1, 4);
		assertEquals(4, interval);
		interval = Util.calculateInterval(-1, 4);
		assertEquals(-8, interval);
		interval = Util.calculateInterval(-1, -4);
		assertEquals(-4, interval);
		interval = Util.calculateInterval(1, -4);
		assertEquals(8, interval);
	}
	
	@Test
	public void testUpdatePitchesFromContour2(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.getContour().set(0, 1);
		melody.getContour().set(1, -1);
		melody.getContour().set(2, 1);
		melody.getContour().set(3, -1);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.updatePitchesFromContour();
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(60, melody.getNotes().get(0).getPitch());
		assertEquals(5, melody.getNotes().get(0).getOctave());
		assertEquals(64, melody.getNotes().get(1).getPitch());
		assertEquals(5, melody.getNotes().get(1).getOctave());
		assertEquals(59, melody.getNotes().get(2).getPitch());
		assertEquals(4, melody.getNotes().get(2).getOctave());
		assertEquals(67, melody.getNotes().get(3).getPitch());
		assertEquals(5, melody.getNotes().get(3).getOctave());
		assertEquals(57, melody.getNotes().get(4).getPitch());
		assertEquals(4, melody.getNotes().get(4).getOctave());
	}
	
	@Test
	public void testUpdatePitchesFromContourRepeat(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(4).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(7).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.getContour().set(0, 1);
		melody.getContour().set(1, 1);
		melody.getContour().set(2, 1);
		melody.getContour().set(3, -1);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.updatePitchesFromContour();
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(60, melody.getNotes().get(0).getPitch());
		assertEquals(5, melody.getNotes().get(0).getOctave());
		assertEquals(64, melody.getNotes().get(1).getPitch());
		assertEquals(5, melody.getNotes().get(1).getOctave());
		assertEquals(64, melody.getNotes().get(2).getPitch());
		assertEquals(5, melody.getNotes().get(2).getOctave());
		assertEquals(67, melody.getNotes().get(3).getPitch());
		assertEquals(5, melody.getNotes().get(3).getOctave());
		assertEquals(67, melody.getNotes().get(4).getPitch());
		assertEquals(5, melody.getNotes().get(4).getOctave());
	}
	
	@Test
	public void testT(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.T(2);
		assertEquals(2, melody.getNotes().get(0).getPitchClass());
		assertEquals(6, melody.getNotes().get(1).getPitchClass());
		assertEquals(1, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testI(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.I();
		assertEquals(0, melody.getNotes().get(0).getPitchClass());
		assertEquals(8, melody.getNotes().get(1).getPitchClass());
		assertEquals(1, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testM(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.M(5);
		assertEquals(0, melody.getNotes().get(0).getPitchClass());
		assertEquals(8, melody.getNotes().get(1).getPitchClass());
		assertEquals(7, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testTI(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.I().T(2);
		assertEquals(2, melody.getNotes().get(0).getPitchClass());
		assertEquals(10, melody.getNotes().get(1).getPitchClass());
		assertEquals(3, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testR(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.R();
		assertEquals(9, melody.getNotes().get(0).getPitchClass());
		assertEquals(7, melody.getNotes().get(1).getPitchClass());
		assertEquals(11, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testRTI(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melodyBlock.addMelodyBlock(melody);
		melodyBlock.I().T(2).R();
		assertEquals(5, melody.getNotes().get(0).getPitchClass());
		assertEquals(7, melody.getNotes().get(1).getPitchClass());
		assertEquals(3, melody.getNotes().get(2).getPitchClass());
	}
	
//	@Test
//	public void testTrelative(){
//		melodyBlock = new MelodyBlock(5,1);
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
//		melodyBlock.addMelodyBlock(melody);
//
//		notes = new ArrayList<>();
//		notes.add(note().pos(DurationConstants.WHOLE).pc(2).pitch(62).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(6).pitch(66).ocatve(5).build());
//		notes.add(note().pos(72).pc(11).pitch(71).ocatve(5).build());
//		melody = new CpMelody(notes,0 , 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
//		melodyBlock.addMelodyBlock(melody);
//
//		Instrument cello = new CelloSolo(0, 3);
//		MelodyBlock melodyBlock2 = new MelodyBlock(4, cello.getVoice());
//		melodyBlock2.setVoice(cello.getVoice());
//		melodyBlock2.setOffset(DurationConstants.HALF);
//		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.T_RELATIVE);
////		operatorType.setSteps(1);
////		operatorType.setFunctionalDegreeCenter(3);
//		melodyBlock2.setOperatorType(operatorType);
//		melodyBlock2.dependsOn(melodyBlock.getVoice());
//		melodyBlock2.setInstrument(cello);
//
//		List<TimeLineKey> keys = new ArrayList<>();
//		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE));
//		keys.add(new TimeLineKey(G, Scale.MAJOR_SCALE,  DurationConstants.WHOLE, DurationConstants.WHOLE * 3));
//		timeLine.addKeysForVoice(keys, 1);
//		melodyBlock2.transformDependingOn(melodyBlock, timeLine);
//		melodyBlock2.getMelodyBlockNotes().forEach(n -> System.out.println(n.getPitchClass() + ", " + n.getPosition()));
////		assertEquals(4, melody.getNotes().get(0).getPitchClass());
////		assertEquals(7, melody.getNotes().get(1).getPitchClass());
////		assertEquals(2, melody.getNotes().get(2).getPitchClass());
////		assertEquals(11, melody.getNotes().get(3).getPitchClass());
////		assertEquals(0, melody.getNotes().get(4).getPitchClass());
//	}
	
//	@Test
//	public void testRetrograde(){
//		melodyBlock = new MelodyBlock(5,1);
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.HALF).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
//		melodyBlock.addMelodyBlock(melody);
//
//		notes = new ArrayList<>();
//		notes.add(note().pos(DurationConstants.WHOLE).pc(2).pitch(62).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(6).pitch(66).ocatve(5).build());
//		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).pitch(71).ocatve(5).build());
//		melody = new CpMelody(notes,0 , 0, DurationConstants.WHOLE + DurationConstants.SIX_EIGHTS);
//		melodyBlock.addMelodyBlock(melody);
//
//		Instrument cello = new CelloSolo(0, 3);
//		MelodyBlock melodyBlock2 = new MelodyBlock(4, cello.getVoice());
//		melodyBlock2.setVoice(cello.getVoice());
////		melodyBlock2.setOffset();
//		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.R);
////		operatorType.setSteps(1);
////		operatorType.setFunctionalDegreeCenter(3);
//		melodyBlock2.setOperatorType(operatorType);
//		melodyBlock2.dependsOn(melodyBlock.getVoice());
//		melodyBlock2.setInstrument(cello);
//
//		List<TimeLineKey> keys = new ArrayList<>();
//		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE * 3));
//		timeLine.addKeysForVoice(keys, 1);
//		melodyBlock2.transformDependingOn(melodyBlock, timeLine);
//		melodyBlock2.getMelodyBlockNotes().forEach(n -> System.out.println(n.getPitchClass() + ", " + n.getPosition()));
////		assertEquals(4, melody.getNotes().get(0).getPitchClass());
////		assertEquals(7, melody.getNotes().get(1).getPitchClass());
////		assertEquals(2, melody.getNotes().get(2).getPitchClass());
////		assertEquals(11, melody.getNotes().get(3).getPitchClass());
////		assertEquals(0, melody.getNotes().get(4).getPitchClass());
//	}

	@Test
	public void testAugmentation(){
		melodyBlock = new MelodyBlock(5,1);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).len(DurationConstants.QUARTER).voice(1).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(5).len(DurationConstants.QUARTER).voice(1).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(65).ocatve(5).len(DurationConstants.EIGHT).voice(1).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.EIGHT).pc(11).pitch(71).ocatve(5).len(DurationConstants.EIGHT).voice(1).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(0).pitch(72).ocatve(5).len(DurationConstants.QUARTER).voice(1).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.setBeatGroup(beatGroupFactory.getBeatGroupUneven(DurationConstants.QUARTER, "fixed"));
		melodyBlock.addMelodyBlock(melody);

		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE));
		keys.add(new TimeLineKey(D, Scale.MAJOR_SCALE,  DurationConstants.WHOLE, DurationConstants.WHOLE * 2));
		timeLine.addKeysForVoice(keys, 1);
//		melodyBlock.setOffset();
		melodyBlock.augmentation(2, timeLine);
		notes = melodyBlock.getMelodyBlockNotesWithRests();
		assertEquals(DurationConstants.HALF, notes.get(0).getLength());
		assertEquals(DurationConstants.HALF, notes.get(1).getLength());
		assertEquals(DurationConstants.QUARTER, notes.get(2).getLength());
		assertEquals(DurationConstants.QUARTER, notes.get(3).getLength());
		assertEquals(DurationConstants.HALF, notes.get(4).getLength());

		assertEquals(0, notes.get(0).getPosition());
		assertEquals(DurationConstants.HALF, notes.get(1).getPosition());
		assertEquals(DurationConstants.WHOLE, notes.get(2).getPosition());
		assertEquals(DurationConstants.WHOLE + DurationConstants.QUARTER, notes.get(3).getPosition());
		assertEquals(DurationConstants.WHOLE + DurationConstants.HALF, notes.get(4).getPosition());

		assertEquals(0, notes.get(0).getPitchClass());
		assertEquals(4, notes.get(1).getPitchClass());
		assertEquals(6, notes.get(2).getPitchClass());//key of D
		assertEquals(11, notes.get(3).getPitchClass());
		assertEquals(1, notes.get(4).getPitchClass());//key of D
	}

	@Test
	public void testUpdateMelodyBlock() {
		melodyBlock = new MelodyBlock(5,0);
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melodyBlock.addMelodyBlock(melody);
		melody = new CpMelody(notes, 0,  DurationConstants.WHOLE,  2* DurationConstants.WHOLE);
		melodyBlock.addMelodyBlock(melody);
		melody = new CpMelody(notes, 0,2 * DurationConstants.WHOLE,  4 * DurationConstants.WHOLE);
		melodyBlock.addMelodyBlock(melody);
		melody = new CpMelody(notes, 0,4 * DurationConstants.WHOLE,  6 * DurationConstants.WHOLE);
		melodyBlock.addMelodyBlock(melody);
		melody = new CpMelody(notes, 0,6 * DurationConstants.WHOLE,  8 * DurationConstants.WHOLE);
		melodyBlock.addMelodyBlock(melody);

		MelodyBlock melodyBlockToUpdate = new MelodyBlock(4,1);
		notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pc(0).pos(0).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melodyBlockToUpdate.addMelodyBlock(melody);
		notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pc(2).pos(DurationConstants.WHOLE).build());
		melody = new CpMelody(notes, 0,  DurationConstants.WHOLE,  4* DurationConstants.WHOLE);
		melodyBlockToUpdate.addMelodyBlock(melody);

		melodyBlock.updateMelodyBlock(melodyBlockToUpdate,2* DurationConstants.WHOLE );
		CpMelody melody = melodyBlock.getMelodyBlocks().get(2);
		Note note = melodyBlock.getMelodyBlocks().get(2).getNotes().get(0);
		assertEquals(2 * DurationConstants.WHOLE, melody.getStart());
		assertEquals(2* DurationConstants.WHOLE, note.getPosition());
		assertEquals(0, note.getVoice());
	}

}

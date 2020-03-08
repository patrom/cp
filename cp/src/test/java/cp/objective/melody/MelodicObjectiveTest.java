package cp.objective.melody;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.model.dissonance.Dissonance;
import cp.model.note.Interval;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class MelodicObjectiveTest extends AbstractTest {

	@Autowired
	private MelodyBlockObjective melodyBlockObjective;
	@Autowired
	@Qualifier(value="TonalDissonance")
	private Dissonance dissonance;
	private double totalWeight;
	private List<Note> melodyNotes;
	@Autowired
	private MelodyDefaultDissonance melodyDefaultDissonance;
	
	private final Random random = new Random();
	
	@BeforeEach
	public void setup() {
		melodyNotes = new ArrayList<>();
	}
	
	private List<Note> generateRandomMelody(){
		int length = 10;
		IntStream intStream = random.ints(60, 79);
		List<Integer> randomPitches = intStream
				.limit(length)
				.boxed()
				.collect(Collectors.toList());
		List<Note> notes = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			Note note = note().pitch(randomPitches.get(i)).pos(i * DurationConstants.EIGHT).len(DurationConstants.EIGHT).positionWeight(1.0).build();
			notes.add(note);
		}
		return notes;
	}
	
	@Test
	public void testMelody(){
		melodyNotes = generateRandomMelody();
		double melodicValue = melodyBlockObjective.evaluateMelody(melodyNotes, 1, melodyDefaultDissonance);
		LOGGER.info("rowMatrix melody :" + melodicValue);
	}
	
	@Test
	public void test_E_C() {
		melodyNotes.add(note().pitch(64).positionWeight(3.0).build());
		melodyNotes.add(note().pitch(60).positionWeight(3.0).build());
		double melodicValue = melodyBlockObjective.evaluateMelody(melodyNotes, 1, melodyDefaultDissonance);
		LOGGER.info("test_E_C :" + melodicValue);
		double expected = Interval.GROTE_TERTS.getMelodicValue();
		assertEquals(expected, melodicValue, 0.001);
	}
	
	@Test
	public void test_E_D_C() {
		melodyNotes.add(note().pitch(64).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(62).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(60).positionWeight(3.0).build());
		totalWeight = 7.5;
		double melodicValue = melodyBlockObjective.evaluateMelody(melodyNotes, 1, melodyDefaultDissonance);
		LOGGER.info("test_E_D_C :" + melodicValue);
		double expected = (Interval.GROTE_SECONDE.getMelodicValue() * (1.5 + 1.5) 
				+ (Interval.GROTE_SECONDE.getMelodicValue() * (1.5 + 3)))/totalWeight;
		assertEquals(expected, melodicValue, 0);
	}
	
	@Test
	public void test_E__D_C() {
		melodyNotes.add(note().pitch(64).positionWeight(2.5).build());
		melodyNotes.add(note().pitch(62).positionWeight(0.5).build());
		melodyNotes.add(note().pitch(60).positionWeight(3.0).build());
		totalWeight = 6.5;
		double melodicValue = melodyBlockObjective.evaluateMelody(melodyNotes, 1, melodyDefaultDissonance);
		LOGGER.info("test_E__D_C :" + melodicValue);
		double expected = (Interval.GROTE_SECONDE.getMelodicValue() * (2.5 + 0.5) + Interval.GROTE_SECONDE.getMelodicValue() * (0.5 + 3))/totalWeight;
		assertEquals(expected, melodicValue, 0);
	}
	
	
	@Test
	public void test_E_D_C_D() {
		melodyNotes.add(note().pitch(64).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(62).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(60).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(62).positionWeight(1.5).build());
		totalWeight = 9.0;
		double melodicValue = melodyBlockObjective.evaluateMelody(melodyNotes, 1, melodyDefaultDissonance);
		LOGGER.info("test_E_D_C_D :" + melodicValue);
		double expected = 3 * (Interval.GROTE_SECONDE.getMelodicValue() * (1.5 + 1.5))/totalWeight;
		assertEquals(expected, melodicValue, 0);
	}
	
	@Test
	public void test_C_D_E_F() {
		melodyNotes.add(note().pitch(60).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(62).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(64).positionWeight(1.5).build());
		melodyNotes.add(note().pitch(65).positionWeight(1.5).build());
		totalWeight = 9.0;
		double melodicValue = melodyBlockObjective.evaluateMelody(melodyNotes, 1, melodyDefaultDissonance);
		LOGGER.info("test_C_D_E_F :" + melodicValue);
		double expected = ((2 * (Interval.GROTE_SECONDE.getMelodicValue() * (1.5 + 1.5))) + (Interval.KLEINE_SECONDE.getMelodicValue() * (1.5 + 1.5)))/totalWeight;
		assertEquals(expected, melodicValue, 0.001);
	}
	
	@Test
	public void testExtractNotesOnLevel(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pos(0).len(DurationConstants.EIGHT).positionWeight(1.0).build());
		notes.add(note().pc(2).pos(6).len(3).positionWeight(0.5).build());
		notes.add(note().pc(3).pos(9).len(3).positionWeight(1.0).build());//max finds only 1
		notes.add(note().pc(4).pos(DurationConstants.QUARTER).len(DurationConstants.EIGHT).positionWeight(0.5).build());
		notes.add(note().pc(5).pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).positionWeight(1.0).build());
		notes = melodyBlockObjective.extractNotesOnLevel(notes, 1);
		assertEquals(2, notes.size());
		assertEquals(5, notes.get(1).getPitchClass());
		notes = melodyBlockObjective.extractNotesOnLevel(notes, 2);
		assertEquals(1, notes.size());
		assertEquals(0, notes.get(0).getPitchClass());
	}
	
//	@Test
//	public void testEvaluateTriadicValueMelody() {
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pc(0).pos(0).len(DurationConstants.EIGHT).positionWeight(1.0).build());
//		notes.add(note().pc(4).pos(6).len(DurationConstants.EIGHT).positionWeight(0.5).build());
//		notes.add(note().pc(7).pos(DurationConstants.QUARTER).len(DurationConstants.EIGHT).positionWeight(1.0).build());
//		notes.add(note().pc(0).pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).positionWeight(0.5).build());
//		double value = melodicObjective.evaluateTriadicValueMelody(notes);
//		assertEquals(0.99, value, 0);
//	}
	
	@Test
	public void testEvaluateMelody() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pitch(60).pc(0).pos(0).len(DurationConstants.EIGHT).positionWeight(2.0).build());
		notes.add(note().pitch(62).pc(2).pos(6).len(DurationConstants.EIGHT).positionWeight(2.0).build());
		notes.add(note().pitch(64).pc(4).pos(DurationConstants.QUARTER).len(DurationConstants.EIGHT).positionWeight(1.0).build());
		double value = melodyBlockObjective.evaluateMelody(notes, 1, melodyDefaultDissonance);
		LOGGER.info("Melody value: " + value);
		assertEquals(1.0, value, 0);
	}
	
	@Test
	public void testEvaluateMelodySamePitch() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pitch(60).pc(0).pos(0).len(DurationConstants.EIGHT).positionWeight(2.0).build());
		notes.add(note().pitch(60).pc(2).pos(6).len(DurationConstants.EIGHT).positionWeight(1.0).build());
		notes.add(note().pitch(64).pc(4).pos(DurationConstants.QUARTER).len(DurationConstants.EIGHT).positionWeight(1.0).build());
		notes.add(note().pitch(64).pc(4).pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).positionWeight(1.0).build());
		double value = melodyBlockObjective.evaluateMelody(notes, 1, melodyDefaultDissonance);
		LOGGER.info("Melody value: " + value);
		assertEquals(0.7, value, 0);
	}



}

package cp.out.arrangement;

import cp.DefaultConfig;
import cp.midi.HarmonyPosition;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class ArrangementTest extends JFrame{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArrangementTest.class.getName());
	
	@Autowired
	private Arrangement arrangement;
	private List<Note> notes;
	private int[] pattern;

	@Before
	public void setUp() throws Exception {
		notes = new ArrayList<>();
		notes.add(note().pos(0).len(DurationConstants.QUARTER).pitch(60).build());
		notes.add(note().pos(DurationConstants.QUARTER).len(36).pitch(61).build());
		
		pattern = new int[4];
		pattern[0] = 0;
		pattern[1] = 6;
		pattern[2] = 18;
		pattern[3] = 24;
	}

	@Test
	public void testApplyFixedPattern() {
		List<Note> rhythmicNotes2 = arrangement.applyFixedPattern(notes, pattern);
		LOGGER.info(rhythmicNotes2.toString());
	}

	@Test
	public void testTranspose() {
		arrangement.transpose(notes, 1);
	}

	@Test
	public void testApplyPattern() {
		List<Note> rhythmicNotes2 = arrangement.applyFixedPattern(notes, pattern);
		LOGGER.info(rhythmicNotes2.toString());
	}
	
	@Test
	public void testAccompagnement() {
		Integer[] compPattern = {6,12,18,30};
		List<HarmonyPosition> harmonyPositions = new ArrayList<>();
		harmonyPositions.add(createHarmonyInstrument(0, notes));
		harmonyPositions.add(createHarmonyInstrument(DurationConstants.HALF, notes));
		List<Integer[]> compPatterns = new ArrayList<>();
		compPatterns.add(compPattern);
		Accompagnement[] compStrategy = {Accompagnement::chordal};
		List<Note> accompagnement = arrangement.accompagnement(harmonyPositions, compPatterns, compStrategy);
		assertEquals(DurationConstants.QUARTER, accompagnement.get(notes.size()).getPosition());
	}

	private HarmonyPosition createHarmonyInstrument(int position, List<Note> notes) {
		HarmonyPosition harmonyInstrument = new HarmonyPosition();
		harmonyInstrument.setNotes(notes);
		harmonyInstrument.setPosition(position);
		return harmonyInstrument;
	}
	
	@Test
	public void testCreateAcc(){
		List<Note> melodyNotes = new ArrayList<>();
		melodyNotes.add(note().pos(0).len(36).pitch(60).build());
		melodyNotes.add(note().pos(DurationConstants.SIX_EIGHTS).len(36).pitch(61).build());
		
		List<Note> compPattern = new ArrayList<>();
		compPattern.add(note().pos(DurationConstants.QUARTER).build());
		compPattern.add(note().pos(DurationConstants.HALF).build());
		compPattern.add(note().pos(DurationConstants.WHOLE).build());
		compPattern.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).build());
		int minimumLength = DurationConstants.EIGHT;
		List<Note> accompagnement = arrangement.createAccompForPattern(melodyNotes, compPattern, minimumLength);
		accompagnement.forEach(note -> LOGGER.info("pos: " + note.getPosition() + ", pitch: " + note.getPitch() + ", len: " + note.getLength()));
		assertEquals(DurationConstants.WHOLE, accompagnement.get(2).getPosition());
		assertEquals(minimumLength, accompagnement.get(2).getLength());
	}
	
	@Test
	public void testUpdatePatternPositions(){
		List<List<Note>> patterns = new ArrayList<>();
		List<Note> patternNotes = new ArrayList<>();
		patternNotes.add(note().pos(0).len(DurationConstants.QUARTER).pitch(60).build());
		patternNotes.add(note().pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).pitch(61).build());
		patterns.add(patternNotes);
		patterns.add(patternNotes);
		
		List<HarmonyPosition> harmonyPositions = new ArrayList<>();
		HarmonyPosition harmonyPosition = new HarmonyPosition();
		harmonyPosition.setPosition(0);
		harmonyPositions.add(harmonyPosition);
		harmonyPosition = new HarmonyPosition();
		harmonyPosition.setPosition(DurationConstants.SIX_EIGHTS);
		harmonyPositions.add(harmonyPosition);
		List<Note> accompagnement = arrangement.updatePatternPositions(harmonyPositions, patterns);
		accompagnement.forEach(note -> LOGGER.info("pos: " + note.getPosition() + ", pitch: " + note.getPitch() + ", len: " + note.getLength()));
		assertEquals(accompagnement.get(2).getPosition(), DurationConstants.SIX_EIGHTS);
	}
	
	@Test
	public void testGetAccompagnement(){
		List<List<Note>> patterns = new ArrayList<>();
		List<Note> patternNotes = new ArrayList<>();
		patternNotes.add(note().pos(0).len(DurationConstants.QUARTER).pitch(60).build());
		patternNotes.add(note().pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).pitch(61).build());
		patterns.add(patternNotes);
		patternNotes = new ArrayList<>();
		patternNotes.add(note().pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).pitch(60).build());
		patternNotes.add(note().pos(DurationConstants.HALF).len(DurationConstants.QUARTER).pitch(61).build());
		patterns.add(patternNotes);
		
		List<HarmonyPosition> harmonyPositions = new ArrayList<>();
		HarmonyPosition harmonyPosition = new HarmonyPosition();
		harmonyPosition.setPosition(0);
		harmonyPositions.add(harmonyPosition);
		harmonyPosition = new HarmonyPosition();
		harmonyPosition.setPosition(36);
		harmonyPositions.add(harmonyPosition);
		List<Note> melodyNotes = new ArrayList<>();
		melodyNotes.add(note().pos(0).len(36).pitch(72).build());
		melodyNotes.add(note().pos(DurationConstants.SIX_EIGHTS).len(36).pitch(73).build());
		int minimumLength = 12;
		List<Note> accompagnement = arrangement.getAccompagnement(melodyNotes , harmonyPositions, patterns, minimumLength);
		accompagnement.forEach(note -> LOGGER.info("pos: " + note.getPosition() + ", pitch: " + note.getPitch() + ", len: " + note.getLength()));
		assertEquals(accompagnement.get(1).getPosition(), DurationConstants.QUARTER);
		assertEquals(accompagnement.get(2).getPosition(), DurationConstants.WHOLE);
		accompagnement.forEach(note -> assertTrue(note.getLength() == minimumLength));
	}

}

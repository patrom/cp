package cp.model.rhythm;

import cp.DefaultConfig;
import cp.midi.HarmonyPosition;
import cp.midi.MelodyInstrument;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.out.instrument.keyboard.Piano;
import cp.out.play.InstrumentMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class RhythmTest {
	
	@Autowired
	private Rhythm rhythm;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	
	@Test
	public void testRhythmPositions() {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(2).build());
		
		List<HarmonyPosition> harmonyPositions = new ArrayList<>();
		harmonyPositions.add(createHarmonyInstrument(0, chordNotes));
		
		chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(11).pitch(59).octave(4).voice(0).build());
		chordNotes.add(note().pc(2).pitch(62).octave(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(2).build());
		harmonyPositions.add(createHarmonyInstrument(24, chordNotes));
		harmonyPositions.add(createHarmonyInstrument(48, chordNotes));
		
		int[] sounds = {6,18,24,36,48};
		List<RhythmPosition> rhythmPositions = rhythm.getRhythmPositions(harmonyPositions, sounds);
		assertEquals(4, rhythmPositions.size());
	}
	
	@Test
	public void testSplitSoundsHarmonyRanges() {
		int[] sounds = {0,6,12,18,24,27,30};
		List<Integer[]> harmonyRanges = new ArrayList<>();
		Integer[] range = {0,18};
		harmonyRanges.add(range);
		Integer[] range2 = {18,30};
		harmonyRanges.add(range2);
		Map<Integer, List<Integer>> harmonyPositions = rhythm.splitRhythmPositionsOverHarmonyRanges(sounds, harmonyRanges);
		assertEquals(2, harmonyPositions.size());
		for (List<Integer> list : harmonyPositions.values()) {
			assertEquals(3, list.size());
		}
	}
	
	@Test
	public void testRhythm3() {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(2).build());
		
		List<HarmonyPosition> harmonyPositions = new ArrayList<>();
		harmonyPositions.add(createHarmonyInstrument(0, chordNotes));
		
		chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(11).pitch(59).octave(4).voice(0).build());
		chordNotes.add(note().pc(2).pitch(62).octave(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(2).build());
		harmonyPositions.add(createHarmonyInstrument(24, chordNotes));
		harmonyPositions.add(createHarmonyInstrument(48, null));
		
		int[] sounds = {6,12, 18, 30 ,36, 42,48};
		Integer[] contour = {1,1,1,-1,-1,-1,1,1,1,-1,-1,-1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0};
		Integer[] texture = {1,1,1,1,1,1};
		List<Note> notes = rhythm.getRhythm(harmonyPositions, contour, 1, 2, 3);
//		List<Note> notes = rhythm.getRhythm(harmonyPositions, sounds, texture, contour, 1);
		
		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
//		melodyInstrument.setInstrumentMapping(new InstrumentMapping(new Piano(),1,0));
//		playOnKontakt(Collections.singletonList(melodyInstrument), 90, 5000);
		
	}
	
	private HarmonyPosition createHarmonyInstrument(int position, List<Note> notes) {
		HarmonyPosition harmonyInstrument = new HarmonyPosition();
		harmonyInstrument.setNotes(notes);
		harmonyInstrument.setPosition(position);
		return harmonyInstrument;
	}
	
	@Test
	public void testRhythm2() {
		List<MelodyInstrument> melodyInstruments = new ArrayList<>();
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(2).build());
		int[] harmonyLength = {0, 48};
//		int[] sounds = melodyGenerator.generateMelodyPositions(harmonyLength, 3, 10);
		Integer[] sounds = {0,6,12,18,24,27,30};
		Integer[] contour = {1,2,1,-1,1};
		Integer[] texture = {1,1,2,1,2,1};
		List<Note> notes = rhythm.getRhythm(chordNotes, sounds, 0, texture, contour);
//		musicXMLWriter.generateMusicXMLForNotes(notes, new KontaktLibPiano(0, 0) , "rhythm");
		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
//		melodyInstrument.setInstrumentMapping(new InstrumentMapping(new Piano(),1,0));
		melodyInstruments.add(melodyInstrument);
//		int[] sounds2 = melodyGenerator.generateMelodyPositions(harmonyLength, 3, 10);
		Integer[] sounds2 = {0,3, 6, 12,18,24,30};
		Integer[] contour2 = {-1,-2,-1,1,1};
		Integer[] texture2 = {1,1,1,1,1,1};
		List<Note> notes2 = rhythm.getRhythm(chordNotes, sounds2, 0, texture2, contour2);
		MelodyInstrument melodyInstrument2 = new MelodyInstrument(notes2, 0);
		melodyInstrument2.setInstrumentMapping(new InstrumentMapping(new Piano(),1,0));
		melodyInstruments.add(melodyInstrument2);
//		playOnKontakt(melodyInstruments, 90, 5000);
	}
	
	@Test
	public void testRhythm(){
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).build());
		Integer[] sounds = {0,6,12,18,24,36,48};
		List<Note> notes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, 0, 2);
		
		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
//		melodyInstrument.setInstrumentMapping(new InstrumentMapping(new Piano(),1,0));
	}

	@Test
	public void testTexture(){
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(2).build());
		List<Note> sounds = new ArrayList<>();
		sounds.add(note().pos(0).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(6).len(DurationConstants.QUARTER).build());
		sounds.add(note().pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(26).len(DurationConstants.EIGHT).build());
		Integer[] contour = {1,-2,1};
		List<Note> contourNotes = rhythm.getContour(chordNotes, sounds, contour, 1);
		Integer[] texture = {1,2,1,2};
		List<Note> textureNotes = rhythm.getTexture(chordNotes, contourNotes, texture);
		assertTrue(textureNotes.size() == 6);
		Note note = textureNotes.get(2);
		Note contourNote = contourNotes.get(1);
		assertEquals(60, note.getPitch());
		assertEquals(contourNote.getPosition(), note.getPosition());
		assertEquals(contourNote.getLength(), note.getLength());
	}

	
	@Test
	public void testGetSounds(){
		Integer[] sounds = {0,6,18,24,30};
		List<Note> notes = rhythm.getSounds(0, sounds);
		assertTrue(notes.size() == 4);
		assertEquals(0, notes.get(0).getPosition());
		assertEquals(6, notes.get(1).getPosition());
		assertEquals(18, notes.get(2).getPosition());
		assertEquals(24, notes.get(3).getPosition());
		assertEquals(6, notes.get(0).getLength());
		assertEquals(12, notes.get(1).getLength());
	}
	
	@Test
	public void testGetContours(){
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).octave(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).octave(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(2).build());
		List<Note> sounds = new ArrayList<>();
		sounds.add(note().pos(0).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(6).len(DurationConstants.QUARTER).build());
		sounds.add(note().pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(26).len(DurationConstants.EIGHT).build());
		Integer[] contour = new Integer[]{1,-2,1};
		List<Note> contourNotes = rhythm.getContour(chordNotes, sounds, contour, 1);
		assertTrue(contourNotes.size() == 4);
		assertEquals(60, contourNotes.get(0).getPitch());
		assertEquals(64, contourNotes.get(1).getPitch());
		assertEquals(55, contourNotes.get(2).getPitch());
		assertEquals(60, contourNotes.get(3).getPitch());
	}
	
	@Test
	public void testGetContours2(){
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(4).pitch(64).octave(5).voice(0).build());
		chordNotes.add(note().pc(7).pitch(67).octave(5).voice(1).build());
		chordNotes.add(note().pc(0).pitch(72).octave(6).voice(2).build());
		List<Note> sounds = new ArrayList<>();
		sounds.add(note().pos(0).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(6).len(DurationConstants.QUARTER).build());
		sounds.add(note().pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(DurationConstants.HALF).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(30).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(DurationConstants.SIX_EIGHTS).len(DurationConstants.EIGHT).build());
		sounds.add(note().pos(42).len(DurationConstants.EIGHT).build());
		Integer[] contour = new Integer[]{-2, -1, 1, 1, -2, -2};
		List<Note> contourNotes = rhythm.getContour(chordNotes, sounds, contour, 1);
		assertTrue(contourNotes.size() == 7);
		assertEquals(64, contourNotes.get(0).getPitch());
		assertEquals(55, contourNotes.get(1).getPitch());
		assertEquals(52, contourNotes.get(2).getPitch());
		assertEquals(55, contourNotes.get(3).getPitch());
		assertEquals(60, contourNotes.get(4).getPitch());
		assertEquals(52, contourNotes.get(5).getPitch());
		assertEquals(43, contourNotes.get(6).getPitch());
	}
}

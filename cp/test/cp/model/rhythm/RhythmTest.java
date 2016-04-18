package cp.model.rhythm;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.midi.HarmonyPosition;
import cp.midi.MelodyInstrument;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.out.instrument.MidiDevice;
import cp.out.instrument.Piano;
import cp.out.print.MusicXMLWriter;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class RhythmTest {
	
	@Autowired
	private Rhythm rhythm;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	@Before
	public void setUp() throws Exception {
	
	}
	
	@Test
	public void testRhythmPositions() {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(2).build());
		
		List<HarmonyPosition> harmonyPositions = new ArrayList<>();
		harmonyPositions.add(createHarmonyInstrument(0, chordNotes));
		
		chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(11).pitch(59).ocatve(4).voice(0).build());
		chordNotes.add(note().pc(2).pitch(62).ocatve(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(2).build());
		harmonyPositions.add(createHarmonyInstrument(24, chordNotes));
		harmonyPositions.add(createHarmonyInstrument(48, chordNotes));
		
		int[] sounds = {6,18,24,36,48};
		List<RhythmPosition> rhythmPositions = rhythm.getRhythmPositions(harmonyPositions, sounds);
		assertEquals(4, rhythmPositions.size());
	}
	
	@Test
	public void testSplitSoundsHarmonyRanges() throws Exception {
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
	public void testRhythm3() throws Exception {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(2).build());
		
		List<HarmonyPosition> harmonyPositions = new ArrayList<>();
		harmonyPositions.add(createHarmonyInstrument(0, chordNotes));
		
		chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(11).pitch(59).ocatve(4).voice(0).build());
		chordNotes.add(note().pc(2).pitch(62).ocatve(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(2).build());
		harmonyPositions.add(createHarmonyInstrument(24, chordNotes));
		harmonyPositions.add(createHarmonyInstrument(48, null));
		
		int[] sounds = {6,12, 18, 30 ,36, 42,48};
		Integer[] contour = {1,1,1,-1,-1,-1,1,1,1,-1,-1,-1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0};
		Integer[] texture = {1,1,1,1,1,1};
		List<Note> notes = rhythm.getRhythm(harmonyPositions, contour, 1, 2, 3);
//		List<Note> notes = rhythm.getRhythm(harmonyPositions, sounds, texture, contour, 1);
		
		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
		melodyInstrument.setInstrument(new Piano(0, 0));
		playOnKontakt(Collections.singletonList(melodyInstrument), 90, 5000);
		
	}
	
	private HarmonyPosition createHarmonyInstrument(int position, List<Note> notes) {
		HarmonyPosition harmonyInstrument = new HarmonyPosition();
		harmonyInstrument.setNotes(notes);
		harmonyInstrument.setPosition(position);
		return harmonyInstrument;
	}
	
	@Test
	public void testRhythm2() throws Exception {
		List<MelodyInstrument> melodyInstruments = new ArrayList<MelodyInstrument>();
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(2).build());
		int[] harmonyLength = {0, 48};
//		int[] sounds = melodyGenerator.generateMelodyPositions(harmonyLength, 3, 10);
		Integer[] sounds = {0,6,12,18,24,27,30};
		Integer[] contour = {1,2,1,-1,1};
		Integer[] texture = {1,1,2,1,2,1};
		List<Note> notes = rhythm.getRhythm(chordNotes, sounds, 0, texture, contour);
//		musicXMLWriter.generateMusicXMLForNotes(notes, new KontaktLibPiano(0, 0) , "rhythm");
		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
		melodyInstrument.setInstrument(new Piano(0, 0));
		melodyInstruments.add(melodyInstrument);
//		int[] sounds2 = melodyGenerator.generateMelodyPositions(harmonyLength, 3, 10);
		Integer[] sounds2 = {0,3, 6, 12,18,24,30};
		Integer[] contour2 = {-1,-2,-1,1,1};
		Integer[] texture2 = {1,1,1,1,1,1};
		List<Note> notes2 = rhythm.getRhythm(chordNotes, sounds2, 0, texture2, contour2);
		MelodyInstrument melodyInstrument2 = new MelodyInstrument(notes2, 0);
		melodyInstrument2.setInstrument(new Piano(0, 0));
		melodyInstruments.add(melodyInstrument2);
		playOnKontakt(melodyInstruments, 90, 5000);
	}
	
	@Test
	public void testRhythm() throws Exception {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).build());
		Integer[] sounds = {0,6,12,18,24,36,48};
		List<Note> notes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, 0, 2);
		
		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
		melodyInstrument.setInstrument(new Piano(0, 0));
		playOnKontakt(Collections.singletonList(melodyInstrument), 90, 5000);
		
	}
	
	private void playOnKontakt(List<MelodyInstrument> melodies, int tempo, long playTime ) throws InvalidMidiDataException, InterruptedException {
		Sequence seq = midiDevicesUtil.createSequence(melodies);
		midiDevicesUtil.playOnDevice(seq, tempo, MidiDevice.KONTAKT);
		Thread.sleep(playTime);
	}

	@Test
	public void testTexture() throws Exception {
		List<Note> chordNotes = new ArrayList<>();
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).voice(1).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(2).build());
		List<Note> sounds = new ArrayList<>();
		sounds.add(note().pos(0).len(6).build());
		sounds.add(note().pos(6).len(12).build());
		sounds.add(note().pos(18).len(6).build());
		sounds.add(note().pos(26).len(6).build());
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
		chordNotes.add(note().pc(0).pitch(60).ocatve(5).voice(0).build());
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).voice(1).build()); 
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(2).build());
		List<Note> sounds = new ArrayList<>();
		sounds.add(note().pos(0).len(6).build());
		sounds.add(note().pos(6).len(12).build());
		sounds.add(note().pos(18).len(6).build());
		sounds.add(note().pos(26).len(6).build());
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
		chordNotes.add(note().pc(4).pitch(64).ocatve(5).voice(0).build());
		chordNotes.add(note().pc(7).pitch(67).ocatve(5).voice(1).build()); 
		chordNotes.add(note().pc(0).pitch(72).ocatve(6).voice(2).build());
		List<Note> sounds = new ArrayList<>();
		sounds.add(note().pos(0).len(6).build());
		sounds.add(note().pos(6).len(12).build());
		sounds.add(note().pos(18).len(6).build());
		sounds.add(note().pos(24).len(6).build());
		sounds.add(note().pos(30).len(6).build());
		sounds.add(note().pos(36).len(6).build());
		sounds.add(note().pos(42).len(6).build());
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

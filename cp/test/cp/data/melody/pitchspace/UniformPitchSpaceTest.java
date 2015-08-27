package cp.data.melody.pitchspace;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;
import cp.model.melody.pitchspace.PitchSpace;
import cp.model.melody.pitchspace.UniformPitchSpace;
import cp.model.note.Note;

public class UniformPitchSpaceTest extends PitchSpaceTest {
	
	@Test
	public void testTranslateToPitchSpaceSamePitchClass() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(0,0,0,0);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 60);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 60);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 48);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 36);
		List<Note> melodyNotes = harmonicMelodies.get(0).getMelodyNotes();
		for (Note note : melodyNotes) {
			assertEquals("pitch not correct", note.getPitch(), 36);
		}
		melodyNotes = harmonicMelodies.get(1).getMelodyNotes();
		assertEquals("pitch not correct", melodyNotes.get(0).getPitch(), 49);
		assertEquals("pitch not correct", melodyNotes.get(1).getPitch(), 48);
		assertEquals("pitch not correct", melodyNotes.get(2).getPitch(), 51);
	}
	
	@Test
	public void testTranslateToPitchSpaceSamePitchClassRange() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(0,0,0,0);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(new Integer[]{1}, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 60);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 60);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 48);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 48);
	}

	@Test
	public void testTranslateToPitchSpaceNoCrossing() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(1,2,3,4);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 64);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 63);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 50);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 37);
	}
	
	@Test
	public void testTranslateToPitchSpaceCrossing() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(1,3,2,4);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 64);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 62);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 51);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 37);
	}
	
	@Test
	public void testTranslateToPitchSpaceCrossingBorder() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(1,2,4,3);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 75);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 64);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 50);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 37);
	}
	
	@Test
	public void testTranslateToPitchSpaceCrossingBorderTop() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(3,2,4,1);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 73);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 64);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 50);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 39);
	}
	
	@Test
	public void testTranslateToPitchSpaceDoubling() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(1,2,2,3);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 63);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 62);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 50);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 37);
	}
	
	@Test
	public void testTranslateToPitchSpaceDoublingCrossing() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(1,3,2,3);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new UniformPitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 63);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 62);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 51);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 37);
	}

}

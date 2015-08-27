package cp.data.melody.pitchspace;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;
import cp.model.melody.pitchspace.PitchSpace;
import cp.model.melody.pitchspace.TopOctavePitchSpace;

public class TopOctavePitchSpaceTest extends PitchSpaceTest{

	@Test
	public void testTranslateToPitchSpace() {
		List<HarmonicMelody> harmonicMelodies = getHarmonicMelodies(1,2,3,4);
		Harmony harmony = new Harmony(0, 30, harmonicMelodies);
		PitchSpace pitchSpace = new TopOctavePitchSpace(range, instruments);
		harmony.setPitchSpace(pitchSpace);
		harmony.translateToPitchSpace();
		assertEquals("pitch not correct", harmonicMelodies.get(3).getHarmonyNote().getPitch(), 76);
		assertEquals("pitch not correct", harmonicMelodies.get(2).getHarmonyNote().getPitch(), 63);
		assertEquals("pitch not correct", harmonicMelodies.get(1).getHarmonyNote().getPitch(), 50);
		assertEquals("pitch not correct", harmonicMelodies.get(0).getHarmonyNote().getPitch(), 37);
	}
}

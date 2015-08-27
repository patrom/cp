package cp.generator;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.model.dissonance.TonalDissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.ChordType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class TonalChordGeneratorTest extends AbstractTest{

	private TonalChordGenerator tonalChordGenerator;
	private int[] positions;
	
	@Before
	public void setUp() throws Exception {
		positions = new int[]{0,48,96,144,192};
		musicProperties.setMeasureWeights(new double[]{1.0, 0.5, 0.75, 0.5, 1.0, 0.5, 0.75, 0.5});
		musicProperties.setChordSize(6);
	}

	@Test
	public void testPickRandomChords() {
		tonalChordGenerator = new TonalChordGenerator(positions, musicProperties);
		tonalChordGenerator.setChords(TonalChords.getTriads(0));
		int[] chord = tonalChordGenerator.pickRandomChord();
		assertTrue(chord.length == musicProperties.getChordSize());
		Chord ch = new Chord(chord[0]);
		for (int j = 0; j < chord.length; j++) {
			ch.addPitchClass(chord[j]);
		}
		assertTrue(ch.getChordType() != ChordType.CH3 || ch.getChordType() != ChordType.CH2);
	}

}

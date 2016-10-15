package cp.nsga;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.out.instrument.Instrument;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class MusicVariableTest {

	private List<CpHarmony> harmonies = new ArrayList<>();
	private Motive motive;
	private Integer[] range = {5};
	private List<Instrument> instruments = new ArrayList<>();
	private MusicProperties musicProperties;


//	@Test
//	public void testCloneMotives() {
//		MusicVariable musicVariable = new MusicVariable(motive);
//		Motive clonedMotive = musicVariable.cloneMotives(motive);
//		assertEquals(harmonies.size(), clonedMotive.getHarmonies().size());
//	}

}

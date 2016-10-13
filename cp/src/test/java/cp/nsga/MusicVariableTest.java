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
	
	@Before
	public void setup() {
//		List<Note> melodyNotes = new ArrayList<>();
//		melodyNotes.add(note().pc(0).pos(0).len(DurationConstants.QUARTER).build());
//		melodyNotes.add(note().pc(2).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).build());
//		int voice = 1;
//		Harmony harmony = harmony().pos(0).len(DurationConstants.HALF).positionWeight(3.0).build();
//		HarmonicMelody harmonicMelody = harmonicMelody().notes(melodyNotes).harmonyNote(note().pc(0).build()).voice(voice).build();
//		harmony.addHarmonicMelody(harmonicMelody);
//		harmony.setPitchSpace(new UniformPitchSpace(range, instruments));
//		harmonies.add(harmony);
//		motive = new Motive(harmonies, musicProperties);
	}


//	@Test
//	public void testCloneMotives() {
//		MusicVariable musicVariable = new MusicVariable(motive);
//		Motive clonedMotive = musicVariable.cloneMotives(motive);
//		assertEquals(harmonies.size(), clonedMotive.getHarmonies().size());
//	}

}

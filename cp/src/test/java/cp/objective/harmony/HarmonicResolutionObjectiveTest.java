package cp.objective.harmony;

import cp.DefaultConfig;
import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class HarmonicResolutionObjectiveTest {

	@Autowired
	@InjectMocks
	private HarmonicResolutionObjective harmonicResolutionObjective;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDissonant() {
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).positionWeight(4.0).build());
		notes.add(note().pos(0).pc(2).positionWeight(1.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, DurationConstants.QUARTER));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.HALF).pc(4).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, DurationConstants.HALF));
		
		harmonies.forEach(h -> h.toChord());
		
		double resolutionValue = harmonicResolutionObjective.getResolutionValue(harmonies);
		assertEquals(0.5, resolutionValue, 0.0);
	}
	
	@Test
	public void testConsonant() {
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).positionWeight(4.0).build());
		notes.add(note().pos(0).pc(4).positionWeight(1.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, DurationConstants.QUARTER));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.HALF).pc(4).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, DurationConstants.HALF));
		
		harmonies.forEach(h -> h.toChord());
		
		double resolutionValue = harmonicResolutionObjective.getResolutionValue(harmonies);
		assertEquals(1.0, resolutionValue, 0.0);
	}

}


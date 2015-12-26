package cp.objective.harmony;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.generator.MusicProperties;
import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
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
		notes.add(note().pos(12).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(12).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, 12));
		
		notes = new ArrayList<>();
		notes.add(note().pos(24).pc(4).positionWeight(1.0).build());
		notes.add(note().pos(24).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, 24));
		
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
		notes.add(note().pos(12).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(12).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, 12));
		
		notes = new ArrayList<>();
		notes.add(note().pos(24).pc(4).positionWeight(1.0).build());
		notes.add(note().pos(24).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, 24));
		
		harmonies.forEach(h -> h.toChord());
		
		double resolutionValue = harmonicResolutionObjective.getResolutionValue(harmonies);
		assertEquals(1.0, resolutionValue, 0.0);
	}

}


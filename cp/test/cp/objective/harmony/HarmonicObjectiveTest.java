package cp.objective.harmony;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.generator.MusicProperties;
import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class HarmonicObjectiveTest extends JFrame {
	
	@Autowired
	@InjectMocks
	private HarmonicObjective harmonicObjective;
	@Autowired
	private MusicProperties musicProperties;
	@Mock
	private Dissonance dissonance;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetHarmonyWeights() {
		when(dissonance.getDissonance(Mockito.any(Chord.class))).thenReturn(1.0);
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(0).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(0).pitch(62).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(59).positionWeight(3.0).build());
		harmonies.add(new CpHarmony(notes, DurationConstants.QUARTER));
		
		double totalHarmonyWeight = harmonicObjective.getHarmonyWeights(harmonies);
		assertEquals((7.0/13.0 + 6.0/13.0)/harmonies.size(), totalHarmonyWeight, 0);
	}

	@Test
	public void testGetTotalHarmonyWeight() {
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).positionWeight(3.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		double totalHarmonyWeight = harmonicObjective.getTotalHarmonyWeight(harmonies);
		assertEquals(13, totalHarmonyWeight, 0);
	}
}

package cp.objective.harmony;

import cp.DefaultConfig;
import cp.model.dissonance.TonalSetClassDissonance;
import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class HarmonicObjectiveTest extends JFrame {

	private static final Logger LOGGER = LoggerFactory.getLogger(HarmonicObjectiveTest.class);
	
	@Autowired
	private HarmonicObjective harmonicObjective;
    @MockBean
	private TonalSetClassDissonance dissonance;
	
	@BeforeEach
	public void setup() {
		harmonicObjective.setDissonance(dissonance);
	}

	@Test
	public void testGetTotalHarmonyWeight() {
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(2).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(7).positionWeight(3.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		double totalHarmonyWeight = harmonicObjective.getTotalHarmonyWeight(harmonies);
		assertEquals(13, totalHarmonyWeight, 0);
	}

	@Test
	public void testHarmonyObjective() {
		when(dissonance.getDissonance(any())).thenReturn(1.0);
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(4).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(7).positionWeight(1.0).build());
		CpHarmony harmony = new CpHarmony(notes, 0);
		harmony.toChord();
		harmonies.add(harmony);

		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pc(11).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(7).positionWeight(1.0).build());
		harmony = new CpHarmony(notes, DurationConstants.QUARTER);
		harmony.toChord();
		harmonies.add(harmony);

		double totalHarmonyWeight = harmonicObjective.getHarmonyWeights(harmonies);
		LOGGER.info("totalHarmonyWeight: " + totalHarmonyWeight);
		assertEquals(1.0, totalHarmonyWeight, 0);
	}
}

package cp.objective.harmony;

import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("HumanizerTest")
public class HarmonicResolutionObjectiveTest {

    @InjectMocks
	private HarmonicResolutionObjective harmonicResolutionObjective;
	@Mock
	private DissonantResolution dissonantResolution;
	
	@BeforeEach
	public void setup() {

	}

	@Test
	public void testNoHarmonieHasResolution() {
		when(dissonantResolution.isDissonant(Mockito.any())).thenReturn(Boolean.TRUE);
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).positionWeight(4.0).build());
		notes.add(note().pos(0).pc(1).positionWeight(1.0).build());
		harmonies.add(new CpHarmony(notes, 0));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, DurationConstants.QUARTER));
		
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.HALF).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(1).positionWeight(2.0).build());
		harmonies.add(new CpHarmony(notes, DurationConstants.HALF));
		
		harmonies.forEach(h -> h.toChord());
		
		double resolutionValue = harmonicResolutionObjective.getResolutionValue(harmonies);
        assertEquals(1.0, resolutionValue, 0.0);
	}

	@Test
	public void testAllConsonantResolution() {
//		when(dissonantResolution.isDissonant(any())).thenReturn(Boolean.FALSE);
		List<CpHarmony> harmonies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).positionWeight(4.0).build());
		notes.add(note().pos(0).pc(4).positionWeight(1.0).build());
		harmonies.add(new CpHarmony(notes, 0));

		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pc(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).positionWeight(2.0).build());
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


package cp.nsga.operator.mutation.melody;

import static cp.model.melody.HarmonicMelodyBuilder.harmonicMelody;
import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class MelodyNoteToHarmonyNoteTest extends AbstractTest{
	
	@Autowired
	private MelodyNoteToHarmonyNote melodyNoteToHarmonyNote;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMutateMelodyNoteToHarmonyNote() {
		List<Harmony> harmonies = new ArrayList<>();
		HarmonicMelody harmonicMelody = harmonicMelody()
					.harmonyNote(note().pc(0).ocatve(5).build())
					.voice(0)
					.notes(note().voice(0).pc(4).pos(0).len(6).build(), 
						   note().voice(0).pc(5).pos(6).len(12).build())
					.build();
		List<HarmonicMelody> harmonicMelodies = new ArrayList<>();
		harmonicMelodies.add(harmonicMelody);
		Harmony harmony = new Harmony(0, 12, harmonicMelodies);
		harmonies.add(harmony);
		List<Integer> indexes = Arrays.asList(1,2,3);
		melodyNoteToHarmonyNote.setAllowedMelodyMutationIndexes(indexes);
		melodyNoteToHarmonyNote.mutateMelodyNoteToHarmonyNote(harmonies);
	}

}

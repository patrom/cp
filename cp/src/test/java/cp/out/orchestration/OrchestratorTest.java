package cp.out.orchestration;

import cp.DefaultConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class OrchestratorTest {
	
	@Autowired
	private Orchestrator ochestrator;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOrchestrate() throws Exception {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).pitch(62).ocatve(5).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).ocatve(5).len(DurationConstants.HALF).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(4).pitch(64).ocatve(5).len(DurationConstants.HALF).build());
		CpMelody melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodyBlocks.add(melodyBlock);
		ochestrator.orchestrate(melodyBlocks, "test");
	}

}

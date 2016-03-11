package cp.out.orchestration;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
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
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).len(12).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).len(12).build());
		notes.add(note().pos(24).pc(4).pitch(64).ocatve(5).len(24).build());
		notes.add(note().pos(48).pc(4).pitch(64).ocatve(5).len(24).build());
		CpMelody melody = new CpMelody(notes, 1, 0, 60);
		MelodyBlock melodyBlock = new MelodyBlock(5,1);
		melodyBlock.addMelodyBlock(melody);
		melodyBlocks.add(melodyBlock);
		ochestrator.orchestrate(melodyBlocks);
	}

}

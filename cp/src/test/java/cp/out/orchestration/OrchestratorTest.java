package cp.out.orchestration;

import cp.DefaultConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
public class OrchestratorTest {
	
	@Autowired
	private Orchestrator ochestrator;

	@Test
	public void testOrchestrate() {
//		List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).octave(5).len(DurationConstants.QUARTER).build());
//		notes.add(note().pos(DurationConstants.QUARTER).pc(2).pitch(62).octave(5).len(DurationConstants.QUARTER).build());
//		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());
//		notes.add(note().pos(DurationConstants.WHOLE).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());
//		CpMelody melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
//		MelodyBlock melodyBlock = new MelodyBlock(5,1);
//		melodyBlock.addMelodyBlock(melody);
//		melodyBlocks.add(melodyBlock);
//		ochestrator.orchestrate(melodyBlocks, "rowMatrix");
	}

}

package cp.out.orchestration;

import cp.DefaultConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.orchestration.quality.PleasantGreen;
import cp.out.play.InstrumentMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class OrchestratorTest {
	
	@Autowired
	private Orchestrator ochestrator;
    @Autowired
	private PleasantGreen pleasantGreen;

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

    @Test
    public void testOrchestrateMeldoy() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).pitch(62).octave(5).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());

        OrchestralQuality orchestralQuality = pleasantGreen;
//        List<Instrument> instruments = orchestralQuality.getBasicInstruments();
//        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.WOODWINDS);
        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(Arrays.asList(InstrumentGroup.WOODWINDS, InstrumentGroup.ORCHESTRAL_STRINGS));
        Orchestra orchestra = ochestrator.orchestrateMelody(notes, DurationConstants.WHOLE * 2, instruments, instruments.size());
        Map<InstrumentMapping, List<Note>> orchestraMap = orchestra.getOrchestra();
        for (Map.Entry<InstrumentMapping, List<Note>> entry : orchestraMap.entrySet()) {
            Instrument instrument = entry.getKey().getInstrument();
            System.out.println(instrument.getInstrumentName());
            entry.getValue().forEach(note -> System.out.println(note));
            System.out.println(
            );
        }
    }

}

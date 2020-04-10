package cp.out.orchestration;

import cp.DefaultConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.quality.GoldenOrange;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.orchestration.quality.PleasantGreen;
import cp.out.orchestration.quality.RichBlue;
import cp.out.play.InstrumentMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static cp.model.note.NoteBuilder.note;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class OrchestratorTest {
	
	@Autowired
	private Orchestrator ochestrator;
    @Autowired
	private PleasantGreen pleasantGreen;
    @Autowired
    private GoldenOrange goldenOrange;
    @Autowired
    private RichBlue richBlue;

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
    public void testOrchestrateMelody() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).pitch(62).octave(5).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());

        OrchestralQuality orchestralQuality = goldenOrange;
//        List<Instrument> instruments = orchestralQuality.getBasicInstruments();
//        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.WOODWINDS);
        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(Arrays.asList(InstrumentGroup.BRASS));
        Orchestra orchestra = ochestrator.orchestrateMelody(notes, DurationConstants.WHOLE * 2, instruments, 2);
        Map<InstrumentMapping, List<Note>> orchestraMap = orchestra.getOrchestra();
        for (Map.Entry<InstrumentMapping, List<Note>> entry : orchestraMap.entrySet()) {
            Instrument instrument = entry.getKey().getInstrument();
            System.out.println(instrument.getInstrumentName());
            entry.getValue().forEach(note -> System.out.println(note));
            System.out.println(
            );
        }
    }

    @Test
    public void testOrchestrateMeldoyCloseCombinations() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).octave(5).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(2).pitch(62).octave(5).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());
        notes.add(note().pos(DurationConstants.WHOLE).pc(4).pitch(64).octave(5).len(DurationConstants.HALF).build());

        OrchestralQuality orchestralQuality = pleasantGreen;
        OrchestralQuality orchestralQuality2 = richBlue;
//        List<Instrument> instruments = orchestralQuality.getBasicInstruments();
//        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(InstrumentGroup.WOODWINDS);
        List<Instrument> instruments = orchestralQuality.getBasicInstrumentsByGroup(Arrays.asList(InstrumentGroup.ORCHESTRAL_STRINGS));
        List<Instrument> instruments2 = orchestralQuality2.getBasicInstrumentsByGroup(Arrays.asList(InstrumentGroup.ORCHESTRAL_STRINGS));
//        List<Instrument> instruments2 = Collections.singletonList(orchestralQuality.getBasicInstrument(InstrumentName.FLUTE.getName()));
        Orchestra orchestra = ochestrator.orchestrateMelodyCloseCombinations(notes, DurationConstants.WHOLE * 2,
                instruments, 2, instruments2, 2);
        Map<InstrumentMapping, List<Note>> orchestraMap = orchestra.getOrchestra();
        for (Map.Entry<InstrumentMapping, List<Note>> entry : orchestraMap.entrySet()) {
            Instrument instrument = entry.getKey().getInstrument();
            System.out.println(instrument.getInstrumentName());
            entry.getValue().forEach(note -> System.out.println(note));
            System.out.println();
        }
    }

}

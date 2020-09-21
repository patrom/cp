package cp.generator;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.config.InstrumentConfig;
import cp.midi.MelodyInstrument;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import cp.out.instrument.strings.Viola;
import cp.out.play.InstrumentMapping;
import cp.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
public class PitchSetGeneratorTest extends AbstractTest {

    @Autowired
    private PitchSetGenerator pitchSetGenerator;
    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private TnTnIType type;

    @Test
    void generatePSC() {
        List<List<Integer>> psc = pitchSetGenerator.generatePSC("5-15");
        for (List<Integer> spacings : psc) {
            spacings.forEach(integer -> System.out.print(integer + ", "));
            System.out.println();
            System.out.println("spacing sum: " + spacings.stream().mapToInt(Integer::intValue).sum());
        }

    }

    @Test
    void generatePsets() throws IOException, InvalidMidiDataException {
        Set[] prime = type.prime6;
        for (int i = 0; i < prime.length; i++) {
            String forteName = prime[i].name;

            List<List<Note>> psets = pitchSetGenerator.generatePsets(0, forteName, DurationConstants.WHOLE, 50);
//            for (List<Note> pset : psets) {
//                pset.forEach(note -> System.out.print(note));
//                System.out.println();
//            }
            Map<Integer, List<Note>> notesPerVoice = psets.stream().flatMap(notes -> notes.stream()).collect(Collectors.groupingBy(note -> note.getVoice()));
//        List<MelodyInstrument> melodyInstruments = new ArrayList<>();
//        for (Map.Entry<Integer, List<Note>> entry : notesPerVoice.entrySet()) {
//            InstrumentMapping instrumentMappingForVoice = instrumentConfig.getInstrumentMappingForVoice(entry.getKey());
//            MelodyInstrument melodyInstrument = new MelodyInstrument();
//            melodyInstrument.setNotes(entry.getValue());
//            melodyInstrument.setInstrumentMapping(instrumentMappingForVoice);
//            melodyInstruments.add(melodyInstrument);
//        }

//        playOnKontakt(melodyInstruments, 60, 20000);
            List<MelodyBlock> melodyBlocks = new ArrayList<>();
            for (Map.Entry<Integer, List<Note>> entry : notesPerVoice.entrySet()) {
                List<Note> notes = entry.getValue();
                int start = notes.get(0).getPosition();
                int end = start + notes.get(0).getLength();
                CpMelody melody = new CpMelody(notes, entry.getKey(), start, end);
                MelodyBlock melodyBlock = new MelodyBlock(5, entry.getKey());
                melodyBlock.addMelodyBlock(melody);
                melodyBlocks.add(melodyBlock);
            }

            writeMidi(melodyBlocks, forteName);
        }

    }
}
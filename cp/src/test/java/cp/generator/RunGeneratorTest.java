package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.ContourType;
import cp.config.BeatGroupConfig;
import cp.midi.MidiDevicesUtil;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class RunGeneratorTest {

    @Autowired
    private RunGenerator runGenerator;
    @Autowired
    private MidiDevicesUtil midiDevicesUtil;
    @Autowired
    private PCGenerator pcGenerator;

    @Test
    void generateRun() {
        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesAsList();
        List<Integer> run = runGenerator.generateExhaustiveRun(pitchClasses, 10, 3);
       run.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    void generateRandomRun() {
        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesAsList();
        List<Integer> run = runGenerator.generateRandomRun(pitchClasses, 10, 5);
        run.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    void generateMelodyRun() throws IOException, InvalidMidiDataException {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("6-7", 5);
//        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesAsList();
        for (int i = 0; i < 20; i++) {
            int size = RandomUtil.getRandomNumberInRange(6, 10);
            int rests = RandomUtil.getRandomNumberInRange(0, 3);
            CpMelody melody = runGenerator.generateMelodyRun(pitchClasses, size, rests, DurationConstants.SIXTEENTH, ContourType.ASC);
            melody.updatePitchesFromContour(5);
//            melody.getNotes().forEach(note -> System.out.println(note));
            MelodyBlock melodyBlock = new MelodyBlock(5, 0);
            melodyBlock.addMelodyBlock(melody);
            writeMidi(Collections.singletonList(melodyBlock), String.valueOf(i));
        }
    }

    private void writeMidi(List<MelodyBlock> melodyBlocks, String id) throws IOException, InvalidMidiDataException {
        Sequence sequence = midiDevicesUtil.createSequence(melodyBlocks, 100);
        Resource resource = new FileSystemResource("");
        midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "src/main/resources/midi/" + id + ".mid");
    }

}
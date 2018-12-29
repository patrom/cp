package cp.out.orchestration;

import cp.DefaultConfig;
import cp.config.OrchestraConfig;
import cp.midi.MelodyInstrument;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class ChordOchestraTest {

    @Autowired
    private ChordOchestra chordOchestra;
    @Autowired
    private OrchestraConfig orchestraConfig;

    @Autowired
    private MidiDevicesUtil midiDevicesUtil;

    @Test
    public void generateChordOrchestra() throws InvalidMidiDataException, IOException {
        int duration = DurationConstants.SIX_EIGHTS * 1;
        chordOchestra.generateChordsOrchestra("4-28", duration, 10,  0);

        List<MelodyInstrument> melodyInstruments = orchestraConfig.getMelodyInstruments();
        for (MelodyInstrument melodyInstrument : melodyInstruments) {
            System.out.println(melodyInstrument.getInstrumentMapping().getInstrument().getInstrumentName());
            for (Note note : melodyInstrument.getNotes()) {
                System.out.println(note.getPitch() + ", " + note.getPosition());
            }
        }

        writeMidiFile(melodyInstruments);
    }

    private void writeMidiFile(List<MelodyInstrument> melodyInstruments) throws InvalidMidiDataException, IOException {
        Sequence sequence = midiDevicesUtil.createSequenceGeneralMidi(melodyInstruments, 60, false);
        Resource outResource = new FileSystemResource("");
        midiDevicesUtil.write(sequence, outResource.getFile().getPath() + "src/main/resources/orch/testorch2.mid");
    }

    @Test
    public void generateChords() throws InvalidMidiDataException, IOException {
        int duration = DurationConstants.SIX_EIGHTS * 4;
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pitch(60).build());
        notes.add(NoteBuilder.note().pitch(64).build());
        notes.add(NoteBuilder.note().pitch(67).build());
//        notes.add(NoteBuilder.note().pitch(72).build());
//        notes.add(NoteBuilder.note().pitch(76).build());
//        notes.add(NoteBuilder.note().pitch(79).build());
//        notes.add(NoteBuilder.note().pitch(84).build());
        notes.forEach(note -> {
            note.setLength(duration);
            note.setDisplayLength(duration);
        });
        chordOchestra.generateChordsOrchestra(notes, duration, 10,  2);

        List<MelodyInstrument> melodyInstruments = orchestraConfig.getMelodyInstruments();
        for (MelodyInstrument melodyInstrument : melodyInstruments) {
            System.out.println(melodyInstrument.getInstrumentMapping().getInstrument().getInstrumentName());
            for (Note note : melodyInstrument.getNotes()) {
                System.out.println(note.getPitch() + ", " + note.getPosition());
            }
        }

        writeMidiFile(melodyInstruments);
    }
}
package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.orchestra.ViennaOrchestra;
import cp.out.print.MusicXMLWriter;
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
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class ChordVoicingGeneratorTest {

    @Autowired
    private ChordVoicingGenerator chordVoicingGenerator;
    @Autowired
    private MusicXMLWriter musicXMLWriter;
    @Autowired
    private MidiDevicesUtil midiDevicesUtil;

    @Autowired
    private TnTnIType type;

    @Test
    public void generate() throws IOException, XMLStreamException, InvalidMidiDataException {
        List<Note> notes = chordVoicingGenerator.generateMaxSpreadVoicingsLowerThanOctave(60, "4-20",5, 5,3);

        Orchestra orchestra = new ViennaOrchestra();
        orchestra.setPiano(notes);
        Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), 60);
        Resource resource = new FileSystemResource("");
        midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "src/main/resources/orch/chordVoicing.mid");

//        Resource resourceOrch = new FileSystemResource("");
//        String path = resourceOrch.getFile().getPath() + "src/main/resources/orch/";
//        musicXMLWriter.createXML(new FileOutputStream(path  + "chordVoicing.xml"), orchestra.getOrchestra());
    }

    @Test
    public void generateMaxSpreadVoicingsLowerThanOctave() throws IOException, XMLStreamException, InvalidMidiDataException{
        Set[] prime = type.prime5;
        for (Set set : prime) {
            System.out.println(set.name);
            List<Note> notes = chordVoicingGenerator.generateMaxSpreadVoicingsLowerThanOctave(set.name, 48);
            notes.forEach(note -> System.out.println(note.toStringDebug()));

            Orchestra orchestra = new ViennaOrchestra();
            orchestra.setPiano(notes);
            Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), 60);
            Resource resource = new FileSystemResource("");
            midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "src/main/resources/orch/" + set.name + ".mid");
        }
    }

    @Test
    public void generateMaxSpreadVoicingsLowerThanOctave2() throws IOException, XMLStreamException, InvalidMidiDataException{
        Set[] prime = type.prime3;
        for (Set set : prime) {
            System.out.println(set.name);
            List<Note> notes = chordVoicingGenerator.generateMaxSpreadVoicingsLowerThanOctave2(set.name, 48);
            notes.forEach(note -> System.out.println(note.toStringDebug()));

            Orchestra orchestra = new ViennaOrchestra();
            orchestra.setPiano(notes);
            Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), 60);
            Resource resource = new FileSystemResource("");
            midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "src/main/resources/orch/" + set.name + ".mid");
        }
    }

}
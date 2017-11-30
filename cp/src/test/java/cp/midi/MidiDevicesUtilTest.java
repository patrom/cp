package cp.midi;

import cp.DefaultConfig;
import cp.config.InstrumentConfig;
import cp.generator.MusicProperties;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.play.InstrumentMapping;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class MidiDevicesUtilTest {

    @Autowired
    @InjectMocks
    private MidiDevicesUtil midiDevicesUtil;

    @Mock
    private InstrumentConfig instrumentConfig;
    @Mock
    private MusicProperties musicProperties;

    private InstrumentMapping instrumentMapping;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        instrumentMapping = new InstrumentMapping(new ViolinSolo(), 1, 0);
        when(musicProperties.getTempo()).thenReturn(60);
    }

    @Test
    public void playOnDevice()  {
    }

    @Test
    public void createSequence() throws InvalidMidiDataException, IOException {
        when(instrumentConfig.getInstrumentMappingForVoice(anyInt())).thenReturn(instrumentMapping);
        MelodyBlock melodyBlock = new MelodyBlock(5, 0);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pitch(60).pc(0).dyn(Dynamic.MF).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.SIXTEENTH).pitch(67).pc(7).dyn(Dynamic.MF).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.EIGHT).pitch(64).pc(4).dyn(Dynamic.MF).len(DurationConstants.SIXTEENTH).art(Articulation.STACCATO)
            .dep(createDependantHarmony(ChordType.CH2_KLEINE_SIXT_CHR)).build());
        notes.add(note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pitch(62).pc(2).dyn(Dynamic.MF).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.QUARTER).pitch(62).pc(2).dyn(Dynamic.MF).len(DurationConstants.QUARTER).tech(Technical.LEGATO)
                .dep(createDependantHarmony(ChordType.CH2_GROTE_TERTS)).build());
        notes.add(note().pos(DurationConstants.HALF).pitch(64).pc(4).dyn(Dynamic.F).len(DurationConstants.QUARTER)
            .dep(createDependantHarmony(ChordType.CH2_GROTE_TERTS)).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pitch(65).pc(5).dyn(Dynamic.MF).len(DurationConstants.QUARTER).tech(Technical.TREMELO).build());
        CpMelody melody = new CpMelody(notes, 0, 0 , DurationConstants.WHOLE);
        melodyBlock.addMelodyBlock(melody);
        write(melodyBlock, "createSequence");
    }

    private DependantHarmony createDependantHarmony(ChordType chordType){
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(chordType);
        return  dependantHarmony;
    }

    private void write(MelodyBlock melodyBlock, String midiFileName) throws InvalidMidiDataException, IOException {
        final Sequence sequence = midiDevicesUtil.createSequence(Collections.singletonList(melodyBlock), 60);
        Resource resource = new FileSystemResource("");
        midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "src/main/resources/test/" + midiFileName + ".mid");
        //        midiDevicesUtil.playOnDevice(sequence, 60 , MidiDevicePlayer.KONTAKT );
    }

}
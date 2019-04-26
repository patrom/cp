package cp.midi;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.config.InstrumentConfig;
import cp.generator.MusicProperties;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.play.InstrumentMapping;
import cp.out.print.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class MidiDevicesUtilTest {

    @Autowired
    private MidiDevicesUtil midiDevicesUtil;

    @MockBean(name = "instrumentConfig")
    private InstrumentConfig instrumentConfig;
    @MockBean(name = "musicProperties")
    private MusicProperties musicProperties;
    @MockBean
    private TimeLine timeLine;
    @Autowired
    private Keys keys;

    private InstrumentMapping instrumentMapping;
    @MockBean(name = "fourVoiceComposition")
    private Composition composition;

    @BeforeEach
    public void setUp() throws Exception {
        instrumentMapping = new InstrumentMapping(new ViolinSolo(), 1, 0);
        when(musicProperties.getTempo()).thenReturn(60);
        TimeLineKey timeLineKey = new TimeLineKey(keys.A, Scale.MAJOR_SCALE);
        when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
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
//        write(melodyBlock, "createSequence");
    }

    private DependantHarmony createDependantHarmony(ChordType chordType){
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(chordType);
        return  dependantHarmony;
    }

    private void write(MelodyBlock melodyBlock, String midiFileName) throws InvalidMidiDataException, IOException {
        final Sequence sequence = midiDevicesUtil.createSequence(Collections.singletonList(melodyBlock), 60);
        Resource resource = new FileSystemResource("");
        midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "src/main/resources/rowMatrix/" + midiFileName + ".mid");
        //        midiDevicesUtil.playOnDevice(sequence, 60 , MidiDevicePlayer.KONTAKT );
    }


    @Test
    public void createVelocityCurve(){
        int notePosition = 0;
        int noteLength = DurationConstants.QUARTER;
//        int split = RandomUtil.getRandomNumberInRange(5, noteLength - 10);
        int split = 110;
        System.out.println(split);
        int peakLevel = 10;
        int positionPerLevelUp =  split / peakLevel;
        System.out.println(positionPerLevelUp);
        int positionPerLevelDown = (noteLength - split) / peakLevel;
        System.out.println(positionPerLevelDown);
        for (int i = 1; i < peakLevel + 1; i++) {
            notePosition = notePosition + positionPerLevelUp;
            System.out.print("level " + i + ", pos :");
            System.out.println(notePosition);
        }
        for (int i = peakLevel - 1; i >= 0; i--) {
            notePosition = notePosition + positionPerLevelDown;
            System.out.print("level " + i  + ", pos :");
            System.out.println(notePosition);
        }
    }



}
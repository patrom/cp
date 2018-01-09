package cp.nsga.operator.mutation.melody;

import cp.DefaultConfig;
import cp.composition.beat.BeatGroupTwo;
import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.generator.provider.MelodyProvider;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 14/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class ProvidedMutationTest {

    @Autowired
    @InjectMocks
    private ProvidedMutation providedMutation;

    @Mock
    private VoiceConfig voiceConfig;
    @Mock
    private TextureConfig textureConfig;
    @Mock
    @Qualifier(value = "melodyManualProvider")
    protected MelodyProvider melodyProvider;
    @Mock
    private MelodyTransformer melodyTransformer;
    @Mock
    private TimeLine timeLine;
    @Autowired
    private Keys keys;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        List<CpMelody> melodyList = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).octave(5).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.SIXTEENTH).pc(2).pitch(62).octave(5).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(4).pitch(64).octave(5).len(DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(notes, 1, 0, DurationConstants.QUARTER);
        melody.setNotesSize(3);
        melody.setTonality(Tonality.TONAL);
        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melodyList.add(melody);
        when(melodyProvider.getMelodies(0)).thenReturn(melodyList);
        TimeLineKey timeLineKey = new TimeLineKey(keys.E, Scale.MAJOR_SCALE,0 ,DurationConstants.WHOLE);
        when(timeLine.getTimeLineKeyAtPosition(Mockito.anyInt(), Mockito.anyInt())).thenReturn(timeLineKey);
        ReflectionTestUtils.setField(providedMutation, "probabilityProvided", 1.0);
    }

    @Test
    public void execute() {
        BeatGroupTwo beatGroupTwo = new BeatGroupTwo(DurationConstants.QUARTER);
        int start = 0;
        CpMelody melody = new CpMelody(new ArrayList<>(), 0, start, start + DurationConstants.QUARTER);
        melody.setBeatGroup(beatGroupTwo);
        MelodyBlock melodyBlock = new MelodyBlock(5,0);
        melodyBlock.addMelodyBlock(melody);
        Motive motive = new Motive(Collections.singletonList(melodyBlock));
        providedMutation.execute(motive);
        melody.getNotes().forEach(n -> {
            System.out.println(n.getLength());
            System.out.println(n.getVoice());
        });
    }

}
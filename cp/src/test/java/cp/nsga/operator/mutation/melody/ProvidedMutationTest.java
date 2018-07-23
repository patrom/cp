package cp.nsga.operator.mutation.melody;

import cp.DefaultConfig;
import cp.composition.voice.MelodyVoice;
import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.generator.provider.MelodyProvider;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 14/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
public class ProvidedMutationTest {

    @Autowired
    private ProvidedMutation providedMutation;

    @Autowired
    private VoiceConfig voiceConfig;
    @MockBean(name = "textureConfig")
    private TextureConfig textureConfig;
    @MockBean(name = "melodyProvider")
    @Qualifier(value = "melodyManualProvider")
    protected MelodyProvider melodyProvider;
    @MockBean(name = "melodyTransformer")
    private MelodyTransformer melodyTransformer;
    @MockBean(name = "timeLine")
    private TimeLine timeLine;
    @Autowired
    private Keys keys;
    @Autowired
    private MelodyVoice melodyVoice;


    @Before
    public void setUp(){
        List<CpMelody> melodyList = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).octave(5).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.SIXTEENTH).pc(2).pitch(62).octave(5).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(4).pitch(64).octave(5).len(DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(notes, 1, 0, DurationConstants.QUARTER);
        melody.setNotesSize(3);
        melody.setTonality(Tonality.TONAL);
        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melodyList.add(melody);
        when(melodyProvider.getMelodies(0)).thenReturn(melodyList);
        TimeLineKey timeLineKey = new TimeLineKey(keys.E, Scale.MAJOR_SCALE,0 ,DurationConstants.WHOLE);
        when(timeLine.getTimeLineKeyAtPosition(Mockito.anyInt(), Mockito.anyInt())).thenReturn(timeLineKey);
//        when(voiceConfig.getVoiceConfiguration((Mockito.anyInt()))).thenReturn(melodyVoice);
        ReflectionTestUtils.setField(providedMutation, "probabilityProvided", 1.0);
    }

    @Test
    public void execute() {
//        BeatGroupTwo beatGroupTwo = new BeatGroupTwo(DurationConstants.QUARTER);
        int start = 0;
        CpMelody melody = new CpMelody(new ArrayList<>(), 0, start, start + DurationConstants.QUARTER);
//        melody.setBeatGroup(beatGroupTwo);
        providedMutation.execute(melody);
        melody.getNotes().forEach(n -> {
            System.out.println(n.getLength());
            System.out.println(n.getVoice());
        });
    }

}
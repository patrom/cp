package cp.nsga.operator.mutation.melody;

import cp.DefaultConfig;
import cp.composition.beat.BeatGroupTwo;
import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.generator.provider.MelodyProvider;
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
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
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
        when(melodyProvider.getMelodies()).thenReturn(melodyList);
        TimeLineKey timeLineKey = new TimeLineKey(keys.E, Scale.MAJOR_SCALE,0 ,DurationConstants.WHOLE);
        when(timeLine.getTimeLineKeyAtPosition(Mockito.anyInt(), Mockito.anyInt())).thenReturn(timeLineKey);
        ReflectionTestUtils.setField(providedMutation, "probabilityProvided", 1.0);
    }

    @Test
    public void execute() throws Exception {
        BeatGroupTwo beatGroupTwo = new BeatGroupTwo(DurationConstants.QUARTER);
        MelodyBlock melodyBlock = new MelodyBlock(5,0);
        for (int i = 0; i < 4; i++) {
            int start = i * DurationConstants.QUARTER;
            CpMelody melody = new CpMelody(new ArrayList<>(), 0, start, start + DurationConstants.QUARTER);
            melody.setBeatGroup(beatGroupTwo);
            melodyBlock.addMelodyBlock(melody);
        }
        providedMutation.execute(melodyBlock);
        melodyBlock.getMelodyBlocks().forEach(m -> {
            System.out.println(m.getNotes());
            System.out.println(m.getStart());
            System.out.println(m.getEnd());
            System.out.println(m.getVoice());
            assertEquals(DurationConstants.HALF, m.getBeatGroupLength());
        });
    }

}
package cp.nsga.operator.mutation.melody;

import cp.DefaultConfig;
import cp.config.VoiceConfig;
import cp.generator.pitchclass.PassingPitchClasses;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 21/05/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class AllNoteMutationTest {

    @Autowired
    private AllNoteMutation allNoteMutation;

    @MockBean
    private VoiceConfig voiceConfig;
    @Autowired
    private PassingPitchClasses passingPitchClasses;
    @MockBean
    private TimeLine timeLine;
    @Autowired
    private Keys keys;

    @BeforeEach
    public void setUp(){
        ReflectionTestUtils.setField(allNoteMutation, "probabilityAllNote", 1.0);
    }

    @Test
    public void doMutation() {
        when(voiceConfig.getRandomPitchClassGenerator(anyInt())).thenReturn(passingPitchClasses::updatePitchClasses);
        TimeLineKey timeLineKey = new TimeLineKey(keys.A, Scale.MAJOR_SCALE);
        when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.EIGHT).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(9).build());
        notes.add(note().pos(DurationConstants.HALF).pc(4).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(7).build());
        CpMelody melody = new CpMelody(notes, 1, 0 , DurationConstants.WHOLE);
        allNoteMutation.doMutation(melody);
        melody.getNotes().forEach(n -> System.out.println(n));
    }

}
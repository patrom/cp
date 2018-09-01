package cp.generator.pitchclass;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroupConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.out.print.note.Key;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
public class OrderRandomNotePitchClassesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRandomNotePitchClassesTest.class);

    @Autowired
    private OrderRandomNotePitchClasses orderRandomNotePitchClasses;
    @MockBean
    private TimeLine timeLine;
    @Autowired
    private Key D;

    @BeforeEach
    public void setUp() throws Exception {
        TimeLineKey timeLineKey = new TimeLineKey(D, Scale.MAJOR_CHORD, 0, 48);
        when(timeLine.getTimeLineKeyAtPosition(Mockito.anyInt(), Mockito.anyInt())).thenReturn(timeLineKey);
    }

    @Test
    public void testUpdatePitchClasses() {
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pc(0).build());
        notes.add(NoteBuilder.note().pc(0).build());
        notes.add(NoteBuilder.note().pc(0).build());
        notes.add(NoteBuilder.note().pc(0).build());
        notes = orderRandomNotePitchClasses.updatePitchClasses(notes, null);
        LOGGER.info("Notes: " + notes);
        Assertions.assertThat(notes.get(0).getPitchClass()).isEqualTo(0);
        Assertions.assertThat(notes.get(1).getPitchClass()).isEqualTo(4);
        Assertions.assertThat(notes.get(2).getPitchClass()).isEqualTo(7);
        //4 note random
    }
}


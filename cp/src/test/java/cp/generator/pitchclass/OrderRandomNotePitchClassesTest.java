package cp.generator.pitchclass;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroups;
import cp.config.BeatGroupConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.note.Key;
import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Autowired
    private BeatGroups beatGroups;

    private TimeLineKey timeLineKey;

    @BeforeEach
    public void setUp() throws Exception {
        timeLineKey = new TimeLineKey(D, Scale.MAJOR_CHORD, 0, DurationConstants.WHOLE);
    }

    @Test
    public void testUpdatePitchClasses() {
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pc(0).build());
        notes.add(NoteBuilder.note().pc(0).build());
        notes.add(NoteBuilder.note().pc(0).build());
        notes.add(NoteBuilder.note().pc(0).build());
        CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
        melody.setBeatGroup(beatGroups.beatGroupTwo);
        melody.setTimeLineKeys(Collections.singletonList(timeLineKey));
        notes = orderRandomNotePitchClasses.updatePitchClasses(melody);
        LOGGER.info("Notes: " + notes);
        for (Note note : notes) {
            assertTrue(
                    ArrayUtils.contains(note.getTimeLineKey().getScale().getPitchClasses(), note.getTimeLineKey().getPitchClassForKey(note.getPitchClass())),
                    () -> String.format("The scale doesn't contain the pitchClass: %s",  note.getTimeLineKey().getPitchClassForKey(note.getPitchClass()))
            );
        }
    }
}


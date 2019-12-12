package cp.generator.pitchclass;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroups;
import cp.config.BeatGroupConfig;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
public class RepeatingPitchClassesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassingPitchClassesTest.class);

    @Autowired
    private RepeatingPitchClasses repeatingPitchClasses;
    @Autowired
    private Keys keys;
    @Autowired
    private BeatGroups beatGroups;

    private TimeLineKey timeLineKey;

    @BeforeEach
    public void setUp() throws Exception {
        timeLineKey = new TimeLineKey(keys.D, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE);
    }

    @Test
    public void testUpdatePitchClasses() {
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pc(0).pos(0).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.QUARTER).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.HALF).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.HALF + DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
        melody.setBeatGroup(beatGroups.beatGroupTwo);
        melody.setTimeLineKeys(Collections.singletonList(timeLineKey));
        notes = repeatingPitchClasses.updatePitchClasses(melody);
        LOGGER.info("Notes: " + notes);
        int firstPc = notes.get(0).getPitchClass();
        notes.forEach(note -> assertEquals(note.getPitchClass(), firstPc));
        for (Note note : notes) {
            assertTrue(
                    ArrayUtils.contains(timeLineKey.getScale().getPitchClasses(), (note.getPitchClass() - note.getTimeLineKey().getKey().getInterval() + 12) % 12),
                    () -> String.format("The scale doesn't contain the pitchClass: %s", (note.getPitchClass() - note.getTimeLineKey().getKey().getInterval() + 12) % 12)
            );
        }
    }

    @Test
    public void testUpdatePitchClassesMultipleKeys() {
        TimeLineKey timeLineKeyAflat = new TimeLineKey(keys.Aflat, Scale.MAJOR_SCALE, 0, DurationConstants.HALF);
        TimeLineKey timeLineKeyC = new TimeLineKey(keys.C, Scale.MAJOR_SCALE, DurationConstants.HALF, DurationConstants.WHOLE);
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pc(0).pos(0).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.QUARTER).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.HALF).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.HALF + DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
        melody.setBeatGroup(beatGroups.beatGroupTwo);
        ArrayList<TimeLineKey> timeLineKeys = new ArrayList<>();
        timeLineKeys.add(timeLineKeyAflat);
        timeLineKeys.add(timeLineKeyC);
        melody.setTimeLineKeys(timeLineKeys);
        notes = repeatingPitchClasses.updatePitchClasses(melody);
        LOGGER.info("Notes: " + notes);
        for (Note note : notes) {
            assertTrue(
                    ArrayUtils.contains(note.getTimeLineKey().getScale().getPitchClasses(), (note.getPitchClass() - note.getTimeLineKey().getKey().getInterval() + 12) % 12),
                    () -> String.format("The scale doesn't contain the pitchClass: %s",  (note.getPitchClass() - note.getTimeLineKey().getKey().getInterval() + 12) % 12)
            );
        }
    }

}

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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
public class RandomAllPitchClassesTest {

    @Autowired
    private RandomAllPitchClasses randomAllPitchClasses;
    @Autowired
    private Keys keys;
    @Autowired
    private BeatGroups beatGroups;

    private TimeLineKey timeLineKey;

    @BeforeEach
    public void setUp() throws Exception {
        timeLineKey = new TimeLineKey(keys.D, Scale.MAJOR_CHORD, 0, DurationConstants.WHOLE);
    }

    @Test
    public void randomPitchClasses() {
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pc(0).pos(0).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.QUARTER).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.HALF).build());
        notes.add(NoteBuilder.note().pc(0).pos(DurationConstants.HALF + DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
        melody.setBeatGroup(beatGroups.beatGroupTwo);
        melody.setTimeLineKeys(Collections.singletonList(timeLineKey));
        List<Note> randomNotes = randomAllPitchClasses.randomPitchClasses(melody);
        List<Integer> pitchClasses = randomNotes.stream().map(note -> note.getPitchClass()).collect(Collectors.toList());
        Assertions.assertThat(pitchClasses).containsAnyOf(2,6,9);
    }
}
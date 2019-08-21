package cp.generator.pitchclass.combination;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.rhythm.DurationConstants.EIGHT;
import static cp.model.rhythm.DurationConstants.QUARTER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
class PitchClassCombinationRepetitionTest {

    @Autowired
    private PitchClassCombination pitchClassCombination;

    @Test
    void getMelody() {
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(5);
        List<Note> notes = new ArrayList<>();
        notes.add(NoteBuilder.note().pos(0).len(QUARTER).build());
        notes.add(NoteBuilder.note().pos(QUARTER).len(EIGHT).build());
        notes.add(NoteBuilder.note().pos(QUARTER + EIGHT).len(QUARTER).build());
        notes.add(NoteBuilder.note().pos(QUARTER + EIGHT + QUARTER).len(EIGHT).build());
        CpMelody melody = pitchClassCombination.getMelody(pitchClasses, notes);
        melody.getNotes().forEach(note -> System.out.println(note.getPitchClass()));
    }
}
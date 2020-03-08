package cp.composition;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombinations;
import cp.config.BeatGroupConfig;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class MelodicValueRhythmCombinationTest {

    private MelodicValueRhythmCombination melodicValueRhythmCombination = new MelodicValueRhythmCombination();

    @Autowired
    private RhythmCombinations rhythmCombinations;

    @Test
    void getMelodyKcombination() {
        List<Integer> pitchClasses = Scale.SET_3_5.getPitchClassesAsList();
        List<CpMelody> melodies = melodicValueRhythmCombination.getMelodyKcombination(pitchClasses, rhythmCombinations.threeNoteEven::pos234, DurationConstants.QUARTER);
        List<Note> notes = melodies.get(0).getNotes();
        notes.forEach(note -> System.out.println(note.toStringDebug()));

        System.out.println();
        melodies = melodicValueRhythmCombination.getMelodyKcombination(pitchClasses, rhythmCombinations.twoNoteEven::pos12, DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.println(note.toStringDebug()));
            System.out.println();
        }

        System.out.println();
        melodies = melodicValueRhythmCombination.getMelodyKcombination(pitchClasses, rhythmCombinations.fourNoteEven::pos1234, DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.println(note.toStringDebug()));
            System.out.println();
        }
    }
}
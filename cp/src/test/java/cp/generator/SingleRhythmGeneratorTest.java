package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.ContourType;
import cp.composition.MelodicValueMelody;
import cp.config.BeatGroupConfig;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class SingleRhythmGeneratorTest {

    @Autowired
    private MelodyGenerator melodyGenerator;
    @Autowired
    private SingleRhythmGenerator singleRhythmGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;

    @Test
    void generateOstinato() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleRhythmGenerator.generateOstinato(rhythmCombinations, DurationConstants.THREE_EIGHTS, ContourType.ASC, 1,2);
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
            System.out.println();
        }
    }

    @Test
    void generateKpermutationOrderedNoRepetitionASC() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        List<Integer> pitchClasses = Scale.SET_3_5.getPitchClassesAsList();
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleRhythmGenerator.generateKpermutationOrderedNoRepetitionASC(rhythmCombinations, DurationConstants.EIGHT,
                pitchClasses, "3-5");
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
            System.out.println();
        }
    }

    @Test
    void generateKpermutationOrderedNoRepetitionDESC() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        List<Integer> pitchClasses = Scale.SET_3_5.getPitchClassesAsList();
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleRhythmGenerator.generateKpermutationOrderedNoRepetitionDESC(rhythmCombinations, DurationConstants.EIGHT,
                pitchClasses, "3-5");
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
            System.out.println();
        }
    }

    @Test
    void generateTranspositionsPitchClassesForStepsAsc() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.twoNoteEven::pos13);
        int[] steps = Scale.CHROMATIC_SCALE.getPitchClasses();
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleRhythmGenerator.generateTranspositionsPitchClassesForStepsAsc(steps, rhythmCombinations, DurationConstants.EIGHT,
                0, 2);
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
            System.out.println();
        }
    }


    @Test
    void generateBalancedPattern() {
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleRhythmGenerator.generateBalancedPattern(allRhythmCombinations.balancedPattern::pos9N30,
                DurationConstants.SIXTEENTH, "3-1");
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
            System.out.println();
        }
    }

}
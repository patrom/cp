package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombinations;
import cp.composition.MelodicValueRhythmCombination;
import cp.config.BeatGroupConfig;
import cp.model.note.Scale;
import cp.out.print.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class PitchClassGeneratorTest {

    @Autowired
    private PitchClassGenerator pitchClassGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;

    @Test
    public void getRandomPitchClassesForForteName() {
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) pitchClassGenerator.getRandomPitchClassesForForteName( "6-Z4", 2);
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            permutationsPitchClass.forEach(integer -> System.out.println(integer));
            System.out.println();
        }
    }

    @Test
    public void getRandomPitchClassesForForteNameInSuperSetClass(){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) pitchClassGenerator.getSetClassesForForteNameInSuperSetClass( "6-Z6", "3-5", 0);
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            permutationsPitchClass.forEach(integer -> System.out.println(integer));
            System.out.println();
        }
    }

    @Test
    public void getSetClassesAndRetrogradeForForteNameInSuperSetClass(){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) pitchClassGenerator.getSetClassesAndRetrogradeForForteNameInSuperSetClass( "7-35", "4-11", 11);
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            permutationsPitchClass.forEach(integer -> System.out.println(integer));
            System.out.println();
        }
    }

    @Test
    void generateKcombinationOrderedWithRepetition() {
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) pitchClassGenerator.generateKcombinationOrderedWithRepetition( "3-5", 0, 0,6,0,1);
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            permutationsPitchClass.forEach(integer -> System.out.println(integer));
            System.out.println();
        }
    }

    @Test
    void allPermutationsForSetClassInSuperSetClass() {
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) pitchClassGenerator.allPermutationsForSetClassInSuperSetClass("5-28", "5-28", 1);
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        System.out.println("Size :" + permutationsPitchClasses.size());
//        Assertions.assertEquals(12, permutationsPitchClasses.size());
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            permutationsPitchClass.forEach(integer -> System.out.print(integer + ", "));
            System.out.println();
        }
    }
}
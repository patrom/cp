package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombinations;
import cp.composition.MelodicValueRhythmCombination;
import cp.config.BeatGroupConfig;
import cp.model.harmony.Chord;
import cp.out.print.Keys;
import org.apache.commons.collections4.IterableGet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class CombinationGeneratorTest {

    @Autowired
    private CombinationGenerator combinationGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;
    @Autowired
    private PCGenerator pcGenerator;

    @Test
    public void getRandomPitchClassesForForteName() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("6-Z4", 0);
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getShuffledPitchClasses(pitchClasses);
        printMelodicValue(melodicValue);
    }

    @Test
    public void getRandomPitchClassesForForteNameInSuperSetClass(){
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("7-35", 11);
        printPitchclasses(pitchClasses);
//        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("6-Z6");
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass(pitchClasses, "3-2");
        printMelodicValue(melodicValue);
        System.out.println("----");
        pitchClasses = pcGenerator.getInversionPitchClasses("7-35", 7);
        printPitchclasses(pitchClasses);
        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "3-2");
        printMelodicValue(melodicValue);
    }

    private void printPitchclasses(List<Integer> pitchClasses) {
        pitchClasses.forEach(integer -> System.out.print(integer + ","));
        System.out.println();
    }

    private void printMelodicValue(MelodicValueRhythmCombination melodicValue) {
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            permutationsPitchClass.forEach(integer -> System.out.print(integer + ", "));
            System.out.println();
        }
    }

    @Test
    public void getSetClassesAndRetrogradeForForteNameInSuperSetClass(){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesAndRetrogradeForForteNameInSuperSetClass( "7-35", "4-11", 11);
        printMelodicValue(melodicValue);
    }

    @Test
    void generateKcombinationOrderedWithRepetition() {
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.generateKcombinationOrderedWithRepetition( "3-5", 0, 0,6,0,1);
        printMelodicValue(melodicValue);
    }

    @Test
    void allPermutationsForSetClassInSuperSetClass() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("8-23", 9);
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "3-11");
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        System.out.println("Size :" + permutationsPitchClasses.size());
//        Assertions.assertEquals(12, permutationsPitchClasses.size());
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            Chord chord = new Chord(permutationsPitchClass, permutationsPitchClass.get(0));
            System.out.println(chord.getChordType());
            permutationsPitchClass.forEach(integer -> System.out.print(integer + ", "));
            System.out.println();
        }
    }
}
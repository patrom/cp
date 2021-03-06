package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombinations;
import cp.composition.MelodicValueRhythmCombination;
import cp.config.BeatGroupConfig;
import cp.model.harmony.Chord;
import cp.out.print.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

    @Test
    public void getRandomPitchClassesForForteNameInSuperSetClass2(){
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("7-Z37", 0);
        printPitchclasses(pitchClasses);
//        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("6-Z6");
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass(pitchClasses, "4-3");
        printMelodicValue(melodicValue);
        System.out.println("----");
        pitchClasses = pcGenerator.getInversionPitchClasses("7-Z37", 0);
        printPitchclasses(pitchClasses);
        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "4-17");
        printMelodicValue(melodicValue);
    }

    @Test
    public void getRandomPitchClassesForForteNameInSuperSetClassOctatonic(){
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("8-28", 0);
        printPitchclasses(pitchClasses);
//        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("6-Z6");
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass(pitchClasses, "3-2");
        printMelodicValue(melodicValue);
        System.out.println("----");
        pitchClasses = pcGenerator.getInversionPitchClasses("8-28", 0);
        printPitchclasses(pitchClasses);
        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "3-2");
        printMelodicValue(melodicValue);
    }

    @Test
    public void getRandomPitchClassesForForteNameInSuperSetClass4(){
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("5-34", 0);
        printPitchclasses(pitchClasses);
//        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("6-Z6");
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass(pitchClasses, "3-6");
        printMelodicValue(melodicValue);
        System.out.println("----");
        pitchClasses = pcGenerator.getInversionPitchClasses("5-34", 9);
        printPitchclasses(pitchClasses);
        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "3-6");
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
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("7-35", 11);//C major
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

    @Test
    void allPermutationsForSetClassInSuperSetClass2() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("5-34", 0);
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "3-6");
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        System.out.println("Size :" + permutationsPitchClasses.size());
//        Assertions.assertEquals(12, permutationsPitchClasses.size());
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
  //          Chord chord = new Chord(permutationsPitchClass, permutationsPitchClass.get(0));
  //          System.out.println(chord.getChordType());
            permutationsPitchClass.forEach(integer -> System.out.print(integer + ", "));
            System.out.println();
        }
    }

    @Test
    void allPassingTonePermutationsInSetClass() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("5-34", 0);
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "2-2");
        List<List<Integer>> permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        System.out.println("Size :" + permutationsPitchClasses.size());
        for (List<Integer> permutationsPitchClass : permutationsPitchClasses) {
            permutationsPitchClass.forEach(integer -> System.out.print(integer + ", "));
            System.out.println();
        }
    }

    @Test
    public void allPermutations(){
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("2-3", 9);
        printPitchclasses(pitchClasses);
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutations(pitchClasses);
        printMelodicValue(melodicValue);
    }
}
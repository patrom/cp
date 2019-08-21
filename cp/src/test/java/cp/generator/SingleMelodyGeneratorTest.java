package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.CompositeRhythmCombination;
import cp.combination.DurationRhythmCombination;
import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.config.BeatGroupConfig;
import cp.generator.pitchclass.combination.PitchClassCombinationRepetition;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
class SingleMelodyGeneratorTest {

    @Autowired
    private SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;

    @Test
    void generateSingleNoteScale() {
    }

    @Test
    void testGenerateSingleNoteScale() {
    }

    @Test
    void generateSingleNote() {
        CpMelody cpMelody = singleMelodyGenerator.generateSingleNote(4, DurationConstants.SIXTEENTH);
        Note note = cpMelody.getNotes().get(0);
        assertEquals(4, note.getPitchClass());
        assertEquals(DurationConstants.SIXTEENTH, note.getLength());
    }

    @Test
    void generatePermutations() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos124);
//        List<CpMelody> melodies = singleMelodyGenerator.generatePermutations(Scale.MAJOR_SCALE, keys.C, 3, rhythmCombinations, DurationConstants.QUARTER);
//        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
//                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
//                .collect(Collectors.joining(","))).collect(Collectors.toList());
//        result.forEach(s -> System.out.println(s));
//        System.out.println(result.size());
//        assertThat(result, hasItems("0,2,4", "2,4,5", "4,5,7", "5,7,9", "7,9,11", "9,11,0" , "11,0,2"));
    }

    @Test
    void getPitchClassForKey() {
        int pitchClassForKey = singleMelodyGenerator.getPitchClassForKey(0, keys.A);
        assertEquals(9, pitchClassForKey);
    }

    @Test
    void generateTranspositions() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        int[] steps = Scale.MAJOR_SCALE.getPitchClasses();
        List<CpMelody> melodies = singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(steps, rhythmCombinations, DurationConstants.QUARTER, 4,5, 0);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
    }

    @Test
    void generateTranspositionsAndInversionsForScale() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        CompositeRhythmCombination compositeRhythmCombination = new CompositeRhythmCombination();
        compositeRhythmCombination.addRhythmCombination(allRhythmCombinations.threeNoteEven::pos134);
        compositeRhythmCombination.addRhythmCombination(allRhythmCombinations.oneNoteEven::pos1);
        rhythmCombinations.add(compositeRhythmCombination);
        List<CpMelody> melodies = singleMelodyGenerator.generateTranspositionsAndInversionsForScale(Scale.OCTATCONIC_01,
                rhythmCombinations, DurationConstants.QUARTER, 4,1, 0, 3);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
    }

    @Test
    void generateTranspositionsForPcs() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        DurationRhythmCombination durationRhythmCombination =
                new DurationRhythmCombination(DurationConstants.QUARTER, DurationConstants.SIXTEENTH, DurationConstants.EIGHT, DurationConstants.HALF);
        rhythmCombinations.add(durationRhythmCombination);
        rhythmCombinations.add(allRhythmCombinations.fourNoteEven::pos1234);
        List<CpMelody> melodies = singleMelodyGenerator.generateTranspositionsForPitchesClasses(Scale.OCTATCONIC_01,
                rhythmCombinations, DurationConstants.QUARTER, 4, 1, 0, 3);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.println(note.getPitchClass() + ", " + note.getPosition()));
            System.out.println();
        }
    }

    @Test
    void generateInversionForPcs() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        CompositeRhythmCombination compositeRhythmCombination = new CompositeRhythmCombination();
        compositeRhythmCombination.addRhythmCombination(allRhythmCombinations.threeNoteEven::pos134);
        compositeRhythmCombination.addRhythmCombination(allRhythmCombinations.oneNoteEven::pos1);
        rhythmCombinations.add(compositeRhythmCombination);
        List<CpMelody> melodies = singleMelodyGenerator.generateInversionsForPitchClasses(Scale.PENTATONIC_SCALE_MINOR,
                rhythmCombinations, DurationConstants.QUARTER, 7, 5, 0, 3);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
    }

    @Test
    public void getMelodyWithRepetition(){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.sixNoteSexTuplet::pos123456);
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(3);
        pitchClasses.add(1);
        pitchClasses.add(11);
        List<CpMelody> melodyWithRepetition = singleMelodyGenerator.getMelodyWithRepetition(pitchClasses, rhythmCombinations, DurationConstants.HALF);
        melodyWithRepetition.forEach(melody -> melody.getNotes().forEach(note -> System.out.println(note.getPitchClass())));
    }

    @Test
    void generatePermutationsWithRepetition() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.fourNoteSexTuplet::pos1356);
        rhythmCombinations.add(allRhythmCombinations.fiveNoteSexTuplet::pos13456);
        List<CpMelody> melodies = singleMelodyGenerator.generatePermutationsWithRepetition(Scale.MAJOR_SCALE, keys.C, 3, rhythmCombinations, DurationConstants.QUARTER);
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        result.forEach(s -> System.out.println(s));
    }

    @Test
    void getMelodyDurations() {
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(5);
//        pitchClasses.add(0);
        CpMelody cpMelody = singleMelodyGenerator.getMelody(pitchClasses, DurationConstants.SIXTEENTH, DurationConstants.EIGHT, DurationConstants.QUARTER, DurationConstants.EIGHT);
        cpMelody.getNotes().forEach(note -> System.out.println(note.getPitchClass()));
    }

    @Test
    void generateTranspositionsAndInversionsWithRhythm() {
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(5);
        pitchClasses.add(0);
        List<CpMelody> melodies = singleMelodyGenerator.generateTranspositionsAndInversionsWithRhythm(pitchClasses, DurationConstants.SIXTEENTH, DurationConstants.EIGHT, DurationConstants.QUARTER, DurationConstants.EIGHT);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
    }

    @Test
    public void testCombinations() {
        List<List<Integer>> combinations = Generator.combination(0,2,4)
                .multi(4)
                .stream()
                .collect(Collectors.toList());

        System.out.println("Multi combinations 3 of 3 symbols (0,2,4):");
        combinations.forEach(System.out::println);

    }

    @Test
    public void generateCombinationNoOrderNoRepetition(){
        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesInKey(keys.Aflat);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        List<CpMelody> melodies = singleMelodyGenerator.generateCombinationNoOrderNoRepetition(pitchClasses, rhythmCombinations,
                3, DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
    }

    @Test
    public void generatePermutationsOrderNoRepetition(){
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(5);
        pitchClasses.add(0);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        List<CpMelody> melodies = singleMelodyGenerator.generatePermutationsOrderedNoRepetition(pitchClasses, rhythmCombinations,
                DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
    }


    @Test
    public void generateKpermutationOrderedNoRepetition(){
        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesInKey(keys.C);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        List<CpMelody> melodies = singleMelodyGenerator.generateKpermutationOrderedNoRepetition(pitchClasses, rhythmCombinations, 3,
                DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
        Assertions.assertEquals(210, melodies.size());
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        assertThat(result, hasItems("0,2,4", "2,4,5", "4,5,7", "5,7,9", "7,9,11", "9,11,0" , "11,0,2"));
    }

    @Test
    public void generateKpermutationOrderedWithRepetition(){
        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesInKey(keys.C);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        List<CpMelody> melodies = singleMelodyGenerator.generateKpermutationOrderedWithRepetition(pitchClasses, rhythmCombinations, 3,
                DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
//        Assertions.assertEquals(210, melodies.size());
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        assertThat(result, hasItems("0,2,2", "2,4,4", "4,5,5", "5,7,7", "7,7,5", "9,11,0" , "11,0,2"));
    }

    @Test
    public void generateKpermutationOrderedWithRepetition2(){
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(0);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.fourNoteEven::pos1234);
        List<CpMelody> melodies = singleMelodyGenerator.generateKpermutationOrderedWithRepetition(pitchClasses, rhythmCombinations, 4,
                DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
//        Assertions.assertEquals(210, melodies.size());
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
//        assertThat(result, hasItems("0,0,4", "4,0,4", "4,0,0", "0,4,0"));
    }

    @Test
    public void generateKcombinationOrderedWithRepetition(){
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(0);
        pitchClasses.add(11);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.fourNoteEven::pos1234);
        List<CpMelody> melodies = singleMelodyGenerator.generateKcombinationOrderedWithRepetition(pitchClasses, rhythmCombinations, 4,
                DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
//        Assertions.assertEquals(210, melodies.size());
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
//        assertThat(result, hasItems("0,0,4", "4,0,4", "4,0,0", "0,4,0"));
    }


}
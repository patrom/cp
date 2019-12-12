package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.rhythm.CompositeRhythmCombination;
import cp.combination.rhythm.DurationRhythmCombination;
import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.MelodicValue;
import cp.composition.MelodicValueMelody;
import cp.composition.MelodicValueRhythmCombination;
import cp.config.BeatGroupConfig;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class SingleMelodyGeneratorTest {

    @Autowired
    private MelodyGenerator melodyGenerator;
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
    void getPitchClassForKey() {
        int pitchClassForKey = singleMelodyGenerator.getPitchClassForKey(0, keys.A);
        assertEquals(9, pitchClassForKey);
    }

    @Test
    void generateTranspositions() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.fourNoteEven::pos1234);
        int[] steps = Scale.MAJOR_SCALE.getPitchClasses();
//        int[] steps = {1,4,5};
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(steps, rhythmCombinations,
                DurationConstants.QUARTER, 0,2,4,5);
        List<CpMelody> melodies = melodicValue.getMelodies();
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        System.out.println(result.size());
//        assertThat(result, hasItems("5,6,1", "8,9,4", "9,10,5"));
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
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        System.out.println(result.size());
        assertThat(result, hasItems("4,1,0,3", "6,3,1,4", "7,4,3,6"));
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
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        System.out.println(result.size());
        assertThat(result, hasItems("7,10,3,0"));
    }

    @Test
    void getMelodyDurations() {
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(5);
        pitchClasses.add(0);
        CpMelody cpMelody = singleMelodyGenerator.getMelody(pitchClasses, DurationConstants.SIXTEENTH, DurationConstants.EIGHT, DurationConstants.QUARTER);
        cpMelody.getNotes().forEach(note -> System.out.println(note.getPitchClass()));
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
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        System.out.println(result.size());
        assertThat(result, hasItems("5,0,4", "0,5,4", "4,5,0"));
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
        assertThat(result, hasItems("0,2,4", "2,4,5", "4,5,7", "5,7,9", "7,9,11", "9,11,0" , "11,0,2", "0,4,7", "4,7,0"));
    }

    @Test
    public void generateKpermutationOrderedNoRepetition2(){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        List<CpMelody> melodies = singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_6_7.getPitchClassesAsList(), rhythmCombinations, 3,
                DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
//        Assertions.assertEquals(210, melodies.size());
        List<String> result = melodies.stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        assertThat(result, hasItems("0,1,6", "1,2,6", "6,1,0", "2,6,1"));
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
        assertThat(result, hasItems("0,0,4,4", "4,0,4,0", "4,0,0,4", "0,4,0,0"));
    }


    @Test
    public void generateKpermutationOrderedWithRepetition3(){
        List<Integer> pitchClasses = Scale.SET_3_5.getPitchClassesAsList();
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
        assertThat(result, hasItems("0,1,6,0", "0,6,1,1"));
    }


    @Test
    public void generateKpermutationOrderedWithRepetition4(){
        List<Integer> pitchClasses =  Arrays.asList(0,4);
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
        assertThat(result, hasItems("0,4,4,0", "0,4,0,4"));
    }


    @Test
    public void generateKcombinationOrderedWithRepetition(){
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(0);
        pitchClasses.add(11);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.fourNoteEven::pos1234);
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleMelodyGenerator.generateKcombinationOrderedWithRepetition(pitchClasses, rhythmCombinations, 4,
                DurationConstants.QUARTER);
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass() + ", "));
            System.out.println();
        }
//        Assertions.assertEquals(210, melodies.size());
        List<String> result = melodicValue.getMelodies().stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
        assertThat(result, hasItems("4,4,0,11", "4,0,0,11", "4,0,11,11"));
    }

    @Test
    public void generateKcombinationOrderedWithRepetition2(){
        List<Integer> pitchClasses = new ArrayList<>();
        pitchClasses.add(4);
        pitchClasses.add(0);
//        pitchClasses.add(11);
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleMelodyGenerator.generateKcombinationOrderedWithRepetition(pitchClasses, rhythmCombinations, 3,
                DurationConstants.THREE_EIGHTS);
//        Assertions.assertEquals(210, melodies.size());
        List<String> result = melodicValue.getMelodies().stream().map(melody -> melody.getNotes().stream()
                .mapToInt(Note::getPitchClass).mapToObj(String::valueOf)
                .collect(Collectors.joining(","))).collect(Collectors.toList());
//        assertThat(result, hasItems("4,4,0,11", "4,0,0,11", "4,0,11,11"));

        List<CpMelody> transposedMelodies = singleMelodyGenerator.transposeMelodies(melodicValue.getMelodies());
        for (CpMelody melody : transposedMelodies) {
            melody.getNotes().forEach(note -> System.out.println(note.getPitchClass() + ", " + note.getPosition()));
            System.out.println("---");
        }
    }

    @Test
    public void generateKcombinationOrderedWithRepetition3(){
        List<Integer> pitchClasses = Scale.SET_3_5_inversion.getPitchClassesAsList();
//        pitchClasses.add(4);
//        pitchClasses.add(0);
//        pitchClasses.add(11);
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) singleMelodyGenerator.generateKcombinationOrderedWithRepetition(pitchClasses, 5);
//        assertThat(result, hasItems("4,4,0,11", "4,0,0,11", "4,0,11,11"));
        for (List<Integer> pc : melodicValue.getPermutationsPitchClasses()) {
            System.out.print(pc + ", ");
            System.out.println();
        }
    }

    @Test
    public void generateKpermutationOrderedNoRepetition3(){
        List<Integer> pitchClasses = Scale.SET_6_7.getPitchClassesInKey(keys.C);
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) singleMelodyGenerator.generateKpermutationOrderedNoRepetition(pitchClasses, "3-5");
        for (List<Integer> pcs : melodicValue.getPermutationsPitchClasses()) {
            pcs.forEach(pc -> System.out.print(pc + ", "));
            System.out.println();
        }
    }

    @Test
    public void generateMelodicValue(){
        List<Integer> pitchClasses = Arrays.asList(1, 2);
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleMelodyGenerator.generateMelodicValue(allRhythmCombinations.balancedPattern.pos7ain30(DurationConstants.EIGHT * 30, DurationConstants.EIGHT),
                pitchClasses, DurationConstants.EIGHT * 30);
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
        }
    }

    @Test
    public void getMelodies(){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos5N30);
        List<Integer> pitchClasses = Arrays.asList(1, 2);
        MelodicValueMelody melodicValue = (MelodicValueMelody) singleMelodyGenerator.getMelodies(rhythmCombinations, DurationConstants.EIGHT, 30, 1);
        for (CpMelody melody : melodicValue.getMelodies()) {
            melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
            System.out.println();
        }
    }

}
package cp.composition;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.combination.rhythm.DurationRhythmCombination;
import cp.generator.ChordGenerator;
import cp.generator.PitchClassGenerator;
import cp.generator.SingleMelodyGenerator;
import cp.generator.SingleRhythmGenerator;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component(value = "melodyMapComposition")
@ConditionalOnProperty(name = "mapComposition", havingValue = "true")
public class MelodyMapComposition {

    protected Map<Integer, MelodicValue> compositionMap = new HashMap<>();

    @Autowired
    private SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    private SingleRhythmGenerator singleRhythmGenerator;
    @Autowired
    private PitchClassGenerator pitchClassGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;
    @Autowired
    private ChordGenerator chordGenerator;

//    @PostConstruct
    public void initAtonal(){

//        harmony.add(new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.RELATED_3, DurationConstants.HALF, keys.C)));
//        harmony.add(new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.RELATED_3, DurationConstants.WHOLE, keys.C)));
//
//        compositionMap.put(0, new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.HALF, keys.C)));
//        oneNote.add(new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.QUARTER, keys.C)));
//        compositionMap.put(0, getRest(DurationConstants.EIGHT, DurationConstants.QUARTER));
        compositionMap.put(0, singleMelodyGenerator.generateSingleNoteScale(Scale.SET_8_17,  DurationConstants.HALF));
        compositionMap.put(1, singleMelodyGenerator.generateSingleNoteScale(Scale.SET_8_17,  DurationConstants.QUARTER));

//        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
////        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
////        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos123);
////        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos124);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteSexTuplet::pos156);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteSexTuplet::pos145);
//
//
//        List<RhythmCombination> rhythmCombinationsLong = new ArrayList<>();
//        rhythmCombinationsLong.add(allRhythmCombinations.threeNoteEven::pos123);
//        rhythmCombinationsLong.add(allRhythmCombinations.threeNoteUneven::pos123);

//        DurationRhythmCombination durationRhythmCombination =
//                new DurationRhythmCombination(DurationConstants.QUARTER, DurationConstants.SIXTEENTH, DurationConstants.SIXTEENTH);
//        rhythmCombinations.add(durationRhythmCombination);
//        RandomRhythmCombination randomRhythmCombination =
//                new RandomRhythmCombination(DurationConstants.EIGHT, DurationConstants.SIXTEENTH, DurationConstants.SIXTEENTH);
//        rhythmCombinations.add(randomRhythmCombination);



//        threeNotes.add(new MelodicValue(singleMelodyGenerator.generateTranspositionsForPitchesClasses(Scale.MAJOR_SCALE, rhythmCombinations, DurationConstants.HALF, 0,2,4)));

//        MelodicValue melodicValue = new MelodicValue();
//        List<Integer> pitchClassesAsList = Scale.SET_3_5.getPitchClassesAsList();
////        int size = RandomUtil.randomInt(3, 6);
//        melodicValue.setPermutationsPitchClasses(singleMelodyGenerator.generateKcombinationOrderedWithRepetition(pitchClassesAsList, pitchClassesAsList.size()));
//        melodicValue.setDuration(DurationConstants.HALF) ;
//        melodicValue.setRhythmCombinations(rhythmCombinations);
//        threeNotes.add(melodicValue);

//        threeNotes.add(new MelodicValue(singleMelodyGenerator.generateInversionsForPitchClasses(Scale.MAJOR_SCALE, rhythmCombinations, DurationConstants.HALF, 0,2,4)));
//        threeNotes.add(new MelodicValue(singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(Scale.MAJOR_SCALE.getPitchClasses(), rhythmCombinations, DurationConstants.HALF, 0,2,4)));

//        melodicValue.addMelodies(singleMelodyGenerator.generatePermutationsOrderedNoRepetition(Scale.SET_3_4.getPitchClassesAsList(), rhythmCombinations, DurationConstants.HALF));
//        melodicValue.addMelodies(singleMelodyGenerator.generatePermutationsOrderedNoRepetition(Scale.SET_3_5.getPitchClassesAsList(), rhythmCombinations, DurationConstants.HALF));
        compositionMap.put(2, singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_8_17.getPitchClassesAsList(), "4-8"));
        compositionMap.put(3, singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_8_17.getPitchClassesAsList(), "4-16"));
//        compositionMap.put(3, singleMelodyGenerator.generateKcombinationOrderedWithRepetition(Scale.SET_8_17.getPitchClassesAsList(), 8));
//        compositionMap.put(3, singleMelodyGenerator.generateKcombinationOrderedWithRepetition(Scale.SET_3_5_inversion.getPitchClassesAsList(), 4));
//        compositionMap.put(2, new MelodicValue(singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_6_7.getPitchClassesAsList(), rhythmCombinationsLong, "3-5", DurationConstants.HALF)));
//        compositionMap.put(3, new MelodicValue(singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_6_7.getPitchClassesAsList(), rhythmCombinationsLong, "3-5", DurationConstants.WHOLE)));

//        compositionMap.put(4, singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_6_7.getPitchClassesAsList(), "3-4"));
//        compositionMap.put(5, singleMelodyGenerator.generateKcombinationOrderedWithRepetition(Scale.SET_3_4.getPitchClassesAsList(), 4));
//        compositionMap.put(6, singleMelodyGenerator.generateKcombinationOrderedWithRepetition(Scale.SET_3_4_inversion.getPitchClassesAsList(), 4));
//        compositionMap.put(5, new MelodicValue(singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_6_7.getPitchClassesAsList(), rhythmCombinationsLong, "3-4", DurationConstants.HALF)));
//        compositionMap.put(6, new MelodicValue(singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_6_7.getPitchClassesAsList(), rhythmCombinationsLong, "3-4", DurationConstants.WHOLE)));
//        threeNotes.add(new MelodicValue(singleMelodyGenerator.generateKpermutationOrderedWithRepetition(Scale.SET_3_5.getPitchClassesAsList(), rhythmCombinations, 4, DurationConstants.HALF)));


//        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesInKey(keys.C);
//        List<CpMelody> melodies = singleMelodyGenerator.generateKpermutationOrderedNoRepetition(pitchClasses, rhythmCombinations, 3,
//                DurationConstants.QUARTER);

//        List<Integer> pitchClasses = Arrays.asList(1, 2);
//        MelodicValue melodicValue = singleMelodyGenerator.generateMelodicValue(allRhythmCombinations.balancedPattern.pos7ain30(DurationConstants.EIGHT * 30, DurationConstants.EIGHT),
//                pitchClasses, DurationConstants.EIGHT * 30);

    }

//    @PostConstruct
    public void initTonal() {
        compositionMap.put(0, getRest(DurationConstants.EIGHT, DurationConstants.QUARTER));
        compositionMap.put(1, singleMelodyGenerator.generateSingleNoteScale(Scale.MAJOR_SCALE, DurationConstants.QUARTER));
        compositionMap.put(2, singleMelodyGenerator.generateSingleNoteScale(Scale.MAJOR_SCALE, DurationConstants.EIGHT));
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.fourNoteEven::pos1234);
        int[] steps = Scale.MAJOR_SCALE.getPitchClasses();
//        int[] steps = {1,4,5};
        compositionMap.put(3, singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(steps, rhythmCombinations,
                DurationConstants.QUARTER, 0,2,4,5));
        compositionMap.put(4, singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(steps, rhythmCombinations,
                DurationConstants.QUARTER, 5,4,2,0));
    }

    @PostConstruct
    public void initRhythm() {

//        compositionMap.put(0, getRest(DurationConstants.EIGHT, DurationConstants.QUARTER));
//        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);
//        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos5_0X000N30);
//        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos5_00X00N30);
//        compositionMap.put(0, singleMelodyGenerator.getMelodies(rhythmCombinations, DurationConstants.EIGHT, 30, 1));
//        compositionMap.put(0, getRest(DurationConstants.EIGHT, DurationConstants.QUARTER));
//        compositionMap.put(1, singleMelodyGenerator.generateSingleNoteScale(Scale.MAJOR_SCALE, DurationConstants.QUARTER));
//        compositionMap.put(2, singleMelodyGenerator.generateSingleNoteScale(Scale.MAJOR_SCALE, DurationConstants.EIGHT));

//        int[] steps = Scale.MAJOR_SCALE.getPitchClasses();
////        int[] steps = {1,4,5};
//        compositionMap.put(3, singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(steps, rhythmCombinations,
//                DurationConstants.QUARTER, 0,2,4,5,7));
//        compositionMap.put(4, singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(steps, rhythmCombinations,
//                DurationConstants.QUARTER, 7,5,4,2,0));

//        compositionMap.put(1, singleRhythmGenerator.generateOstinato(rhythmCombinations, DurationConstants.THREE_EIGHTS, ContourType.DESC, 0,4,11));
//        compositionMap.put(1, singleRhythmGenerator.generatePitches(rhythmCombinations, DurationConstants.THREE_EIGHTS,  60,63,55));
//        compositionMap.put(1, singleRhythmGenerator.generatePitches(rhythmCombinations, DurationConstants.THREE_EIGHTS, ContourType.ASC, 0, 0,2,8));
//        compositionMap.put(1, singleRhythmGenerator.generateKpermutationOrderedNoRepetitionASC(rhythmCombinations, DurationConstants.THREE_EIGHTS,
//                Scale.SET_3_11.getPitchClassesAsList(),"3-11"));

//        compositionMap.put(1, singleRhythmGenerator.generateTranspositionsPitchClassesForStepsAsc(Scale.SET_3_11.getPitchClasses(),rhythmCombinations,
//                DurationConstants.THREE_EIGHTS, 0, 4 ,7));

//        compositionMap.put(2, singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.SET_6_30.getPitchClassesAsList(), "3-1"));
//        List<Integer> pitchClasses = singleMelodyGenerator.getPitchClasses("4-25");
//        compositionMap.put(2, singleMelodyGenerator.generateKpermutationOrderedNoRepetition(pitchClasses, "4-25"));
//        compositionMap.put(3, singleMelodyGenerator.generateKpermutationOrderedNoRepetition(Scale.CHROMATIC_SCALE.getPitchClassesAsList(), "5-5"));
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) pitchClassGenerator.allPermutationsForSetClassInSuperSetClass("5-28", "5-28", 1);

//        MelodicValue melodicValue = pitchClassGenerator.getSetClassesForForteNameInSuperSetClass("7-35", "3-4",1);
        compositionMap.put(0, melodicValue);
//        compositionMap.put(1, melodicValue);

//        DurationRhythmCombination durationRhythmCombination = new DurationRhythmCombination(DurationConstants.QUARTER,DurationConstants.THREE_EIGHTS,DurationConstants.EIGHT,DurationConstants.QUARTER, DurationConstants.THREE_EIGHTS,DurationConstants.EIGHT,DurationConstants.QUARTER );
//        rhythmCombinations2.add(durationRhythmCombination);
//        rhythmCombinations2.add(allRhythmCombinations.sixNoteSexTuplet::pos123456);
//        compositionMap.put(1, singleMelodyGenerator.generateKcombinationOrderedWithRepetitionAndRest(Arrays.asList(0, 3, 6, 9), rhythmCombinations2, 6, DurationConstants.HALF));

//        compositionMap.put(2, singleMelodyGenerator.generateKcombinationOrderedWithRepetitionAndRest(Arrays.asList(1, 7, 1, 7), rhythmCombinations2, 6, DurationConstants.HALF));

//        compositionMap.put(1, singleMelodyGenerator.generateKpermutationOrderedWithRepetition(Arrays.asList(0, 4, 2,1), rhythmCombinations2, 4, DurationConstants.HALF));
//        compositionMap.put(1, singleMelodyGenerator.generateKcombinationOrderedWithRepetition(Arrays.asList(0, 4, 2, 5), rhythmCombinations2, 4, DurationConstants.HALF));
//
//        compositionMap.put(1, singleRhythmGenerator.generateTranspositionsPitchClassesForStepsDesc(Scale.SET_6_30.getPitchClasses(), rhythmCombinations,  DurationConstants.THREE_SIXTEENTH, 0, 1, 3));
//        List<RhythmCombination> rhythmCombinations2 = new ArrayList<>();
//        rhythmCombinations2.add(allRhythmCombinations.sixNoteSexTuplet::pos123456);
//        compositionMap.put(3, singleRhythmGenerator.generateTranspositionsPitchClassesForStepsDesc(Scale.SET_6_30.getPitchClasses(), rhythmCombinations2,  DurationConstants.THREE_SIXTEENTH * 2, 0, 1, 3, 6, 7, 9));

//        compositionMap.put(3, singleRhythmGenerator.generateOstinato(rhythmCombinations, DurationConstants.THREE_EIGHTS, ContourType.DESC, 0,4,11));
//        compositionMap.put(3, singleRhythmGenerator.generateOstinato(rhythmCombinations, DurationConstants.THREE_EIGHTS, ContourType.DESC, 0,4,11));
//        compositionMap.put(3, singleRhythmGenerator.generatePitches(rhythmCombinations, DurationConstants.THREE_EIGHTS,  60,64,80));
    }



    public List<MelodicValue> getCompositionMap(int... keys) {
        List<MelodicValue> allMelodicValues = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            allMelodicValues.add(compositionMap.get(keys[i]).clone());
        }
        return allMelodicValues;
    }

    public MelodicValue getCompositionMap(int key) {
        return compositionMap.get(key).clone();
    }

    private MelodicValue getRest(int... durations){
        List<CpMelody> melodies = new ArrayList<>();
        for (int i = 0; i < durations.length; i++) {
            int duration = durations[i];
            CpMelody cpMelody = singleMelodyGenerator.generateRest(duration);
            melodies.add(cpMelody);
        }
        return new MelodicValueMelody(melodies);
    }
}

package cp.composition;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.generator.*;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component(value = "melodyMapComposition")
@ConditionalOnProperty(name = "mapComposition", havingValue = "true")
public class MelodyMapComposition {

    protected Map<Integer, MelodicValue> compositionMap = new HashMap<>();

    @Autowired
    private SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    private SingleRhythmGenerator singleRhythmGenerator;
    @Autowired
    private CombinationGenerator combinationGenerator;
    @Autowired
    private PCGenerator pcGenerator;
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
//        List<Integer> pitchClasses = pcGenerator.getRandomRepetitionPitchClasses("4-21",1);
//        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("8-23",0);//to G major
//        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "3-2");
//        List<Integer> pitchClasses = pcGenerator.getPitchClasses("8-23"); // to F major
//        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "3-2", 9);
//        List<Integer> pitchClasses = pcGenerator.getPitchClasses("8-23", 4);
//        List<Integer> pitchClasses = pcGenerator.getPitchClasses("8-28", 0);
//        List<Integer> pitchClasses = pcGenerator.getPitchClasses("5-34", 0);
//        List<Integer> pitchClasses = pcGenerator.getPitchClasses("6-7", 0);
        List<Integer> pitchClasses = Scale.CHROMATIC_SCALE.getPitchClassesAsList();
//        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("6-Z6");
//        MelodicValue melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass(pitchClasses, "2-2");
        MelodicValue melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "2-2");
        compositionMap.put(0, melodicValue);

        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "3-1");
        compositionMap.put(7, melodicValue);
        //Common notes
//        pitchClasses = pcGenerator.getPitchClasses("2-3", 9);
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutations(pitchClasses);
        compositionMap.put(1, singleMelodyGenerator.generateSingleNotesForteName("6-7", 0));
//        compositionMap.put(1, melodicValue);
//        pitchClasses = pcGenerator.getInversionPitchClasses("3-6", 3);
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "3-6");
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutations(pitchClasses);
//        compositionMap.put(1, melodicValue);
//        pitchClasses = pcGenerator.getInversionPitchClasses("8-23", 2);
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "3-2");
//        MelodicValue melodicValue = pitchClassGenerator.getSetClassesForForteNameInSuperSetClass("7-35", "3-4",1);


//        pitchClasses = pcGenerator.getInversionPitchClasses("2-3", 9);
////        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "3-6");
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutations(pitchClasses);
//        compositionMap.put(5, melodicValue);

//        pitchClasses = pcGenerator.getPitchClasses("7-35");
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.allPermutationsForSetClassInSuperSetClass(pitchClasses, "3-11");
//        compositionMap.put(1, melodicValue);

        //rests
        compositionMap.put(2, getRest(DurationConstants.QUARTER));
        compositionMap.put(3, getRest(DurationConstants.HALF));
        //single notes
//        compositionMap.put(5, singleMelodyGenerator.generateSingleNoteForteName("6-7", DurationConstants.THREE_EIGHTS, 0));
//        compositionMap.put(6, singleMelodyGenerator.generateSingleNoteForteName("6-7", DurationConstants.HALF, 0));
        compositionMap.put(4, singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.HALF));
        compositionMap.put(5, singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.THREE_QUARTERS));
        compositionMap.put(6, singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.WHOLE));

//        pitchClasses = pcGenerator.getPitchClasses("8-23", 4);
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass(pitchClasses, "3-2");
//        compositionMap.put(5, melodicValue);
//
//        pitchClasses = pcGenerator.getInversionPitchClasses("8-23", 2);
//        melodicValue = (MelodicValueRhythmCombination) combinationGenerator.getSetClassesForForteNameInSuperSetClass( pitchClasses, "3-2");
//        compositionMap.put(6, melodicValue);
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
        MelodicValue melodicValue = compositionMap.get(key).clone();
        melodicValue.setMelodicNumber(key);
        return melodicValue;
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

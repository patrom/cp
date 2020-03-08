package cp.generator;

import cp.combination.RhythmCombination;
import cp.composition.ContourType;
import cp.composition.MelodicValue;
import cp.composition.MelodicValueMelody;
import cp.composition.MelodicValueRhythmCombination;
import cp.config.InstrumentConfig;
import cp.model.harmony.Chord;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SingleRhythmGenerator extends Generator {

    @Autowired
    private InstrumentConfig instrumentConfig;

    public MelodicValue generateOstinato(List<RhythmCombination> rhythmCombinations, int duration, ContourType contourType, Integer... pitchClasses){
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (RhythmCombination rhythmCombination : rhythmCombinations) {
            transpositionMelodies.add(getMelody(Arrays.asList(pitchClasses), rhythmCombination, duration));
        }
        MelodicValueMelody melodicValue = new MelodicValueMelody();
        melodicValue.setMelodies(transpositionMelodies);
        melodicValue.setContourType(contourType);
        return melodicValue;
    }

    /**
     * Generate pitches -> skip pitch update in application.properties
     * @param rhythmCombinations
     * @param duration
     * @param pitches
     * @return
     */
    public MelodicValue generatePitches(List<RhythmCombination> rhythmCombinations, int duration, Integer... pitches){
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (RhythmCombination rhythmCombination : rhythmCombinations) {
            transpositionMelodies.add(getMelodyPitches(Arrays.asList(pitches), rhythmCombination, duration));
        }
        MelodicValueMelody melodicValue = new MelodicValueMelody();
        melodicValue.setMelodies(transpositionMelodies);
        return melodicValue;
    }

    public MelodicValue generateBalancedPattern(RhythmCombination balancedRhythmCombination, int pulse, String forteName){
        List<Integer> pitchClasses = getPitchClasses(forteName);
        List<List<Integer>> subsetsForteName = getSubsets(pitchClasses, forteName);
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        List<List<Integer>> transposePitchClasses = transposePitchClasses(subsetsForteName);
        for (List<Integer> transposePitchClass : transposePitchClasses) {
            transpositionMelodies.add(getMelodyPulse(transposePitchClass, balancedRhythmCombination, pulse));
        }
        MelodicValueMelody melodicValue = new MelodicValueMelody();
        melodicValue.setMelodies(transpositionMelodies);
        melodicValue.setContourType(ContourType.ASC);
        return melodicValue;
    }

//    /**
//     * Generate pitches depending on color config (instrument) -> skip pitch update in application.properties
//     * @param rhythmCombinations
//     * @param duration
//     * @param pitchClasses
//     * @return
//     */
//    public MelodicValue generatePitches(List<RhythmCombination> rhythmCombinations, int duration , ContourType contourType, int voice, Integer... pitchClasses){
//        List<CpMelody> transpositionMelodies = new ArrayList<>();
//        for (RhythmCombination rhythmCombination : rhythmCombinations) {
//            CpMelody melody = getMelodyPitches(Arrays.asList(pitchClasses), rhythmCombination, duration);
//            Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
//            melody.updatePitchesFromContour(instrument.pickRandomOctaveFromRange());
//            transpositionMelodies.add(melody);
//        }
//        MelodicValueMelody melodicValue = new MelodicValueMelody();
//        melodicValue.setMelodies(transpositionMelodies);
//        melodicValue.setContourType(contourType);
//        return melodicValue;
//    }

    /**
     * Alle permutaties van pitchclasses voor forteName, ascending pitches
     *
     * vb. alle permutaties van setclass 3-5 voor setclass 6-7
     *
     * @param forteName forteName
     */
    public MelodicValue generateKpermutationOrderedNoRepetitionASC(List<RhythmCombination> rhythmCombinations, int duration,
                                                                List<Integer> pitchClasses, String forteName){
        List<List<Integer>> subsetsForteName = getSubsets(pitchClasses, forteName);
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        List<List<Integer>> transposePitchClasses = transposePitchClasses(subsetsForteName);
        for (List<Integer> transposePitchClass : transposePitchClasses) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                CpMelody melody = getMelody(transposePitchClass, rhythmCombination, duration);
                transpositionMelodies.add(melody);
            }
        }
        MelodicValueMelody melodicValue = new MelodicValueMelody();
        melodicValue.setMelodies(transpositionMelodies);
        melodicValue.setContourType(ContourType.ASC);
        return melodicValue;
    }

    public MelodicValue generateKpermutationOrderedNoRepetitionDESC(List<RhythmCombination> rhythmCombinations, int duration,
                                                                   List<Integer> pitchClasses, String forteName){
        List<List<Integer>> subsetsForteName = getSubsets(pitchClasses, forteName);
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        List<List<Integer>> transposePitchClasses = transposePitchClasses(subsetsForteName);
        for (List<Integer> transposePitchClass : transposePitchClasses) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                CpMelody melody = getMelody(transposePitchClass, rhythmCombination, duration);
                transpositionMelodies.add(melody);
            }
        }
        MelodicValueMelody melodicValue = new MelodicValueMelody();
        melodicValue.setMelodies(transpositionMelodies);
        melodicValue.setContourType(ContourType.DESC);
        return melodicValue;
    }

    private List<List<Integer>> getSubsets(List<Integer> pitchClasses, String forteName) {
        List<List<Integer>> subsets = org.paukov.combinatorics3.Generator.combination(pitchClasses)
                .simple(Integer.parseInt(forteName.substring(0,1)))
                .stream()
                .map(combination -> org.paukov.combinatorics3.Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        List<List<Integer>> subsetsForteName = new ArrayList<>();
        for (List<Integer> subset : subsets) {
            Chord chord = new Chord(subset, 0);
            if(chord.getForteName().equals(forteName)){
                subsetsForteName.add(subset);
            }
        }

        if (subsetsForteName.isEmpty()) {
            throw new IllegalStateException("Geen subsets gevonden");
        }
        return subsetsForteName;
    }

    /**
     * Alle transposities van de pitchclasses voor opgegeven stappen (pitchclasses)
     */
    public MelodicValue generateTranspositionsPitchClassesForStepsAsc(int[] steps, List<RhythmCombination> rhythmCombinations, int duration , Integer... pitchClasses){
        List<Integer> transpositions = new ArrayList<>();
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (int step : steps) {
            transpositions.clear();
            for (Integer pc : pitchClasses) {
                transpositions.add((pc + step) % 12);
            }
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                CpMelody melody = getMelody(transpositions, rhythmCombination, duration);
                transpositionMelodies.add(melody);
            }
        }
        MelodicValueMelody melodicValue = new MelodicValueMelody();
        melodicValue.setMelodies(transpositionMelodies);
        melodicValue.setContourType(ContourType.ASC);
        return melodicValue;
    }

    /**
     * Alle transposities van de pitchclasses voor opgegeven stappen (pitchclasses)
     */
    public MelodicValue generateTranspositionsPitchClassesForStepsDesc(int[] steps, List<RhythmCombination> rhythmCombinations, int duration , Integer... pitchClasses){
        List<Integer> transpositions = new ArrayList<>();
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (int step : steps) {
            transpositions.clear();
            for (Integer pc : pitchClasses) {
                transpositions.add((pc + step) % 12);
            }
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                CpMelody melody = getMelody(transpositions, rhythmCombination, duration);
                transpositionMelodies.add(melody);
            }
        }
        MelodicValueMelody melodicValue = new MelodicValueMelody();
        melodicValue.setMelodies(transpositionMelodies);
        melodicValue.setContourType(ContourType.DESC);
        return melodicValue;
    }
}

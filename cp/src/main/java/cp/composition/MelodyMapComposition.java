package cp.composition;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.combination.rhythm.DurationRhythmCombination;
import cp.combination.rhythm.RandomRhythmCombination;
import cp.generator.ChordGenerator;
import cp.generator.SingleMelodyGenerator;
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

    @Autowired
    private SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;
    @Autowired
    private ChordGenerator chordGenerator;

    private List<MelodicValue> oneNote = new ArrayList<>();
    private List<MelodicValue> twoNotes = new ArrayList<>();
    private List<MelodicValue> threeNotes = new ArrayList<>();
    private List<MelodicValue> fourNotes = new ArrayList<>();

    private List<MelodicValue> harmony = new ArrayList<>();
    private List<MelodicValue> bass = new ArrayList<>();

    @PostConstruct
    public void init(){

//        harmony.add(new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.RELATED_3, DurationConstants.HALF, keys.C)));
//        harmony.add(new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.RELATED_3, DurationConstants.WHOLE, keys.C)));
//
//        oneNote.add(new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.HALF, keys.C)));
//        oneNote.add(new MelodicValue(singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.QUARTER, keys.C)));
//        oneNote.add(new MelodicValue(Collections.singletonList(singleMelodyGenerator.generateRest(DurationConstants.QUARTER))));

        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos124);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);

//        DurationRhythmCombination durationRhythmCombination =
//                new DurationRhythmCombination(DurationConstants.QUARTER, DurationConstants.SIXTEENTH, DurationConstants.SIXTEENTH);
//        rhythmCombinations.add(durationRhythmCombination);
        RandomRhythmCombination randomRhythmCombination =
                new RandomRhythmCombination(DurationConstants.EIGHT, DurationConstants.SIXTEENTH, DurationConstants.SIXTEENTH);
        rhythmCombinations.add(randomRhythmCombination);


//        threeNotes.add(new MelodicValue(singleMelodyGenerator.generateTranspositionsForPitchesClasses(Scale.MAJOR_SCALE, rhythmCombinations, DurationConstants.HALF, 0,2,4)));

        MelodicValue melodicValue = new MelodicValue();
        melodicValue.setPermutationsPitchClasses(singleMelodyGenerator.generateTranspositionsForPitchesClasses(Scale.MAJOR_SCALE, 0,2,4));
        melodicValue.setDuration(DurationConstants.HALF);
        melodicValue.setRhythmCombinations(rhythmCombinations);
        threeNotes.add(melodicValue);

//        threeNotes.add(new MelodicValue(singleMelodyGenerator.generateInversionsForPitchClasses(Scale.MAJOR_SCALE, rhythmCombinations, DurationConstants.HALF, 0,2,4)));
//        threeNotes.add(new MelodicValue(singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(Scale.MAJOR_SCALE.getPitchClasses(), rhythmCombinations, DurationConstants.HALF, 0,2,4)));


//        List<Integer> pitchClasses = Scale.MAJOR_SCALE.getPitchClassesInKey(keys.C);
//        List<CpMelody> melodies = singleMelodyGenerator.generateKpermutationOrderedNoRepetition(pitchClasses, rhythmCombinations, 3,
//                DurationConstants.QUARTER);

    }

    public List<Integer> getPitchClasses(String fortename){
        int[] setClass = chordGenerator.generatePitchClasses(fortename);
       return Arrays.stream(setClass).boxed().collect(Collectors.toList());
    }

    public List<MelodicValue> getOneNote() {
        return oneNote;
    }

    public List<MelodicValue> getTwoNotes() {
        return twoNotes;
    }

    public List<MelodicValue> getThreeNotes() {
        return threeNotes;
    }

    public List<MelodicValue> getFourNotes() {
        return fourNotes;
    }

    public List<MelodicValue> getHarmony() {
        return harmony;
    }

    public List<MelodicValue> getBass() {
        return bass;
    }
}

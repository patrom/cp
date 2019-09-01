package cp.composition;

import cp.combination.DurationRhythmCombination;
import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.generator.SingleMelodyGenerator;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static cp.model.note.NoteBuilder.note;

@Component(value = "melodyMapComposition")
@ConditionalOnProperty(name = "mapComposition", havingValue = "true")
public class MelodyMapComposition {

    private Map<Integer, List<CpMelody>> melodyMap = new HashMap<>();
    @Autowired
    private SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;


    private List<CpMelody> oneNote = new ArrayList<>();
    private List<CpMelody> twoNotes = new ArrayList<>();
    private List<CpMelody> threeNotes = new ArrayList<>();
    private List<CpMelody> fourNotes = new ArrayList<>();

    @PostConstruct
    public void init(){

        oneNote.addAll(singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.HALF, keys.C));
        oneNote.addAll(singleMelodyGenerator.generateSingleNoteScale(Scale.CHROMATIC_SCALE, DurationConstants.QUARTER, keys.C));
        oneNote.add(singleMelodyGenerator.generateRest(DurationConstants.QUARTER));

        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos124);

        DurationRhythmCombination durationRhythmCombination =
                new DurationRhythmCombination(DurationConstants.QUARTER, DurationConstants.SIXTEENTH, DurationConstants.SIXTEENTH);
        rhythmCombinations.add(durationRhythmCombination);

        threeNotes.addAll(singleMelodyGenerator.generateTranspositionsForPitchesClasses(Scale.MAJOR_SCALE, rhythmCombinations, DurationConstants.HALF, 0,2,4));
        threeNotes.addAll(singleMelodyGenerator.generateInversionsForPitchClasses(Scale.MAJOR_SCALE, rhythmCombinations, DurationConstants.HALF, 0,2,4));
        threeNotes.addAll(singleMelodyGenerator.generateTranspositionsPitchClassesForSteps(Scale.MAJOR_SCALE.getPitchClasses(), rhythmCombinations, DurationConstants.HALF, 0,2,4));

//        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        List<CpMelody> melodies = singleMelodyGenerator.generateTranspositions(rhythmCombinations, DurationConstants.QUARTER, 0,4,5);

    }

    public List<CpMelody> getOneNote() {
        return oneNote;
    }

    public List<CpMelody> getTwoNotes() {
        return twoNotes;
    }

    public List<CpMelody> getThreeNotes() {
        return threeNotes;
    }

    public List<CpMelody> getFourNotes() {
        return fourNotes;
    }
}

package cp.composition;

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

        oneNote.addAll(singleMelodyGenerator.generateSingleNoteScale(Scale.MAJOR_SCALE, DurationConstants.HALF, keys.A));
        oneNote.addAll(singleMelodyGenerator.generateSingleNoteScale(Scale.MAJOR_SCALE, DurationConstants.EIGHT, keys.A));

        for (int i = 0; i < 5; i++) {
            List<Note> notes = new ArrayList<>();
            notes.add(note().pos(0).pc(i).len(DurationConstants.QUARTER).build());
            notes.add(note().pos(DurationConstants.QUARTER).pc(i + 1).len(DurationConstants.QUARTER).build());
            CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.HALF);
            twoNotes.add(melody);
        }

        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
//        rhythmCombinations.add(allRhythmCombinations.twoNoteEven::pos13);
        threeNotes.addAll(singleMelodyGenerator.generatePermutations(Scale.MAJOR_SCALE, keys.A, 3, rhythmCombinations, DurationConstants.QUARTER));

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

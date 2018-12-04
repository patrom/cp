package cp.combination.uneven;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component
public class SeptTuplet {

    public List<Note> pos1234567(int beat, int pulse) {
        List<Note> notes = new ArrayList<>();
        int noteLength = beat/7;
        switch (beat) {
            case DurationConstants.QUARTER:
                notes =  posWithBeam(noteLength);
                notes.forEach(n -> {n.setSepttuplet(true);
                    n.setTimeModification("16th");});
                break;
        }
        return notes;
    }

    public List<Note> pos234567(int beat, int pulse) {
        List<Note> notes = new ArrayList<>();
        int noteLength = beat/7;
        switch (beat) {
            case DurationConstants.QUARTER:
                notes =  posWithBeamRest(noteLength);
                notes.forEach(n -> {n.setSepttuplet(true);
                    n.setTimeModification("16th");});
                break;
        }
        return notes;
    }

    private List<Note> posWithBeam(int length){
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).len(length).beam(BeamType.BEGIN_BEGIN).tuplet(TupletType.START).build());
        notes.add(note().pos(length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(2 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(3 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(4 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(5 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(6 * length).len(length).beam(BeamType.END_END).tuplet(TupletType.STOP).build());
        return notes;
    }

    private List<Note> posWithBeamRest(int length){
        List<Note> notes = new ArrayList<>();
        notes.add(note().rest().pos(0).len(length).beam(BeamType.BEGIN_BEGIN).tuplet(TupletType.START).build());
        notes.add(note().pos(length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(2 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(3 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(4 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(5 * length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
        notes.add(note().pos(6 * length).len(length).beam(BeamType.END_END).tuplet(TupletType.STOP).build());
        return notes;
    }


    public static void main(String[] args) {
        SeptTuplet septTuplet = new SeptTuplet();
        List<Note > notes = septTuplet.pos1234567(DurationConstants.QUARTER, DurationConstants.QUARTER);
        notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
    }
}

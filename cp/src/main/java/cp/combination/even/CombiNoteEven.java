package cp.combination.even;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CombiNoteEven {

    @Autowired
    public OneNoteEven oneNoteEven;
    @Autowired
    public TwoNoteEven twoNoteEven;
    @Autowired
    public ThreeNoteEven threeNoteEven;
    @Autowired
    public FourNoteEven fourNoteEven;

    public List<Note> pos23pos12(int beat) {
        List<Note> notes = twoNoteEven.pos23(beat);
        List<Note> notes1 = twoNoteEven.pos12(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public static void main(String[] args) {
        CombiNoteEven combiNoteEven = new CombiNoteEven();
        List<Note > notes = combiNoteEven.pos23pos12(DurationConstants.QUARTER);
        notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength() + ", " + n.isRest()));
    }
}

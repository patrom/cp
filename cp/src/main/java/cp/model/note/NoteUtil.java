package cp.model.note;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cp.model.note.NoteBuilder.note;

@Component
public class NoteUtil {

    public List<Note> getNotesForPositions(int beat, List<Integer> positions, int size) {
        Integer firstPosition = positions.get(0);
        if (firstPosition != 0){ // positions should start from 0!
            positions = positions.stream().map(integer -> {
                return integer - firstPosition;
            }).collect(Collectors.toList());
        }
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (positions.contains(i)) {
                notes.add(note().pos(i * beat).build());
            }
        }

        int positionsSize = positions.size() - 1;
        for (int i = 0; i < positionsSize; i++) {
            int length = positions.get(i + 1) - positions.get(i);
            Note note = notes.get(i);
            note.setLength(length * beat);
            note.setDisplayLength(length * beat);
        }

        //last
        int length = size - positions.get(positionsSize);
        Note note = notes.get(positionsSize);
        note.setLength(length * beat);
        note.setDisplayLength(length * beat);
        return notes;
    }
}

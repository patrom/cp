package cp.combination.balance;

import cp.model.note.Note;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cp.model.note.NoteBuilder.note;

@Component
public class BalancedPattern {

    private int size = 30;

    public List<Note> pos5_X0000(int beat) {
        int beat30 = beat/30;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        return getNotesForPositions(beat30, positions5);
    }

    public List<Note> pos5_0X000(int beat) {
        int beat30 = beat/30;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        positions5 = positions5.stream().map(integer -> integer = integer + 1).collect(Collectors.toList());
        return getNotesForPositions(beat30, positions5);
    }

    public List<Note> pos5_00X00(int beat) {
        int beat30 = beat/30;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        positions5 = positions5.stream().map(integer -> integer = integer + 2).collect(Collectors.toList());
        return getNotesForPositions(beat30, positions5);
    }

    public List<Note> pos3(int beat) {
        int beat30 = beat/30;
        List<Integer> positions3 = Arrays.asList(0,10,20);
        return getNotesForPositions(beat30, positions3);
    }

    private List<Note> getNotesForPositions(int beat, List<Integer> positions) {
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (positions.contains(i)) {
                notes.add(note().pos(i * beat).build());
            }
        }

        int positionsSize = positions.size();
        if (notes.size() != positionsSize) {
            System.out.println(("stop"));
        }
        for (int i = 0; i < positionsSize; i++) {
            int length = (size - positions.get(i %  positionsSize) + positions.get((i + 1) % positionsSize)) % size;
            Note note = notes.get(i);
            note.setLength(length * beat);
            note.setDisplayLength(length * beat);
        }
        return notes;
    }
}
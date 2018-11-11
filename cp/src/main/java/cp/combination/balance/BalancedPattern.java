package cp.combination.balance;

import cp.model.note.Note;
import cp.util.RandomUtil;
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
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 9);
        List<Integer> positions = Arrays.asList(0,10,20);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return getNotesForPositions(beat30, positions);
    }

    public List<Note> pos6in30(int beat) {
        int beat30 = beat/30;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(9,10,16,22,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return getNotesForPositions(beat30, positions);
    }

    private List<Note> getNotesForPositions(int beat, List<Integer> positions) {
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
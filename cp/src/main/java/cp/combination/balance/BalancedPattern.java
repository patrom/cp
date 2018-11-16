package cp.combination.balance;

import cp.model.note.Note;
import cp.model.note.NoteUtil;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class BalancedPattern {

    @Autowired
    private NoteUtil noteUtil;

    private int size = 30;
    private int limitPositions;

    public void setSize(int size) {
        this.size = size;
    }

    public void setLimitPositions(int limitPositions) {
        this.limitPositions = limitPositions;
    }

    public List<Note> pos5_X0000(int beat) {
        int beat30 = beat/size;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        return noteUtil.getNotesForPositions(beat30, positions5, size);
    }

    public List<Note> pos5_0X000(int beat) {
        int beat30 = beat/size;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        positions5 = positions5.stream().map(integer -> integer = integer + 1).collect(Collectors.toList());
        return noteUtil.getNotesForPositions(beat30, positions5, size);
    }

    public List<Note> pos5_00X00(int beat) {
        int beat30 = beat/size;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        positions5 = positions5.stream().map(integer -> integer = integer + 2).collect(Collectors.toList());
        return noteUtil.getNotesForPositions(beat30, positions5, size);
    }

    public List<Note> pos3(int beat) {
        int beat30 = beat/size;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 9);
        List<Integer> positions = Arrays.asList(0,10,20);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(beat30, positions, size);
    }

    public List<Note> pos6in30(int beat) {
        int beat30 = beat/size;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(9,10,16,22,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(beat30, positions, size);
    }

    public List<Note> posRandom(int beat) {
        int pulse = beat/size;
        if (limitPositions == 0) {
            limitPositions = RandomUtil.getRandomNumberInRange(1, size - 1);
        }
        List<Integer> positions = new Random().ints(0, size)
                .boxed()
                .distinct()
                .limit(limitPositions).sorted().collect(Collectors.toList());
//        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }

}
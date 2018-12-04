package cp.combination.balance;

import cp.model.note.Note;
import cp.model.note.NoteUtil;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BalancedPattern {

    @Autowired
    private NoteUtil noteUtil;

    private int limitPositions;

    public void setLimitPositions(int limitPositions) {
        this.limitPositions = limitPositions;
    }

    public List<Note> pos5_X0000(int beat, int pulse) {
        int size = beat/pulse;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        return noteUtil.getNotesForPositions(pulse, positions5, size);
    }

    public List<Note> pos5_0X000(int beat, int pulse) {
        int size = beat/pulse;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        positions5 = positions5.stream().map(integer -> integer = integer + 1).collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions5, size);
    }

    public List<Note> pos5_00X00(int beat, int pulse) {
        int size = beat/pulse;
        List<Integer> positions5 = Arrays.asList(0,5,10,15,20,25);
        positions5 = positions5.stream().map(integer -> integer = integer + 2).collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions5, size);
    }

    public List<Note> pos5(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 4);
        List<Integer> positions = Arrays.asList(0,5,10,15,20,25);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }


    public List<Note> pos3(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 9);
        List<Integer> positions = Arrays.asList(0,10,20);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }

    public List<Note> pos6in30(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(9,10,16,22,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }

    public List<Note> pos7ain30(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(9,10,16,17,27,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }

    public List<Note> pos7bin30(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(5,11,12,18,22,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }

    public List<Note> pos8ain30(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(9,10,11,17,21,27,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }

    public List<Note> pos8bin30(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(5,9,11,15,21,22,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }

    public List<Note> pos9in30(int beat, int pulse) {
        int size = beat/pulse;
        int randomNumber = RandomUtil.getRandomNumberInRange(0, 29);
        List<Integer> positions = Arrays.asList(5,9,11,15,17,21,27,28,29);
        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }


}
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

    public List<Note> pos5N30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(5,11,17,23,29);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }

//    public List<Note> pos5_0X000N30(int beat, int pulse) {
//        int n = 30;
//        List<Integer> positions5 = Arrays.asList(0,6,12,18,24);
//        positions5 = positions5.stream().map(integer -> integer = integer + 1).collect(Collectors.toList());
//        return noteUtil.getNotesForPositions(pulse, positions5, n);
//    }
//
//    public List<Note> pos5_00X00N30(int beat, int pulse) {
//        int n = 30;
//        List<Integer> positions5 = Arrays.asList(0,6,12,18,24);
//        positions5 = positions5.stream().map(integer -> integer = integer + 2).collect(Collectors.toList());
//        return noteUtil.getNotesForPositions(pulse, positions5, n);
//    }

//    public List<Note> pos5N30(int beat, int pulse) {
//        int n = 30;
//        int size = 30 * pulse;
//        int randomNumber = RandomUtil.getRandomNumberInRange(0, 4);
//        List<Integer> positions = Arrays.asList(5,11,17,23,29);
//        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
//        return noteUtil.getNotesForPositions(pulse, positions, n);
//    }

    public List<Note> pos3N30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(0,10,20);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }

    public List<Note> pos6N30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(9,10,16,22,28,29);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }

    public List<Note> pos7ain30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(9,10,16,17,27,28,29);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }

    public List<Note> pos7bN30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(5,11,12,18,22,28,29);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }

    public List<Note> pos8aN30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(9,10,11,17,21,27,28,29);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }

    public List<Note> pos8bN30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(5,9,11,15,21,22,28,29);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }

    public List<Note> pos9N30(int beat, int pulse) {
        int n = 30;
        List<Integer> positions = Arrays.asList(5,9,11,15,17,21,27,28,29);
        return noteUtil.getNotesForPositions(pulse, positions, n);
    }
    
}
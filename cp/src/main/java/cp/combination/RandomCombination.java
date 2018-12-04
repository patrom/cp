package cp.combination;

import cp.model.note.Note;
import cp.model.note.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class RandomCombination {

    @Autowired
    private NoteUtil noteUtil;

    public List<Note> random1in2(int beat, int pulse) {
        return posRandom(2, pulse, 1);
    }

    public List<Note> random1in3(int beat, int pulse) {
        return posRandom(3, pulse, 1);
    }

    public List<Note> random2in3(int beat, int pulse) {
        return posRandom(3, pulse, 2);
    }

    public List<Note> random1in4(int beat, int pulse) {
        return posRandom(4, pulse, 1);
    }

    public List<Note> random2in4(int beat, int pulse) {
        return posRandom(4, pulse, 2);
    }

    public List<Note> random3in4(int beat, int pulse) {
        return posRandom(4, pulse, 3);
    }

    public List<Note> random1in5(int beat, int pulse) {
        return posRandom(5, pulse, 1);
    }

    public List<Note> random2in5(int beat, int pulse) {
        return posRandom(5, pulse, 2);
    }

    public List<Note> random3in5(int beat, int pulse) {
        return posRandom(5, pulse, 3);
    }

    public List<Note> random4in5(int beat, int pulse) {
        return posRandom(5, pulse, 4);
    }

    public List<Note> random1in6(int beat, int pulse) {
        return posRandom(6, pulse, 1);
    }

    public List<Note> random2in6(int beat, int pulse) {
        return posRandom(6, pulse, 2);
    }

    public List<Note> random3in6(int beat, int pulse) {
        return posRandom(6, pulse, 3);
    }

    public List<Note> random4in6(int beat, int pulse) {
        return posRandom(6, pulse, 4);
    }

    public List<Note> random5in6(int beat, int pulse) {
        return posRandom(6, pulse, 5);
    }

    protected List<Note> posRandom(int size, int pulse, int limit) {
        List<Integer> positions = new Random().ints(0, size)
                .boxed()
                .distinct()
                .limit(limit).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(pulse, positions, size);
    }
}

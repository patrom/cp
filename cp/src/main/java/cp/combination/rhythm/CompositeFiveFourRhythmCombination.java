package cp.combination.rhythm;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class CompositeFiveFourRhythmCombination implements RhythmCombination {

    private List<RhythmCombination> rhythmCombinationsThree = new ArrayList<>();
    private List<RhythmCombination> rhythmCombinationsTwo = new ArrayList<>();

    @Override
    public List<Note> getNotes(int beatLength, int pulse) {
        List<Note> notes = new ArrayList<>();
        int length = beatLength / 5;
        int totalLength = 0;
        RhythmCombination rhythmCombination3 = RandomUtil.getRandomFromList(rhythmCombinationsThree);
        List<Note> rhythmCombinationNotes = rhythmCombination3.getNotes(length * 3, pulse);
        notes.addAll(rhythmCombinationNotes);
        totalLength = totalLength + length * 3;

        RhythmCombination rhythmCombination2 = RandomUtil.getRandomFromList(rhythmCombinationsTwo);
        rhythmCombinationNotes = rhythmCombination2.getNotes(length * 2, pulse);
        for (Note note : rhythmCombinationNotes) {
            note.setPosition(note.getPosition() + totalLength);
        }
        notes.addAll(rhythmCombinationNotes);

        return notes;
    }

    public void addRhythmCombinationThree(RhythmCombination rhythmCombination){
        this.rhythmCombinationsThree.add(rhythmCombination);
    }

    public void addRhythmCombinationTwo(RhythmCombination rhythmCombination){
        this.rhythmCombinationsTwo.add(rhythmCombination);
    }
}


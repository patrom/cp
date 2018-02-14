package cp.model.twelve;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class RandomSegmentBuilder extends AggregateBuilder {

    public RandomSegmentBuilder(int start, List<Integer> beats, int voice, int[] pitchClasses, RhythmCombination... rhythmCombinations) {
        super(start, beats, voice, pitchClasses, rhythmCombinations);
    }

    /**
     * Herhaalt pitchclasses - random select pc - mogelijk dat niet alle pc (uit pitchClassesScale) worden gebruikt.
     * @param pitchClassesScale
     */
    @Override
    public void notesLargerOrEqualThanScale(int[] pitchClassesScale) {
        List<Note> gridNotesWithoutRest = getGridNotesWithoutRest();
        for (Note note : gridNotesWithoutRest) {
            int pitchClass = RandomUtil.getRandomFromIntArray(pitchClassesScale);
            note.setPitchClass(pitchClass);
        }
    }

    /**
     * Maakt dependency notes - random select pitchclasses - mogelijk dat niet alle pitchclasses worden gebruikt
     * @param pitchClasses
     */
    @Override
    public List<Note> addNoteDependenciesAndPitchClasses(int[] pitchClasses) {
        List<Note> notesWithoutRests = getGridNotesWithoutRest();
        int sizeToDistribute = pitchClasses.length - notesWithoutRests.size();
        List<Note> dependentNotes = new ArrayList<>();
        for (int i = 0; i < sizeToDistribute; i++) {
            Note dependentNote = RandomUtil.getRandomFromList(notesWithoutRests).clone();
            dependentNotes.add(dependentNote);
        }
        List<Note> notesToGroup = new ArrayList<>(notesWithoutRests);
        notesToGroup.addAll(dependentNotes);
        Collections.sort(notesToGroup);

        List<Integer> allPitchClasses = stream(pitchClasses).boxed().collect(toList());
        for (Note note : notesToGroup) {
            Integer pitchClass = RandomUtil.getRandomFromList(allPitchClasses);
            note.setPitchClass(pitchClass);
        }
        return notesToGroup;
    }
}

package cp.model.twelve;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class PartialTwelveToneBuilder extends AggregateBuilder {

    public PartialTwelveToneBuilder(int start, List<Integer> beats, int voice, int[] pitchClasses, RhythmCombination... rhythmCombinations) {
        super(start, beats, voice, pitchClasses, rhythmCombinations);
    }

    /**
     * Herhaalt pitchclasses - insert herhaalde pc random in bestaande reeks pitchclasses (volgorde gedeeltelijk bewaard) - alle pc worden gebruikt.
     * @param pitchClassesScale
     */
    @Override
    public void notesLargerOrEqualThanScale(int[] pitchClassesScale) {
        List<Note> gridNotesWithoutRest = getGridNotesWithoutRest();
        int size = gridNotesWithoutRest.size();
        int sizeToRepeat = size - pitchClassesScale.length;
        List<Integer> pitchClasses = stream(pitchClassesScale).boxed().collect(toList());
        for (int i = 0; i < sizeToRepeat; i++) {
            int pitchClass = RandomUtil.getRandomFromIntArray(pitchClassesScale);
            int index = RandomUtil.getRandomIndex(pitchClasses);
            pitchClasses.add(index, pitchClass);
        }
        int i = 0;
        for (Integer pitchClass : pitchClasses) {
            Note note = gridNotesWithoutRest.get(i);
            note.setPitchClass(pitchClass);
            i++;
        }
    }

    /**
     * Maakt dependency notes - random select pitchclasses - alle pitchclasses worden gebruikt
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
            allPitchClasses.remove(pitchClass);
        }
        return notesToGroup;
    }
}

package cp.generator;

import cp.combination.RhythmCombination;
import cp.composition.Composition;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.print.note.Key;
import cp.util.Permutations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;

@Component
public class SingleMelodyGenerator {

    @Autowired
    private Composition composition;

    public List<CpMelody> generateSingleNoteScale(Scale scale, int duration, Key key){
        List<CpMelody> melodies = new ArrayList<>();
        for (int pitchClass : scale.getPitchClasses()) {
            int pitchClassForKey = getPitchClassForKey(pitchClass, key);
            melodies.add(generateSingleNote(pitchClassForKey, duration));
        }
        return melodies;
    }

    public List<CpMelody> generateSingleNoteScale(Scale scale, int duration){
        List<CpMelody> melodies = new ArrayList<>();
        for (int pitchClass : scale.getPitchClasses()) {
            melodies.add(generateSingleNote(pitchClass, duration));
        }
        return melodies;
    }

    public CpMelody generateSingleNote(int pitchClass, int duration){
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(pitchClass).len(duration).build());
        return new CpMelody(notes, -1, 0, duration);
    }

    public List<CpMelody> generatePermutations(Scale scale, Key key, int subsetSize, List<RhythmCombination> rhythmCombinations, int duration){
        List<CpMelody> allPermutationMelodies = new ArrayList<>();
        List<Integer> pitchClassesInKey = scale.getPitchClassesInKey(key);
        List<List<Integer>> subsets = Permutations.getSubsets(pitchClassesInKey, subsetSize);
        for (List<Integer> subset : subsets) {
            Stream<Stream<Integer>> permutationsSubset = Permutations.of(subset);
            Stream<List<CpMelody>> listStream = permutationsSubset.map(integerStream ->
                    getMelody(integerStream.collect(Collectors.toList()), rhythmCombinations, duration));
            List<CpMelody> allPermutationMelodiesSubset = listStream.flatMap(cpMelodies -> cpMelodies.stream()).collect(Collectors.toList());
            allPermutationMelodies.addAll(allPermutationMelodiesSubset);
        }
        return allPermutationMelodies;
    }

    private List<CpMelody> getMelody(List<Integer> pitchClasses, List<RhythmCombination> rhythmCombinations, int duration) {
        int size = pitchClasses.size();
        List<CpMelody> melodies = new ArrayList<>();
        for (RhythmCombination rhythmCombination : rhythmCombinations) {
            List<Note> notes = rhythmCombination.getNotes(duration, 0);
            List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
            for (int i = 0; i < size; i++) {
                Note note = notesNoRest.get(i);
                note.setPitchClass(pitchClasses.get(i));
            }
            CpMelody cpMelody = new CpMelody(notes, -1, 0, duration);
            melodies.add(cpMelody);
        }
        return melodies;
    }

    public int getPitchClassForKey(int pitchClass, Key key) {
        return (pitchClass + key.getInterval()) % 12;
    }
}

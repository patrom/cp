package cp.generator.pitchclass.combination;

import cp.combination.RhythmCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PitchClassCombinationRepetition implements PitchClassCombination {

    @Override
    public CpMelody getMelody(List<Integer> pitchClasses, List<Note> notes) {
        int size = pitchClasses.size();
        List<Integer> allPitchClassses = new ArrayList<>(pitchClasses);
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        while (size < notesNoRest.size()){
            int randomIndex = RandomUtil.getRandomIndex(allPitchClassses);
            allPitchClassses.add(randomIndex, allPitchClassses.get(randomIndex));
            size = size + 1;
        }

        for (int i = 0; i < size; i++) {
            Note note = notesNoRest.get(i);
            note.setPitchClass(allPitchClassses.get(i));
        }
        int duration = notes.stream().mapToInt(value -> value.getLength()).sum();
        return new CpMelody(notes, -1, 0, duration);
    }
}

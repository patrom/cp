package cp.generator;

import cp.composition.MelodicValueRhythmCombination;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PitchSetGenerator{

    @Autowired
    private PCGenerator pcGenerator;

    //Morris class notes
    public List<List<Integer>> generatePSC(String forteName){
        List<Integer> pitchClasses = pcGenerator.getPitchClasses(forteName, 0);
        List<List<Integer>> subsets = Generator.permutation(pitchClasses).simple().stream().collect(Collectors.toList());
        if (subsets.isEmpty()) {
            throw new IllegalStateException("Geen subsets gevonden");
        }
        int size = subsets.get(0).size() - 1;
        List<Integer[]> figuredBassList = new ArrayList<>();
        for (List<Integer> permutation : subsets) {
            Integer[] FB = new Integer[size];
            for (int i = 0; i < size; i++) {
                Integer pc = permutation.get(i);
                Integer nextPc = permutation.get(i+1);
                int interval = ((nextPc + 12) - pc) % 12;
                FB[i] = interval;
            }
            figuredBassList.add(FB);
        }
        List<List<Integer>> positionPermutations = Generator.permutation(0, 1).withRepetitions(size).stream().collect(Collectors.toList());
        List<List<Integer>> PSC = new ArrayList<>();
        for (int i = 0; i < figuredBassList.size(); i++) {
            Integer[] intervals = figuredBassList.get(i);
            for (List<Integer> positions : positionPermutations) {
                List<Integer> intervalSpacing = new ArrayList<>();
                for (int j = 0; j < positions.size(); j++) {
                    int position = positions.get(j);
                    Integer interval = intervals[j];
                    if (position == 1) {
                        interval = interval + 12;
                    }
                    intervalSpacing.add(interval);
                }
                PSC.add(intervalSpacing);
            }
        }
        return PSC;
    }

    public List<List<Note>> generatePsets(int bassNote, String forteName, int duration, int maxSpacing){
        List<List<Integer>> psc = generatePSC(forteName);
        List<List<Integer>> maxPsc = psc.stream().filter(integers -> integers.stream().mapToInt(Integer::intValue).sum() <= maxSpacing).collect(Collectors.toList());
        List<List<Note>> psets = new ArrayList<>();
        int position = 0;
        for (List<Integer> spacings : maxPsc) {
            int bass = RandomUtil.randomInt(52, 59);
            int voice = 0;
            List<Note> notes = new ArrayList<>();
            Note bassnote = NoteBuilder.note().pitch(bass).pos(position).voice(voice).len(duration).pc(bass % 12).build();
            notes.add(bassnote);
            int totalSpacing = 0;
            for (Integer spacing : spacings) {
                voice = voice + 1;
                totalSpacing = totalSpacing + spacing;
                int pitch = bass + totalSpacing;
                Note note = NoteBuilder.note().pitch(pitch).pos(position).voice(voice).len(duration).pc(pitch % 12).build();
                notes.add(note);
            }
            psets.add(notes);
            position = position + duration;
        }
        return psets;
    }
}

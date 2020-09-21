package cp.generator;

import cp.composition.ContourType;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RunGenerator {

    @Autowired
    private ChordGenerator chordGenerator;

    private List<Integer> tempPitchClasses = new ArrayList<>();

    public CpMelody generateMelodyRun(List<Integer> pitchClasses, int size, int rest, int duration, ContourType contourType){
        List<Integer> run = generateExhaustiveRun(pitchClasses, size, rest);
        int pos = 0;
        List<Note> notes = new ArrayList<>();
        for (Integer pitchClass : run) {
            Note note = NoteBuilder.note().pc(pitchClass).pitch(pitchClass).pos(pos).len(duration).build();
            pos = pos + duration;
            notes.add(note);
        }
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        Note lastNote = notes.get(notes.size() - 1);
        lastNote.setLength(DurationConstants.QUARTER);
        CpMelody melody = new CpMelody(notesNoRest, 0, 0, lastNote.getPosition() + lastNote.getLength());
        if (ContourType.ASC == contourType){
            melody.updateContourAscending();
        }
        if (ContourType.DESC == contourType){
            melody.updateContourDescending();
        }
        return melody;
    }

    public List<Integer> generateExhaustiveRun(List<Integer> pitchClasses, int size, int rest){
        List<Integer> generatedRun = new ArrayList<>();
        int positions = size - rest;
        for (int i = 0; i < positions; i++) {
            if (tempPitchClasses.isEmpty()){
                tempPitchClasses.addAll(pitchClasses);
            }
            int index = RandomUtil.getRandomIndex(tempPitchClasses);
            Integer pitchClass = tempPitchClasses.get(index);
            generatedRun.add(pitchClass);
            tempPitchClasses.remove(index);
        }

        for (int i = 0; i < rest; i++) {
            int index = RandomUtil.getRandomIndex(generatedRun);
            generatedRun.add(index, Integer.MIN_VALUE);
        }
        Collections.shuffle(generatedRun);
        return generatedRun;
    }

    public List<Integer> generateRandomRun(List<Integer> pitchClasses, int size, int rest){
        List<Integer> generatedRun = new ArrayList<>();
        int positions = size - rest;
        for (int i = 0; i < positions; i++) {
            Integer pitchClass = RandomUtil.getRandomFromList(pitchClasses);
            generatedRun.add(pitchClass);
        }
        for (int i = 0; i < rest; i++) {
            int index = RandomUtil.getRandomIndex(generatedRun);
            generatedRun.add(index, Integer.MIN_VALUE);
        }
        Collections.shuffle(generatedRun);
        return generatedRun;
    }
}

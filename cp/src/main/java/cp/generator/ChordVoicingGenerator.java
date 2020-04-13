package cp.generator;

import com.google.common.util.concurrent.CycleDetectingLockFactory;
import cp.model.harmony.Chord;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import org.antlr.stringtemplate.language.ArrayWrappedInList;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ChordVoicingGenerator {

    @Autowired
    private ChordGenerator chordGenerator;

    public List<Note> generateMaxSpreadVoicingsLowerThanOctave(int pitchBass, String forteName, Integer... intervals){
        List<List<Integer>> listOfIntervals = Generator.permutation(intervals)
                .simple()
                .stream()
                .collect(Collectors.toList());
        return getNotes(pitchBass, forteName, listOfIntervals);
    }

    private List<Note> getNotes(int pitchBass, String forteName, List<List<Integer>> listOfIntervals) {
        List<Note> allNotes = new ArrayList();
        int position = 0;
        for (List<Integer> listOfInterval : listOfIntervals) {
            List<Note> notes = new ArrayList();
            int pitch = pitchBass;
            int pc = pitchBass % 12;
            Note bassNote = NoteBuilder.note().pitch(pitch).pc(pc).pos(position).len(DurationConstants.WHOLE).build();
            notes.add(bassNote);
            for (Integer interval : listOfInterval) {
                pitch = pitch + interval;
                Note note = NoteBuilder.note().pitch(pitch).pc(pitch % 12).pos(position).len(DurationConstants.WHOLE).build();
                notes.add(note);
            }
            Chord chord = new Chord(notes);
            if(forteName.equals(chord.getForteName())){
                allNotes.addAll(notes);
                position = position + DurationConstants.WHOLE;
            }
        }
        return allNotes;
    }

    public List<Note> generateMaxSpreadVoicingsLowerThanOctave(String forteName, int pitchBass){
        int[] intervalVector = chordGenerator.getIntervalVector(forteName);
        Collection<Integer> intervalsVector = new ArrayList<>();
        int interval = intervalVector[0];
        if(interval != 0){
            for (int i = 0; i < interval; i++) {
                intervalsVector.add(11);
            }
        }

        interval = intervalVector[1];
        if(interval != 0){
            for (int i = 0; i < interval; i++) {
                intervalsVector.add(10);
            }
        }

        interval = intervalVector[2];
        if(interval != 0){
            for (int i = 0; i < interval; i++) {
                intervalsVector.add(9);
            }
        }

        interval = intervalVector[3];
        if(interval != 0){
            for (int i = 0; i < interval; i++) {
                intervalsVector.add(8);
            }
        }

        interval = intervalVector[4];
        if(interval != 0){
            for (int i = 0; i < interval; i++) {
                intervalsVector.add(7);
            }
        }

        interval = intervalVector[5];
        if(interval != 0){
            for (int i = 0; i < interval; i++) {
                intervalsVector.add(6);
            }
        }

        List<List<Integer>> intervals = Generator.combination(intervalsVector)
                .simple(Integer.parseInt(forteName.substring(0,1)) -1)
                .stream()
                .map(combination -> Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        return getNotes(pitchBass, forteName, intervals);
    }

    public List<Note> generateMaxSpreadVoicingsLowerThanOctave2(String forteName, int pitchBass){
        int[] intervalVector = chordGenerator.getIntervalVector(forteName);
        Collection<Integer> intervalsVector = new ArrayList<>();
        int interval = intervalVector[0];
        if(interval != 0){
            if (interval == 1) {
                intervalsVector.add(11);
            } else{
                intervalsVector.add(11);
                intervalsVector.add(1);
            }
        }

        interval = intervalVector[1];
        if(interval != 0){
            if (interval == 1) {
                intervalsVector.add(10);
            } else{
                intervalsVector.add(10);
                intervalsVector.add(2);
            }
        }

        interval = intervalVector[2];
        if(interval != 0){
            if (interval == 1) {
                intervalsVector.add(9);
            } else{
                intervalsVector.add(9);
                intervalsVector.add(3);
            }
        }

        interval = intervalVector[3];
        if(interval != 0){
            if(interval != 0){
                if (interval == 1) {
                    intervalsVector.add(8);
                } else{
                    intervalsVector.add(8);
                    intervalsVector.add(4);
                }
            }
        }

        interval = intervalVector[4];
        if(interval != 0){
            if(interval != 0){
                if (interval == 1) {
                    intervalsVector.add(7);
                } else{
                    intervalsVector.add(7);
                    intervalsVector.add(5);
                }
            }
        }

        interval = intervalVector[5];
        if(interval != 0){
            for (int i = 0; i < interval; i++) {
                intervalsVector.add(6);
            }
        }

        List<List<Integer>> intervals = Generator.combination(intervalsVector)
                .simple(Integer.parseInt(forteName.substring(0,1)) -1)
                .stream()
                .map(combination -> Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        return getNotes(pitchBass, forteName, intervals);
    }

}

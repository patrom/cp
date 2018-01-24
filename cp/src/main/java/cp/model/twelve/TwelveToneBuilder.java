package cp.model.twelve;

import cp.combination.RhythmCombination;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class TwelveToneBuilder {

    private Set<Integer> durations = new HashSet<>();

    private List<Note> gridNotes;
    private int start;
    private int beat;
    private RhythmCombination[] rhythmCombinations;
    private int repeat;
    private int length;
    private int voice;

    private Scale scale;


    public TwelveToneBuilder(int start, int beat, int voice,  Scale scale, int repeat, RhythmCombination... rhythmCombinations) {
        this.start = start;
        this.beat = beat;
        this.scale = scale;
        this.repeat = repeat;
        this.voice = voice;
        this.rhythmCombinations = rhythmCombinations;
    }

    public TwelveToneBuilder(int beat, int repeat, RhythmCombination... rhythmCombinations) {
        this.beat = beat;
        this.rhythmCombinations = rhythmCombinations;
        this.repeat = repeat;
    }

    public void setGridNotes(List<Note> gridNotes) {
        this.gridNotes = gridNotes;
    }

    public List<Note> getGridNotes() {
        return gridNotes;
    }

    public int getLength() {
        return length;
    }

    //    public List<Note> build(int beat, RhythmCombination... rhythmCombinations){
//        int start = 0;
//        this.beat = beat;
//        for (RhythmCombination rhythmCombination : rhythmCombinations) {
//            List<Note> notes = rhythmCombination.getNotes(beat);
//            for (Note note : notes) {
//                note.setPosition(note.getPosition() + start);
//            }
//            start = start + beat;
//            gridNotes.addAll(notes);
//        }
//        end = start;
//        List<Integer> lengths = gridNotes.stream().map(note -> note.getLength()).distinct().collect(toList());
//        for (Integer duration : lengths) {
//            durations.add(duration);
//            durations.add(beat - duration);
//            durations.add(beat + duration);
//        }
//        return gridNotes;
//    }


    public List<Note> buildRepeat(){
        createGridrepeat();
        updatePitchClasses();
        return gridNotes;
    }

    protected void updatePitchClasses() {
        long count = gridNotes.stream().filter(note -> !note.isRest()).count();
        if(count == 0 ){
            System.out.println("All rests, not pitches");
            return;
        }
        int[] pitchClasses = scale.getPitchClasses();
        if (count >= pitchClasses.length) {
            //repeat notes
            notesLargerOrEqualThanScale(scale);
        } else if (count < pitchClasses.length) {
            //build dependant notes
            notesSmallerThanScale(scale);
        }
    }


    protected void createGridrepeat() {
        gridNotes = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            RhythmCombination rhythmCombination = RandomUtil.getRandomFromArray(rhythmCombinations);
            List<Note> notes = rhythmCombination.getNotes(beat);
            for (Note note : notes) {
                note.setPosition(start + note.getPosition() + length);
                note.setVoice(voice);
            }
            length = length + beat;
            gridNotes.addAll(notes);
        }
    }



    public Optional<Integer> getFirstPositionBefore(int notePosition){
        return gridNotes.stream().map(note -> note.getPosition()).filter(position -> position < notePosition).sorted(Comparator.reverseOrder()).findFirst();
    }

    public Optional<Integer> getFirstPositionAfter(int notePosition){
        return gridNotes.stream().map(note -> note.getPosition()).filter(position -> position > notePosition).sorted().findFirst();
    }

    public Integer getRandomPositionBeforeOrAfter(int position){
        if (gridNotes.isEmpty()) {
            throw new IllegalStateException("grid was not set");
        }
        if (RandomUtil.toggleSelection()) {
            return getFirstPositionBefore(position).orElseGet(() -> getFirstPositionAfter(position).get());
        } else {
            return getFirstPositionAfter(position).orElseGet(() -> getFirstPositionBefore(position).get());
        }
    }

//    public List<Note> getTwelveToneNotes(Integer voice, Scale scale) {
//        int[] pitchClasses = scale.getPitchClasses();
//        List<Note> rowNotes = new ArrayList<>();
//        for (int pitchClass : pitchClasses) {
//            Note note = RandomUtil.getRandomFromList(gridNotes);
//            rowNotes.add(note.clone());
//        }
//        Collections.sort(rowNotes);
//
//        for (int i = 0; i < pitchClasses.length; i++) {
//            int pitchClass = pitchClasses[i];
//            Note note = rowNotes.get(i);
//            note.setPitchClass(pitchClass);
//            note.setVoice(voice);
//        }
//        //set length of notes
//        NavigableMap<Integer, List<Note>> notesPerPosition = rowNotes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, toList()));
//        List<Note> melodyNotes = new ArrayList<>();
//        for (Map.Entry<Integer, List<Note>> entry : notesPerPosition.entrySet()) {
//            Integer position = entry.getKey();
//            List<Note> notes = entry.getValue();
//            Integer nextPosition = notesPerPosition.higherKey(entry.getKey());// next
//            int length;
//            if (nextPosition == null) {//last position
//                length = calculateLengthBetween( position, getEnd(), beat);
//            } else {
//                length = calculateLengthBetween( position, getEnd(), beat);
//            }
//            for (Note note : notes) {
//                note.setLength(length);
//                note.setDisplayLength(length);
//            }
//            if (notes.size() > 1) {
//                Note melodyNote = notes.get(0);
//                notes.remove(melodyNote);
//                DependantHarmony dependantHarmony = new DependantHarmony();
//                dependantHarmony.setChordType(ChordType.TWELVE_TONE);
//                dependantHarmony.setNotes(notes);
//                melodyNote.setDependantHarmony(dependantHarmony);
//                melodyNotes.add(melodyNote);
//            } else {
//                melodyNotes.add(notes.get(0));
//            }
//        }
//        return melodyNotes;
//    }

    protected int calculateLengthBetween(int position, int nextPosition, int beat){
        int relativePositionNote = position % beat;
        int relativePositionNextNote = nextPosition % beat;
        int beatsInbetween = ((nextPosition - position) / beat) - 1;
        int lengthFirstNote = beat - relativePositionNote;
        int randomNumberInRange = RandomUtil.getRandomNumberInRange(0, 2);
        switch (randomNumberInRange){
            case 0:
                return lengthFirstNote;
            case 1:
                if(beatsInbetween > 0 ){
                    int randomBeats = RandomUtil.getRandomNumberInRange(1, beatsInbetween);
                    return lengthFirstNote + (randomBeats * beat);
                }
            case 2:
                if(beatsInbetween > 0){
                    return lengthFirstNote + (beatsInbetween * beat) + relativePositionNextNote;
                }
                return lengthFirstNote + relativePositionNextNote;
        }
        return lengthFirstNote;
    }

    private List<Note> getGridNotesWithoutRest(){
        return gridNotes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
    }

    public void notesLargerOrEqualThanScale(Scale scale){
        List<Note> gridNotesWithoutRest = getGridNotesWithoutRest();
        int size = gridNotesWithoutRest.size();
        int sizeToRepeat = size - scale.getPitchClasses().length;
        List<Integer> pitchClasses = stream(scale.getPitchClasses()).boxed().collect(toList());
        for (int i = 0; i < sizeToRepeat; i++) {
            int pitchClass = RandomUtil.getRandomFromIntArray(scale.getPitchClasses());
            int index = pitchClasses.indexOf(pitchClass);//No multiple same pitchclasses!!!!
            pitchClasses.add(index, pitchClass);
        }
        int i = 0;
        for (Integer pitchClass : pitchClasses) {
            Note note = gridNotesWithoutRest.get(i);
            note.setPitchClass(pitchClass);
            i++;
        }
    }

    public void notesSmallerThanScale(Scale scale){
        List<Note> gridNotesWithoutRest = getGridNotesWithoutRest();
        int sizeToDistribute = scale.getPitchClasses().length - gridNotesWithoutRest.size();
        List<Note> dependentNotes = new ArrayList<>();
        for (int i = 0; i < sizeToDistribute; i++) {
            Note depandantNote = RandomUtil.getRandomFromList(gridNotesWithoutRest).clone();
            dependentNotes.add(depandantNote);
        }
        List<Note> notesToGroup = new ArrayList<>(gridNotesWithoutRest);
        notesToGroup.addAll(dependentNotes);
        Map<Integer, List<Note>> notesPerPosition = notesToGroup.stream().collect(groupingBy(Note::getPosition, TreeMap::new, toList()));
        for (Map.Entry<Integer, List<Note>> entry : notesPerPosition.entrySet()) {
            List<Note> notes = entry.getValue();
            if (notes.size() > 1) {
                Note melodyNote = notes.get(0);
                notes.remove(melodyNote);
                DependantHarmony dependantHarmony = new DependantHarmony();
                dependantHarmony.setChordType(ChordType.TWELVE_TONE);
                dependantHarmony.setNotes(notes);
                melodyNote.setDependantHarmony(dependantHarmony);
            }
        }
        int i = 0;
        for (Note gridNote : gridNotesWithoutRest) {
            gridNote.setPitchClass(scale.getPitchClasses()[i]);
            i++;
            if (gridNote.getDependantHarmony() != null) {
                List<Note> depandentNotes = gridNote.getDependantHarmony().getNotes();
                for (Note depandentNote : depandentNotes) {
                    depandentNote.setPitchClass(scale.getPitchClasses()[i]);
                    i++;
                }
            }
        }
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

}

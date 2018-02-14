package cp.model.twelve;

import cp.combination.RhythmCombination;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class TwelveToneBuilder extends AggregateBuilder{

    private List<Note> splitNotes;
    private int parentVoice = -1;

    public TwelveToneBuilder(int start, List<Integer> beats, int voice, int[] pitchClasses, RhythmCombination... rhythmCombinations) {
        super(start, beats, voice, pitchClasses, rhythmCombinations);
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

    public List<Note> buildRepeat(){
        createGridrepeat();
        long count = gridNotes.stream().filter(note -> !note.isRest()).count();
        while(count == 0 ){
            createGridrepeat();
            count = gridNotes.stream().filter(note -> !note.isRest()).count();
        }
        updatePitchClasses();
        return gridNotes;
    }

    protected void updatePitchClasses() {
        long count = gridNotes.stream().filter(note -> !note.isRest()).count();
        if (count >= pitchClasses.length) {
            //repeat notes
            notesLargerOrEqualThanScale(pitchClasses);
        } else if (count < pitchClasses.length) {
            //build dependant notes
            notesSmallerThanScale(pitchClasses);
        }
    }

//    public void updatePitchClasses(int[] pitchClasses) {
//        long count = gridNotes.stream().filter(note -> !note.isRest()).count();
//        if (count >= pitchClasses.length) {
//            //repeat notes
//            notesLargerOrEqualThanScale(pitchClasses);
//        } else if (count < pitchClasses.length) {
//            //build dependant notes
//            notesSmallerThanScale(pitchClasses);
//        }
//    }

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

    /**
     * Herhaalt pitchclasses - volgorde pitchclasses blijft behouden.
     * @param pitchClassesScale
     */
    @Override
    public void notesLargerOrEqualThanScale(int[] pitchClassesScale){
        List<Note> gridNotesWithoutRest = getGridNotesWithoutRest();
        int size = gridNotesWithoutRest.size();
        int sizeToRepeat = size - pitchClassesScale.length;
        List<Integer> pitchClasses = stream(pitchClassesScale).boxed().collect(toList());
        for (int i = 0; i < sizeToRepeat; i++) {
            int pitchClass = RandomUtil.getRandomFromIntArray(pitchClassesScale);
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


    public void notesSmallerThanScale(int[] pitchClasses){
        List<Note> gridNotesWithoutRest = getGridNotesWithoutRest();
        int sizeToDistribute = pitchClasses.length - gridNotesWithoutRest.size();
        List<Note> dependentNotes = new ArrayList<>();
        for (int i = 0; i < sizeToDistribute; i++) {
            Note dependentNote = RandomUtil.getRandomFromList(gridNotesWithoutRest).clone();
            dependentNotes.add(dependentNote);
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
            gridNote.setPitchClass(pitchClasses[i]);
            i++;
            if (gridNote.getDependantHarmony() != null) {
                List<Note> dependingNotes = gridNote.getDependantHarmony().getNotes();
                for (Note dependentNote : dependingNotes) {
                    dependentNote.setPitchClass(pitchClasses[i]);
                    i++;
                }
            }
        }
    }

    /**
     * Maakt dependency notes - volgorde pitchclasses blijft behouden - alle pitchclasses worden gebruikt
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
        int i = 0;
        for (Note note : notesToGroup) {
            note.setPitchClass(pitchClasses[i]);
            i++;
        }
        return notesToGroup;
    }

    public List<Note> getSplitNotes(int voice){
        return splitNotes.stream().filter(note -> note.getVoice() == voice).sorted().collect(Collectors.toList());
    }

    public int getParentVoice() {
        return parentVoice;
    }

    public void setParentVoice(int parentVoice) {
        this.parentVoice = parentVoice;
    }
}

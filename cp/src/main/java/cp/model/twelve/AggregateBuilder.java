package cp.model.twelve;

import cp.combination.RhythmCombination;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public abstract class AggregateBuilder {

    protected List<Note> gridNotes;
    protected int start;
    protected List<Integer> beats;
    protected RhythmCombination[] rhythmCombinations;
    protected int length;
    protected int voice;
    protected int[] pitchClasses;
    protected BuilderType builderType;

    protected List<Note> splitNotes;
    protected int parentVoice = -1;

    public AggregateBuilder(int start, List<Integer> beats , int voice,  int[] pitchClasses,  RhythmCombination... rhythmCombinations) {
        this.start = start;
        this.beats = beats;
        this.voice = voice;
        this.pitchClasses = pitchClasses;
        this.rhythmCombinations = rhythmCombinations;
    }

    protected void createGridrepeat() {
        //clear for mutation
        length = 0;
        gridNotes = new ArrayList<>();
        Collections.shuffle(beats);
        for (Integer duration : beats) {
            RhythmCombination rhythmCombination = RandomUtil.getRandomFromArray(rhythmCombinations);
            List<Note> notes = rhythmCombination.getNotes(duration, DurationConstants.EIGHT);
            for (Note note : notes) {
                note.setPosition(start + note.getPosition() + length);
                note.setVoice(voice);
            }
            length = length + duration;
            gridNotes.addAll(notes);
        }
    }

    public void createGrid() {
        createGridrepeat();
        long count = gridNotes.stream().filter(note -> !note.isRest()).count();
        while(count == 0){
            createGridrepeat();
            count = gridNotes.stream().filter(note -> !note.isRest()).count();
        }
    }

    public List<Note> createNoteDependencies(List<Note> notesToGroup){
        List<Note> notesWithDependencies = new ArrayList<>();
        Map<Integer, List<Note>> notesPerPosition = notesToGroup.stream()
                .filter(note -> !note.isRest())
                .collect(groupingBy(Note::getPosition, TreeMap::new, toList()));
        for (Map.Entry<Integer, List<Note>> entry : notesPerPosition.entrySet()) {
            List<Note> notes = entry.getValue();
            if (notes.size() > 1) {
                Note melodyNote = notes.get(0);
                notes.remove(melodyNote);
                DependantHarmony dependantHarmony = new DependantHarmony();
                dependantHarmony.setChordType(ChordType.TWELVE_TONE);
                dependantHarmony.setNotes(notes);
                melodyNote.setDependantHarmony(dependantHarmony);
                notesWithDependencies.add(melodyNote);
            } else {
                notesWithDependencies.addAll(notes);
            }
        }
        return notesWithDependencies;
    }

    protected List<Note> getGridNotesWithoutRest(){
        return gridNotes.stream().filter(note -> !note.isRest()).collect(toList());
    }

    public abstract void notesLargerOrEqualThanScale(int[] pitchClassesScale);

    public abstract List<Note> addNoteDependenciesAndPitchClasses(int[] pitchClasses);

    public void setGridNotes(List<Note> gridNotes) {
        this.gridNotes = gridNotes;
    }

    public List<Note> getGridNotes() {
        return gridNotes;
    }

    public int getLength() {
        return length;
    }

    public int getStart() {
        return start;
    }

    public int getEnd(){
        return start + length;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int[] getPitchClasses() {
        return pitchClasses;
    }

    public BuilderType getBuilderType() {
        return builderType;
    }

    public void setBuilderType(BuilderType builderType) {
        this.builderType = builderType;
    }
}

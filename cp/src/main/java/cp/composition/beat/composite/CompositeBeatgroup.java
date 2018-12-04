package cp.composition.beat.composite;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.note.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeBeatgroup extends BeatGroup {

    public CompositeBeatgroup(int length) {
        super(length);
    }

    @Override
    public List<Note> getRhythmNotesForBeatgroupType(int size) {
        List<Note> allNotes = new ArrayList<>();
        for (int i = 0; i < beatGroups.size(); i++) {
            BeatGroup beatGroup = beatGroups.get(i);
            NoteSizeValueObject valueObject = beatGroup.getRandomRhythmNotesForBeatgroupType();//Size not possible -> Rhythmmutation not working correctly!!!
            List<Note> notes = valueObject.getNotes();
            for (Note note : notes) {
                note.setPosition(note.getPosition() + (beatGroup.getBeatLength() * i));
            }
            allNotes.addAll(notes);
        }
        return allNotes;
    }

    @Override
    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType() {
        List<Note> allNotes = new ArrayList<>();
        int key = 0;
        for (int i = 0; i < beatGroups.size(); i++) {
            BeatGroup beatGroup = beatGroups.get(i);
            NoteSizeValueObject valueObject = beatGroup.getRandomRhythmNotesForBeatgroupType();
            List<Note> notes = valueObject.getNotes();
            for (Note note : notes) {
                note.setPosition(note.getPosition() + (beatGroup.getBeatLength() * i));
            }
            allNotes.addAll(notes);
            key = key + valueObject.getKey();
        }
        return new NoteSizeValueObject(key, allNotes);
    }

    public void addBeatGroups(BeatGroup... beatGroup) {
        for (BeatGroup group : beatGroup) {
//            rhythmCombinationMap.
        }
        this.beatGroups.addAll(Arrays.asList(beatGroup));
    }
}

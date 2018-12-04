package cp.composition.voice;

import cp.model.note.Note;

import java.util.List;

/**
 * Created by prombouts on 12/06/2017.
 */
public class NoteSizeValueObject {

    private Integer key;
    private List<Note> notes;

    public NoteSizeValueObject(Integer key, List<Note> notes) {
        this.key = key;
        this.notes = notes;
    }

    public Integer getKey() {
        return key;
    }

    public List<Note> getNotes() {
        return notes;
    }

}

package cp.generator.dependant;

import cp.model.note.Note;

/**
 * Created by prombouts on 29/01/2017.
 */
public class NoteTuple {

    private Note first;
    private Note second;

    public NoteTuple(Note first, Note second) {
        this.first = first;
        this.second = second;
    }

    public Note getFirst() {
        return first;
    }

    public Note getSecond() {
        return second;
    }

}

package cp.composition.accomp;

import cp.composition.beat.BeatGroup;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;

import java.util.List;

/**
 * Created by prombouts on 19/04/2017.
 */
public class AccompGroup {

    protected BeatGroup beatGroup;

    protected List<Integer> contour;

    protected int miniumLength = DurationConstants.QUARTER;

    protected List<Note> harmonyNotes;

    public AccompGroup(BeatGroup beatGroup, List<Integer> contour) {
        this.beatGroup = beatGroup;
        this.contour = contour;
    }

    public List<Integer> getContour() {
        return contour;
    }

    public void setContour(List<Integer> contour) {
        this.contour = contour;
    }

//    public void setHarmonyNotes(List<Note> harmonyNotes) {
//        this.harmonyNotes = harmonyNotes;
//    }
//
//    protected List<Note> updatePitchesFromContour(){
//        List<Note> notes = beatGroup.getNotes();
//        int harmonySize = harmonyNotes.size();
//        for (int i = 0; i < notes.size(); i++) {
//            Note note = notes.get(i);
//            Note harmonyNote = harmonyNotes.get(i % harmonySize);
//            note.setPitchClass(harmonyNote.getPitchClass());
//            note.setPitch(harmonyNote.getPitch());
//            note.setOctave(harmonyNote.getOctave());
//        }
//        int size = notes.size() - 1;
//        for (int i = 0; i < size; i++) {
//            Note note = notes.get(i);
//            Note nextNote = notes.get(i + 1);
//            int difference = nextNote.getPitchClass() - note.getPitchClass();
//            int direction = contour.get(i);
//            int interval = Util.calculateInterval(direction, difference);
//            nextNote.setPitch(note.getPitch() + interval);
//            nextNote.setOctave(nextNote.getPitch()/12);
//        }
//        return notes;
//    }

    public List<Note> getNotes(){
        List<Note> notes = beatGroup.getNotes();
        notes.forEach(n -> n.setArticulation(Articulation.STACCATO));
        Note firstNote = notes.get(0);
        firstNote.setDynamic(Dynamic.SFZ);
        firstNote.setArticulation(null);
        Note lastNote = notes.get(notes.size() - 1);
        lastNote.setDynamic(Dynamic.SFZ);
        lastNote.setArticulation(null);
        return notes;
    }

    public BeatGroup getBeatGroup() {
        return beatGroup;
    }
}

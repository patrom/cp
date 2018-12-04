package cp.combination.even;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component
public class ThreeNoteEven {

	@Autowired
	public RhythmCombinations rhythmCombinations;

	public List<Note> pos123(int beat, int pulse) {
		List<Note> notes = new ArrayList<>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN_BEGIN).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.CONTINUE_END).build());
			notes.add(note().pos(beat2).len(beat2).beam(BeamType.END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.END).build());
			notes.add(note().pos(beat2).len(beat2).build());
		}else{
			notes.add(note().pos(0).len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).build());
			notes.add(note().pos(beat2).len(beat2).build());
		}
		return notes;
	}
	
	public List<Note> pos134(int beat, int pulse) {
		List<Note> notes = new ArrayList<>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).len(beat2).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat2).len(beat4).beam(BeamType.CONTINUE_BEGIN).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END_END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).len(beat2).build());
			notes.add(note().pos(beat2).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END).build());
		}else{
			notes.add(note().pos(0).len(beat2).build());
			notes.add(note().pos(beat2).len(beat4).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).build());
		}
		return notes;
	}
	
	public List<Note> pos234(int beat, int pulse) {
		List<Note> notes = new ArrayList<>();
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.BEGIN_BEGIN).build());
			notes.add(note().pos(2 * beat4).len(beat4).beam(BeamType.CONTINUE_CONTINUE).build());
			notes.add(note().pos(3 * beat4).len(beat4).beam(BeamType.END_END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).build());
			notes.add(note().pos(2 * beat4).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(3 * beat4).len(beat4).beam(BeamType.END).build());
		}else{
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).build());
			notes.add(note().pos(2 * beat4).len(beat4).build());
			notes.add(note().pos(3 * beat4).len(beat4).build());
		}
		return notes;
	}
	
	public List<Note> pos124(int beat, int pulse) {
		List<Note> notes = new ArrayList<>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat4).len(beat2).beam(BeamType.CONTINUE).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END).build());
		} else {
			notes.add(note().pos(0).len(beat4).build());
			notes.add(note().pos(beat4).len(beat2).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).build());
		}
		return notes;
	}

	public List<Note> pos14_and(int beat, int pulse) {
		List<Note> notes = new ArrayList<>();
		if (beat == DurationConstants.WHOLE){
			notes.add(note().pos(0).len(DurationConstants.HALF + DurationConstants.QUARTER).beam(BeamType.BEGIN).build());
			notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).len(DurationConstants.EIGHT).beam(BeamType.END).build());
			notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).len(DurationConstants.EIGHT).build());
		}else{
			throw new IllegalStateException("beat not implemented: " + beat);
		}
		return notes;
	}

	public List<Note> pos1pos34(int beat, int pulse) {
		return combi(beat, rhythmCombinations.oneNoteEven::pos1, rhythmCombinations.twoNoteEven::pos34, pulse);
	}

    public List<Note> pos1pos24(int beat, int pulse) {
        return combi(beat, rhythmCombinations.oneNoteEven::pos1, rhythmCombinations.twoNoteEven::pos24, pulse);
    }

    public List<Note> pos1pos13(int beat, int pulse) {
        return combi(beat, rhythmCombinations.oneNoteEven::pos1, rhythmCombinations.twoNoteEven::pos13, pulse);
    }

	private List<Note> combi(int beat, RhythmCombination rhythmCombinationFirst, RhythmCombination rhythmCombinationSecond, int pulse) {
	    List<Note> notes = null;
        if (beat == DurationConstants.WHOLE){
            int beatLength = DurationConstants.HALF;
            notes = rhythmCombinationFirst.getNotes(beatLength, pulse);
            List<Note> notes1 = rhythmCombinationSecond.getNotes(beatLength, pulse);
            notes1.forEach(note -> note.setPosition(note.getPosition() + beatLength));
            notes.addAll(notes1);
            return notes;
        }
        return notes;
    }
	
	
	public static void main(String[] args) {
		ThreeNoteEven threeNoteEven = new ThreeNoteEven();
		List<Note > notes = threeNoteEven.pos123(DurationConstants.HALF, DurationConstants.EIGHT);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteEven.pos124(DurationConstants.HALF, DurationConstants.EIGHT);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteEven.pos134(DurationConstants.HALF, DurationConstants.EIGHT);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteEven.pos234(DurationConstants.HALF, DurationConstants.EIGHT);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}

}

package cp.out.print;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cp.model.note.Note;

public class BeatMap {

	private NavigableMap<Integer, List<Note>> notesPerBeat;
	private int beat;
	
	public BeatMap() {
	}

	public void createBeatMap(List<Note> notes, int beat){
		this.beat = beat;
		notesPerBeat = notesForBeat(notes, beat);
		Note last = notes.get(notes.size() - 1);
		int lastIndex = (last.getPosition() + last.getLength())/beat;
		for (int key = 0; key < lastIndex; key++) {
			notesPerBeat.computeIfAbsent(key, v -> new ArrayList<Note>());
		}
	}
	
	private TreeMap<Integer, List<Note>> notesForBeat(List<Note> notes, int beat){
		return  notes.stream().collect(groupingBy(n -> n.beat(beat), TreeMap::new, Collectors.toList()));
	}
	
	public List<Note> getNoteForBeat(int index){
		return notesPerBeat.get(index);
	}

	public NavigableMap<Integer, List<Note>> getNotesPerBeat() {
		return notesPerBeat;
	}
	
	public List<Note> createTies(){
		for (int j = 0; j < notesPerBeat.lastKey(); j++) {
			if (notesPerBeat.containsKey(j)) {
				List<Note> beatNotes = notesPerBeat.get(j);
				if (!beatNotes.isEmpty()) {
					Note lastNote = beatNotes.get(beatNotes.size() - 1);
					int end = (j + 1) * beat;
					if ((lastNote.getPosition() + lastNote.getDisplayLength()) > end ) {//split note between beat and next beat
						int lastNoteLength = lastNote.getDisplayLength();
						int newLength = end - lastNote.getPosition();
						lastNote.setLength(newLength);
						lastNote.setDisplayLength(newLength);
						if (!lastNote.isRest()) {
							lastNote.setTieStart(true);
						}
						Note clone = lastNote.clone();
						if (notesPerBeat.containsKey(j + 1)) {
							List<Note> nextBeatNotes = notesPerBeat.get(j + 1);
							if (!nextBeatNotes.isEmpty()) {
								Note firstNote = nextBeatNotes.get(0);
								int length = firstNote.getPosition() - end;
								clone.setPosition(end);
								clone.setLength(length);
								clone.setDisplayLength(length);
								if (!lastNote.isRest()) {
									clone.setTieEnd(true);
								}
								nextBeatNotes.add(0,clone);
							}else{
								clone.setPosition(end);
								clone.setLength(lastNoteLength - newLength);
								clone.setDisplayLength(lastNoteLength - newLength);
								if (!lastNote.isRest()) {
									clone.setTieEnd(true);
								}
								nextBeatNotes.add(clone);
							}
						}
					}
				}
			}
		}
		return notesPerBeat.values().stream().flatMap(list -> list.stream()).sorted().collect(toList());
	}
	
//	public List<Note> createTies(){
////		Predicate<Note> filteredNotes = n -> n.getLength() != 24 || !( n.getLength() == 24 && (n.getPosition() == 0 || n.getPosition() == 12 || n.getPosition() == 24));
//		Predicate<Note> filteredNotes = n -> true;
//		return createTies(filteredNotes);
//	}
	
	public int size(){
		return notesPerBeat.size();
	}
	
	public Collection<List<Note>> values(){
		return notesPerBeat.values();
	}

}

package cp.out.print;

public class Performance {

//	private List<Melody> extractMelodies_concat(List<Harmony> harmonies) {
//	melodies.clear();
//	List<NotePos> allNotes = new ArrayList<>();
//	for (Harmony list : harmonies) {
//		for (NotePos notePos : list.getNotes()) {
//			allNotes.add(notePos);
//		}
//	}
//	Map<Integer, List<NotePos>> melodyMap = allNotes.stream().collect(groupingBy(n -> n.getVoice()));
//	for (Entry<Integer, List<NotePos>> entry: melodyMap.entrySet()) {
//		List<NotePos> notes = entry.getValue();
//		List<NotePos> clonedNotes = new ArrayList<>();
//		for (NotePos notePos : notes) {
//			try {
//				clonedNotes.add((NotePos) notePos.clone());
//			} catch (CloneNotSupportedException e) {
//				e.printStackTrace();
//			}
//		}
//		List<NotePos> notePositions = concatNotesWithSamePitch(clonedNotes);
//		Melody melody = new Melody(notePositions);
//		melodies.add(melody);
//	}
//	return melodies;
//}
//
//public List<NotePos> concatNotesWithSamePitch(List<NotePos> notePositions) {
//	List<NotePos> notesToRemove = new ArrayList<>();
//	int size = notePositions.size() - 1;
//	for (int i = 0; i < size; i++) {
//		NotePos note = notePositions.get(i);
//		NotePos nextNote = notePositions.get(i + 1);
//		int j = 1;
//		while (note.samePitch(nextNote)) {
//			note.setLength(note.getLength() + nextNote.getLength());
//			notesToRemove.add(nextNote);
//			if ((i + j) < size) {
//				j++;
//				nextNote = notePositions.get(i + j);
//			}else {
//				i = notePositions.indexOf(nextNote) - 1;
//				break;
//			}
//		}
//	}
//	notePositions.removeAll(notesToRemove);
//	return notePositions;
//}

}

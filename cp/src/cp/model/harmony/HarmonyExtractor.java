package cp.model.harmony;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.model.note.NoteBuilder;

@Component
public class HarmonyExtractor {

	public void extractHarmony(List<Note> melodyNotes, int voices){
		List<Note> tempHarmonyNotes = new ArrayList<Note>();
		for (int i = 0; i < voices; i++) {
			tempHarmonyNotes.add(note().voice(i).build());
		}
		
		Map<Integer, List<Note>> map = mapNotesForPosition(melodyNotes);
		for (Entry<Integer, List<Note>> entry : map.entrySet()) {
			List<Note> notesForPosition = entry.getValue();
			tempHarmonyNotes.stream().forEach(n -> n.setPosition(entry.getKey()));
			for (Note note : notesForPosition) {
				tempHarmonyNotes.stream().filter(n -> n.getVoice() == note.getVoice()).findFirst().ifPresent(n -> n.copy(note));
			}
			System.out.print(tempHarmonyNotes.toString());
			System.out.println();
			
		}
	}
	
	protected  Map<Integer, List<Note>> mapNotesForPosition(List<Note> notes){
		return  notes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, Collectors.toList()));
	}
}

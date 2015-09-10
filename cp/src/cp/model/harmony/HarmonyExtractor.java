package cp.model.harmony;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.nsga.operator.mutation.melody.OneNoteMutation;

@Component
public class HarmonyExtractor {
	
	private static Logger LOGGER = Logger.getLogger(HarmonyExtractor.class.getName());

	public Map<Integer, List<Note>> extractHarmony(List<Note> melodyNotes, int voices){
		Map<Integer, List<Note>> extractedHarmonies = new TreeMap<>();
		List<Note> tempHarmonyNotes = new ArrayList<Note>();
		for (int i = 0; i < voices; i++) {
			tempHarmonyNotes.add(note().voice(i).build());
		}
		
//		TreeMap<Integer, List<Note>> positionsMap = mapNotesForPosition(melodyNotes);
		Map<Integer, List<Note>> harmonyMap = mapNotesForPosition(melodyNotes);
		for (Entry<Integer, List<Note>> entry : harmonyMap.entrySet()) {
			//update position - remove weight
			tempHarmonyNotes.stream().forEach(n -> {
				n.setPosition(entry.getKey());
				n.setPositionWeight(0);
			});
			//update with notes at position
			List<Note> notesForPosition = entry.getValue();
			for (Note note : notesForPosition) {
				tempHarmonyNotes.stream()
					.filter(n -> n.getVoice() == note.getVoice())
					.findFirst()
					.ifPresent(n -> {
						n.setPitch(note.getPitch());
						n.setPitchClass(note.getPitchClass());
						n.setPositionWeight(note.getPositionWeight());
						});
			}
			List<Note> harmonyNotes = tempHarmonyNotes.stream().map(n -> n.clone()).collect(Collectors.toList());
			extractedHarmonies.put(entry.getKey(), harmonyNotes);
		}
		LOGGER.info(extractedHarmonies.toString());
		return extractedHarmonies;
	}
	
	protected Map<Integer, List<Note>> getHarmonyMap(TreeMap<Integer, List<Note>> positionsMap){
		int startingKey = positionsMap.firstKey();
		for (Entry<Integer, List<Note>> entry : positionsMap.entrySet()) {
			if (entry.getValue().size() > 1) {
				startingKey = entry.getKey();
				break;
			}
		}
		return positionsMap.subMap(startingKey, positionsMap.lastKey());
	}
	
	protected  TreeMap<Integer, List<Note>> mapNotesForPosition(List<Note> notes){
		return  notes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, Collectors.toList()));
	}
}

package cp.model.harmony;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cp.model.dissonance.Dissonance;
import cp.model.note.Note;

@Component
public class HarmonyExtractor {
	
	@Autowired 
	@Qualifier(value="TonalDissonance")
	private Dissonance dissonance;
	
	private static Logger LOGGER = LoggerFactory.getLogger(HarmonyExtractor.class.getName());

	public List<CpHarmony> extractHarmony(List<Note> melodyNotes, int voices){
		List<CpHarmony>  extractedHarmonies = new ArrayList<>();
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
			List<Note> harmonyNotes = tempHarmonyNotes.stream()
					.filter(n -> n.getPitch() != 0)
					.map(n -> n.clone())
					.collect(toList());
			if (harmonyNotes.size() > 1) {
				CpHarmony harmony = new CpHarmony(harmonyNotes);
				harmony.toChord();
				extractedHarmonies.add(harmony);
			}
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

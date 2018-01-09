package cp.model.harmony;

import cp.composition.Composition;
import cp.model.note.Note;
import cp.model.texture.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class HarmonyExtractor {

	@Autowired
	private Composition composition;
	@Autowired
	private Texture texture;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HarmonyExtractor.class.getName());

	public List<CpHarmony> extractHarmony(List<Note> melodyNotes, int voices){
		List<CpHarmony>  extractedHarmonies = new ArrayList<>();
		List<Note> tempHarmonyNotes = new ArrayList<>();
		for (int i = 0; i < voices; i++) {
			tempHarmonyNotes.add(note().voice(i).build());
		}
		
		Map<Integer, List<Note>> harmonyMap = mapNotesForPosition(melodyNotes);
		for (Entry<Integer, List<Note>> entry : harmonyMap.entrySet()) {
			//update position - remove weight
			tempHarmonyNotes.forEach(n -> {
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
						n.setOctave(note.getOctave());
						n.setDependantHarmony(note.getDependantHarmony());
						});
			}
			List<Note> harmonyNotes = tempHarmonyNotes.stream()
					.filter(n -> n.getPitch() != 0)
					.map(n -> n.clone())
					.collect(toList());
			//texture notes
            List<Note> textureNotes = texture.getTextureNotes(harmonyNotes);
            harmonyNotes.addAll(textureNotes);
            if (harmonyNotes.size() > 1) {
				CpHarmony harmony = new CpHarmony(harmonyNotes, entry.getKey());
				harmony.toChord();
				extractedHarmonies.add(harmony);
			}
		}
		LOGGER.debug(extractedHarmonies.toString());
		updateHarmonyEnd(extractedHarmonies);
		return extractedHarmonies;
	}

	public List<CpHarmony> extractHarmony(List<Note> melodyNotes){
        List<CpHarmony>  extractedHarmonies = new ArrayList<>();
        List<Integer> positions = melodyNotes.stream()
                .map(note -> note.getPosition())
                .sorted()
                .distinct()
                .collect(Collectors.toList());

        for (Integer position : positions) {
            List<Note> harmonyNotes = melodyNotes.stream()
					.filter(note -> !note.isRest())
                    .filter(note -> note.getPosition() <= position && note.getEndPostion() > position)
                    .map(n -> n.clone())
                    .collect(Collectors.toList());
            //texture notes
            List<Note> textureNotes = texture.getTextureNotes(harmonyNotes);
            harmonyNotes.addAll(textureNotes);
            if (harmonyNotes.size() > 1) {
                CpHarmony harmony = new CpHarmony(harmonyNotes, position);
                harmony.toChord();
                extractedHarmonies.add(harmony);
            }
        }
        LOGGER.debug(extractedHarmonies.toString());
        updateHarmonyEnd(extractedHarmonies);
		return extractedHarmonies;
	}

	private void updateHarmonyEnd(List<CpHarmony> extractedHarmonies) {
		if (!extractedHarmonies.isEmpty()) {
			Collections.sort(extractedHarmonies);
			int size = extractedHarmonies.size() - 1;
			for (int i = 0; i < size; i++) {
                CpHarmony harmony = extractedHarmonies.get(i);
                CpHarmony nextHarmony = extractedHarmonies.get(i + 1);
                harmony.setEnd(nextHarmony.getPosition());
            }
			extractedHarmonies.get(size).setEnd(composition.getEnd());
		}
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

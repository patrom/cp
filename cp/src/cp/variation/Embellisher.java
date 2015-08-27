package cp.variation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.variation.nonchordtone.Variation;

@Component
public class Embellisher {

	@Autowired
	private VariationSelector variationSelector;
	
	public List<Note> embellish(List<Note> notes){
		if (notes.size() <= 1) {
			throw new IllegalArgumentException("size");
		}
		List<Note> embellishedMelody = new ArrayList<>();
		Note[] notePositions = notes.toArray(new Note[notes.size()]);
		Variation variation = null;
		for (int j = 0; j < notePositions.length - 1; j++) {
			Note note = notePositions[j];
			Note nextNote = notePositions[j + 1];
			int interval = nextNote.getPitch() - note.getPitch();
			variation = variationSelector.selectVariation(interval);
			if (variation.getExcludedVoices().contains(note.getVoice())) {
				embellishedMelody.add(note.copy());
				continue;
			}
			List<Note> embellishedNotes  = variation.createVariation(note, nextNote);
			if (variation.isSecondNoteChanged()) {
				j++;
			}
			embellishedMelody.addAll(embellishedNotes);
		}
		if (!variation.isSecondNoteChanged()) {
			embellishedMelody.add(notes.get(notes.size() - 1));//add last note of list
		}
		return embellishedMelody;
	}

}

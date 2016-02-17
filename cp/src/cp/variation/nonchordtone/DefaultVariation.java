package cp.variation.nonchordtone;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;

@Component
public class DefaultVariation extends Variation {

	@Override
	public List<Note> createVariation(Note note, Note secondNote) {
		return Collections.singletonList(note.clone());
	}

}

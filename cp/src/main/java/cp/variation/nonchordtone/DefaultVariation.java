package cp.variation.nonchordtone;

import cp.model.note.Note;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DefaultVariation extends Variation {

	@Override
	public List<Note> createVariation(Note note, Note secondNote) {
		return Collections.singletonList(note.clone());
	}

}

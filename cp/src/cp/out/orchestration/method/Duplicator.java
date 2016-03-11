package cp.out.orchestration.method;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.orchestration.ClassicalOrchestra;
import cp.out.orchestration.Orchestra;

@Component
public class Duplicator {
	
	@Autowired
	private ClassicalOrchestra orchestra;

	public List<Note> duplicate(Instrument instrumentToDuplicate, Instrument instrument, int octave){
		List<Note> duplicateNotes = orchestra.getNotes(instrumentToDuplicate).stream()
				.map(n -> n.clone())
				.collect(toList());
		duplicateNotes.forEach(n ->{
			if (!n.isRest()) {
				n.transposePitch(octave);
			}
		});
		instrument.updateMelodyBetween(duplicateNotes);
		return duplicateNotes;
	}
}

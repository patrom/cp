package cp.out.orchestration.method;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.orchestration.orchestra.ClassicalOrchestra;

@Component
public class Duplicator {
	
	@Autowired
	private ClassicalOrchestra orchestra;

	public List<Note> duplicateUpdateBetween(Instrument instrumentToDuplicate, Instrument instrument, int octave){
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
	
	public List<Note> duplicateRemoveNotBetween(Instrument instrumentToDuplicate, Instrument instrument, int octave){
		List<Note> duplicateNotes = orchestra.getNotes(instrumentToDuplicate).stream()
				.map(n -> n.clone())
				.collect(toList());
		duplicateNotes.forEach(n ->{
			if (!n.isRest()) {
				n.transposePitch(octave);
			}
		});
		return instrument.removeMelodyNotBetween(duplicateNotes);
	}
}

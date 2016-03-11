package cp.out.orchestration;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.out.instrument.Instrument;

@Component
public class ClassicalOrchestra extends Orchestra {

	public ClassicalOrchestra() {
		map.put(flute, new ArrayList<>());
		map.put(oboe, new ArrayList<>());
		map.put(clarinet, new ArrayList<>());
		map.put(bassoon, new ArrayList<>());
		
		map.put(horn, new ArrayList<>());
		map.put(trumpet, new ArrayList<>());
		
		map.put(violin1, new ArrayList<>());
		map.put(violin2, new ArrayList<>());
		map.put(viola, new ArrayList<>());
		map.put(cello, new ArrayList<>());
		map.put(bass, new ArrayList<>());
	}
	
	public List<Note> duplicate(Instrument instrumentToDuplicate, Instrument instrument, int octave) {
		List<Note> duplicateNotes = map.get(instrumentToDuplicate).stream()
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

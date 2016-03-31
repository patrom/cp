package cp.out.orchestration.orchestra;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.strings.ViolinsII;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;


public class ClassicalOrchestra extends Orchestra {

	public ClassicalOrchestra() {
		flute = new Flute(0, 0);
		oboe = new Oboe(1, 1);
		clarinet = new Clarinet(2, 2);
		bassoon = new Bassoon(3, 3);
		horn = new FrenchHorn(4, 4);
		trumpet = new Trumpet(5, 5);
		violin1 = new ViolinsI(6, 6);
		violin2 = new ViolinsII(7, 7);
		viola = new Viola(8, 8);
		cello = new Cello(9, 9);
		bass = new Doublebass(10, 10);
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
		instrument.updateMelodyInRange(duplicateNotes);
		return duplicateNotes;
	}
}

package cp.generator;

import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component
public class ChordGenerator {
	public CpHarmony generateChord(String forteName){
		TnTnIType type = new TnTnIType();
		int[] pcs;
		if (forteName.startsWith("2")) {
			type.initPrime2();
			pcs = getSet(forteName, type.prime2);
		}
		else if (forteName.startsWith("3")) {
			type.initPrime3();
			pcs = getSet(forteName, type.prime3);
		}
		else if (forteName.startsWith("4")) {
			type.initPrime4();
			pcs = getSet(forteName, type.prime4);
		}
		else if (forteName.startsWith("5")) {
			type.initPrime5();
			pcs = getSet(forteName, type.prime5);
		}
		else if (forteName.startsWith("6")) {
			type.initPrime6();
			pcs = getSet(forteName, type.prime6);
		} else {
			throw new IllegalArgumentException("No set class found for forte name: " + forteName);
		}
		List<Note> notes = new ArrayList<>();
		for (int pc : pcs) {
			notes.add(note().pc(pc).build());
		}
		return new CpHarmony(notes, 0);
	}

	private int[] getSet(String forteName, Set[] set) {
		for (int i = 0; i < set.length; i++) {
			if(set[i].name.equals(forteName)){
				return set[i].tntnitype;
			}
		}
		throw new IllegalArgumentException("No set class found for forte name: " + forteName);
	}
}

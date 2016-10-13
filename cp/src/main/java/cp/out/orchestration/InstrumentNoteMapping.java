package cp.out.orchestration;

import cp.model.note.Note;
import cp.out.instrument.Instrument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;

public class InstrumentNoteMapping {

	private final Map<Instrument, List<Note>> noteForInstrument = new HashMap<>();

	public void addNoteForInstrument(int pitch, Instrument instrument) {
		int octave = (int) Math.ceil(pitch / 12);
		int pc = pitch % 12;
		Note note = note().pitch(pitch).pc(pc).len(48).ocatve(octave).build();
		noteForInstrument.compute(instrument, (k, v) -> {
			if (v == null) {
				List<Note> list = new ArrayList<>();
				list.add(note);
				return list;
			} else {
				v.add(note);
				return v;
			}
		});
	}

	public Map<Instrument, List<Note>> getNotesForInstrument() {
		return noteForInstrument;
	}

	public long getChordSize() {
		return noteForInstrument.values().stream().flatMap(n -> n.stream()).count();
	}

}

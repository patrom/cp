package cp.out.instrument.register;

import static cp.model.note.NoteBuilder.note;

import cp.model.note.NoteBuilder;
import cp.out.instrument.strings.ViolinSolo;

public class ViolinSoloPleasant extends InstrumentRegister{

	public ViolinSoloPleasant() {
		low = note().pc(9).pitch(69).ocatve(5).build();
		high = note().pc(9).pitch(81).ocatve(6).build();
		instrument = new ViolinSolo();
		intensity = "resonant-normal";
	}

	
}

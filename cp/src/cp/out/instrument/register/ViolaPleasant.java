package cp.out.instrument.register;

import static cp.model.note.NoteBuilder.note;

public class ViolaPleasant extends InstrumentRegister{

	public ViolaPleasant() {
		low = note().pc(2).pitch(62).ocatve(5).build();
		high = note().pc(2).pitch(74).ocatve(6).build();
		intensity = "resonant-normal";
	}

	
}

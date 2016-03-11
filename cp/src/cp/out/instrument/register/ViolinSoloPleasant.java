package cp.out.instrument.register;

import static cp.model.note.NoteBuilder.note;

public class ViolinSoloPleasant extends InstrumentRegister{

	public ViolinSoloPleasant() {
		low = note().pc(9).pitch(69).ocatve(5).build();
		high = note().pc(9).pitch(81).ocatve(6).build();
		intensity = "resonant-normal";
	}

	
}

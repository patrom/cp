package cp.out.orchestration;

import cp.out.instrument.Instrument;

@FunctionalInterface
public interface VerticalRelation {

	public InstrumentNoteMapping combine(int[] pitches, Instrument topInstrument, Instrument bottomInstrument);
}

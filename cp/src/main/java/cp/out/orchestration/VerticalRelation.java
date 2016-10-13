package cp.out.orchestration;

import cp.out.instrument.Instrument;

@FunctionalInterface
public interface VerticalRelation {

	InstrumentNoteMapping combine(int[] pitches, Instrument topInstrument, Instrument bottomInstrument);
}

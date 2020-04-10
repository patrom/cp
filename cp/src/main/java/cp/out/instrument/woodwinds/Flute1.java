package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Flute1 extends Flute {

    public Flute1() {
        super();
        setInstrumentName(InstrumentName.FLUTE.getName());
    }

    public Flute1(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.FLUTE.getName());
    }
}

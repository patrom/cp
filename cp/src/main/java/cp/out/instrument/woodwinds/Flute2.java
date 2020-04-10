package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Flute2 extends Flute{

    public Flute2() {
        super();
        setInstrumentName(InstrumentName.FLUTE_2.getName());
    }

    public Flute2(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.FLUTE_2.getName());
    }
}

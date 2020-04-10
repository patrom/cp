package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Clarinet2  extends Clarinet {

    public Clarinet2() {
        super();
        setInstrumentName(InstrumentName.CLARINET_2.getName());
    }

    public Clarinet2(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.CLARINET_2.getName());
    }
}

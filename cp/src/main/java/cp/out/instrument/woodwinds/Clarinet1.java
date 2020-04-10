package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Clarinet1 extends Clarinet {

    public Clarinet1() {
        super();
        setInstrumentName(InstrumentName.CLARINET.getName());
    }

    public Clarinet1(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.CLARINET.getName());
    }
}

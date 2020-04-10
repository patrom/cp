package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Bassoon1 extends Bassoon {

    public Bassoon1() {
        super();
        setInstrumentName(InstrumentName.BASSOON.getName());
    }

    public Bassoon1(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.BASSOON.getName());
    }
}

package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Bassoon2 extends Bassoon {

    public Bassoon2() {
        super();
        setInstrumentName(InstrumentName.BASSOON_2.getName());
    }

    public Bassoon2(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.BASSOON_2.getName());
    }
}

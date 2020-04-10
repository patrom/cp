package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Oboe2 extends Oboe{

    public Oboe2() {
        super();
        setInstrumentName(InstrumentName.OBOE_2.getName());
    }

    public Oboe2(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.OBOE_2.getName());
    }
}

package cp.out.instrument.woodwinds;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Oboe1 extends Oboe{

    public Oboe1() {
        super();
        setInstrumentName(InstrumentName.OBOE.getName());
    }

    public Oboe1(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.OBOE.getName());
    }
}

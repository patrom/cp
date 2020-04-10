package cp.out.instrument.brass;

import cp.out.instrument.Technical;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Trumpet2 extends Trumpet{

    public Trumpet2(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.TRUMPET_2.getName());
    }

    public Trumpet2() {
        super();
        setInstrumentName(InstrumentName.TRUMPET_2.getName());
    }

    public Trumpet2(InstrumentRegister instrumentRegister, Technical technical) {
        super(instrumentRegister, technical);
        setInstrumentName(InstrumentName.TRUMPET_2.getName());
    }
}

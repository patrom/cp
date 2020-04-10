package cp.out.instrument.brass;

import cp.out.instrument.Technical;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Trombone2 extends Trombone {

    public Trombone2(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.TROMBONE_2.getName());
    }

    public Trombone2() {
        super();
        setInstrumentName(InstrumentName.TROMBONE_2.getName());
    }

    public Trombone2(InstrumentRegister instrumentRegister, Technical technical) {
        super(instrumentRegister, technical);
        setInstrumentName(InstrumentName.TROMBONE_2.getName());
    }
}

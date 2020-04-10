package cp.out.instrument.brass;

import cp.out.instrument.Technical;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class FrenchHorn2 extends FrenchHorn {

    public FrenchHorn2(InstrumentRegister instrumentRegister) {
        super(instrumentRegister);
        setInstrumentName(InstrumentName.HORN_2.getName());
    }

    public FrenchHorn2() {
        super();
        setInstrumentName(InstrumentName.HORN_2.getName());
    }

    public FrenchHorn2(InstrumentRegister instrumentRegister, Technical technical) {
        super(instrumentRegister, technical);
        setInstrumentName(InstrumentName.HORN_2.getName());
    }
}

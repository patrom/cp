package cp.out.instrument.percussion.indeterminate.cymbals;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Gong extends Instrument {

    public Gong() {
        init();
    }

    private void init() {
        instrumentGroup = InstrumentGroup.PERCUSSION;
        order = 0;
        setLowest(53);
        setHighest(89);
//        setGeneralMidi(GeneralMidi.VIBRAPHONE);

//        setInstrumentName(InstrumentName.VIBRAPHONE.getName());
//        setInstrumentSound("pitched-percussion.vibraphone");
//        setVirtualName("Vibraphone");
    }

    public Gong(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }
}


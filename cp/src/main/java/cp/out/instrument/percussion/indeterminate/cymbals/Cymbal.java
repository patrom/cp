package cp.out.instrument.percussion.indeterminate.cymbals;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Cymbal extends Instrument {

    public Cymbal() {
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

    public Cymbal(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }
}


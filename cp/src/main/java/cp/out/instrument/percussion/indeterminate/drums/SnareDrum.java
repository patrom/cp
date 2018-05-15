package cp.out.instrument.percussion.indeterminate.drums;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class SnareDrum extends Instrument {

    public SnareDrum() {
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

    public SnareDrum(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }
}

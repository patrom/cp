package cp.out.instrument.percussion.determinate;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class TubalarBells extends Instrument {

    public TubalarBells() {
        init();
    }

    private void init() {
        instrumentGroup = InstrumentGroup.PERCUSSION;
        order = 0;
        setLowest(48);
        setHighest(80);
//        setGeneralMidi(GeneralMidi.);

        setInstrumentName(InstrumentName.TUBALAR_BELLS.getName());
//        setInstrumentSound("pitched-percussion.vibraphone");
        setVirtualName("Tubalar Bells");
    }

    public TubalarBells(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }
}
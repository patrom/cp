package cp.out.instrument.percussion.determinate;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Crotales extends Instrument {

    public Crotales() {
        init();
    }

    private void init() {
        instrumentGroup = InstrumentGroup.PERCUSSION;
        order = 0;
        setLowest(70);
        setHighest(102);
//        setGeneralMidi(GeneralMidi.);

        setInstrumentName(InstrumentName.CROTALES.getName());
//        setInstrumentSound("pitched-percussion.vibraphone");
        setVirtualName("Crotales");
    }

    public Crotales(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }
}
package cp.out.instrument.percussion.determinate;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

/**
 * Created by prombouts on 18/06/2017.
 */
public class Vibraphone extends Instrument {

    public Vibraphone() {
        init();
    }

    private void init() {
        instrumentGroup = InstrumentGroup.PERCUSSION;
        order = 0;
        setLowest(53);
        setHighest(89);
        setGeneralMidi(GeneralMidi.VIBRAPHONE);

        setInstrumentName(InstrumentName.VIBRAPHONE.getName());
        setInstrumentSound("pitched-percussion.vibraphone");
        setVirtualName("Vibraphone");
    }

    public Vibraphone(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }
}
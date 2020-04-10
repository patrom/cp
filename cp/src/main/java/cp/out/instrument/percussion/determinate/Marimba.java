package cp.out.instrument.percussion.determinate;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

/**
 * Created by prombouts on 18/06/2017.
 */
public class Marimba extends Instrument {

    public Marimba() {
        init();
    }

    private void init() {
        instrumentGroup = InstrumentGroup.MALLETS;
        order = 0;
        setLowest(36);
        setHighest(96);
		setGeneralMidi(GeneralMidi.MARIMBA);

        setInstrumentName(InstrumentName.MARIMBA.getName());
        setInstrumentSound("pitched-percussion.marimba");
        setVirtualName("Marimba");
    }

    public Marimba(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }
}
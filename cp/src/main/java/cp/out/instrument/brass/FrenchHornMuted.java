package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class FrenchHornMuted extends Instrument {

    public FrenchHornMuted(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }

    public FrenchHornMuted() {
        init();
    }

    private void init() {
        instrumentGroup = InstrumentGroup.BRASS;
        order = 1;
        setLowest(40);
        setHighest(70);
        setGeneralMidi(GeneralMidi.FRENCH_HORN);

        setInstrumentName(InstrumentName.HORN.getName());
        setInstrumentSound("brass.french-horn");
        setVirtualName("Horn");
    }

}

package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class BassTromboneMuted extends Instrument {

    public BassTromboneMuted(InstrumentRegister instrumentRegister) {
        init();
        setInstrumentRegister(instrumentRegister);
    }

    private void init() {
        instrumentGroup = InstrumentGroup.BRASS;
        order = 3;
        setLowest(34);
        setHighest(65);
        setGeneralMidi(GeneralMidi.TROMBONE);

        setInstrumentName(InstrumentName.BASS_TROMBONE.getName());
        setInstrumentSound("brass.trombone.bass");
        setVirtualName("Bass Trombone");
        setClef("F");
    }

    public BassTromboneMuted() {
        init();
    }

}

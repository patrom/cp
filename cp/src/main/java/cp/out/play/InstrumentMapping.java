package cp.out.play;

import cp.out.instrument.Instrument;

/**
 * Created by prombouts on 4/11/2016.
 */
public class InstrumentMapping {

    private Instrument instrument;
    private int channel;

    public InstrumentMapping(Instrument instrument, int channel) {
        this.instrument = instrument;
        this.channel = channel;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getChannel() {
        return channel;
    }

}

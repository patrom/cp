package cp.out.play;

import cp.out.instrument.Instrument;

/**
 * Created by prombouts on 4/11/2016.
 */
public class InstrumentMapping implements Comparable<InstrumentMapping>{

    private Instrument instrument;
    private int channel;
    private int scoreOrder;

    public InstrumentMapping(Instrument instrument, int channel, int scoreOrder) {
        this.instrument = instrument;
        this.channel = channel;
        this.scoreOrder = scoreOrder;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getChannel() {
        return channel;
    }

    public int getScoreOrder() {
        return scoreOrder;
    }

    @Override
	public int compareTo(InstrumentMapping instrumentMapping) {
		if (this.scoreOrder < instrumentMapping.getScoreOrder()) {
			return -1;
		}else if (this.scoreOrder > instrumentMapping.getScoreOrder()){
			return 1;
		}
		return 0;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstrumentMapping)) return false;

        InstrumentMapping that = (InstrumentMapping) o;

        if (getChannel() != that.getChannel()) return false;
        if (getScoreOrder() != that.getScoreOrder()) return false;
        return getInstrument() != null ? getInstrument().equals(that.getInstrument()) : that.getInstrument() == null;

    }

    @Override
    public int hashCode() {
        int result = getInstrument() != null ? getInstrument().hashCode() : 0;
        result = 31 * result + getChannel();
        result = 31 * result + getScoreOrder();
        return result;
    }
}

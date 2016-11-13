package cp.out.play;

import cp.out.instrument.Instrument;
import cp.out.orchestration.quality.OrchestralQuality;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 4/11/2016.
 */
public class InstrumentMapping implements Comparable<InstrumentMapping>{

    private Instrument instrument;
    private int channel;//playback channel on Kontakt/Vienna (+1)
    private int scoreOrder;//order on page layout: 0 is top, ...
    private OrchestralQuality orchestralQuality;
    private List<InstrumentMapping> dependantInstruments = new ArrayList<>();

    public InstrumentMapping(Instrument instrument, int channel, int scoreOrder) {
        this.instrument = instrument;
        this.channel = channel - 1;
        this.scoreOrder = scoreOrder;
    }

    public void addDependantInstrument(InstrumentMapping instrumentMapping){
        dependantInstruments.add(instrumentMapping);
    }

    public List<InstrumentMapping> getDependantInstruments() {
        return dependantInstruments;
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

    public OrchestralQuality getOrchestralQuality() {
        return orchestralQuality;
    }

    public void setOrchestralQuality(OrchestralQuality orchestralQuality) {
        this.orchestralQuality = orchestralQuality;
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

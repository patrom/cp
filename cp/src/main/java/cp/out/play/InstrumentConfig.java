package cp.out.play;

import cp.out.instrument.Instrument;
import cp.out.instrument.strings.CelloSolo;
import cp.out.instrument.strings.ViolaSolo;
import cp.out.instrument.strings.ViolinSolo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by prombouts on 4/11/2016.
 */
@Component
public class InstrumentConfig {

    private Map<Integer, InstrumentMapping> instruments = new TreeMap<>();

    public InstrumentConfig() {
        instruments = getStringTrio();
    }

    public Map<Integer, InstrumentMapping> getInstruments(){
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStringDuo(){
        instruments.put(1,new InstrumentMapping(new ViolinSolo(), 2, 1));
        instruments.put(0,new InstrumentMapping(new CelloSolo(), 1, 0));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStringTrio(){
        instruments.put(2,new InstrumentMapping(new ViolinSolo(), 3, 2));
        instruments.put(1,new InstrumentMapping(new ViolaSolo(), 2, 1));
        instruments.put(0,new InstrumentMapping(new CelloSolo(), 1, 0));
        return instruments;
    }

    public InstrumentMapping getInstrumentMappingForVoice(int voice){
        return instruments.get(voice);
    }

    public Instrument getInstrumentForVoice(int voice){
        return instruments.get(voice).getInstrument();
    }

    public int getChannelForVoice(int voice){
        return instruments.get(voice).getChannel();
    }

    public int getSize(){
        return instruments.size();
    }
}

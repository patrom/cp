package cp.out.play;

import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.plucked.Guitar;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.CelloSolo;
import cp.out.instrument.strings.ViolaSolo;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.quality.Pleasant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by prombouts on 4/11/2016.
 */
@Component
public class InstrumentConfig {

    @Autowired
    private Pleasant pleasant;

    private Map<Integer, InstrumentMapping> instruments = new TreeMap<>();

    public InstrumentConfig() {
        instruments = getStringQuartet();
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

    private Map<Integer, InstrumentMapping> getStringQuartet(){
        instruments.put(3,new InstrumentMapping(new ViolinSolo(), 2, 0));
        instruments.put(2,new InstrumentMapping(new ViolinSolo(), 2, 1));
        instruments.put(1,new InstrumentMapping(new ViolaSolo(), 1, 2));
        instruments.put(0,new InstrumentMapping(new CelloSolo(), 0, 3));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getPiano(int totalVoices){
        for (int i = 0; i < totalVoices; i++) {
            instruments.put(i,new InstrumentMapping(new Piano(), i + 1, i));
        }
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getClassicalGuitar(){
        instruments.put(2,new InstrumentMapping(new Guitar(new InstrumentRegister(40, 55)), 1, 0));
        instruments.put(1,new InstrumentMapping(new Guitar(new InstrumentRegister(50, 67)), 1, 0));
        instruments.put(0,new InstrumentMapping(new Guitar(new InstrumentRegister(67, 76)), 1, 0));
		return instruments;
	}

	public Map<Integer, InstrumentMapping> getWindQuartet(){
        instruments.put(3,new InstrumentMapping(new Flute(), 4, 3));
        instruments.put(2,new InstrumentMapping(new Oboe(), 3, 2));
        instruments.put(1,new InstrumentMapping(new Clarinet(), 2, 1));
        instruments.put(0,new InstrumentMapping(new Bassoon(), 1, 0));
		return instruments;
	}


	public Map<Integer, InstrumentMapping> getFluteClarinetBassoonGreen(){
        instruments.put(2,new InstrumentMapping(pleasant.getFlute(), 3, 2));
        instruments.put(1,new InstrumentMapping(pleasant.getClarinet(), 2, 1));
        instruments.put(0,new InstrumentMapping(pleasant.getBassoon(), 1, 0));
		return instruments;
	}
//
//	public List<Instrument> getPianoAndViolin(){
//		List<Instrument> voices = new ArrayList<>();
//		voices.add(new ViolinSolo(4, 1));
//		voices.add(new Piano(3, 0));
//		voices.add(new Piano(2, 0));
//		voices.add(new Piano(1, 0));
//		voices.add(new Piano(0, 0));
//		return voices;
//	}
//
//	public List<Instrument> getPianoAndFlute(){
//		List<Instrument> voices = new ArrayList<>();
//		voices.add(new Flute(4, 1));
//		voices.add(new Piano(3, 0));
//		voices.add(new Piano(2, 0));
//		voices.add(new Piano(1, 0));
//		voices.add(new Piano(0, 0));
//		return voices;
//	}
//
//	public List<Instrument> getPianoAnd2Flutes(){
//		List<Instrument> voices = new ArrayList<>();
//		voices.add(new Flute(5, 1));
//		voices.add(new Flute(4, 1));
//		voices.add(new Piano(3, 0));
//		voices.add(new Piano(2, 0));
//		voices.add(new Piano(1, 0));
//		voices.add(new Piano(0, 0));
//		return voices;
//	}
//
//
//	public List<Instrument> getStrings(OrchestralQuality orchestralQuality){
////		Instrument basses = pleasant.getBasicInstrument(InstrumentName.BASS.getName());
//		Instrument cellos = orchestralQuality.getBasicInstrument(InstrumentName.CELLO.getName());
//		Instrument violas = orchestralQuality.getBasicInstrument(InstrumentName.VIOLA.getName());
//		Instrument violins = orchestralQuality.getBasicInstrument(InstrumentName.VIOLIN_I.getName());
////		cellos.setChannel(0);
////		violas.setChannel(1);
////		violins.setChannel(2);
//		List<Instrument> strings = new ArrayList<>();
////		strings.add(basses);
//		strings.add(cellos);
//		strings.add(violas);
//		strings.add(violins);
//		return strings;
//	}
//
//	public List<Instrument> getStringsDoubleTriads(){
//		List<Instrument> strings = new ArrayList<>();
//		strings.add(new ViolinSolo(5, 0));
//		strings.add(new ViolinSolo(4, 0));
//		strings.add(new ViolinSolo(3, 0));
//		strings.add(new CelloSolo(2, 2));
//		strings.add(new CelloSolo(1, 2));
//		strings.add(new DoublebassSolo(0, 3));
//		return strings;
//	}
//
    public List<InstrumentMapping> getOrderedInstrumentMappings(){
        List<InstrumentMapping> instrumentMappings = new ArrayList<>(instruments.values());
        Collections.sort(instrumentMappings);
        return instrumentMappings;
    }

    public Map<Integer, InstrumentMapping> getInstruments(){
        return instruments;
    }

    public InstrumentMapping getInstrumentMappingForVoice(int voice){
        return instruments.get(voice);
    }

    public Instrument getInstrumentForVoice(int voice){
        InstrumentMapping instrumentMapping = instruments.get(voice);
        if (instrumentMapping == null){
            return null;
        }
        return instrumentMapping.getInstrument();
    }

    public int getChannelForVoice(int voice){
        return instruments.get(voice).getChannel();
    }

    public int getSize(){
        return instruments.size();
    }
}

package cp.out.play;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.*;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.plucked.Guitar;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
import cp.out.instrument.voice.Alto;
import cp.out.instrument.voice.Bass;
import cp.out.instrument.voice.Soprano;
import cp.out.instrument.voice.Tenor;
import cp.out.instrument.woodwinds.*;
import cp.out.orchestration.InstrumentName;
import cp.out.orchestration.quality.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by prombouts on 4/11/2016.
 */
@Component
public class InstrumentConfig {

    @Autowired
    private Brilliant brilliant;
    @Autowired
    private Bright bright;
    @Autowired
    private Pleasant pleasant;
    @Autowired
    private Rich rich;
    @Autowired
    private Golden golden;
    @Autowired
    private Glowing glowing;
    @Autowired
    private Mellow mellow;
    @Autowired
    private Warm warm;

    private Map<Integer, InstrumentMapping> instruments = new TreeMap<>();
    private List<InstrumentMapping> allInstrumentMappings = new ArrayList<>();

    @PostConstruct
    public void instrumentInit() {
        Piano piano = new Piano();
        piano.setInstrumentRegister(new InstrumentRegister(67,80));
        instruments.put(4,new InstrumentMapping(piano, 4, 4));
        instruments = getStrings(mellow);
        for (InstrumentMapping instrumentMapping : instruments.values()) {
            allInstrumentMappings.add(instrumentMapping);
            allInstrumentMappings.addAll(instrumentMapping.getDependantInstruments());
        }
        Collections.sort(allInstrumentMappings);
    }

    public Map<Integer, InstrumentMapping> getInstruments(){
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStringDuo(){
        instruments.put(1,new InstrumentMapping(new ViolinSolo(), 3, 0));
        instruments.put(0,new InstrumentMapping(new CelloSolo(), 1, 1));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStringTrio(){
        instruments.put(2,new InstrumentMapping(new ViolinSolo(), 3, 0));
        instruments.put(1,new InstrumentMapping(new ViolaSolo(), 2, 1));
        instruments.put(0,new InstrumentMapping(new CelloSolo(), 1, 2));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStringQuartet(){
        instruments.put(3,new InstrumentMapping(new ViolinSolo(), 3, 0));
        instruments.put(2,new InstrumentMapping(new ViolinSolo(), 3, 1));
        instruments.put(1,new InstrumentMapping(new ViolaSolo(), 2, 2));
        instruments.put(0,new InstrumentMapping(new CelloSolo(), 1, 3));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getSAChoir(){
        instruments.put(1,new InstrumentMapping(new Soprano(), 1, 0));
        instruments.put(0,new InstrumentMapping(new Alto(), 2, 1));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getSATBChoir(){
        instruments.put(3,new InstrumentMapping(new Soprano(), 1, 1));
        instruments.put(2,new InstrumentMapping(new Alto(), 2, 1));
        instruments.put(1,new InstrumentMapping(new Tenor(), 3, 2));
        instruments.put(0,new InstrumentMapping(new Bass(), 4, 3));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getSAATBChoir(){
        instruments.put(4,new InstrumentMapping(new Soprano(), 1, 0));
        instruments.put(3,new InstrumentMapping(new Alto(), 2, 1));
        instruments.put(2,new InstrumentMapping(new Alto(), 2, 2));
        instruments.put(1,new InstrumentMapping(new Tenor(), 3, 3));
        instruments.put(0,new InstrumentMapping(new Bass(), 4, 4));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getPiano(int totalVoices){
        for (int i = 0; i < totalVoices; i++) {
            instruments.put(i,new InstrumentMapping(new Piano(), i + 1, i));
        }
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getClassicalGuitar(){
        instruments.put(0,new InstrumentMapping(new Guitar(new InstrumentRegister(40, 55)), 1, 3));
        instruments.put(1,new InstrumentMapping(new Guitar(new InstrumentRegister(50, 67)), 1, 2));
        instruments.put(2,new InstrumentMapping(new Guitar(new InstrumentRegister(67, 76)), 1, 1));
        instruments.put(3,new InstrumentMapping(new Guitar(new InstrumentRegister(67, 76)), 1, 0));
		return instruments;
	}

	public Map<Integer, InstrumentMapping> getWindQuartet(){
        instruments.put(3,new InstrumentMapping(new Flute(), 4, 0));
        instruments.put(2,new InstrumentMapping(new Oboe(), 3, 1));
        instruments.put(1,new InstrumentMapping(new Clarinet(), 2, 2));
        instruments.put(0,new InstrumentMapping(new Bassoon(), 1, 3));
		return instruments;
	}

	public Map<Integer, InstrumentMapping> getFluteClarinetBassoonGreen(){
        instruments.put(3,new InstrumentMapping(pleasant.getFlute(), 4, 0));
        instruments.put(2,new InstrumentMapping(pleasant.getClarinet(), 2, 1));
        instruments.put(1,new InstrumentMapping(pleasant.getClarinet(), 2, 2));
        instruments.put(0,new InstrumentMapping(pleasant.getBassoon(), 1, 3));
		return instruments;
	}

    public Map<Integer, InstrumentMapping> getStrings(OrchestralQuality orchestralQuality){
		Instrument cello = orchestralQuality.getBasicInstrument(InstrumentName.CELLO.getName());
		Instrument viola = orchestralQuality.getBasicInstrument(InstrumentName.VIOLA.getName());
		Instrument violin = orchestralQuality.getBasicInstrument(InstrumentName.VIOLIN_I.getName());
        instruments.put(3,new InstrumentMapping(violin, 3, 0));
        instruments.put(2,new InstrumentMapping(violin, 3, 1));
//        instruments.put(2,new InstrumentMapping(viola, 2, 2));
        instruments.put(1,new InstrumentMapping(viola, 2, 2));
        instruments.put(0,new InstrumentMapping(cello, 1, 3));
        return instruments;
	}

	public Map<Integer, InstrumentMapping> getPianoAndViolin(){
        instruments.put(4,new InstrumentMapping(new ViolinSolo(), 3, 0));
        instruments.put(3,new InstrumentMapping(pleasant.getPiano(), 4, 1));
        instruments.put(2,new InstrumentMapping(rich.getPiano(), 4, 2));
        instruments.put(1,new InstrumentMapping(rich.getPiano(), 4, 3));
        instruments.put(0,new InstrumentMapping(mellow.getPiano(), 4, 4));
        return instruments;
	}
	public Map<Integer, InstrumentMapping> getOrchestra(){
        InstrumentMapping piccolo = new InstrumentMapping(new Piccolo(), 2, 0);
        InstrumentMapping flute = new InstrumentMapping(new Flute(), 1, 1);
        InstrumentMapping altoFlute = new InstrumentMapping(new AltoFlute(), 2, 2);
        InstrumentMapping oboe = new InstrumentMapping(new Oboe(), 2, 3);
        InstrumentMapping corAnglais = new InstrumentMapping(new CorAnglais(), 2, 4);
        InstrumentMapping clarinetEflat = new InstrumentMapping(new ClarinetEFlat(), 2, 5);
        InstrumentMapping clarinet = new InstrumentMapping(new Clarinet(), 3, 6);
        InstrumentMapping bassClarinet = new InstrumentMapping(new BassClarinet(), 2, 7);
        InstrumentMapping bassoon = new InstrumentMapping(new Bassoon(), 4, 8);
        InstrumentMapping contrabassoon = new InstrumentMapping(new ContraBassoon(), 2, 9);

        InstrumentMapping horn = new InstrumentMapping(new FrenchHorn(), 5, 10);
        InstrumentMapping trumpet = new InstrumentMapping(new Trumpet(), 6, 11);
        InstrumentMapping trombone = new InstrumentMapping(new Trombone(), 7, 12);
        InstrumentMapping bassTrombone = new InstrumentMapping(new BassTrombone(), 2, 13);
        InstrumentMapping tuba = new InstrumentMapping(new Tuba(), 2, 14);

        InstrumentMapping violin1 = new InstrumentMapping(new ViolinsI(), 8, 15);
        InstrumentMapping violin2 = new InstrumentMapping(new ViolinsII(), 8, 16);
        InstrumentMapping viola = new InstrumentMapping(new Viola(), 9, 17);
        InstrumentMapping cello = new InstrumentMapping(new Cello(), 10, 18);
        InstrumentMapping bass = new InstrumentMapping(new Doublebass(), 11, 19);

        violin1.setOrchestralQuality(rich);
        flute.setOrchestralQuality(bright);
        violin1.addDependantInstrument(flute);
        instruments.put(3,violin1);
        instruments.put(2,violin2);
        instruments.put(1,viola);
        cello.addDependantInstrument(bassoon);
        instruments.put(0,cello);
        return  instruments;
    }

    public List<InstrumentMapping> getOrderedInstrumentMappings(){
        List<InstrumentMapping> instrumentMappings = new ArrayList<>(instruments.values());
        Collections.sort(instrumentMappings);
        return instrumentMappings;
    }

    public InstrumentMapping getInstrumentMappingForVoice(int voice){
        return instruments.get(voice);
    }

    public InstrumentMapping getInstrumentMapping(int index){
        return allInstrumentMappings.get(index);
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

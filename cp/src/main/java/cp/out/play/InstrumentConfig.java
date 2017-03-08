package cp.out.play;

import cp.out.instrument.Articulation;
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
    private BrilliantWhite brilliantWhite;
    @Autowired
    private BrightYellow brightYellow;
    @Autowired
    private PleasantGreen pleasantGreen;
    @Autowired
    private RichBlue richBlue;
    @Autowired
    private GoldenOrange goldenOrange;
    @Autowired
    private GlowingRed glowingRed;
    @Autowired
    private MellowPurple mellowPurple;
    @Autowired
    private WarmBrown warmBrown;


    @Autowired
    private MediumRange mediumRange;

    private Map<Integer, InstrumentMapping> instruments = new TreeMap<>();
    private List<InstrumentMapping> allInstrumentMappings = new ArrayList<>();

    @PostConstruct
    public void instrumentInit(){

//        Piano piano = new Piano();
//        piano.setInstrumentRegister(new InstrumentRegister(67,80));
//        instruments.put(4,new InstrumentMapping(piano, 4, 4));
//        instruments = getSAATBChoir();
//        instruments.put(0,new InstrumentMapping(new ViolinSolo() , 3, 0));
//        instruments = getPianoAndStrinqQuartet(pleasantGreen, mellowPurple, richBlue);
        instruments = getStrinqQuartet(mediumRange, mellowPurple, mediumRange);
//        instruments = getStringTrio();
//        instruments = getInstrument(5, new Clarinet());
//        instruments.put(0,new InstrumentMapping(new ViolinSolo() , 3, 0));
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
        InstrumentMapping violin = new InstrumentMapping(new ViolinSolo(), 3, 0);
        violin.setArticulation(Articulation.DETACHE);
        instruments.put(2,violin);
        InstrumentMapping viola = new InstrumentMapping(new ViolaSolo(), 2, 1);
        viola.setArticulation(Articulation.DETACHE);
        instruments.put(1,viola);
        InstrumentMapping cello = new InstrumentMapping(new CelloSolo(), 1, 2);
        cello.setArticulation(Articulation.DETACHE);
        instruments.put(0,cello);
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStrinqQuartet(OrchestralQuality orchestralQualityMelody, OrchestralQuality orchestralQualityBass, OrchestralQuality orchestralQualityAcc) {
        InstrumentMapping violin1 = new InstrumentMapping(new ViolinSolo(), 3, 0);
        violin1.setArticulation(Articulation.DETACHE);
        violin1.setOrchestralQuality(orchestralQualityMelody);
        instruments.put(3, violin1);
        InstrumentMapping violin2 = new InstrumentMapping(new ViolinSolo(), 3, 1);
        violin2.setArticulation(Articulation.DETACHE);
        violin2.setOrchestralQuality(orchestralQualityAcc);
        instruments.put(2, violin2);
        InstrumentMapping viola = new InstrumentMapping(new ViolaSolo(), 2, 2);
        viola.setArticulation(Articulation.DETACHE);
        viola.setOrchestralQuality(orchestralQualityAcc);
        instruments.put(1, viola);
        InstrumentMapping cello = new InstrumentMapping(new CelloSolo(), 1, 3);
        cello.setArticulation(Articulation.DETACHE);
        cello.setOrchestralQuality(orchestralQualityBass);
        instruments.put(0, cello);
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getPianoAndStrinqQuartet(OrchestralQuality orchestralQualityMelody, OrchestralQuality orchestralQualityBass, OrchestralQuality orchestralQualityAcc) {
//        InstrumentMapping harmony1 = new InstrumentMapping(new ViolinSolo(), 7, 7);
//        instruments.put(7, harmony1);
//        InstrumentMapping harmony2 = new InstrumentMapping(new ViolinSolo(), 6, 6);
//        instruments.put(6, harmony2);
        InstrumentMapping harmony3 = new InstrumentMapping(new ViolinSolo(), 5, 5);
        instruments.put(5, harmony3);


        InstrumentMapping piano = new InstrumentMapping(new Piano(), 4, 4);
        piano.setOrchestralQuality(orchestralQualityAcc);
        instruments.put(4, piano);

        InstrumentMapping violin1 = new InstrumentMapping(new ViolinSolo(), 3, 0);
        violin1.setArticulation(Articulation.DETACHE);
        violin1.setOrchestralQuality(orchestralQualityMelody);
        instruments.put(3, violin1);
        InstrumentMapping violin2 = new InstrumentMapping(new ViolinSolo(), 3, 1);
        violin2.setArticulation(Articulation.DETACHE);
        violin2.setOrchestralQuality(orchestralQualityAcc);
        instruments.put(2, violin2);
        InstrumentMapping viola = new InstrumentMapping(new ViolaSolo(), 2, 2);
        viola.setArticulation(Articulation.DETACHE);
        viola.setOrchestralQuality(orchestralQualityAcc);
        instruments.put(1, viola);
        InstrumentMapping cello = new InstrumentMapping(new CelloSolo(), 1, 3);
        cello.setArticulation(Articulation.DETACHE);
        cello.setOrchestralQuality(orchestralQualityBass);
        instruments.put(0, cello);

        //harmony mapping
        violin1.addHarmonyInstrumentMapping(5,5);
        violin2.addHarmonyInstrumentMapping(6,6);
        viola.addHarmonyInstrumentMapping(7,7);
        return instruments;
    }


    private Map<Integer, InstrumentMapping> getStringQuartet(){
        InstrumentMapping violin1 = new InstrumentMapping(new ViolinSolo(), 3, 0);
        violin1.setArticulation(Articulation.DETACHE);
        instruments.put(3,violin1);
        InstrumentMapping violin2 = new InstrumentMapping(new ViolinSolo(), 3, 1);
        violin2.setArticulation(Articulation.DETACHE);
        instruments.put(2,violin2);
        InstrumentMapping viola = new InstrumentMapping(new ViolaSolo(), 2, 2);
        viola.setArticulation(Articulation.DETACHE);
        instruments.put(1,viola);
        InstrumentMapping cello = new InstrumentMapping(new CelloSolo(), 1, 3);
        cello.setArticulation(Articulation.DETACHE);
        instruments.put(0,cello);
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

    public Map<Integer, InstrumentMapping> getInstrument(int totalVoices, Instrument instrument){
        for (int i = 0; i < totalVoices; i++) {
            instruments.put(i,new InstrumentMapping(instrument, i + 1, i));
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
        instruments.put(3,new InstrumentMapping(pleasantGreen.getFlute(), 4, 0));
        instruments.put(2,new InstrumentMapping(pleasantGreen.getClarinet(), 2, 1));
        instruments.put(1,new InstrumentMapping(pleasantGreen.getClarinet(), 2, 2));
        instruments.put(0,new InstrumentMapping(pleasantGreen.getBassoon(), 1, 3));
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
        instruments.put(3,new InstrumentMapping(pleasantGreen.getPiano(), 4, 1));
        instruments.put(2,new InstrumentMapping(richBlue.getPiano(), 4, 2));
        instruments.put(1,new InstrumentMapping(richBlue.getPiano(), 4, 3));
        instruments.put(0,new InstrumentMapping(mellowPurple.getPiano(), 4, 4));
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

        violin1.setOrchestralQuality(brightYellow);
        flute.setOrchestralQuality(brightYellow);
        violin1.addDependantInstrument(flute);
        violin1.addDependantInstrument(clarinet);

        instruments.put(1,violin1);
//        instruments.put(2,violin2);
//        instruments.put(1,viola);
        cello.addDependantInstrument(horn);
        cello.setOrchestralQuality(richBlue);
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
            throw new IllegalStateException("InstrumentConfig not available for voice: " + voice);
        }
        return instrumentMapping.getInstrument();
    }

    public Articulation getArticuationForVoice(int voice){
        InstrumentMapping instrumentMapping = instruments.get(voice);
        if (instrumentMapping == null){
            throw new IllegalStateException("InstrumentConfig not available for voice: " + voice);
        }
        return instrumentMapping.getArticulation();
    }

    public int getChannelForVoice(int voice){
        return instruments.get(voice).getChannel();
    }

    public int getSize(){
        return instruments.size();
    }
}

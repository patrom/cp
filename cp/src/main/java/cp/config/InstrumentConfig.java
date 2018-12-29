package cp.config;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.*;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Glockenspiel;
import cp.out.instrument.percussion.determinate.Marimba;
import cp.out.instrument.percussion.determinate.Xylophone;
import cp.out.instrument.plucked.Guitar;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
import cp.out.instrument.voice.Alto;
import cp.out.instrument.voice.Bass;
import cp.out.instrument.voice.Soprano;
import cp.out.instrument.voice.Tenor;
import cp.out.instrument.woodwinds.*;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.play.InstrumentMapping;
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
    private ColorQualityConfig colorQualityConfig;

    private Map<Integer, InstrumentMapping> instruments = new TreeMap<>();

    @PostConstruct
    public void instrumentInit(){
//        Piano piano = new Piano();
//        instruments.put(0,new InstrumentMapping(piano, 1, 0));
//        instruments.put(1,new InstrumentMapping(piano, 2, 1));
//        instruments.put(2,new InstrumentMapping(piano, 3, 2));
//        instruments.put(3,new InstrumentMapping(piano, 4, 3));
//        instruments = getSATBChoir();
//        instruments = getSAChoir();
//        instruments.put(0,new InstrumentMapping(new ViolinSolo() , 3, 0));
//        instruments = getPianoAndStrinqQuartet(pleasantGreen, mellowPurple, richBlue);
//        instruments = getStrinqQuartet(mediumRange, mellowPurple, mediumRange);
//        instruments = getOrchestra();
//        instruments = getWoodWind_Horn_mutedBrass();
//        instruments = getWindQuartet();
//        instruments = getBrassTrio();
//        instruments = getBrassQuartet();
//        instruments = getRhythmQuartet();
        instruments = getStrinqQuartetOrchestral();
//        instruments = getTesttOrchestral();
//        instruments = getStrinqQuintetOrchestral();
//        instruments = getBrassQuintet();
//        instruments = getStrinqSextet();
//        orchestra = getOrchestra();
//        instruments = getWoodWindsDuo();
//        instruments = getStringTrio();
//        instruments = getStringDuo();
//        instruments = getGuitarDuo();
//        instruments = getInstrument(5, new Clarinet());
    }

    private Map<Integer, InstrumentMapping> getWoodWindsDuo(){
        instruments.put(1,new InstrumentMapping(new Flute(), 1, 0));
        instruments.put(0,new InstrumentMapping(new Clarinet(), 3, 1));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStringTrio(){
        instruments.put(2,new InstrumentMapping(new ViolinsI(), 4, 0));
        instruments.put(1,new InstrumentMapping(new Viola(), 2, 1));
        instruments.put(0,new InstrumentMapping(new Cello(), 1, 2));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getStringDuo(){
        instruments.put(1, new InstrumentMapping(new ViolinsI(), 4, 1));
        instruments.put(0, new InstrumentMapping(new Cello(), 1, 2));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getGuitarDuo(){
        instruments.put(1,new InstrumentMapping(new Flute(), 2, 1));
        instruments.put(0,new InstrumentMapping(new Guitar(), 1, 2));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getBrassTrio(){
        instruments.put(2,new InstrumentMapping(new Trumpet(), 1, 0));
        instruments.put(1,new InstrumentMapping(new Trombone(), 2, 1));
        instruments.put(0,new InstrumentMapping(new FrenchHorn(), 3, 2));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getBrassQuartet(){
        instruments.put(3,new InstrumentMapping(new Trumpet(), 1, 0));
        instruments.put(2,new InstrumentMapping(new Trombone(), 2, 1));
        instruments.put(1,new InstrumentMapping(new FrenchHorn(), 3, 2));
        instruments.put(0,new InstrumentMapping(new FrenchHorn(), 4, 3));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getBrassQuintet(){
        instruments.put(4,new InstrumentMapping(new Trumpet(), 5, 0));
        instruments.put(3,new InstrumentMapping(new Trumpet(), 4, 1));
        instruments.put(2,new InstrumentMapping(new FrenchHorn(), 3, 2));
        instruments.put(1,new InstrumentMapping(new Trombone(), 2, 3));
        instruments.put(0,new InstrumentMapping(new Tuba(), 1, 4));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getStrinqQuartet() {
        instruments.put(3, new InstrumentMapping(new ViolinSolo(), 4, 0));
        instruments.put(2, new InstrumentMapping(new Flute(), 3, 1));
        instruments.put(1, new InstrumentMapping(new ViolaSolo(), 2, 2));
        instruments.put(0, new InstrumentMapping(new CelloSolo(), 1, 3));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getRhythmQuartet() {
        instruments.put(3, new InstrumentMapping(new Glockenspiel(), 4, 0));
        instruments.put(2, new InstrumentMapping(new Flute(), 3, 1));
        instruments.put(1, new InstrumentMapping(new Celesta(), 2, 2));
        instruments.put(0, new InstrumentMapping(new DoubleBass(), 1, 3));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getRhythmQuartet2() {
        instruments.put(3, new InstrumentMapping(new Xylophone(), 4, 0));
        instruments.put(2, new InstrumentMapping(new CorAnglais(), 3, 1));
        instruments.put(1, new InstrumentMapping(new Harp(), 2, 2));
        instruments.put(0, new InstrumentMapping(new Bassoon(), 1, 3));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getStrinqQuartetOrchestral() {
        instruments.put(3, new InstrumentMapping(new ViolinsI(), 1, 0));
        instruments.put(2, new InstrumentMapping(new ViolinsII(), 2, 1));
        instruments.put(1, new InstrumentMapping(new Viola(), 3, 2));
        instruments.put(0, new InstrumentMapping(new Cello(), 5, 3));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getTesttOrchestral() {
        instruments.put(3, new InstrumentMapping(new ViolinsI(), 8, 0));
        instruments.put(2, new InstrumentMapping(new Trumpet(), 6, 1));
        instruments.put(1, new InstrumentMapping(new Viola(), 10, 2));
        instruments.put(0, new InstrumentMapping(new Cello(), 11, 3));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getStrinqQuintetOrchestral() {
        instruments.put(4, new InstrumentMapping(new ViolinsI(), 5, 0));
        instruments.put(3, new InstrumentMapping(new ViolinsII(), 4, 1));
        instruments.put(2, new InstrumentMapping(new Viola(), 3, 2));
        instruments.put(1, new InstrumentMapping(new Cello(), 2, 3));
        instruments.put(0, new InstrumentMapping(new DoubleBass(), 1, 4));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getStrinqSextet() {
        instruments.put(5, new InstrumentMapping(new ViolinSolo(), 4, 0));
        instruments.put(4, new InstrumentMapping(new ViolinSolo(), 4, 1));
        instruments.put(3, new InstrumentMapping(new ViolaSolo(), 2, 2));
        instruments.put(2, new InstrumentMapping(new ViolaSolo(), 2, 3));
        instruments.put(1, new InstrumentMapping(new CelloSolo(), 1, 4));
        instruments.put(0, new InstrumentMapping(new CelloSolo(), 1, 5));
        return instruments;
    }

    public Map<Integer, InstrumentMapping> getPercussion() {
//        instruments.put(3, new InstrumentMapping(new ViolinSolo(), 4, 0));
        instruments.put(2, new InstrumentMapping(new Celesta(), 3, 1));
        instruments.put(1, new InstrumentMapping(new Marimba(), 2, 2));
        instruments.put(0, new InstrumentMapping(new Marimba(), 1, 3));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getPianoAndStrinqQuartet() {
//        InstrumentMapping harmony1 = new InstrumentMapping(new ViolinSolo(), 7, 7);
//        instruments.put(7, harmony1);
//        InstrumentMapping harmony2 = new InstrumentMapping(new ViolinSolo(), 6, 6);
//        instruments.put(6, harmony2);
        InstrumentMapping harmony3 = new InstrumentMapping(new ViolinSolo(), 5, 5);
        instruments.put(5, harmony3);

        InstrumentMapping piano = new InstrumentMapping(new Piano(), 4, 4);
        instruments.put(4, piano);

        InstrumentMapping violin1 = new InstrumentMapping(new ViolinSolo(), 3, 0);
        instruments.put(3, violin1);
        InstrumentMapping violin2 = new InstrumentMapping(new ViolinSolo(), 3, 1);
        instruments.put(2, violin2);
        InstrumentMapping viola = new InstrumentMapping(new ViolaSolo(), 2, 2);
        instruments.put(1, viola);
        InstrumentMapping cello = new InstrumentMapping(new CelloSolo(), 1, 3);
        instruments.put(0, cello);

        //harmony mapping
        violin1.addHarmonyInstrumentMapping(5,5);
        violin2.addHarmonyInstrumentMapping(6,6);
        viola.addHarmonyInstrumentMapping(7,7);
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getSAChoir(){
        instruments.put(1,new InstrumentMapping(new Soprano(), 1, 0));
        instruments.put(0,new InstrumentMapping(new Tenor(), 2, 1));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getBAChoir(){
        instruments.put(1,new InstrumentMapping(new Soprano(), 1, 0));
        instruments.put(0,new InstrumentMapping(new Bass(), 2, 1));
        return instruments;
    }

    private Map<Integer, InstrumentMapping> getSATBChoir(){
        instruments.put(3,new InstrumentMapping(new Soprano(), 1, 0));
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
        instruments.put(3,new InstrumentMapping(new Flute(), 1, 0));
        instruments.put(2,new InstrumentMapping(new Oboe(), 2, 1));
        instruments.put(1,new InstrumentMapping(new Clarinet(), 3, 2));
        instruments.put(0,new InstrumentMapping(new Bassoon(), 4, 3));
		return instruments;
	}

	public Map<Integer, InstrumentMapping> getFluteClarinetBassoon(){
        instruments.put(2,new InstrumentMapping(new Flute(), 1, 0));
//        instruments.put(2,new InstrumentMapping(new Clarinet(), 2, 1));
        instruments.put(1,new InstrumentMapping(new Clarinet(), 3, 1));
        instruments.put(0, new InstrumentMapping(new Bassoon(), 4, 2));
		return instruments;
	}

	public Map<Integer, InstrumentMapping> getPianoAndViolin(){
        instruments.put(4,new InstrumentMapping(new ViolinSolo(), 3, 0));
        instruments.put(3,new InstrumentMapping(new Piano(), 4, 1));
        instruments.put(2,new InstrumentMapping(new Piano(), 4, 2));
        instruments.put(1,new InstrumentMapping(new Piano(), 4, 3));
        instruments.put(0,new InstrumentMapping(new Piano(), 4, 4));
        return instruments;
	}

	private Map<Integer, InstrumentMapping> getOrchestra(){
        List<InstrumentMapping> orchestra = new ArrayList<>();
        orchestra.add(new InstrumentMapping(new Piccolo(), 1, 1));
        orchestra.add(new InstrumentMapping(new Flute(), 2, 2));
        orchestra.add(new InstrumentMapping(new Oboe(), 3, 3));
        orchestra.add(new InstrumentMapping(new CorAnglais(), 4, 4));
        orchestra.add(new InstrumentMapping(new Clarinet(), 5, 5));
        orchestra.add(new InstrumentMapping(new BassClarinet(), 6, 6));
        orchestra.add(new InstrumentMapping(new Bassoon(), 7, 7));
        orchestra.add(new InstrumentMapping(new ContraBassoon(), 8, 8));

        orchestra.add(new InstrumentMapping(new FrenchHorn(), 9, 9));
        orchestra.add(new InstrumentMapping(new Trumpet(), 10, 10));
        orchestra.add(new InstrumentMapping(new Trombone(), 11, 11));
        orchestra.add(new InstrumentMapping(new Tuba(), 12, 12));

        orchestra.add(new InstrumentMapping(new ViolinsI(), 13, 13));
        orchestra.add(new InstrumentMapping(new Viola(), 14, 14));
        orchestra.add(new InstrumentMapping(new Cello(), 15, 15));
        orchestra.add(new InstrumentMapping(new DoubleBass(), 16, 16));
        int voice = orchestra.size() - 1;
        for (InstrumentMapping instrumentMapping : orchestra) {
            instruments.put(voice, instrumentMapping);
            voice--;
        }
        return  instruments;
    }

    private Map<Integer, InstrumentMapping> getWoodWind_Horn_mutedBrass(){
        List<InstrumentMapping> orchestra = new ArrayList<>();
        orchestra.add(new InstrumentMapping(new Piccolo(), 1, 1));
        orchestra.add(new InstrumentMapping(new Flute(), 2, 2));
        orchestra.add(new InstrumentMapping(new AltoFlute(), 3, 3));
        orchestra.add(new InstrumentMapping(new Oboe(), 4, 4));
        orchestra.add(new InstrumentMapping(new CorAnglais(), 5, 5));
        orchestra.add(new InstrumentMapping(new ClarinetEFlat(), 6, 6));
        orchestra.add(new InstrumentMapping(new Clarinet(), 7, 7));
        orchestra.add(new InstrumentMapping(new BassClarinet(), 8, 8));
        orchestra.add(new InstrumentMapping(new Bassoon(), 9, 9));
        orchestra.add(new InstrumentMapping(new ContraBassoon(), 10, 10));

        orchestra.add(new InstrumentMapping(new FrenchHorn(), 11, 11));
        orchestra.add(new InstrumentMapping(new Trumpet(), 12, 12));
        orchestra.add(new InstrumentMapping(new TrumpetMuted(), 13, 13));//muted
        orchestra.add(new InstrumentMapping(new Trombone(), 14, 14));//muted
        orchestra.add(new InstrumentMapping(new TromboneMuted(), 15, 15));//muted
        orchestra.add(new InstrumentMapping(new Tuba(), 16, 16));//muted

        int voice = orchestra.size() - 1;
        for (InstrumentMapping instrumentMapping : orchestra) {
            instruments.put(voice, instrumentMapping);
            voice--;
        }
        return  instruments;
    }


    public InstrumentMapping getInstrumentMappingForVoice(int voice){
        return instruments.get(voice);
    }

    public InstrumentMapping getInstrumentMapping(int index){
        return instruments.get(index);
    }

    public Instrument getInstrumentForVoice(int voice){
        InstrumentMapping instrumentMapping = instruments.get(voice);
        if (instrumentMapping == null){
            throw new IllegalStateException("InstrumentConfig not available for voice: " + voice);
        }
        OrchestralQuality orchestralQuality = colorQualityConfig.getOchestralQualityForVoice(voice);
        if(orchestralQuality != null){
            return orchestralQuality.getBasicInstrument(instrumentMapping.getInstrument().getInstrumentName());
        }
        return instrumentMapping.getInstrument();
    }

    public InstrumentMapping getInstrumentMappingByBame(String name){
        Optional<InstrumentMapping> mapping = instruments.values().stream().filter(instrumentMapping -> instrumentMapping.getInstrument().getInstrumentName().equals(name)).findFirst();
        if (mapping.isPresent()) {
            return mapping.get();
        }
//        throw new IllegalStateException("InstrumentMapping not available for name: " + name);
        return null;
    }

    public int getSize(){
        return instruments.size();
    }

    public InstrumentMapping getInstrumentMappingForScoreOrder(int order){
        for (InstrumentMapping instrumentMapping : instruments.values()) {
            if (instrumentMapping.getScoreOrder() ==  order){
                return instrumentMapping;
            }
        }
        throw new IllegalStateException("No Instrument found in instrumentConfig for: " + order);
    }

    public Map<Integer, InstrumentMapping> getInstruments() {
        return instruments;
    }

    public boolean contains(Instrument instrument) {
        return instruments.values().stream().anyMatch(instrumentMapping -> instrumentMapping.getInstrument().equals(instrument));
    }
}

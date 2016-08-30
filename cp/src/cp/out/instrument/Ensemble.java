package cp.out.instrument;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.plucked.Guitar;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.CelloSolo;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.DoublebassSolo;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolaSolo;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.orchestration.quality.Pleasant;

//reverse numbering Kontakt!
@Component
public class Ensemble {
	
	@Autowired
	private Pleasant pleasant;
	
	public List<Instrument> getStringDuo(){
		List<Instrument> stringDuo = new ArrayList<Instrument>();
		stringDuo.add(new ViolinSolo(1, 1));
		stringDuo.add(new CelloSolo(0, 0));
		return stringDuo;
	}

	public List<Instrument> getStringQuartet(){
		List<Instrument> stringQuartet = new ArrayList<Instrument>();
		stringQuartet.add(new Cello());
		stringQuartet.add(new Viola());
		stringQuartet.add(new ViolinSolo());
		stringQuartet.add(new ViolinSolo());
		return stringQuartet;
	}
	
	public List<Instrument> getWindQuartet(){
		List<Instrument> instruments = new ArrayList<Instrument>();
		instruments.add(new Flute(3, 3));
		instruments.add(new Oboe(2, 1));
		instruments.add(new Clarinet(1, 2));
		instruments.add(new Bassoon(0, 0));
		return instruments;
	}
	
	public List<Instrument> getPianoAndViolin(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new ViolinSolo(4, 1));
		voices.add(new Piano(3, 0));
		voices.add(new Piano(2, 0));
		voices.add(new Piano(1, 0));
		voices.add(new Piano(0, 0));
		return voices;
	}
	
	public List<Instrument> getPianoAndFlute(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new Flute(4, 1));
		voices.add(new Piano(3, 0));
		voices.add(new Piano(2, 0));
		voices.add(new Piano(1, 0));
		voices.add(new Piano(0, 0));
		return voices;
	}
	
	public List<Instrument> getPianoAnd2Flutes(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new Flute(5, 1));
		voices.add(new Flute(4, 1));
		voices.add(new Piano(3, 0));
		voices.add(new Piano(2, 0));
		voices.add(new Piano(1, 0));
		voices.add(new Piano(0, 0));
		return voices;
	}
	
	public List<Instrument> getPiano(int totalVoices){
		List<Instrument> voices = new ArrayList<Instrument>();
		for (int i = 0; i < totalVoices; i++) {
			voices.add(new Piano(i, 0));
		}
		return voices;
	}
	
	public List<Instrument> getStringTrio(){
		List<Instrument> strings = new ArrayList<Instrument>();
		strings.add(new Cello(0,0));
		strings.add(new Viola(1,1));
		strings.add(new ViolinSolo(2,2));
		return strings;
	}
	
	public List<Instrument> getStrings(OrchestralQuality orchestralQuality){
//		Instrument basses = pleasant.getBasicInstrument(InstrumentName.BASS.getName());
		Instrument cellos = orchestralQuality.getBasicInstrument(InstrumentName.CELLO.getName());
		Instrument violas = orchestralQuality.getBasicInstrument(InstrumentName.VIOLA.getName());
		Instrument violins = orchestralQuality.getBasicInstrument(InstrumentName.VIOLIN_I.getName());
//		cellos.setChannel(0);
//		violas.setChannel(1);
//		violins.setChannel(2);
		List<Instrument> strings = new ArrayList<Instrument>();
//		strings.add(basses);
		strings.add(cellos);
		strings.add(violas);
		strings.add(violins);
		return strings;
	}
	
	public List<Instrument> getStringsDoubleTriads(){
		List<Instrument> strings = new ArrayList<Instrument>();
		strings.add(new ViolinSolo(5, 0));
		strings.add(new ViolinSolo(4, 0));
		strings.add(new ViolinSolo(3, 0));
		strings.add(new CelloSolo(2, 2));
		strings.add(new CelloSolo(1, 2));
		strings.add(new DoublebassSolo(0, 3));
		return strings;
	}
	
	public List<Instrument> getClassicalGuitar(){
		List<Instrument> guitar = new ArrayList<Instrument>();
		guitar.add(new Guitar(new InstrumentRegister(40, 55)));
		guitar.add(new Guitar(new InstrumentRegister(50, 67)));
		guitar.add(new Guitar(new InstrumentRegister(67, 76)));
		return guitar;
	}
	
	public List<Instrument> getFluteClarinetBassoonGreen(){
		Instrument bassoon = pleasant.getBasicInstrument(InstrumentName.BASSOON.getName());
		Instrument clarinet = pleasant.getBasicInstrument(InstrumentName.CLARINET.getName());
		Instrument flute = pleasant.getBasicInstrument(InstrumentName.FLUTE.getName());
		List<Instrument> instruments = new ArrayList<Instrument>();
		instruments.add(bassoon);
		instruments.add(clarinet);
		instruments.add(flute);
		return instruments;
	}
	
}

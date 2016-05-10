package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.Glockenspiel;
import cp.out.instrument.percussion.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Piccolo;
import cp.out.orchestration.InstrumentName;

@Component
public class Brilliant extends OrchestralQuality{

	public Brilliant() {
		color = "white";
		quality = "brilliant";
		type = "basic";
		instruments = Stream.of(
				new Piccolo(new InstrumentRegister(83, 108)),
				new Clarinet(new InstrumentRegister(82, 92)),
				new Trumpet(new InstrumentRegister(82, 92)),
//				new Piano(new InstrumentRegister(96, 108)),
				new ViolinsI(new InstrumentRegister(89, 100)),
				new Viola(new InstrumentRegister(81, 93)),
				new Cello(new InstrumentRegister(69, 81)),
				new Glockenspiel(new InstrumentRegister(103, 108)),
				new Celesta(new InstrumentRegister(96, 108)),
				new Piano(new InstrumentRegister(96, 108))
				).collect(Collectors.toList());
	}
	
	public Instrument getClarinet(){
		return getBasicInstrument(InstrumentName.CLARINET.getName());
	}
	
	public Instrument getTrumpet(){
		return getBasicInstrument(InstrumentName.TRUMPET.getName());
	}
	
	public Instrument getPiano(){
		return getBasicInstrument(InstrumentName.PIANO.getName());
	}
	
	public Instrument getViolinsI(){
		return getBasicInstrument(InstrumentName.VIOLIN_I.getName());
	}
	
	public Instrument getViola(){
		return getBasicInstrument(InstrumentName.VIOLA.getName());
	}
	
	public Instrument getCello(){
		return getBasicInstrument(InstrumentName.CELLO.getName());
	}

}
	

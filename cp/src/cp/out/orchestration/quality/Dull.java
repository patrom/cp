package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import cp.out.instrument.Instrument;
import cp.out.instrument.Piano;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Dull extends OrchestralQuality{

	public Dull() {
		color = "gray";
		quality = "dull";
		type = "basic";
		
		instruments = Stream.of(
				new FrenchHorn(new InstrumentRegister(35, 41)),
				new Trumpet(new InstrumentRegister(52, 58)),
				new Trombone(new InstrumentRegister(40, 48)),
				new Piano(new InstrumentRegister(21, 28))
				).collect(Collectors.toList());
	}
	
	public Instrument getFrenchHorn(){
		return getInstrument(InstrumentName.HORN.getName());
	}
	
	public Instrument getTrumpet(){
		return getInstrument(InstrumentName.TRUMPET.getName());
	}
	
	public Instrument getTrombone(){
		return getInstrument(InstrumentName.TROMBONE.getName());
	}
	
	public Instrument getPiano(){
		return getInstrument(InstrumentName.PIANO.getName());
	}

}

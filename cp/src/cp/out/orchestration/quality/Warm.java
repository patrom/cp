package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Doublebass;
import cp.out.orchestration.InstrumentName;

public class Warm extends OrchestralQuality{

	public Warm() {
		color = "brown";
		quality = "warm";
		type = "basic";
		instruments = Stream.of(
				new FrenchHorn(new InstrumentRegister(55, 68)),
				new Trumpet(new InstrumentRegister(58, 55)),
				new Trombone(new InstrumentRegister(48, 53)),
				new Doublebass(new InstrumentRegister(39, 45))
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
	
	public Instrument getDoubleBass(){
		return getInstrument(InstrumentName.BASS.getName());
	}

}

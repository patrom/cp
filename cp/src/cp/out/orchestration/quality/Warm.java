package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.BassTrombone;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.woodwinds.AltoFlute;
import cp.out.instrument.woodwinds.ContraBassoon;
import cp.out.orchestration.InstrumentName;
@Component
public class Warm extends OrchestralQuality{

	public Warm() {
		color = "brown";
		quality = "warm";
		type = "basic";
		instruments = Stream.of(
				new AltoFlute(new InstrumentRegister(55, 71)),
				new FrenchHorn(new InstrumentRegister(55, 68)),
				new Trumpet(new InstrumentRegister(58, 55)),
				new Trombone(new InstrumentRegister(48, 53)),
				new BassTrombone(new InstrumentRegister(34, 51)),
				new Doublebass(new InstrumentRegister(39, 45)),
				new ContraBassoon(new InstrumentRegister(22, 54))
				).collect(Collectors.toList());
	}
	
	public Instrument getFrenchHorn(){
		return getBasicInstrument(InstrumentName.HORN.getName());
	}
	
	public Instrument getTrumpet(){
		return getBasicInstrument(InstrumentName.TRUMPET.getName());
	}
	
	public Instrument getTrombone(){
		return getBasicInstrument(InstrumentName.TROMBONE.getName());
	}
	
	public Instrument getDoubleBass(){
		return getBasicInstrument(InstrumentName.BASS.getName());
	}

}

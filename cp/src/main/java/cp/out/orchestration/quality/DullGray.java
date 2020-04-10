package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trombone1;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class DullGray extends OrchestralQuality{

	public DullGray() {
		color = "gray";
		quality = "dull";
		type = "basic";
		
		instruments = Stream.of(
				new FrenchHorn(new InstrumentRegister(35, 41)),
				new Trumpet(new InstrumentRegister(52, 58)),
				new Trombone1(new InstrumentRegister(40, 48))
//				new Piano(new InstrumentRegister(21, 28))
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
	
	public Instrument getPiano(){
		return getBasicInstrument(InstrumentName.PIANO.getName());
	}

}

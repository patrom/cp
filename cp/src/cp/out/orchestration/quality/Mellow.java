package cp.out.orchestration.quality;

import static java.util.stream.Collectors.toList;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import cp.out.instrument.Instrument;
import cp.out.instrument.Piano;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.orchestration.InstrumentName;
@Component
public class Mellow extends OrchestralQuality{

	public Mellow() {
		color = "purple";
		quality = "mellow";
		type = "basic";
		instruments = Stream.of(
				new FrenchHorn(new InstrumentRegister(41, 55)),
//				new Piano(new InstrumentRegister(28, 55)),
				new ViolinsI(new InstrumentRegister(55, 67)),
				new Viola(new InstrumentRegister(48, 60)),
				new Cello(new InstrumentRegister(36, 49)),
				new Doublebass(new InstrumentRegister(24, 50))
				).collect(toList());
	}
	
	public Instrument getFrenchHorn(){
		return getBasicInstrument(InstrumentName.HORN.getName());
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
	
	public Instrument getDoubleBass(){
		return getBasicInstrument(InstrumentName.BASS.getName());
	}

}

package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
import cp.out.orchestration.InstrumentName;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
@Component
public class MellowPurple extends OrchestralQuality{

	public MellowPurple() {
		color = "purple";
		quality = "mellowPurple";
		type = "basic";
		instruments = Stream.of(
				new FrenchHorn(new InstrumentRegister(41, 55)),
				new ViolinsI(new InstrumentRegister(55, 67)),
				new ViolinSolo(new InstrumentRegister(55, 67)),
				new Viola(new InstrumentRegister(48, 60)),
				new ViolaSolo(new InstrumentRegister(48, 60)),
				new Cello(new InstrumentRegister(36, 49)),
				new CelloSolo(new InstrumentRegister(36, 49)),
				new Doublebass(new InstrumentRegister(24, 50)),
				new Harp(new InstrumentRegister(23, 41)),
				new Piano(new InstrumentRegister(28, 55))
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

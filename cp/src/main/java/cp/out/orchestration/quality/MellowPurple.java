package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.FrenchHorn1;
import cp.out.instrument.brass.Tuba;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Vibraphone;
import cp.out.instrument.plucked.Harp;
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
				new FrenchHorn1(new InstrumentRegister(41, 55)),
                new Tuba(new InstrumentRegister(34, 54)),
				new ViolinsI(new InstrumentRegister(55, 67)),
				new ViolinsII(new InstrumentRegister(55, 67)),
				new ViolinSolo(new InstrumentRegister(55, 67)),
				new Viola(new InstrumentRegister(48, 60)),
				new ViolaSolo(new InstrumentRegister(48, 60)),
				new Cello(new InstrumentRegister(36, 49)),
				new CelloSolo(new InstrumentRegister(36, 49)),
				new DoubleBass(new InstrumentRegister(24, 50)),
				new DoublebassSolo(new InstrumentRegister(24, 50)),
				new Harp(new InstrumentRegister(23, 41)),
				new Piano(new InstrumentRegister(28, 55)),
				new Vibraphone(new InstrumentRegister(53, 72))
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

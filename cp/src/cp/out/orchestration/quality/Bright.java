package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;

public class Bright extends OrchestralQuality{

	public Bright() {
		color = "yellow";
		quality = "bright";
		type = "basic";
		instruments = Stream.of(
				new Flute(new InstrumentRegister(79, 98)),
				new Oboe(new InstrumentRegister(80, 91)),
				new Clarinet(new InstrumentRegister(77, 82)),
				new Trumpet(new InstrumentRegister(78, 83)),
				new ViolinsI(new InstrumentRegister(76, 89)),
				new Viola(new InstrumentRegister(69, 81)),
				new Cello(new InstrumentRegister(57, 69))
				).collect(Collectors.toList());
	}
	
	public Instrument getFlute(){
		return getInstrument(InstrumentName.FLUTE.getName());
	}
	
	public Instrument getOboe(){
		return getInstrument(InstrumentName.OBOE.getName());
	}
	
	public Instrument getClarinet(){
		return getInstrument(InstrumentName.CLARINET.getName());
	}
	
	public Instrument getTrumpet(){
		return getInstrument(InstrumentName.TRUMPET.getName());
	}
	
	public Instrument getViolinsI(){
		return getInstrument(InstrumentName.VIOLIN_I.getName());
	}
	
	public Instrument getViola(){
		return getInstrument(InstrumentName.VIOLA.getName());
	}
	
	public Instrument getCello(){
		return getInstrument(InstrumentName.CELLO.getName());
	}

}

package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import cp.out.instrument.Instrument;
import cp.out.instrument.Piano;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.orchestration.InstrumentName;
@Component
public class Rich extends OrchestralQuality{

	public Rich() {
		color = "blue";
		quality = "rich";
		type = "basic";
		instruments = Stream.of(
				new Flute(new InstrumentRegister(59, 71)),
				new Clarinet(new InstrumentRegister(50, 65)),
				new ViolinsI(new InstrumentRegister(62, 74)),
				new Viola(new InstrumentRegister(55, 67)),
				new Cello(new InstrumentRegister(43, 55)),
				new Doublebass(new InstrumentRegister(38, 55))
//				new Piano(new InstrumentRegister(55, 72))
				).collect(Collectors.toList());
	}
	
	public Instrument getFlute(){
		return getInstrument(InstrumentName.FLUTE.getName());
	}
	
	public Instrument getClarinet(){
		return getInstrument(InstrumentName.CLARINET.getName());
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
	
	public Instrument getDoubleBass(){
		return getInstrument(InstrumentName.BASS.getName());
	}
	
	public Instrument getPiano(){
		return getInstrument(InstrumentName.PIANO.getName());
	}

}

package cp.out.orchestration.quality;

import static java.util.stream.Collectors.toList;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.AltoFlute;
import cp.out.instrument.woodwinds.BassClarinet;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.ClarinetEFlat;
import cp.out.instrument.woodwinds.CorAnglais;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;

@Component
public class Pleasant extends OrchestralQuality{

	public Pleasant() {
		color = "green";
		quality = "pleasant";
		type = "basic";
		instruments = Stream.of(
				new Flute(new InstrumentRegister(71, 79)),
				new ClarinetEFlat(new InstrumentRegister(55, 71)),
				new Clarinet(new InstrumentRegister(67, 77)),
				new BassClarinet(new InstrumentRegister(55, 82)),
				new Bassoon(new InstrumentRegister(34, 72)),
				new ViolinsI(new InstrumentRegister(69, 81)),
				new Viola(new InstrumentRegister(62, 74)),
				new Cello(new InstrumentRegister(50, 61)),
				new Doublebass(new InstrumentRegister(43, 49))
				).collect(toList());
		
		complementaryInstruments = Stream.of(
				new AltoFlute(new InstrumentRegister(80, 91)),
				new Oboe(new InstrumentRegister(67, 80)),
				new CorAnglais(new InstrumentRegister(67, 84)),
				new Clarinet(new InstrumentRegister(50, 67)),
				new BassClarinet(new InstrumentRegister(43, 55)),
				new ViolinsI(new InstrumentRegister(76, 89)),
				new Viola(new InstrumentRegister(69, 81)),
				new Cello(new InstrumentRegister(57, 69))
				).collect(toList());
	}
	
	public Instrument getFlute(){
		return getBasicInstrument(InstrumentName.FLUTE.getName());
	}
	
	public Instrument getClarinet(){
		return getBasicInstrument(InstrumentName.CLARINET.getName());
	}
	
	public Instrument getBassoon(){
		return getBasicInstrument(InstrumentName.BASSOON.getName());
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

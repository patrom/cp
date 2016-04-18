package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;
@Component
public class Golden extends OrchestralQuality{

	public Golden() {
		color = "orange";
		quality = "golden";
		type = "basic";
		instruments = Stream.of(
				new Oboe(new InstrumentRegister(67, 80)),
				new FrenchHorn(new InstrumentRegister(68, 79)),
				new Trumpet(new InstrumentRegister(70, 78)),
				new Trombone(new InstrumentRegister(64, 77)),
				new ViolinsI(new InstrumentRegister(74, 84))
				).collect(Collectors.toList());
	}
	
	public Instrument getOboe(){
		return getInstrument(InstrumentName.OBOE.getName());
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
	
	public Instrument getViolinsI(){
		return getInstrument(InstrumentName.VIOLIN_I.getName());
	}

}

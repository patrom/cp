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
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.AltoFlute;
import cp.out.instrument.woodwinds.CorAnglais;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;
@Component
public class Golden extends OrchestralQuality{

	public Golden() {
		color = "orange";
		quality = "golden";
		type = "basic";
		instruments = Stream.of(
				new AltoFlute(new InstrumentRegister(80, 91)),
				new CorAnglais(new InstrumentRegister(67, 84)),
				new Oboe(new InstrumentRegister(67, 80)),
				new FrenchHorn(new InstrumentRegister(68, 79)),
				new Trumpet(new InstrumentRegister(70, 78)),
				new Trombone(new InstrumentRegister(64, 77)),
				new BassTrombone(new InstrumentRegister(55, 69)),
				new ViolinsI(new InstrumentRegister(74, 84))
				).collect(Collectors.toList());
	}
	
	public Instrument getOboe(){
		return getBasicInstrument(InstrumentName.OBOE.getName());
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
	
	public Instrument getViolinsI(){
		return getBasicInstrument(InstrumentName.VIOLIN_I.getName());
	}

}

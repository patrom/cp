package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
public class Glowing extends OrchestralQuality{
	
	@Autowired
	private Warm warm;

	public Glowing() {
		color = "red";
		quality = "glowing";
		type = "basic";
		instruments = Stream.of(
				new AltoFlute(new InstrumentRegister(67, 80)),
				new CorAnglais(new InstrumentRegister(52, 67)),
				new Oboe(new InstrumentRegister(58, 67)),
				new FrenchHorn(new InstrumentRegister(68, 79)),
				new Trumpet(new InstrumentRegister(64, 70)),
				new Trombone(new InstrumentRegister(53, 64)),
				new BassTrombone(new InstrumentRegister(41, 55)),
				new ViolinsI(new InstrumentRegister(67, 74))
				).collect(Collectors.toList());
	}
	
	@PostConstruct
	private void initComplementaryQualities(){
		closeQualities.add(warm);
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

package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.*;
import cp.out.instrument.percussion.determinate.Xylophone;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.strings.ViolinsII;
import cp.out.instrument.woodwinds.AltoFlute;
import cp.out.instrument.woodwinds.CorAnglais;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class GoldenOrange extends OrchestralQuality{
	
	@Autowired
	private GlowingRed glowingRed;

	public GoldenOrange() {
		color = "orange";
		quality = "golden";
		type = "basic";
		instruments = Stream.of(
				new AltoFlute(new InstrumentRegister(80, 91)),
				new CorAnglais(new InstrumentRegister(67, 84)),
				new Oboe(new InstrumentRegister(67, 80)),
				new FrenchHorn(new InstrumentRegister(68, 79)),
				new Trumpet(new InstrumentRegister(70, 78)),
				new TrumpetMuted(new InstrumentRegister(58, 70)),
				new Trombone(new InstrumentRegister(64, 77)),
				new TromboneMuted(new InstrumentRegister(48, 64)),
				new BassTrombone(new InstrumentRegister(55, 69)),
				new BassTromboneMuted(new InstrumentRegister(41, 55)),
				new ViolinsI(new InstrumentRegister(74, 84)),
				new ViolinsII(new InstrumentRegister(74, 84)),
				new ViolinSolo(new InstrumentRegister(74, 84)),
				new Xylophone(new InstrumentRegister(65, 108)),
				new Harp(new InstrumentRegister(60, 84))
				).collect(Collectors.toList());
		
	}
	
	@PostConstruct
	private void initComplementaryQualities(){
		closeQualities.add(glowingRed);
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

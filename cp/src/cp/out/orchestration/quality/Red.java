package cp.out.orchestration.quality;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;

public class Red extends OrchestralQuality{

	public Red() {
		color = "red";
		quality = "glowing";
		type = "basic";
		instruments = Stream.of(
				new Oboe(new InstrumentRegister(58, 67)),
				new FrenchHorn(new InstrumentRegister(68, 79)),
				new Trumpet(new InstrumentRegister(64, 70)),
				new Trombone(new InstrumentRegister(53, 64)),
				new ViolinsI(new InstrumentRegister(67, 74))
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

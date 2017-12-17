package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.*;
import cp.out.orchestration.InstrumentName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BrightYellow extends OrchestralQuality{
	
	@Autowired
	private PleasantGreen pleasantGreen;
	@Autowired
	private GoldenOrange goldenOrange;

	public BrightYellow() {
		color = "yellow";
		quality = "bright";
		type = "basic";
		instruments = Stream.of(
				new Piccolo(new InstrumentRegister(74, 83)),
				new Flute(new InstrumentRegister(79, 98)),
				new Oboe(new InstrumentRegister(80, 91)),
				new ClarinetEFlat(new InstrumentRegister(73, 95)),
				new Clarinet(new InstrumentRegister(77, 82)),
				new Trumpet(new InstrumentRegister(78, 83)),
				new ViolinsI(new InstrumentRegister(76, 89)),
				new ViolinsII(new InstrumentRegister(76, 89)),
				new ViolinSolo(new InstrumentRegister(76, 89)),
				new Viola(new InstrumentRegister(69, 81)),
				new ViolaSolo(new InstrumentRegister(69, 81)),
				new Cello(new InstrumentRegister(57, 69)),
				new CelloSolo(new InstrumentRegister(57, 69)),
				new Celesta(new InstrumentRegister(84, 96)),
				new Harp(new InstrumentRegister(95, 103)),
				new Piano(new InstrumentRegister(84, 96))
				).collect(Collectors.toList());
		
		
	}
	
	@PostConstruct
	private void initComplementaryQualities(){
		closeQualities.add(pleasantGreen);
		closeQualities.add(goldenOrange);
	}
	
	public Instrument getFlute(){
		return getBasicInstrument(InstrumentName.FLUTE.getName());
	}
	
	public Instrument getOboe(){
		return getBasicInstrument(InstrumentName.OBOE.getName());
	}
	
	public Instrument getClarinet(){
		return getBasicInstrument(InstrumentName.CLARINET.getName());
	}
	
	public Instrument getTrumpet(){
		return getBasicInstrument(InstrumentName.TRUMPET.getName());
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

}

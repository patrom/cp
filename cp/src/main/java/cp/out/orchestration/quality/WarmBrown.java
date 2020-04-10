package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.*;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.DoubleBass;
import cp.out.instrument.strings.DoublebassSolo;
import cp.out.instrument.woodwinds.AltoFlute;
import cp.out.instrument.woodwinds.ContraBassoon;
import cp.out.orchestration.InstrumentName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class WarmBrown extends OrchestralQuality{

	@Autowired
	private MellowPurple mellowPurple;

	public WarmBrown() {
		color = "brown";
		quality = "warm";
		type = "basic";
		instruments = Stream.of(
				new AltoFlute(new InstrumentRegister(55, 71)),
				new FrenchHorn1(new InstrumentRegister(55, 68)),
				new Trumpet1(new InstrumentRegister(58, 64)),
				new Trombone1(new InstrumentRegister(48, 53)),
				new BassTrombone(new InstrumentRegister(34, 51)),
				new DoubleBass(new InstrumentRegister(39, 45)),
				new DoublebassSolo(new InstrumentRegister(39, 45)),
				new ContraBassoon(new InstrumentRegister(22, 54)),
				new Harp(new InstrumentRegister(41, 60))
				).collect(Collectors.toList());
		
	}
	
	@PostConstruct
	private void initComplementaryQualities(){
		closeQualities.add(mellowPurple);
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
	
	public Instrument getDoubleBass(){
		return getBasicInstrument(InstrumentName.BASS.getName());
	}

}

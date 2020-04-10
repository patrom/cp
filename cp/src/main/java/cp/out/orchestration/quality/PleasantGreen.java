package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Glockenspiel;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.*;
import cp.out.orchestration.InstrumentName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class PleasantGreen extends OrchestralQuality{
	
	@Autowired
	private RichBlue richBlue;

	public PleasantGreen() {
		color = "green";
		quality = "pleasantGreen";
		type = "basic";
		instruments = Stream.of(
				new Flute2(new InstrumentRegister(71, 79)),
				new ClarinetEFlat(new InstrumentRegister(55, 71)),
				new Clarinet1(new InstrumentRegister(67, 77)),
				new BassClarinet(new InstrumentRegister(55, 82)),
				new Bassoon2(new InstrumentRegister(34, 57)),
				new ViolinsI(new InstrumentRegister(69, 81)),
				new ViolinsII(new InstrumentRegister(69, 81)),
				new ViolinSolo(new InstrumentRegister(69, 81)),
				new Viola(new InstrumentRegister(62, 74)),
				new ViolaSolo(new InstrumentRegister(62, 74)),
				new Cello(new InstrumentRegister(50, 61)),
				new CelloSolo(new InstrumentRegister(50, 61)),
				new DoubleBass(new InstrumentRegister(43, 49)),
				new DoublebassSolo(new InstrumentRegister(43, 49)),
				new Glockenspiel(new InstrumentRegister(87, 103)),
				new Celesta(new InstrumentRegister(72, 84)),
				new Harp(new InstrumentRegister(84, 95)),
				new Piano(new InstrumentRegister(72, 84))
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
	
	@PostConstruct
	private void initComplementaryQualities(){
		closeQualities.add(richBlue);
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

	public Instrument getPiano(){
		return getBasicInstrument(InstrumentName.PIANO.getName());
	}

}

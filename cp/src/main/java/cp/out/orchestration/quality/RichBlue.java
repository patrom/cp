package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;
import cp.out.instrument.brass.FrenchHorn2;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Glockenspiel;
import cp.out.instrument.percussion.determinate.Timpani;
import cp.out.instrument.percussion.determinate.Vibraphone;
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
public class RichBlue extends OrchestralQuality{
	
	@Autowired
	private MellowPurple mellowPurple;

	public RichBlue() {
		color = "blue";
		quality = "richBlue";
		type = "basic";
		instruments = Stream.of(
				new Flute1(new InstrumentRegister(59, 71)),
				new Clarinet2(new InstrumentRegister(50, 65)),
                new Bassoon2(new InstrumentRegister(58, 72)),
				new BassClarinet(new InstrumentRegister(34, 53)),
				new ViolinsI(new InstrumentRegister(62, 74)),
                new FrenchHorn2(new InstrumentRegister(53, 73), Technical.CON_SORDINO),
				new ViolinsII(new InstrumentRegister(62, 74)),
				new ViolinSolo(new InstrumentRegister(62, 74)),
				new Viola(new InstrumentRegister(55, 67)),
				new ViolaSolo(new InstrumentRegister(55, 67)),
				new Cello(new InstrumentRegister(43, 55)),
				new CelloSolo(new InstrumentRegister(43, 55)),
				new DoubleBass(new InstrumentRegister(38, 55)),
				new DoublebassSolo(new InstrumentRegister(38, 55)),
				new Piano(new InstrumentRegister(55, 72)),
				new Glockenspiel(new InstrumentRegister(77, 87)),
				new Celesta(new InstrumentRegister(60, 72)),
				new Vibraphone(new InstrumentRegister(72, 89)),
				new Timpani(new InstrumentRegister(41, 53))
				).collect(Collectors.toList());
		
	}
	
	@PostConstruct
	private void initComplementaryQualities(){
		closeQualities.add(mellowPurple);
	}
	
	public Instrument getFlute(){
		return getBasicInstrument(InstrumentName.FLUTE.getName());
	}
	
	public Instrument getClarinet(){
		return getBasicInstrument(InstrumentName.CLARINET.getName());
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

package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.Glockenspiel;
import cp.out.instrument.percussion.Timpani;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.BassClarinet;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.orchestration.InstrumentName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class Rich extends OrchestralQuality{
	
	@Autowired
	private Mellow mellow;

	public Rich() {
		color = "blue";
		quality = "rich";
		type = "basic";
		instruments = Stream.of(
				new Flute(new InstrumentRegister(59, 71)),
				new Clarinet(new InstrumentRegister(50, 65)),
				new BassClarinet(new InstrumentRegister(34, 53)),
				new ViolinsI(new InstrumentRegister(62, 74)),
				new Viola(new InstrumentRegister(55, 67)),
				new Cello(new InstrumentRegister(43, 55)),
				new Doublebass(new InstrumentRegister(38, 55)),
				new Piano(new InstrumentRegister(55, 72)),
				new Glockenspiel(new InstrumentRegister(77, 87)),
				new Celesta(new InstrumentRegister(60, 72)),
				new Timpani(new InstrumentRegister(41, 53))
				).collect(Collectors.toList());
		
	}
	
	@PostConstruct
	private void initComplementaryQualities(){
		closeQualities.add(mellow);
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
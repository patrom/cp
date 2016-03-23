package cp.out.orchestration.quality;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.util.RandomUtil;

@Component
public class Pleasant extends OrchestralQuality{
	
//	private List<Instrument> instruments = Stream.of(
//			new Flute(new InstrumentRegister(71, 79)),
//			new Clarinet(new InstrumentRegister(67, 77)),
//			new Bassoon(new InstrumentRegister(34, 72)),
//			new ViolinsI(new InstrumentRegister(69, 81)),
//			new Viola(new InstrumentRegister(62, 74)),
//			new Cello(new InstrumentRegister(50, 61)),
//			new Doublebass(new InstrumentRegister(43, 49))
//			).collect(Collectors.toList());

	public Pleasant() {
		color = "green";
		quality = "pleasant";
		type = "basic";
		instruments = Stream.of(
				new Flute(new InstrumentRegister(71, 79)),
				new Clarinet(new InstrumentRegister(67, 77)),
				new Bassoon(new InstrumentRegister(34, 72)),
				new ViolinsI(new InstrumentRegister(69, 81)),
				new Viola(new InstrumentRegister(62, 74)),
				new Cello(new InstrumentRegister(50, 61)),
				new Doublebass(new InstrumentRegister(43, 49))
				).collect(Collectors.toList());
	}
	
	
}

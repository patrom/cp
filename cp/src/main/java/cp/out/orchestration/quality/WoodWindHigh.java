package cp.out.orchestration.quality;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.woodwinds.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WoodWindHigh extends OrchestralQuality{

    /**
     * Rimsky-Korsakov
     */
    public WoodWindHigh() {
        type = "basic";
        instruments = Stream.of(
                new Piccolo(new InstrumentRegister(91, 103)),
                new Flute(new InstrumentRegister(79,91)),
                new AltoFlute(new InstrumentRegister(72, 84)),
                new Oboe(new InstrumentRegister(79,89)),
                new CorAnglais(new InstrumentRegister(72,80)),
                new ClarinetEFlat(new InstrumentRegister(75, 87)),
                new Clarinet(new InstrumentRegister(70, 82)),
                new BassClarinet(new InstrumentRegister(60, 72)),
                new Bassoon(new InstrumentRegister(60,69)),
                new ContraBassoon(new InstrumentRegister(36,55))
        ).collect(Collectors.toList());
    }
}
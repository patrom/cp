package cp.out.orchestration.quality;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.woodwinds.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WoodWindMiddle extends OrchestralQuality{

    /**
     * Rimsky-Korsakov
     */
    public WoodWindMiddle() {
        type = "basic";
        instruments = Stream.of(
                new Piccolo(new InstrumentRegister(79,91)),
                new Flute(new InstrumentRegister(67, 79)),
                new AltoFlute(new InstrumentRegister(60,72)),
                new Oboe(new InstrumentRegister(67,79)),
                new CorAnglais(new InstrumentRegister(60,72)),
                new ClarinetEFlat(new InstrumentRegister(63,75)),
                new Clarinet(new InstrumentRegister(58,70)),
                new BassClarinet(new InstrumentRegister(48,60)),
                new Bassoon(new InstrumentRegister(48,60)),
                new ContraBassoon(new InstrumentRegister(36,48))
        ).collect(Collectors.toList());
    }
}
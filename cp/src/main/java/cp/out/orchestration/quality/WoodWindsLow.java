package cp.out.orchestration.quality;

import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.woodwinds.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WoodWindsLow extends OrchestralQuality{

    /**
     * Rimsky-Korsakov
     */
    public WoodWindsLow() {
        type = "basic";
        instruments = Stream.of(
                new Piccolo(new InstrumentRegister(74, 79)),
                new Flute(new InstrumentRegister(59, 67)),
                new AltoFlute(new InstrumentRegister(55, 60)),
                new Oboe(new InstrumentRegister(59, 67)),
                new CorAnglais(new InstrumentRegister(52, 60)),
                new ClarinetEFlat(new InstrumentRegister(55, 63)),
                new Clarinet(new InstrumentRegister(48, 58)),
                new BassClarinet(new InstrumentRegister(34, 48)),
                new Bassoon(new InstrumentRegister(34, 48)),
                new ContraBassoon(new InstrumentRegister(22, 36))
        ).collect(Collectors.toList());
    }
}

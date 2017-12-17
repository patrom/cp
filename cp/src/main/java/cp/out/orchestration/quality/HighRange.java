package cp.out.orchestration.quality;

import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.ViolaSolo;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.strings.ViolinsII;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.ClarinetEFlat;
import cp.out.instrument.woodwinds.Flute;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 17/03/2017.
 */
@Component
public class HighRange extends OrchestralQuality {

    public HighRange() {
        InstrumentRegister range = new InstrumentRegister(72, 84);
        instruments = Stream.of(
                new Flute(range),
                new ClarinetEFlat(range),
                new Clarinet(range),
                new ViolinsI(range),
                new ViolinsII(range),
                new ViolinSolo(range),//TODO Range?
//                new Viola(new InstrumentRegister(62, 74)),
                new ViolaSolo(range),
//                new Cello(new InstrumentRegister(50, 61)),
//                new CelloSolo(new InstrumentRegister(50, 61)),
//                new DoubleBass(new InstrumentRegister(43, 49)),
//                new Glockenspiel(new InstrumentRegister(87, 103)),
//                new Celesta(new InstrumentRegister(72, 84)),
//                new Harp(new InstrumentRegister(84, 95)),
                new Piano(range)
        ).collect(toList());

    }
}

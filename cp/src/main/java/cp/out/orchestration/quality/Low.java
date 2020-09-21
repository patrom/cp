package cp.out.orchestration.quality;

import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Marimba;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.CelloSolo;
import cp.out.instrument.strings.DoubleBass;
import cp.out.instrument.strings.DoublebassSolo;
import cp.out.instrument.woodwinds.Bassoon;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class Low extends OrchestralQuality {

    public Low() {
        InstrumentRegister range = new InstrumentRegister(36, 60);
        instruments = Stream.of(
//                new Flute(new InstrumentRegister(71, 79)),
//                new ClarinetEFlat(new InstrumentRegister(55, 71)),
//                new Clarinet(new InstrumentRegister(67, 77)),
//                new BassClarinet(new InstrumentRegister(55, 82)),
                new Bassoon(range),

//                new Viola(new InstrumentRegister(62, 74)),
//                new Cello(new InstrumentRegister(50, 61)),
                new CelloSolo(range),
                new Cello(range),
                new DoubleBass(range),
                new DoublebassSolo(range),
                new Marimba(range),
//                new DoubleBass(new InstrumentRegister(43, 49)),
//                new Glockenspiel(new InstrumentRegister(87, 103)),
//                new Celesta(new InstrumentRegister(72, 84)),
//                new Harp(new InstrumentRegister(84, 95)),
                new Piano(range)
        ).collect(toList());
    }
}

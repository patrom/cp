package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.Marimba;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.ClarinetEFlat;
import cp.out.instrument.woodwinds.Flute;
import cp.out.orchestration.InstrumentName;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 10/07/2017.
 */
@Component
public class High extends OrchestralQuality {

    public High() {
        InstrumentRegister range = new InstrumentRegister(60, 84);
        instruments = Stream.of(
                new Flute(range),
                new ClarinetEFlat(range),
                new Clarinet(range),
//                new BassClarinet(new InstrumentRegister(55, 82)),
                new Bassoon(range),
                new ViolinsI(range),
                new ViolinsII(range),
                new ViolinSolo(range),
                new Viola(range),
                new ViolaSolo(range),
                new Cello(range),
                new CelloSolo(range),
//                new DoubleBass(new InstrumentRegister(43, 49)),
                new Marimba(range),
//                new Glockenspiel(new InstrumentRegister(87, 103)),
//                new Celesta(new InstrumentRegister(72, 84)),
//                new Harp(new InstrumentRegister(84, 95)),
                new Piano(range)
        ).collect(toList());

    }

    public Instrument getPiano(){
        return getBasicInstrument(InstrumentName.PIANO.getName());
    }
}

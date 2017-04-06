package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.ViolaSolo;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.orchestration.InstrumentName;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 14/01/2017.
 */
@Component
public class MediumRange extends OrchestralQuality {

    public MediumRange() {
        InstrumentRegister range = new InstrumentRegister(48, 72);
        instruments = Stream.of(
//                new Flute(new InstrumentRegister(71, 79)),
//                new ClarinetEFlat(new InstrumentRegister(55, 71)),
//                new Clarinet(new InstrumentRegister(67, 77)),
//                new BassClarinet(new InstrumentRegister(55, 82)),
                new Bassoon(range),
//                new ViolinsI(new InstrumentRegister(69, 81)),
                new ViolinSolo(range),//TODO Range?
//                new Viola(new InstrumentRegister(62, 74)),
                new ViolaSolo(range),
//                new Cello(new InstrumentRegister(50, 61)),
//                new CelloSolo(new InstrumentRegister(50, 61)),
//                new Doublebass(new InstrumentRegister(43, 49)),
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

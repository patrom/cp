package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.orchestration.InstrumentName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 14/01/2017.
 */
@Component
public class MediumRange extends OrchestralQuality {

    @Autowired
    private RichBlue richBlue;

    public MediumRange() {
        instruments = Stream.of(
//                new Flute(new InstrumentRegister(71, 79)),
//                new ClarinetEFlat(new InstrumentRegister(55, 71)),
//                new Clarinet(new InstrumentRegister(67, 77)),
//                new BassClarinet(new InstrumentRegister(55, 82)),
//                new Bassoon(new InstrumentRegister(34, 72)),
//                new ViolinsI(new InstrumentRegister(69, 81)),
                new ViolinSolo(new InstrumentRegister(48, 72)),//TODO Range?
//                new Viola(new InstrumentRegister(62, 74)),
//                new ViolaSolo(new InstrumentRegister(62, 74)),
//                new Cello(new InstrumentRegister(50, 61)),
//                new CelloSolo(new InstrumentRegister(50, 61)),
//                new Doublebass(new InstrumentRegister(43, 49)),
//                new Glockenspiel(new InstrumentRegister(87, 103)),
//                new Celesta(new InstrumentRegister(72, 84)),
//                new Harp(new InstrumentRegister(84, 95)),
                new Piano(new InstrumentRegister(48, 72))
        ).collect(toList());

    }

    public Instrument getPiano(){
        return getBasicInstrument(InstrumentName.PIANO.getName());
    }
}

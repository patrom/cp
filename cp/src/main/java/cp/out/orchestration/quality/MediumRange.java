package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Marimba;
import cp.out.instrument.plucked.Guitar;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.*;
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
                new Flute(new InstrumentRegister(59, 72)),
                new Oboe(new InstrumentRegister(58, 79)),
//                new ClarinetEFlat(new InstrumentRegister(55, 71)),
                new Clarinet(new InstrumentRegister(50, 72)),
//                new BassClarinet(new InstrumentRegister(55, 82)),
                new Bassoon(range),
                new ViolinsI(new InstrumentRegister(55, 72)),
                new ViolinsII(new InstrumentRegister(55, 72)),
                new ViolinSolo(new InstrumentRegister(55, 72)),//TODO Range?
                new Viola(range),
                new ViolaSolo(range),
                new Cello(range),
                new CelloSolo(range),
                new DoubleBass(range),
                new Marimba(range),
//                new Glockenspiel(new InstrumentRegister(87, 103)),
                new Celesta(new InstrumentRegister(60, 84)),
                new Harp(range),
                new Piano(range),
                new Trombone(range),
                new FrenchHorn(range),
                new Trumpet(new InstrumentRegister(58, 72)),

                new Guitar(new InstrumentRegister(55, 71)),
                new CorAnglais(new InstrumentRegister(52, 72))


        ).collect(toList());

    }

    public Instrument getPiano(){
        return getBasicInstrument(InstrumentName.PIANO.getName());
    }
}

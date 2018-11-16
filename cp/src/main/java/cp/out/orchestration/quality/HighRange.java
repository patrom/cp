package cp.out.orchestration.quality;

import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Glockenspiel;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.*;
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
                new Viola(range),
                new ViolaSolo(range),
                new Cello(range),
                new CelloSolo(range),
                new Glockenspiel(range),
                new Celesta(range),
                new Harp(range),
                new Piano(range)
        ).collect(toList());

    }
}

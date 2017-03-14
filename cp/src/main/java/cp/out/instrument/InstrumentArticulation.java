package cp.out.instrument;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 10/12/2016.
 */
@Component
public class InstrumentArticulation {

    List<Articulation> stringArticulations = new ArrayList<>();
    List<Articulation> woodwindArticulations = new ArrayList<>();

    public InstrumentArticulation() {
        stringArticulations = Stream.of(
                Articulation.LEGATO,
//                Articulation.PORTATO,
                Articulation.SFORZANDO,
                Articulation.STACCATO,
                Articulation.MARCATO,
                Articulation.PIZZICATO,
                Articulation.TREMELO
        ).collect(toList());
        woodwindArticulations = Stream.of(
                Articulation.LEGATO,
                Articulation.HARMONIC,
                Articulation.SUSTAINVIBRATO,

//                Articulation.PORTATO,
                Articulation.SFORZANDO,
                Articulation.STACCATO,
                Articulation.MARCATO
//                Articulation.TREMELO
        ).collect(toList());
    }

    public List<Articulation> getArticulations(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
                return stringArticulations;
            case WOODWINDS:
                return woodwindArticulations;
        }
        return Collections.emptyList();
    }
}

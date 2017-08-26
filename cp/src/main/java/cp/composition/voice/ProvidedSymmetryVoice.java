package cp.composition.voice;

import cp.model.melody.CpMelody;
import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class ProvidedSymmetryVoice extends Voice {

    private List<CpMelody> melodies;

    @PostConstruct
    public void init(){
        melodiesProvided = true;
        setTimeconfig();
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());

        mutationOperators = providedSymmetryOperators;

        technical = Technical.LEGATO;
    }

}

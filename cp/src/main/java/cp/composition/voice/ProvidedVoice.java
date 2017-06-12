package cp.composition.voice;

import cp.generator.provider.MelodyProvider;
import cp.model.melody.CpMelody;
import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 15/05/2017.
 */
@Component
public class ProvidedVoice extends Voice {

    @Autowired
    @Qualifier(value = "melodyGeneratorProvider")
    private MelodyProvider melodyProvider;

    private List<CpMelody> melodies;

    @PostConstruct
    public void init(){
//        melodies = melodyProvider.getMelodies();
        setTimeconfig();
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());
        pitchClassGenerators.add(emptyPitchClasses::updatePitchClasses);
        mutationOperators = rhytmMutationOperators;
        technical = Technical.LEGATO;
    }

}

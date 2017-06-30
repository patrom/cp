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
 * Created by prombouts on 26/06/2017.
 */
@Component
public class ProvidedDoubleVoice extends Voice {

    private List<CpMelody> melodies;

    @Autowired
    @Qualifier(value = "melodyDoubleProvider")//melodyManualProvider - melodyGeneratorProvider
    protected MelodyProvider melodyDoubleProvider;

    @PostConstruct
    public void init(){
        melodiesProvided = true;
        setTimeconfig();
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());

        melodyProvider = melodyDoubleProvider;

        mutationOperators = providedMutationOperators;

        technical = Technical.LEGATO;
    }

}
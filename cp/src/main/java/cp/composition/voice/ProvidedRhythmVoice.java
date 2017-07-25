package cp.composition.voice;

import cp.generator.provider.MelodyProvider;
import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
@Component
public class ProvidedRhythmVoice extends Voice {

    @Autowired
    @Qualifier(value = "melodyRhythmProvider")//melodyManualProvider - melodyGeneratorProvider
    protected MelodyProvider melodyRhythmProvider;

    @PostConstruct
    public void init(){
        melodiesProvided = true;
        setTimeconfig();
        dynamic = Dynamic.P;
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());

        melodyProvider = melodyRhythmProvider;

        //        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);

        mutationOperators = providedRhythmOperators;

        technical = Technical.LEGATO;
    }
}

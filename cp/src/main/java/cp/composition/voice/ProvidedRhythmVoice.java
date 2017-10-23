package cp.composition.voice;

import cp.generator.provider.MelodyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class ProvidedRhythmVoice extends Voice {

    @Autowired
    @Qualifier(value = "melodyRhythmProvider")//melodyManualProvider - melodyGeneratorProvider
    protected MelodyProvider melodyRhythmProvider;

    @PostConstruct
    public void init(){
        melodiesProvided = true;
        setTimeconfig();

        //        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);

    }
}

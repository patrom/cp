package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 5/12/2016.
 */
@Component
public class DoubleTimeVoice extends Voice {

    @PostConstruct
    public void init() {
        setTimeconfig();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
//        rhythmCombinations1 = timeConfig::getBeatsDoubleLength;

    }


}

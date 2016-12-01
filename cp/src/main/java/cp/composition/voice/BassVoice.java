package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 25/11/2016.
 */
@Component
public class BassVoice extends VoiceConfig {

    @PostConstruct
    public void init(){
        setTimeconfig();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//		pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        beatGroupStrategy = timeConfig::getBeatsDoubleLength;
    }
}

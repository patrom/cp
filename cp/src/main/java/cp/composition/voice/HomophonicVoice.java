package cp.composition.voice;

import cp.model.note.Dynamic;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class HomophonicVoice extends VoiceConfig{

    @PostConstruct
    public void init(){
        setTimeconfig();
        volume = Dynamic.MF.getLevel();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        beatGroupStrategy = timeConfig::getHomophonicBeatGroup;
    }

}

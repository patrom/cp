package cp.composition.voice;

import cp.model.note.Dynamic;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 7/01/2017.
 */
@Component
public class HarmonyVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
        dynamic = Dynamic.MF;
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);

        randomBeats = false;
        randomRhythmCombinations = false;
    }


}

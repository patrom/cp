package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class MelodyVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
//        timeConfig = timeDouble44;

//        evenRhythmCombinationsPerNoteSize = getCombinations();
//        unevenRhythmCombinationsPerNoteSize = getCombinations();

//        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);

    }

}

package cp.composition.voice;

import cp.model.melody.CpMelody;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by prombouts on 15/05/2017.
 */
@Component
public class ProvidedVoice extends Voice {

    private List<CpMelody> melodies;

    @PostConstruct
    public void init(){
        melodiesProvided = true;
        setTimeconfig();
//        timeConfig = timeRandom;

//        evenRhythmCombinationsPerNoteSize = homophonicEven;
//        unevenRhythmCombinationsPerNoteSize = homophonicUneven;

//        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
    }

}

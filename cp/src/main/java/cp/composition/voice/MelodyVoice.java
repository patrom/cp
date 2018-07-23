package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class MelodyVoice extends Voice {

    @PostConstruct
    public void init(){

//        timeConfig = timeDouble44;

//        evenRhythmCombinationsPerNoteSize = getCombinations();
//        unevenRhythmCombinationsPerNoteSize = getCombinations();N

//        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
//        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(orderPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(orderRandomNotePitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(orderNoteRepetitionPitchClasses::updatePitchClasses);
        allBeatgroups = Arrays.asList(beatgroups.beatgroupFourMotive);
//        allBeatgroups = Arrays.asList(beatgroups.beatGroupTwo, beatgroups.beatGroupThree, beatgroups.beatGroupFour, beatgroups.beatgroupFourMotive);
        setTimeconfig();
    }

}

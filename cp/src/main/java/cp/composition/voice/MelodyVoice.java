package cp.composition.voice;

import cp.nsga.operator.mutation.MutationType;
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
        setTimeconfig();
//        timeConfig = timeDouble44;

//        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);

        mutationTypes = Arrays.asList(MutationType.HARMONY);

    }

}

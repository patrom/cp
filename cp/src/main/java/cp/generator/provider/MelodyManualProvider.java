package cp.generator.provider;

import cp.model.melody.CpMelody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 12/05/2017.
 */
@Component(value = "melodyManualProvider")
public class MelodyManualProvider extends AbstractProvidder implements MelodyProvider{

    @Autowired
    @Qualifier(value = "melodyGeneratorProvider")
    protected MelodyProvider melodyGeneratorProvider;

    public List<CpMelody> getMelodies(int voice){
        if(melodies.isEmpty()){
            getTonalMelodies();
//            melodies.add(getNote(0, DurationConstants.THREE_EIGHTS));
//            melodies.add(getRest(0, DurationConstants.QUARTER));
            melodies.addAll(melodyGeneratorProvider.getMelodies(voice));
        }
        return melodies;
    }

}

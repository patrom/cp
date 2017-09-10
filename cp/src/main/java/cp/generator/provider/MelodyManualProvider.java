package cp.generator.provider;

import cp.model.melody.CpMelody;
import cp.model.rhythm.DurationConstants;
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
    public MelodyProvider melodyProvider;

    public List<CpMelody> getMelodies(){
        if(melodies.isEmpty()){
            getTonalMelodies();
            melodies.add(getRest(0, DurationConstants.EIGHT));
//            melodies.add(getRest(0, DurationConstants.QUARTER));
//            melodies.addAll(melodyProvider.getMelodies());
        }
        return melodies;
    }

}

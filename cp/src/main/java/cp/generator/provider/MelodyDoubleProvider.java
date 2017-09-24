package cp.generator.provider;

import cp.model.melody.CpMelody;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 26/06/2017.
 */
@Component(value = "melodyDoubleProvider")
public class MelodyDoubleProvider extends AbstractProvidder implements MelodyProvider{

    @Autowired
    private Keys keys;

    @Autowired
    @Qualifier(value = "melodyManualProvider")
    public MelodyProvider melodyProvider;

    private List<CpMelody> doubleMelodies = new ArrayList<>();

    public List<CpMelody> getMelodies(int voice){
        if(doubleMelodies.isEmpty()){
            final List<CpMelody> atonalMelodies = getAtonalMelodies();
            for (CpMelody melody : atonalMelodies) {
                doubleMelodies.add(augmentationOrDiminuation(melody, 2));
            }
            doubleMelodies.add(getRest(0, DurationConstants.EIGHT));
            doubleMelodies.add(getRest(0, DurationConstants.QUARTER));
        }
        return doubleMelodies;
    }

}

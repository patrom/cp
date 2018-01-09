package cp.nsga.operator.mutation.melody;

import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by prombouts on 11/02/2017.
 */
@Component(value = "oneNoteChromaticMutation")
public class OneNoteChromaticMutation implements MutationOperator<Motive> {

    private static Logger LOGGER = LoggerFactory.getLogger(OneNoteChromaticMutation.class.getName());

    private double probabilityOneNoteChromatic;

    @Autowired
    public OneNoteChromaticMutation(@Value("${probabilityOneNoteChromatic}") double probabilityOneNoteChromatic) {
        this.probabilityOneNoteChromatic = probabilityOneNoteChromatic;
    }

    public void doMutation(CpMelody melody)  {
        if ((melody.getMutationType() == MutationType.ALL || melody.getMutationType() == MutationType.PITCH) &&PseudoRandom.randDouble() < probabilityOneNoteChromatic) {
            melody.updateRandomNote(Scale.CHROMATIC_SCALE.pickRandomPitchClass());
//          LOGGER.debug("OneNoteChromaticMutation");
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive.getRandomMutableMelody());
        return motive;
    }
}

package cp.nsga.operator.mutation.melody;

import cp.model.melody.CpMelody;
import cp.model.melody.MusicElement;
import cp.model.note.Scale;
import cp.nsga.operator.mutation.MutationOperator;
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
public class OneNoteChromaticMutation implements MutationOperator<MusicElement> {

    private static Logger LOGGER = LoggerFactory.getLogger(OneNoteChromaticMutation.class.getName());

    private double probabilityOneNoteChromatic;

    @Autowired
    public OneNoteChromaticMutation(@Value("${probabilityOneNoteChromatic}") double probabilityOneNoteChromatic) {
        this.probabilityOneNoteChromatic = probabilityOneNoteChromatic;
    }

    public void doMutation(CpMelody melody)  {
        if (PseudoRandom.randDouble() < probabilityOneNoteChromatic) {
            melody.updateRandomNote(Scale.CHROMATIC_SCALE.pickRandomPitchClass());
//          LOGGER.info("OneNoteChromaticMutation");
        }
    }

    @Override
    public MusicElement execute(MusicElement melody) {
        doMutation((CpMelody)melody);
        return melody;
    }
}

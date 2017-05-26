package cp.nsga.operator.mutation.melody;

import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Scale;
import cp.nsga.operator.mutation.MutationOperator;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by prombouts on 11/02/2017.
 */
@Component(value = "oneNoteChromaticMutation")
public class OneNoteChromaticMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(OneNoteChromaticMutation.class.getName());

    private double probabilityOneNoteChromatic;

    @Autowired
    private TimeLine timeLine;

    @Autowired
    public OneNoteChromaticMutation(@Value("${probabilityOneNoteChromatic}") double probabilityOneNoteChromatic) {
        this.probabilityOneNoteChromatic = probabilityOneNoteChromatic;
    }

    public void doMutation(double probability, MelodyBlock melodyBlock)  {
        if (PseudoRandom.randDouble() < probability) {
            Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isMutable());
            if (optionalMelody.isPresent()) {
                optionalMelody.get().updateRandomNote(Scale.CHROMATIC_SCALE.pickRandomPitchClass());
//                LOGGER.info("OneNoteChromaticMutation");
            }
        }
    }



    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(probabilityOneNoteChromatic, melodyBlock);
        return melodyBlock;
    }
}

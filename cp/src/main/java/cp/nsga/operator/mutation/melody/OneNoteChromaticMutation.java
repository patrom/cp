package cp.nsga.operator.mutation.melody;

import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Scale;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by prombouts on 11/02/2017.
 */
@Component(value = "oneNoteChromaticMutation")
public class OneNoteChromaticMutation extends AbstractMutation {

    private static Logger LOGGER = LoggerFactory.getLogger(OneNoteChromaticMutation.class.getName());

    @Autowired
    private TimeLine timeLine;

    @Autowired
    public OneNoteChromaticMutation(HashMap<String, Object> parameters) {
        super(parameters);
    }

    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
            MelodyBlock mutableMelodyBlock = motive.getRandomMutableMelody();
            Optional<CpMelody> optionalMelody = mutableMelodyBlock.getRandomMelody(m -> m.isMutable());
            if (optionalMelody.isPresent()) {
                optionalMelody.get().updateRandomNote(Scale.CHROMATIC_SCALE.pickRandomPitchClass());
//                LOGGER.info("OneNoteChromaticMutation");
            }
        }
    }

    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;
        Double probability = (Double) getParameter("probabilityOneNoteChromatic");
        if (probability == null) {
            Configuration.logger_.severe("probabilityOneNoteChromatic: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

}

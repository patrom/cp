package cp.nsga.operator.mutation.melody;

import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.model.note.Dynamic;
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

/**
 * Created by prombouts on 5/01/2017.
 */
@Component(value = "dynamicMutation")
public class DynamicMutation extends AbstractMutation {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticulationMutation.class);

    @Autowired
    public DynamicMutation(HashMap<String, Object> parameters) {
        super(parameters);
    }


    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
            MelodyBlock mutableMelody = motive.getRandomMutableMelody();
            mutableMelody.updateDynamic(Dynamic.FF);
//			LOGGER.info("Dynamic mutated");
        }
    }

    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;
        Double probability = (Double) getParameter("probabilityDynamic");
        if (probability == null) {
            Configuration.logger_.severe("probabilityDynamic: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

}
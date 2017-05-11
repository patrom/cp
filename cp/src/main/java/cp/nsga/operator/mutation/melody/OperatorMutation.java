package cp.nsga.operator.mutation.melody;

import cp.composition.voice.VoiceConfig;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Structure;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import cp.util.RandomUtil;
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
 * Created by prombouts on 9/05/2017.
 */
@Component(value = "operatorMutation")
public class OperatorMutation extends AbstractMutation {

    private static Logger LOGGER = LoggerFactory.getLogger(OperatorMutation.class);

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TimeLine timeLine;

    @Autowired
    public OperatorMutation(HashMap<String, Object> parameters) {
        super(parameters);
    }

    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable) solution.getDecisionVariables()[0]).getMotive();
            MelodyBlock melodyBlock = motive.getRandomMutableMelody();
            Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
            if (optionalMelody.isPresent()) {

                CpMelody melody = optionalMelody.get();
                if (melody.getStructure() == Structure.TONAL) {
                    int random = RandomUtil.getRandomNumberInRange(0, 2);
                    int steps;
                    int degree;
                    switch (random) {
                        case 0:
                            steps = RandomUtil.getRandomNumberInRange(0, 7);
                            melody.transposePitchClasses(steps, timeLine);
                            LOGGER.info("transpose:" + steps);
                            break;
//                    case 5:
//                        steps = RandomUtil.getRandomNumberInRange(0, 7);
//                        melody.R().transposePitchClasses(steps, 0, timeLine);
//                        break;
                        case 1:
                            degree = RandomUtil.getRandomNumberInRange(1, 7);
                            melody.inversePitchClasses(degree, timeLine);
                            LOGGER.info("inverse:" + degree);
                            break;
//                    case 7:
//                        degree = RandomUtil.getRandomNumberInRange(1, 7);
//                        melody.R().inversePitchClasses(degree, 0, timeLine);
//                        break;
//                    case 6:
//                        melodyBlock.augmentation(factor, timeLine);
//                        break;
//                    case 7:
//                        melodyBlock.diminution(factor, timeLine);
//                    case 3:
//                        int steps = RandomUtil.getRandomNumberInRange(0, 7);
//                        melodyBlock.M(steps);
                        default:
                            break;
                    }
                } else if (melody.getStructure() == Structure.ATONAL) {
                    int random = RandomUtil.getRandomNumberInRange(0, 4);
                    int steps;
                    switch (random) {
                        case 0:
                            steps = RandomUtil.getRandomNumberInRange(0, 7);
                            melody.T(steps);
                            LOGGER.info("T:" + steps);
                            break;
                        case 1:
                            steps = RandomUtil.getRandomNumberInRange(0, 7);
                            melody.I().T(steps);
                            LOGGER.info("IT:" + steps);
                            break;
                        case 2:
                            steps = RandomUtil.getRandomNumberInRange(0, 7);
                            melody.R().T(steps);
                            break;
                        case 3:
                            steps = RandomUtil.getRandomNumberInRange(0, 7);
                            melody.R().I().T(steps);
                            break;
                        default:
                            break;

                    }
                }
            }
        }
    }

    /**
     * Executes the operation
     * @param object An object containing a solution to mutate
     * @return An object containing the mutated solution
     * @throws JMException
     */
    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;
        Double probability = (Double) getParameter("probabilityOperatorMutation");
        if (probability == null) {
            Configuration.logger_.severe("probabilityOperatorMutation: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

}


package cp.nsga.operator.mutation.melody;

import cp.composition.voice.VoiceConfig;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import cp.out.play.InstrumentConfig;
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
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 23/04/2017.
 */
@Component(value = "repetitionMelody")
public class RepetitionMelody extends AbstractMutation {

    private static Logger LOGGER = LoggerFactory.getLogger(RepetitionMelody.class);

    @Autowired
    private ReplaceRhythmDependantMelody replaceRhythmDependantMelody;
    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private TimeLine timeLine;

    private List<Integer> melodySizes = Stream.of(2,4,6).collect(toList());//size of melody times 2

    @Autowired
    public RepetitionMelody(HashMap<String, Object> parameters) {
        super(parameters);
    }

    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
            MelodyBlock melodyBlock = motive.getRandomMutableMelody();
            List<CpMelody> melodyBlocks = melodyBlock.getMelodyBlocks();
            int maxSizeMelodies = RandomUtil.getRandomFromList(melodySizes);
            int startIndex = RandomUtil.randomInt(0, melodyBlocks.size() - maxSizeMelodies);
            int startReplaceIndex = RandomUtil.randomInt(0, melodyBlocks.size() - maxSizeMelodies);
            if(startIndex != startReplaceIndex){
                melodyBlock.repeatMelody(startIndex, startReplaceIndex, maxSizeMelodies);
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
        Double probability = (Double) getParameter("probabilityRepetitionMelody");
        if (probability == null) {
            Configuration.logger_.severe("probabilityRepetitionMelody: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

}


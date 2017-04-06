package cp.nsga.operator.mutation.melody;

import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentDirections;
import cp.out.instrument.Technical;
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

/**
 * Created by prombouts on 6/04/2017.
 */
@Component(value = "technicalMutation")
public class TechnicalMutation extends AbstractMutation{
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicalMutation.class);

    @Autowired
    public TechnicalMutation(HashMap<String, Object> parameters) {
        super(parameters);
    }

    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private InstrumentDirections instrumentDirections;

    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
            MelodyBlock mutableMelody = motive.getRandomMutableMelody();
            Instrument instrument = instrumentConfig.getInstrumentForVoice(mutableMelody.getVoice());
            List<Technical> technicals = instrumentDirections.getTechnicals(instrument.getInstrumentGroup());
            if (technicals.isEmpty()){
                LOGGER.info("technicals empty");
            }else{
                mutableMelody.updateTechnical(RandomUtil.getRandomFromList(technicals));
//                LOGGER.info("technical mutated");
            }
        }
    }

    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;
        Double probability = (Double) getParameter("probabilityTechnical");
        if (probability == null) {
            Configuration.logger_.severe("probabilityTechnical: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

}


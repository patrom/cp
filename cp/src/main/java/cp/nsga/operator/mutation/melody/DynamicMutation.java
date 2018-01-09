package cp.nsga.operator.mutation.melody;

import cp.config.TimbreConfig;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Dynamic;
import cp.model.timbre.Timbre;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 5/01/2017.
 */
@Component(value = "dynamicMutation")
public class DynamicMutation implements MutationOperator<Motive> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticulationMutation.class);

    private double probabilityDynamic;

    @Autowired
    private TimbreConfig timbreConfig;

    @Autowired
    public DynamicMutation(@Value("${probabilityDynamic}") double probabilityDynamic) {
        this.probabilityDynamic = probabilityDynamic;
    }

    public void doMutation(CpMelody melody) {
        if ((melody.getMutationType() == MutationType.ALL
                || melody.getMutationType() == MutationType.TIMBRE)
				&& PseudoRandom.randDouble() < probabilityDynamic) {
            Timbre timbre = timbreConfig.getTimbreConfigForVoice(melody.getVoice());
            List<Dynamic> dynamics = timbre.getDynamics();
            Dynamic dynamic = RandomUtil.getRandomFromList(dynamics);
            melody.updateDynamic(dynamic);
//			LOGGER.debug("Dynamic mutated");
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive.getRandomMutableMelody());
        return motive;
    }
}
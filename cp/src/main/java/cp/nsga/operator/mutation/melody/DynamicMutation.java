package cp.nsga.operator.mutation.melody;

import cp.composition.voice.Voice;
import cp.config.InstrumentConfig;
import cp.config.VoiceConfig;
import cp.model.melody.MelodyBlock;
import cp.model.note.Dynamic;
import cp.nsga.operator.mutation.MutationOperator;
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
public class DynamicMutation implements MutationOperator<MelodyBlock> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticulationMutation.class);

    private double probabilityDynamic;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private InstrumentConfig instrumentConfig;

    @Autowired
    public DynamicMutation(@Value("${probabilityDynamic}") double probabilityDynamic) {
        this.probabilityDynamic = probabilityDynamic;
    }

    public void doMutation(MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probabilityDynamic) {
            Voice voiceConfiguration = voiceConfig.getVoiceConfiguration(melodyBlock.getVoice());
            List<Dynamic> dynamics = voiceConfiguration.getDynamics();
            Dynamic dynamic = RandomUtil.getRandomFromList(dynamics);
            melodyBlock.updateDynamic(dynamic);
//			LOGGER.info("Dynamic mutated");
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(melodyBlock);
        return melodyBlock;
    }
}
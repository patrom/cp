package cp.nsga.operator.mutation.melody;

import cp.composition.voice.Voice;
import cp.config.InstrumentConfig;
import cp.config.VoiceConfig;
import cp.model.melody.MelodyBlock;
import cp.nsga.operator.mutation.MutationOperator;
import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 6/04/2017.
 */
@Component(value = "technicalMutation")
public class TechnicalMutation implements MutationOperator<MelodyBlock> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicalMutation.class);

    private double probabilityTechnical;

    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private VoiceConfig voiceConfig;

    @Autowired
    public TechnicalMutation(@Value("${probabilityTechnical}") double probabilityTechnical) {
        this.probabilityTechnical = probabilityTechnical;
    }

    public void doMutation(MelodyBlock melodyBlock)  {
        if (PseudoRandom.randDouble() < probabilityTechnical) {
            Instrument instrument = instrumentConfig.getInstrumentForVoice(melodyBlock.getVoice());
            Voice voiceConfiguration = voiceConfig.getVoiceConfiguration(melodyBlock.getVoice());
            List<Technical> technicals = voiceConfiguration.getTechnicals(instrument.getInstrumentGroup());
            if (technicals.isEmpty()){
                LOGGER.info("technicals empty");
            }else{
                melodyBlock.updateTechnical(RandomUtil.getRandomFromList(technicals));
//                LOGGER.info("technical mutated");
            }
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(melodyBlock);
        return melodyBlock;
    }
}


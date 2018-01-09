package cp.nsga.operator.mutation.melody;

import cp.config.InstrumentConfig;
import cp.config.TimbreConfig;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.timbre.Timbre;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
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
public class TechnicalMutation implements MutationOperator<Motive> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicalMutation.class);

    private double probabilityTechnical;

    @Autowired
    private InstrumentConfig instrumentConfig;

    @Autowired
    private TimbreConfig timbreConfig;

    @Autowired
    public TechnicalMutation(@Value("${probabilityTechnical}") double probabilityTechnical) {
        this.probabilityTechnical = probabilityTechnical;
    }

    public void doMutation(CpMelody melody)  {
        if ((melody.getMutationType() == MutationType.ALL
                || melody.getMutationType() == MutationType.TIMBRE)
                && PseudoRandom.randDouble() < probabilityTechnical) {
            Instrument instrument = instrumentConfig.getInstrumentForVoice(melody.getVoice());
            Timbre timbre = timbreConfig.getTimbreConfigForVoice(melody.getVoice());
            List<Technical> technicals = timbre.getTechnicals(instrument.getInstrumentGroup());
            if (technicals.isEmpty()){
                LOGGER.info("technicals empty");
            }else{
                melody.updateTechnical(RandomUtil.getRandomFromList(technicals));
                LOGGER.debug("technical mutated");
            }
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive.getRandomMutableMelody());
        return motive;
    }
}


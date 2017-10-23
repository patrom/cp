package cp.nsga.operator.mutation.melody;

import cp.config.InstrumentConfig;
import cp.config.TimbreConfig;
import cp.config.VoiceConfig;
import cp.model.melody.CpMelody;
import cp.model.timbre.Timbre;
import cp.nsga.operator.mutation.MutationOperator;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value="articulationMutation")
public class ArticulationMutation implements MutationOperator<CpMelody> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticulationMutation.class);
	
	private double probabilityArticulation;

	@Autowired
	private InstrumentConfig instrumentConfig;
	@Autowired
	private VoiceConfig voiceConfig;
	@Autowired
	private TimbreConfig timbreConfig;

	@Autowired
	public ArticulationMutation(@Value("${probabilityArticulation}") double probabilityArticulation) {
		this.probabilityArticulation = probabilityArticulation;
	}

	public void doMutation(CpMelody melody) {
		if (PseudoRandom.randDouble() < probabilityArticulation) {
			Instrument instrument = instrumentConfig.getInstrumentForVoice(melody.getVoice());
            Timbre timbre = timbreConfig.getTimbreConfigForVoice(melody.getVoice());
			List<Articulation> articulations = timbre.getArticulations(instrument.getInstrumentGroup());
			if (articulations.isEmpty()){
				LOGGER.info("articulations empty");
			}else{
				melody.updateArticulation(RandomUtil.getRandomFromList(articulations));
			}
		} 
	}


	@Override
	public CpMelody execute(CpMelody melody) {
		doMutation(melody);
		return melody;
	}
}

package cp.nsga.operator.mutation.melody;

import cp.composition.voice.Voice;
import cp.config.InstrumentConfig;
import cp.config.VoiceConfig;
import cp.model.melody.CpMelody;
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
	public ArticulationMutation(@Value("${probabilityArticulation}") double probabilityArticulation) {
		this.probabilityArticulation = probabilityArticulation;
	}

	public void doMutation(CpMelody melody) {
		if (PseudoRandom.randDouble() < probabilityArticulation) {
			Instrument instrument = instrumentConfig.getInstrumentForVoice(melody.getVoice());
			Voice voiceConfiguration = voiceConfig.getVoiceConfiguration(melody.getVoice());
			List<Articulation> articulations = voiceConfiguration.getArticulations(instrument.getInstrumentGroup());
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

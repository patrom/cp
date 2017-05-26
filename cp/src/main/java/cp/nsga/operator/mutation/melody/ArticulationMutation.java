package cp.nsga.operator.mutation.melody;

import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.model.melody.MelodyBlock;
import cp.nsga.operator.mutation.MutationOperator;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentConfig;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value="articulationMutation")
public class ArticulationMutation implements MutationOperator<MelodyBlock> {

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

	public void doMutation(MelodyBlock melodyBlock) {
		if (PseudoRandom.randDouble() < probabilityArticulation) {
			Instrument instrument = instrumentConfig.getInstrumentForVoice(melodyBlock.getVoice());
			Voice voiceConfiguration = voiceConfig.getVoiceConfiguration(melodyBlock.getVoice());
			List<Articulation> articulations = voiceConfiguration.getArticulations(instrument.getInstrumentGroup());
			if (articulations.isEmpty()){
				LOGGER.info("articulations empty");
			}else{
				melodyBlock.updateArticulation(RandomUtil.getRandomFromList(articulations));
			}
		} 
	}


	@Override
	public MelodyBlock execute(MelodyBlock melodyBlock) {
		doMutation(melodyBlock);
		return melodyBlock;
	}
}

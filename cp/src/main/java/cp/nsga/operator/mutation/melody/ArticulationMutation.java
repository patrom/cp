package cp.nsga.operator.mutation.melody;

import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentDirections;
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

@Component(value="articulationMutation")
public class ArticulationMutation extends AbstractMutation{

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticulationMutation.class);
	
	@Autowired
	public ArticulationMutation(HashMap<String, Object> parameters) {
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
			List<Articulation> articulations = instrumentDirections.getArticulations(instrument.getInstrumentGroup());
			if (articulations.isEmpty()){
				LOGGER.info("articulations empty");
			}else{
				mutableMelody.updateArticulation(RandomUtil.getRandomFromList(articulations));
			}
		} 
	}

	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;
		Double probability = (Double) getParameter("probabilityArticulation");
		if (probability == null) {
			Configuration.logger_.severe("probabilityArticulation: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability, solution);
		return solution;
	} 

}

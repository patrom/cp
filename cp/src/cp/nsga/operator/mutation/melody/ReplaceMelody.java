package cp.nsga.operator.mutation.melody;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import cp.combination.NoteCombination;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;

@Component(value="replaceMelody")
public class ReplaceMelody extends AbstractMutation{

	private static Logger LOGGER = LoggerFactory.getLogger(ReplaceMelody.class);

	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private NoteCombination noteCombination;
	
	@Autowired
	public ReplaceMelody(HashMap<String, Object> parameters) {
		super(parameters);
	}

	/**
	 * Perform the mutation operation
	 * @param probability Mutation probability
	 * @param solution The solution to mutate
	 * @throws JMException
	 */
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			MelodyBlock melodyBlock = motive.getRandomMutableMelody();
			Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody();
			if (optionalMelody.isPresent()) {
				CpMelody melody = optionalMelody.get();
				List<Note> melodyNotes = noteCombination.getNotes(melody.getBeat(), melody.getVoice());
				melodyNotes.forEach(n -> {
					n.setVoice(melody.getVoice());
					n.setPosition(n.getPosition() + melody.getStart());
					int pitchClass = (melody.getScale().pickRandomPitchClass() + melody.getKey()) % 12;
					n.setPitchClass(pitchClass);
				});
				melody.updateNotes(melodyNotes);
				LOGGER.info("Melody replaced");
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
		Double probability = (Double) getParameter("probabilityReplaceMelody");
		if (probability == null) {
			Configuration.logger_.severe("probabilityReplaceMelody: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability.doubleValue(), solution);
		return solution;
	} 

}

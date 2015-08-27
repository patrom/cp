package cp.nsga.operator.mutation;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import cp.generator.MusicProperties;
import cp.model.harmony.Harmony;
import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;

public abstract class AbstractMutation extends Mutation{
	
	protected static Logger LOGGER = Logger.getLogger(AbstractMutation.class.getName());

	@Autowired
	protected MusicProperties musicProperties;
	
	public AbstractMutation(HashMap<String, Object> parameters) {
		super(parameters);
	}

	protected HarmonicMelodyMutation harmonicMelodyMutation = new HarmonicMelodyMutation();
	
	protected HarmonyMutation harmonyMutation = new HarmonyMutation();
	
	@Override
	public abstract Object execute(Object arg0) throws JMException;

	public void setAllowedMelodyMutationIndexes(List<Integer> allowedIndexes) {
		harmonicMelodyMutation.setAllowedMutationIndexes(allowedIndexes);
	}
	
	public void setAllowedHarmonies(List<Harmony> harmonies) {
		harmonyMutation.randomHarmony(harmonies);
	}
	
	public void setOuterBoundaryIncluded(boolean isOuterBoundaryIncluded) {
		this.harmonyMutation.setOuterBoundaryIncluded(isOuterBoundaryIncluded);
	}
}

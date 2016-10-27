package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
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

import static java.util.stream.Collectors.toList;

@Component(value="copyMelody")
public class CopyMelody extends AbstractMutation{

	private static Logger LOGGER = LoggerFactory.getLogger(CopyMelody.class);

	@Autowired
	private ReplaceRhythmDependantMelody replaceRhythmDependantMelody;

	private PitchClassGenerator pitchClassGenerator;

	public void setPitchClassGenerator(PitchClassGenerator pitchClassGenerator) {
		this.pitchClassGenerator = pitchClassGenerator;
		replaceRhythmDependantMelody.setPitchClassGenerator(pitchClassGenerator);
	}

	@Autowired
	private TimeLine timeLine;


	private Composition composition;

	@Autowired
	public CopyMelody(HashMap<String, Object> parameters) {
		super(parameters);
	}

	/**
	 * Perform the mutation operation
	 * @param probability Mutation probability
	 * @param solution The solution to mutate
	 */
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			MelodyBlock melodyBlock = motive.getRandomMutableMelody();
			CpMelody melodyToReplace = RandomUtil.getRandomFromList(melodyBlock.getMelodyBlocks());
			CpMelody optionalMelody = motive.getRandomMelodyWithBeatGroupLength(melodyToReplace.getBeatGroupLength());
			if (optionalMelody != null && !melodyToReplace.equals(optionalMelody) && melodyToReplace.getBeatGroupLength() == optionalMelody.getBeatGroupLength()) {
				CpMelody clonedMelody = optionalMelody.clone();

				int offsetKey = melodyToReplace.getStart() -  clonedMelody.getStart();
				clonedMelody.T(0);
				if(RandomUtil.toggleSelection()){
					int steps = RandomUtil.getRandomNumberInRange(0, 7);
					clonedMelody.transposePitchClasses(steps, offsetKey , timeLine);
				}else{
					int steps = RandomUtil.getRandomNumberInRange(1, 7);
					clonedMelody.inversePitchClasses(steps, offsetKey , timeLine);
				}

////				clonedMelody.inversePitchClasses(2, 0 , timeLine);
//				int random = RandomUtil.random(4);
//				switch (random){
//					case 0:
//						clonedMelody.T(0);
//					case 1:
//						clonedMelody.I();
//					case 2:
//						clonedMelody.M(0);
//					case 3:
//						clonedMelody.transposePitchClasses(2, 0 , timeLine);
//				}
				clonedMelody.getNotes().forEach(n -> {
					n.setVoice(melodyToReplace.getVoice());
					n.setPosition(n.getPosition() + offsetKey);
				});
				melodyToReplace.updateNotes(clonedMelody.getNotes());
//				LOGGER.info("Melody copy: ");

				//Rhythm dependant melodies
				List<MelodyBlock> rhythmDependantMelodies =  motive.getMelodyBlocks().stream()
						.filter(m -> m.isRhythmDependant() && m.getDependingVoice() == melodyToReplace.getVoice())
						.collect(toList());
				replaceRhythmDependantMelody.updateDependantMelodyBlockWithMelody(melodyToReplace, rhythmDependantMelodies);
			}
		}
	}

	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;
		Double probability = (Double) getParameter("probabilityCopyMelody");
		if (probability == null) {
			Configuration.logger_.severe("probabilityCopyMelody: probability not " +
			"specified");
			Class cls = String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability, solution);
		return solution;
	}

	public void setComposition(Composition composition) {
		this.composition = composition;
	} 

}

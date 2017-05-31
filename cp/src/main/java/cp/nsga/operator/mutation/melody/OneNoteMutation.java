package cp.nsga.operator.mutation.melody;

import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.nsga.operator.mutation.MutationOperator;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value="oneNoteMutation")
public class OneNoteMutation implements MutationOperator<MelodyBlock> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(OneNoteMutation.class.getName());

	private double probabilityOneNote;

	@Autowired
	private TimeLine timeLine;

	@Autowired
	public OneNoteMutation(@Value("${probabilityOneNote}") double probabilityOneNote) {
		this.probabilityOneNote = probabilityOneNote;
	}

	//one pitch
	public void doMutation(double probability, MelodyBlock melodyBlock) {
		if (PseudoRandom.randDouble() < probability) {
			Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isMutable());
			if (optionalMelody.isPresent()) {
				optionalMelody.get().updateRandomNote(timeLine);
//				LOGGER.info("one note");
			}
		} 
	}

	@Override
	public MelodyBlock execute(MelodyBlock melodyBlock) {
		doMutation(probabilityOneNote, melodyBlock);
		return melodyBlock;
	}
}

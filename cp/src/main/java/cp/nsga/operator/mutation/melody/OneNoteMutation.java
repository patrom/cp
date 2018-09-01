package cp.nsga.operator.mutation.melody;

import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.nsga.operator.mutation.MutationOperator;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="oneNoteMutation")
public class OneNoteMutation implements MutationOperator<CpMelody> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(OneNoteMutation.class.getName());

	private double probabilityOneNote;

	@Autowired
	private TimeLine timeLine;

	@Autowired
	public OneNoteMutation(@Value("${probabilityOneNote}") double probabilityOneNote) {
		this.probabilityOneNote = probabilityOneNote;
	}

	//one pitch
	public void doMutation(double probability, CpMelody melody) {
		if (PseudoRandom.randDouble() < probability && !melody.hasScale()) {
			melody.updateRandomNote(timeLine);
			LOGGER.debug("one note " + melody.getVoice());
		}
	}

	@Override
	public CpMelody execute(CpMelody melodyBlock) {
		doMutation(probabilityOneNote, melodyBlock);
		return melodyBlock;
	}
}

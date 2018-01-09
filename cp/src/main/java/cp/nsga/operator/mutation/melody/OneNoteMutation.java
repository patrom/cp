package cp.nsga.operator.mutation.melody;

import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="oneNoteMutation")
public class OneNoteMutation implements MutationOperator<Motive> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(OneNoteMutation.class.getName());

	private double probabilityOneNote;

	@Autowired
	private TimeLine timeLine;

	@Autowired
	public OneNoteMutation(@Value("${probabilityOneNote}") double probabilityOneNote) {
		this.probabilityOneNote = probabilityOneNote;
	}

	//one pitch
	public void doMutation( CpMelody melody) {
		if ((melody.getMutationType() == MutationType.ALL || melody.getMutationType() == MutationType.PITCH) && PseudoRandom.randDouble() < probabilityOneNote) {
			melody.updateRandomNote(timeLine);
//			LOGGER.debug("one note" + melody.getVoice());
		}
	}

	@Override
	public Motive execute(Motive motive) {
		doMutation(motive.getRandomMutableMelody());
		return motive;
	}
}

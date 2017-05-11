package cp.nsga.operator.mutation.melody;

import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
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
import java.util.Optional;

/**
 * Created by prombouts on 6/05/2017.
 */
@Component(value = "rhythmMutation")
public class RhythmMutation extends AbstractMutation {

    private static Logger LOGGER = LoggerFactory.getLogger(RhythmMutation.class);

    @Autowired
    private VoiceConfig voiceConfig;

    @Autowired
    public RhythmMutation(HashMap<String, Object> parameters) {
        super(parameters);
    }

    public void doMutation(double probability, Solution solution) throws JMException {
        if (PseudoRandom.randDouble() < probability) {
            Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
            MelodyBlock melodyBlock = motive.getRandomMutableMelody();
            Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
            if (optionalMelody.isPresent()) {
                Voice voice = voiceConfig.getVoiceConfiguration(melodyBlock.getVoice());
                CpMelody melody = optionalMelody.get();
                List<Note> rhythmNotes = voice.getRhythmNotesForBeatgroup(melody.getBeatGroup());
                melody.updateRhythmNotes(rhythmNotes);
//				LOGGER.info("Melody replaced: " + melody.getVoice());
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
        Double probability = (Double) getParameter("probabilityRhythmMutation");
        if (probability == null) {
            Configuration.logger_.severe("probabilityRhythmMutation: probability not " +
                    "specified");
            Class cls = java.lang.String.class;
            String name = cls.getName();
            throw new JMException("Exception in " + name + ".execute()");
        }
        doMutation(probability, solution);
        return solution;
    }

}

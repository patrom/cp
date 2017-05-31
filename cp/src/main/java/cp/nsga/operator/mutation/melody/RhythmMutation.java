package cp.nsga.operator.mutation.melody;

import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.operator.mutation.MutationOperator;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by prombouts on 6/05/2017.
 */
@Component(value = "rhythmMutation")
public class RhythmMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(RhythmMutation.class);

    private double probability;

    @Autowired
    private VoiceConfig voiceConfig;

    @Autowired
    public RhythmMutation(@Value("${probabilityRhythmProbability}") double probability) {
        this.probability = probability;
    }

    public void doMutation(double probability, MelodyBlock melodyBlock)  {
        if (PseudoRandom.randDouble() < probability) {
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


    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(probability, melodyBlock);
        return melodyBlock;
    }
}

package cp.nsga.operator.mutation.melody;

import cp.composition.voice.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
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
 * Created by prombouts on 21/05/2017.
 */
@Component
public class AllNoteMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(AllNoteMutation.class);

    private double probabilityAllNote;

    @Autowired
    private VoiceConfig voiceConfig;

    @Autowired
    public AllNoteMutation(@Value("${probabilityAllNote}") double probabilityAllNote) {
        this.probabilityAllNote = probabilityAllNote;
    }

    public void doMutation(MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probabilityAllNote) {
            Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
            if (optionalMelody.isPresent()) {
                CpMelody melody = optionalMelody.get();
                List<Note> melodyNotes = melody.getNotes();
                PitchClassGenerator pitchClassGenerator = voiceConfig.getRandomPitchClassGenerator(melody.getVoice());
                melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes);
                melody.updateNotes(melodyNotes);
//				LOGGER.info("Melody replaced: " + melody.getVoice());
            }
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(melodyBlock);
        return melodyBlock;
    }
}

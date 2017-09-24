package cp.nsga.operator.mutation.melody;

import cp.config.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.nsga.operator.mutation.MutationOperator;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 21/05/2017.
 */
@Component
public class AllNoteMutation implements MutationOperator<CpMelody> {

    private static Logger LOGGER = LoggerFactory.getLogger(AllNoteMutation.class);

    private double probabilityAllNote;

    @Autowired
    private VoiceConfig voiceConfig;

    @Autowired
    public AllNoteMutation(@Value("${probabilityAllNote}") double probabilityAllNote) {
        this.probabilityAllNote = probabilityAllNote;
    }

    //all pitches
    public void doMutation(CpMelody melody) {
        if (PseudoRandom.randDouble() < probabilityAllNote) {
            List<Note> melodyNotes = melody.getNotes();
            if (!melodyNotes.isEmpty()) {
                PitchClassGenerator pitchClassGenerator = voiceConfig.getRandomPitchClassGenerator(melody.getVoice());
                melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes);
                melody.updateNotes(melodyNotes);
//				LOGGER.info("All notes: " + melody.getVoice());
            }
        }
    }

    @Override
    public CpMelody execute(CpMelody melody) {
        doMutation(melody);
        return melody;
    }
}

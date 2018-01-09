package cp.nsga.operator.mutation.melody;

import cp.config.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by prombouts on 21/05/2017.
 */
@Component
public class AllNoteMutation implements MutationOperator<Motive> {

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

        if ((melody.getMutationType() == MutationType.ALL || melody.getMutationType() == MutationType.PITCH) && PseudoRandom.randDouble() < probabilityAllNote) {
            List<Note> melodyNotes = melody.getNotes();
            if (!melodyNotes.isEmpty()) {
                PitchClassGenerator pitchClassGenerator = voiceConfig.getRandomPitchClassGenerator(melody.getVoice());
                melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes);
                melody.updateNotes(melodyNotes);
				LOGGER.debug("All notes: " + melody.getVoice());
            }
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive.getRandomMutableMelody(Stream.of(MutationType.ALL, MutationType.PITCH)));
        return motive;
    }
}

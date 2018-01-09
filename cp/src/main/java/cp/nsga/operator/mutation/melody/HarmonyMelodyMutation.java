package cp.nsga.operator.mutation.melody;

import cp.config.TimbreConfig;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.texture.Texture;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component(value = "harmonyMelodyMutation")
public class HarmonyMelodyMutation implements MutationOperator<Motive> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HarmonyMelodyMutation.class);

    private double probabilityHarmonic;

    @Autowired
    private TimbreConfig timbreConfig;
    @Autowired
    private Texture texture;

    @Autowired
    public HarmonyMelodyMutation(@Value("${probabilityHarmonic}") double probabilityHarmonic) {
        this.probabilityHarmonic = probabilityHarmonic;
    }

    public void doMutation(Motive motive) {
        if (PseudoRandom.randDouble() < probabilityHarmonic) {
            CpMelody melody = motive.getRandomMutableMelodyWithMininalNotesSize(2);
            List<Note> notes = melody.getNotes();
            List<Note> allHarmonyNotes = motive.getMelodyBlocks().stream()
                    .filter(melodyBlock1 -> melodyBlock1.getVoice() != melody.getVoice())
                    .flatMap(melodyBlock1 -> melodyBlock1.getMelodyBlockNotes().stream())
                    .collect(toList());
            for (Note note : notes) {
                List<Note> harmonyNotes = allHarmonyNotes.stream()
                        .filter(harmonyNote -> !harmonyNote.isRest())
                        .filter(harmonyNote -> harmonyNote.getPosition() <= note.getPosition() && harmonyNote.getEndPostion() > note.getPosition())
                        .map(n -> n.clone())
                        .collect(toList());
                //texture notes
                List<Note> textureNotes = texture.getTextureNotes(harmonyNotes);
                harmonyNotes.addAll(textureNotes);
                Note harmonyNote = RandomUtil.getRandomFromList(harmonyNotes);
                note.setPitchClass(harmonyNote.getPitchClass());
//                    LOGGER.info("melody harmony mutated: " + melody.getVoice());
            }
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive);
        return motive;
    }
}

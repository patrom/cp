package cp.nsga.operator.mutation.melody;

import cp.config.TimbreConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.CpMelodyHarmonic;
import cp.model.melody.MusicElement;
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

@Component(value = "oneNoteHarmonyMutation")
public class OneNoteHarmonyMutation implements MutationOperator<MusicElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OneNoteHarmonyMutation.class);

    private double probabilityOneNoteHarmony;

    @Autowired
    private TimbreConfig timbreConfig;
    @Autowired
    private Texture texture;

    @Autowired
    public OneNoteHarmonyMutation(@Value("${probabilityOneNoteHarmony}") double probabilityOneNoteHarmony) {
        this.probabilityOneNoteHarmony = probabilityOneNoteHarmony;
    }

    public void doMutation(CpMelodyHarmonic cpMelodyHarmonic) {
        if (PseudoRandom.randDouble() < probabilityOneNoteHarmony) {
            CpMelody melody = cpMelodyHarmonic.getCpMelody();

            List<Note> allHarmonyNotes = cpMelodyHarmonic.getMelodyBlocks().stream()
                    .filter(melodyBlock1 -> melodyBlock1.getVoice() != melody.getVoice())
                    .flatMap(melodyBlock1 -> melodyBlock1.getMelodyBlockNotes().stream())
                    .collect(toList());
            Note note = RandomUtil.getRandomFromList(melody.getNotes());
            List<Note> harmonyNotes = allHarmonyNotes.stream()
                    .filter(harmonyNote -> !harmonyNote.isRest())
                    .filter(harmonyNote -> harmonyNote.getPosition() <= note.getPosition() && harmonyNote.getEndPostion() > note.getPosition())
                    .map(n -> n.clone())
                    .collect(toList());
            //texture notes
            List<Note> textureNotes = texture.getTextureNotes(harmonyNotes);
            harmonyNotes.addAll(textureNotes);
            Note harmonyNote = null;
            if (!harmonyNotes.isEmpty()) {
                harmonyNote = RandomUtil.getRandomFromList(harmonyNotes);
                note.setPitchClass(harmonyNote.getPitchClass());
                LOGGER.info("one note harmony mutated: " + melody.getVoice());
            }
//                    else {
//                        LOGGER.info("Empty harmony collection????" + note.getPosition());
//                    }



        }
    }

    @Override
    public MusicElement execute(MusicElement melody) {
        doMutation((CpMelodyHarmonic) melody);
        return melody;
    }
}

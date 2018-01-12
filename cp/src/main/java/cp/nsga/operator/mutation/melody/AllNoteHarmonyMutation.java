package cp.nsga.operator.mutation.melody;

import cp.config.TimbreConfig;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
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
import java.util.stream.Collectors;

@Component(value = "allNoteHarmonyMutation")
public class AllNoteHarmonyMutation implements MutationOperator<Motive> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllNoteHarmonyMutation.class);

    private double probabilityAllNoteHarmony;

    @Autowired
    private TimbreConfig timbreConfig;
    @Autowired
    private Texture texture;

    @Autowired
    public AllNoteHarmonyMutation(@Value("${probabilityAllNoteHarmony}") double probabilityAllNoteHarmony) {
        this.probabilityAllNoteHarmony = probabilityAllNoteHarmony;
    }

    public void doMutation(Motive motive) {
        if (PseudoRandom.randDouble() < probabilityAllNoteHarmony) {
            CpMelody melody = motive.getRandomMutableMelodyWithMininalNotesSize(2);
            int melodyVoice = 2;
            MelodyBlock melodyBlock = motive.getRandomMutableMelodyBlockForVoice(melodyVoice);
            List<Note> notes = melody.getNotes();
            List<Note> allHarmonyNotes = motive.getMelodyBlocks().stream()
                    .filter(melodyBlock1 -> melodyBlock1.getVoice() != melodyVoice)
                    .flatMap(melodyBlock1 -> melodyBlock1.getMelodyBlockNotes().stream())
                    .collect(Collectors.toList());
            if(notes.size() > 1){
                for (Note note : notes) {
                    List<Note> harmonyNotes = allHarmonyNotes.stream()
                            .filter(harmonyNote -> !harmonyNote.isRest())
                            .filter(harmonyNote -> harmonyNote.getPosition() <= note.getPosition() && harmonyNote.getEndPostion() > note.getPosition())
                            .collect(Collectors.toList());
                    //texture notes
                    List<Note> textureNotes = texture.getTextureNotes(harmonyNotes);
                    harmonyNotes.addAll(textureNotes);
                    Note harmonyNote = RandomUtil.getRandomFromList(harmonyNotes);
                    note.setPitchClass(harmonyNote.getPitchClass());
//                    LOGGER.info("all note harmony mutated: " + melody.getVoice());
                }
                melody.updateNotes(notes);
            }
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive);
        return motive;
    }
}

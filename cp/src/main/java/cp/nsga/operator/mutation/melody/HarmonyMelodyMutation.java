package cp.nsga.operator.mutation.melody;

import cp.config.TimbreConfig;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "harmonyMelodyMutation")
public class HarmonyMelodyMutation implements MutationOperator<Motive> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HarmonyMelodyMutation.class);

    private double probabilityHarmonic;

    @Autowired
    private TimbreConfig timbreConfig;

    @Autowired
    public HarmonyMelodyMutation(@Value("${probabilityHarmonic}") double probabilityHarmonic) {
        this.probabilityHarmonic = probabilityHarmonic;
    }

    public void doMutation(Motive motive) {
        if (PseudoRandom.randDouble() < probabilityHarmonic) {
//            CpMelody melody = motive.getRandomMutableMelody();
            MelodyBlock melodyBlock = motive.getRandomMutableMelodyBlockExcludingVoice(4);
            CpMelody melody = RandomUtil.getRandomFromList(melodyBlock.getMelodyBlocks());
            List<Note> notes = melody.getNotes();
            if(notes.size() > 1){
                final Map<Integer, List<Note>> harmonyNotesByPosition = motive.getHarmonies().stream()
                        .filter(harmony -> harmony.getPosition() >= melody.getStart() && melody.getEnd() < harmony.getEnd())
                        .flatMap(harmony -> harmony.getNotes().stream())
                        .filter(note -> note.getVoice() != melody.getVoice())
                        .collect(Collectors.groupingBy(Note::getPosition));
                for (Note note : notes) {
                    List<Note> harmonyNotes = harmonyNotesByPosition.get(note.getPosition());
                    Note harmonyNote = RandomUtil.getRandomFromList(harmonyNotes);
                    note.setPitchClass(harmonyNote.getPitchClass());
                }
            }

			LOGGER.info("melody harmony mutated");
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive);
        return motive;
    }
}

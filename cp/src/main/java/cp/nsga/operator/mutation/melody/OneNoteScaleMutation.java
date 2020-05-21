package cp.nsga.operator.mutation.melody;

import cp.config.CompositionMapConfig;
import cp.config.map.CompositionMap;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "oneNoteScaleMutation")
public class OneNoteScaleMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(OneNoteScaleMutation.class.getName());

    private double probabilityOneNoteScale;
    @Value("${mutation.voices.oneNote}")
    private List<Integer> mutationVoices = new ArrayList<>();
    @Autowired
    protected CompositionMapConfig compositionMapConfig;

    @Autowired
    public OneNoteScaleMutation(@Value("${probabilityOneNoteScale}") double probabilityOneNoteScale) {
        this.probabilityOneNoteScale = probabilityOneNoteScale;
    }

    public void doMutation(double probability, MelodyBlock melodyBlock)  {
        if (PseudoRandom.randDouble() < probability && mutationVoices.contains(melodyBlock.getVoice())) {
            CompositionMap compositionMap = compositionMapConfig.getCompositionMapForVoice(melodyBlock.getVoice());
            CpMelody randomMelody = compositionMap.getMelody(melodyBlock.getVoice());
            Note randomNote = RandomUtil.getRandomFromList(randomMelody.getNotes());
            CpMelody melody = RandomUtil.getRandomFromList(melodyBlock.getMelodyBlocks());
            melody.updateRandomNote(randomNote.getPitchClass());
//            melody.updateRandomNote(Scale.CHROMATIC_SCALE.pickRandomPitchClass());
//          LOGGER.info("OneNoteScaleMutation " + melody.getVoice());
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(probabilityOneNoteScale, melodyBlock);
        return melodyBlock;
    }
}

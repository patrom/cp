package cp.nsga.operator.mutation.manipulation;

import cp.config.map.CompositionMap;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "melodyInsertNoteMutation")
public class MelodyInsertNoteMutation extends ManipulationMutation<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(MelodyInsertNoteMutation.class.getName());

    private double probabilityMelodyManipulation;
    @Value("${mutation.voices.insertNoteRandom}")
    private List<Integer> mutationVoices = new ArrayList<>();

    @Autowired
    public MelodyInsertNoteMutation(@Value("${probabilityMelodyManipulation}") double probabilityMelodyManipulation) {
        this.probabilityMelodyManipulation = probabilityMelodyManipulation;
    }

    public void doMutation(double probability, MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probability && mutationVoices.contains(melodyBlock.getVoice())) {
            CompositionMap compositionMap = compositionMapConfig.getCompositionMapForVoice(melodyBlock.getVoice());
            CpMelody randomMelody = compositionMap.getMelodyWithMultipleNotes(melodyBlock.getVoice());
            randomMelody.insertNotesOrdered();
            melodyBlock.randomInsertMelody(randomMelody);
            updateCompositionLength(melodyBlock, compositionMap);
            LOGGER.debug("melody map insert notes" + randomMelody.getVoice());
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(probabilityMelodyManipulation, melodyBlock);
        return melodyBlock;
    }
}




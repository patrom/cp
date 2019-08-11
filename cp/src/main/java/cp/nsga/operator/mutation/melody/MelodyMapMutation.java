package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.config.CompostionMapConfig;
import cp.config.map.CompositionMap;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="melodyMapMutation")
public class MelodyMapMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(OneNoteMutation.class.getName());

    private double probabilityMelodyMap;
    @Autowired
    private CompostionMapConfig compostionMapConfig;
    @Autowired
    private Composition composition;

    @Autowired
    public MelodyMapMutation(@Value("${probabilityMelodyMap}") double probabilityMelodyMap) {
        this.probabilityMelodyMap = probabilityMelodyMap;
    }

    //one pitch
    public void doMutation(double probability, MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probability) {
            CompositionMap compositionMap = compostionMapConfig.getCompositionMapForVoice(melodyBlock.getVoice());
            melodyBlock.randomInsertMelody(compositionMap.getRandomMelody());

            //keep length of composition
            int stop = composition.getEnd();
            int start = melodyBlock.getLength();
            int end = melodyBlock.getLength();
            while (end < stop) {
                CpMelody randomMelody = compositionMap.getRandomMelody();
                randomMelody.setStart(start);
                randomMelody.setEnd(start + randomMelody.getLength());
                randomMelody.updateNotePositions(start);
                randomMelody.setMutationType(MutationType.MELODY_MAP);
                randomMelody.setVoice(melodyBlock.getVoice());
                melodyBlock.addMelodyBlock(randomMelody);
                start = randomMelody.getEnd();
                end = start;
            }
            LOGGER.debug("melody map" + melodyBlock.getVoice());
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(probabilityMelodyMap, melodyBlock);
        return melodyBlock;
    }
}


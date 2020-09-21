package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.config.CompositionMapConfig;
import cp.config.TextureConfig;
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

@Component(value="melodyMapRandomMutation")
public class MelodyMapRandomMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(MelodyMapRandomMutation.class.getName());

    private double probabilityMelodyMap;
    @Autowired
    private CompositionMapConfig compositionMapConfig;
    @Autowired
    private Composition composition;
    @Autowired
    private TextureConfig textureConfig;

    @Autowired
    public MelodyMapRandomMutation(@Value("${probabilityMelodyMap}") double probabilityMelodyMap) {
        this.probabilityMelodyMap = probabilityMelodyMap;
    }

    //one pitch
    public void doMutation(double probability, MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probability) {
            CompositionMap compositionMap = compositionMapConfig.getCompositionMapForVoice(melodyBlock.getVoice());
            melodyBlock.randomInsertMelody(compositionMap.getMelody(melodyBlock.getVoice()));

            //keep length of composition
            int stop = composition.getEnd();
            int start = melodyBlock.getLength();
            int end = melodyBlock.getLength();
            if (end < stop) {
                while (end < stop) {
                    CpMelody melody = compositionMap.getMelody(melodyBlock.getVoice());
                    melody.setStart(start);
                    melody.setEnd(start + melody.getLength());
                    melody.updateNotePositions(start, melodyBlock.getVoice());
                    melody.setMutationType(MutationType.MELODY_MAP);
                    melody.setVoice(melodyBlock.getVoice());
                    melodyBlock.addMelodyBlock(melody);
                    start = melody.getEnd();
                    end = start;
                }
            } else {
                while (stop < end) {
                    melodyBlock.removeEnd();
                    end = melodyBlock.getLength();
                }
            }
            LOGGER.debug("melody map random melody map" + melodyBlock.getVoice());
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(probabilityMelodyMap, melodyBlock);
        return melodyBlock;
    }
}


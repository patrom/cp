package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.config.CompostionMapConfig;
import cp.config.TextureConfig;
import cp.config.map.CompositionMap;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component(value="melodyMapMutation")
public class MelodyMapMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(OneNoteMutation.class.getName());

    private double probabilityMelodyMap;
    @Autowired
    private CompostionMapConfig compostionMapConfig;
    @Autowired
    private Composition composition;
    @Autowired
    private TextureConfig textureConfig;

    @Autowired
    public MelodyMapMutation(@Value("${probabilityMelodyMap}") double probabilityMelodyMap) {
        this.probabilityMelodyMap = probabilityMelodyMap;
    }

    //one pitch
    public void doMutation(double probability, MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probability) {
            CompositionMap compositionMap = compostionMapConfig.getCompositionMapForVoice(melodyBlock.getVoice());
            melodyBlock.randomInsertMelody(compositionMap.getRandomMelody(melodyBlock.getVoice()));

            //keep length of composition
            int stop = composition.getEnd();
            int start = melodyBlock.getLength();
            int end = melodyBlock.getLength();
            if (end < stop) {
                while (end < stop) {
                    CpMelody randomMelody = compositionMap.getRandomMelody(melodyBlock.getVoice());
                    randomMelody.setStart(start);
                    randomMelody.setEnd(start + randomMelody.getLength());
                    randomMelody.updateNotePositions(start);
                    randomMelody.setMutationType(MutationType.MELODY_MAP);
                    randomMelody.setVoice(melodyBlock.getVoice());
                    melodyBlock.addMelodyBlock(randomMelody);
                    start = randomMelody.getEnd();
                    end = start;
                }
            } else {
                while (stop < end) {
                    melodyBlock.removeEnd();
                    end = melodyBlock.getLength();
                }
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


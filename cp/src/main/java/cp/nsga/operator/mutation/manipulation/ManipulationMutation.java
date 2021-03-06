package cp.nsga.operator.mutation.manipulation;

import cp.composition.Composition;
import cp.config.CompositionMapConfig;
import cp.config.TextureConfig;
import cp.config.map.CompositionMap;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ManipulationMutation<T> implements MutationOperator<MelodyBlock> {

    @Autowired
    protected CompositionMapConfig compositionMapConfig;
    @Autowired
    protected Composition composition;
    @Autowired
    protected TextureConfig textureConfig;

    protected void updateCompositionLength(MelodyBlock melodyBlock, CompositionMap compositionMap) {
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
    }

}

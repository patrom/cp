package cp.composition;

import cp.generator.dependant.DependantConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by prombouts on 4/02/2017.
 */
public abstract class CompositionDependingHarmony {

    @Autowired
    protected Composition composition;
    @Autowired
    protected DependantConfig dependantConfig;

    protected MelodyBlock getDependantMelodyBlock(int voice){
        MelodyBlock dependantMelodyBlock = new MelodyBlock(0, voice);
        dependantMelodyBlock.addMelodyBlock(new CpMelody(new ArrayList<>(),voice,composition.getStart(), composition.getEnd()));
        dependantMelodyBlock.setMutable(false);
        dependantMelodyBlock.setRhythmDependant(true);
        return dependantMelodyBlock;
    }
}

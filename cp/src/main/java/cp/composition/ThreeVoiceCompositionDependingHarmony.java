package cp.composition;

import cp.generator.dependant.DependantHarmonyGenerator;
import cp.model.melody.MelodyBlock;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 6/02/2017.
 */
@Aspect
@Component
public class ThreeVoiceCompositionDependingHarmony extends CompositionDependingHarmony {

    @AfterReturning(pointcut="execution(* cp..*ThreeVoiceComposition.*(..))", returning="melodyBlocks")
    public void addDependingHarmonies(List<MelodyBlock> melodyBlocks) {
        for (DependantHarmonyGenerator dependantHarmonyGenerator : composition.getDependantHarmonyGenerators()) {
            melodyBlocks.add(getDependantMelodyBlock(dependantHarmonyGenerator.getDependingVoice()));
            if (dependantHarmonyGenerator.hasSecondDependingVoice()) {
                melodyBlocks.add(getDependantMelodyBlock(dependantHarmonyGenerator.getSecondDependingVoice()));
            }
        }
    }

}

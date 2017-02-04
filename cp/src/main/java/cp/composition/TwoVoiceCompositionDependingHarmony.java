package cp.composition;

import cp.composition.voice.VoiceConfig;
import cp.model.harmony.ChordType;
import cp.model.melody.MelodyBlock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 4/02/2017.
 */
@Aspect
@Component
public class TwoVoiceCompositionDependingHarmony extends CompositionDependingHarmony {

    @Before("execution(* cp..*TwoVoiceComposition.*(..))")
    public void setChordTypes(JoinPoint joinPoint) {
        if (dependantConfig.hasSourcegVoice()) {
            VoiceConfig sourceVoice = composition.getVoiceConfiguration(dependantConfig.getSourceVoice());
            //has to be set first, before generation
            sourceVoice.hasDependentHarmony(true);
            sourceVoice.addChordType(ChordType.CH2_GROTE_TERTS);
//		melodyVoice.addChordType(ChordType.CH2_KWART);
//		melodyVoice.addChordType(ChordType.CH2_KWINT);
//		melodyVoice.addChordType(ChordType.ALL);
//        sourceVoice.addChordType(ChordType.CH2_GROTE_SIXT);
//		melodyVoice.addChordType(ChordType.MAJOR);
//        sourceVoice.addChordType(ChordType.MAJOR_1);
//		melodyVoice.addChordType(ChordType.MAJOR_2);
//        sourceVoice.addChordType(ChordType.DOM);
        }
    }

    @AfterReturning(pointcut="execution(* cp..*TwoVoiceComposition.*(..))", returning="melodyBlocks")
    public void addDependingHarmonies(List<MelodyBlock> melodyBlocks) {
        if (dependantConfig.hasSourcegVoice()) {
            melodyBlocks.add(getDependantMelodyBlock(dependantConfig.getDependingVoice()));
            if (dependantConfig.hasSecondDependingVoice()) {
                melodyBlocks.add(getDependantMelodyBlock(dependantConfig.getSecondDependingVoice()));
            }
        }
    }

}

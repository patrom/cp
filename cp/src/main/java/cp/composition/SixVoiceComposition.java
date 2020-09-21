package cp.composition;

import cp.model.melody.MelodyBlock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component(value="sixVoiceComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "6")
public class SixVoiceComposition extends Composition{

    @PostConstruct
    public void initInstruments(){
        if(instrumentConfig.getSize() < 6){
            throw new IllegalStateException("Set instrument config to correct instrument");
        }
        instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
        instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
        instrument3 = instrumentConfig.getInstrumentForVoice(voice2);
        instrument4 = instrumentConfig.getInstrumentForVoice(voice3);
        instrument5 = instrumentConfig.getInstrumentForVoice(voice4);
        instrument6 = instrumentConfig.getInstrumentForVoice(voice5);
    }

    public List<MelodyBlock> allRandom(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1);
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2);
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3);
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlockConfig(voice4);
        melodyBlocks.add(melodyBlock5);

        MelodyBlock melodyBlock6 = melodyGenerator.generateMelodyBlockConfig(voice5);
        melodyBlocks.add(melodyBlock6);

        return melodyBlocks;
    }

    public List<MelodyBlock> compositionMap(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.pickMelodies(voice0);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.pickMelodies(voice1);
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.pickMelodies(voice2);
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.pickMelodies(voice3);
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock5 = melodyGenerator.pickMelodies(voice4);
        melodyBlocks.add(melodyBlock5);

        MelodyBlock melodyBlock6 = melodyGenerator.pickMelodies(voice5);
        melodyBlocks.add(melodyBlock6);
        return melodyBlocks;
    }
}

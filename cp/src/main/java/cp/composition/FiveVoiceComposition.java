package cp.composition;

import cp.model.melody.MelodyBlock;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 14/11/2016.
 */
@Component(value="fiveVoiceComposition")
public class FiveVoiceComposition extends Composition {

    @PostConstruct
    public void initInstruments(){
        Assert.isTrue(instrumentConfig.getSize() >= 4);
        instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
        instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
        instrument3 = instrumentConfig.getInstrumentForVoice(voice2);
        instrument4 = instrumentConfig.getInstrumentForVoice(voice3);
        instrument5 = instrumentConfig.getInstrumentForVoice(voice4);
    }

    /**
     * Voice 0: bass halftime
     * Voice 1: halftime rhythm
     * Voice 2: halftime rhythm duplicate voice 1
     * voice 3: free acc
     * Voice 4: melody
     * @return melodies
     */
    public List<MelodyBlock> accDuplicateRhythm(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
        melodyBlock.setInstrument(instrument1);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getHomophonicBeatGroup);
        melodyBlock2.setInstrument(instrument2);
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock2, instrument3, voice2);
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlock(voice3, instrument4.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
        melodyBlock4.setInstrument(instrument4);
        melodyBlocks.add(melodyBlock4);

        //melody
        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlock(voice4, instrument5.pickRandomOctaveFromRange());
        melodyBlock5.setInstrument(instrument5);
        melodyBlocks.add(melodyBlock5);

        return melodyBlocks;
    }
}


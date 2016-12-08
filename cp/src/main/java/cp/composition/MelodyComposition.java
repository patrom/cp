package cp.composition;

import cp.model.melody.MelodyBlock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 20/11/2016.
 */
@Component(value = "melodyComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "1")
public class MelodyComposition extends Composition {

    @PostConstruct
    public void initInstruments() {
        instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
        voiceConfiguration.put(voice0, melodyVoice);
    }

    public List<MelodyBlock> melody(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlock.setInstrument(instrument1);
        melodyBlocks.add(melodyBlock);

        return melodyBlocks;
    }
}
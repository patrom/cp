package cp.composition;

import cp.model.melody.MelodyBlock;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 20/11/2016.
 */
@Component(value = "melodyComposition")
public class MelodyComposition extends Composition {

    @PostConstruct
    public void initInstruments() {
        Assert.isTrue(instrumentConfig.getSize() >= 1);
        instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
    }

    public List<MelodyBlock> melody(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlock.setInstrument(instrument1);
        melodyBlocks.add(melodyBlock);

        return melodyBlocks;
    }
}

package cp.composition;

import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 20/11/2016.
 */
@Component(value = "melodyComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "1")
public class MelodyComposition extends Composition {

    public List<MelodyBlock> melody(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0);
        melodyBlocks.add(melodyBlock);

        return melodyBlocks;
    }

    public List<MelodyBlock> melodyProvided(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0);
        melodyBlocks.add(melodyBlock);

        return melodyBlocks;
    }

    public List<MelodyBlock> test(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.pickMelodies(voice0);
        melodyBlocks.add(melodyBlock);
        return melodyBlocks;
    }

}

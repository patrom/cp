package cp.config.map;

import cp.composition.MelodyMapComposition;
import cp.config.TextureConfig;
import cp.generator.SingleMelodyGenerator;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.out.print.Keys;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CompositionMap {

    protected Map<Integer, List<CpMelody>> compositionMap = new HashMap<>();

    @Autowired
    protected SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    protected Keys keys;
    @Autowired
    protected MelodyMapComposition melodyMapComposition;
    @Autowired
    protected TextureConfig textureConfig;

    public CpMelody getRandomMelody(int voice){
        int randomIndex = RandomUtil.randomInt(0, compositionMap.size());
        List<CpMelody> melodies = compositionMap.get(randomIndex);
        CpMelody cpMelody = RandomUtil.getRandomFromList(melodies).clone();
        cpMelody.setVoice(voice);
        if (textureConfig.hasTexture(voice)) {
            for (Note melodyNote : cpMelody.getNotesNoRest()) {
                DependantHarmony dependantHarmony = textureConfig.getTextureFor(voice, melodyNote.getPitchClass());
                melodyNote.setDependantHarmony(dependantHarmony);
            }
        }
        return cpMelody;
    }
}

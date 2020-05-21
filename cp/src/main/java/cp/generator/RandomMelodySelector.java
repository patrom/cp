package cp.generator;

import cp.composition.MelodicValue;
import cp.config.TextureConfig;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "randomMelodySelector")
public class RandomMelodySelector implements MelodySelector {

    @Autowired
    protected TextureConfig textureConfig;

    @Override
    public CpMelody getMelody(int voice, List<MelodicValue> melodicValues) {
        MelodicValue melodicValue = RandomUtil.getRandomFromList(melodicValues);
        CpMelody cpMelody = melodicValue.pickRandomMelody().clone();
        cpMelody.setMelodyNumber(melodicValue.getMelodicNumber());
        cpMelody.setVoice(voice);
        if (textureConfig.hasTexture(voice)) {
            for (Note melodyNote : cpMelody.getNotesNoRest()) {
                DependantHarmony dependantHarmony = textureConfig.getTextureFor(voice, melodyNote.getPitchClass());
                melodyNote.setDependantHarmony(dependantHarmony);
            }
        }
        return cpMelody;
    }

    @Override
    public CpMelody getMelodyWithMultipleNotes(int voice, List<MelodicValue> melodicValues) {
        MelodicValue melodicValue = RandomUtil.getRandomFromList(melodicValues);
        CpMelody cpMelody = melodicValue.pickRandomMelodyWithMultipleNotes().clone();
        cpMelody.setMelodyNumber(melodicValue.getMelodicNumber());
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

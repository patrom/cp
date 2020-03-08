package cp.generator;

import cp.composition.MelodicValue;
import cp.config.TextureConfig;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component(value = "exhaustiveMelodySelector")
public class ExhaustiveMelodySelector implements MelodySelector {

    private List<MelodicValue> exhaustiveMelodicValues;

    @Autowired
    protected TextureConfig textureConfig;

    @Override
    public CpMelody getMelody(int voice, List<MelodicValue> melodicValues) {
        CpMelody cpMelody;
        if (melodicValues.isEmpty()) {
            melodicValues.addAll(exhaustiveMelodicValues);
        }
        MelodicValue melodicValue = RandomUtil.getRandomFromList(melodicValues);
        melodicValues.remove(melodicValue);
        cpMelody = melodicValue.pickExhaustiveMelody().clone();
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
        return null;
    }
}

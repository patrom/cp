package cp.config.map;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.MelodicValue;
import cp.composition.MelodicValueRhythmCombination;
import cp.composition.MelodyMapComposition;
import cp.config.TextureConfig;
import cp.generator.MelodySelector;
import cp.generator.SingleMelodyGenerator;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.out.print.Keys;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;

import java.util.*;

@DependsOn("melodyMapComposition")
public abstract class CompositionMap {

    protected List<MelodicValue> melodicValues = new ArrayList<>();
    protected List<MelodicValue> exhaustiveMelodicValues = new ArrayList<>();

    @Autowired
    protected SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    protected Keys keys;
    @Autowired
    protected MelodyMapComposition melodyMapComposition;
    @Autowired
    protected TextureConfig textureConfig;
    @Autowired
    protected RhythmCombinations allRhythmCombinations;
    @Autowired
    @Qualifier(value = "randomMelodySelector") //exhaustiveMelodySelector - randomMelodySelector
    protected MelodySelector melodySelector;

    public CpMelody getMelody(int voice){
        CpMelody cpMelody = melodySelector.getMelody(voice, melodicValues).clone();
//        CpMelody cpMelody = RandomUtil.getRandomFromList(melodicValues).pickRandomMelody().clone();
//        cpMelody.setVoice(voice);
//        if (textureConfig.hasTexture(voice)) {
//            for (Note melodyNote : cpMelody.getNotesNoRest()) {
//                DependantHarmony dependantHarmony = textureConfig.getTextureFor(voice, melodyNote.getPitchClass());
//                melodyNote.setDependantHarmony(dependantHarmony);
//            }
//        }
        return cpMelody;
    }

    public CpMelody getExhaustiveMelody(int voice){
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

    public void addMelodicValue(int keyMap, List<RhythmCombination> rhythmCombinations, int duration){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) melodyMapComposition.getCompositionMap(keyMap);
        melodicValue.setRhythmCombinations(rhythmCombinations);
        melodicValue.setDuration(duration);
        melodicValue.setPulse(singleMelodyGenerator.getPulse());
        melodicValues.add(melodicValue);
    }

    public void setMelodyMapComposition(MelodyMapComposition melodyMapComposition) {
        this.melodyMapComposition = melodyMapComposition;
    }
}

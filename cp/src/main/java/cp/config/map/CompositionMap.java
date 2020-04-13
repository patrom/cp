package cp.config.map;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.*;
import cp.config.TextureConfig;
import cp.generator.MelodySelector;
import cp.generator.PitchClassGenerator;
import cp.generator.SingleMelodyGenerator;
import cp.generator.SingleRhythmGenerator;
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
    protected SingleRhythmGenerator singleRhythmGenerator;
    @Autowired
    protected PitchClassGenerator pitchClassGenerator;
    @Autowired
    protected Keys keys;
    @Autowired
    protected MelodyMapComposition melodyMapComposition;
    @Autowired
    protected RhythmMapComposition rhythmMapComposition;
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

    public CpMelody getMelodyWithMultipleNotes(int voice){
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

    public void addMelodicValue(int melodyKey, int rhythmKey, int duration){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) melodyMapComposition.getCompositionMap(melodyKey);
        melodicValue.setRhythmCombinations(rhythmMapComposition.getrhythmMap(rhythmKey));
        melodicValue.setDuration(duration);
        melodicValue.setPulse(singleMelodyGenerator.getPulse());//balanced patterns
        melodicValues.add(melodicValue);
    }

    public void addMelodicValue(int keyMap, List<RhythmCombination> rhythmCombinations, int duration){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) melodyMapComposition.getCompositionMap(keyMap);
        melodicValue.setRhythmCombinations(rhythmCombinations);
        melodicValue.setDuration(duration);
        melodicValue.setPulse(singleMelodyGenerator.getPulse());//balanced patterns
        melodicValues.add(melodicValue);
    }

    public void addMelodicValueAscendingContour(int keyMap, List<RhythmCombination> rhythmCombinations, int duration){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) melodyMapComposition.getCompositionMap(keyMap);
        melodicValue.setRhythmCombinations(rhythmCombinations);
        melodicValue.setDuration(duration);
        melodicValue.setPulse(singleMelodyGenerator.getPulse());//balanced patterns
        melodicValue.setContourType(ContourType.ASC);
        melodicValues.add(melodicValue);
    }

    public void addMelodicValueDescendingContour(int keyMap, List<RhythmCombination> rhythmCombinations, int duration){
        MelodicValueRhythmCombination melodicValue = (MelodicValueRhythmCombination) melodyMapComposition.getCompositionMap(keyMap);
        melodicValue.setRhythmCombinations(rhythmCombinations);
        melodicValue.setDuration(duration);
        melodicValue.setPulse(singleMelodyGenerator.getPulse());//balanced patterns
        melodicValue.setContourType(ContourType.DESC);
        melodicValues.add(melodicValue);
    }

    public void setMelodyMapComposition(MelodyMapComposition melodyMapComposition) {
        this.melodyMapComposition = melodyMapComposition;
    }
}

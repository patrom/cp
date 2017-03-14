package cp.composition;

import cp.generator.dependant.DependantGenerator;
import cp.model.harmony.ChordType;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.relation.OperatorRelation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 18/10/2016.
 */

@Component(value="fourVoiceComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "4")
public class FourVoiceComposition extends  Composition {

    @PostConstruct
    public void initInstruments(){
       if(instrumentConfig.getSize() < 4){
          throw new IllegalStateException("Set instrument config to correct instrument");
       }
        instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
        instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
        instrument3 = instrumentConfig.getInstrumentForVoice(voice2);
        instrument4 = instrumentConfig.getInstrumentForVoice(voice3);

        voiceConfiguration.put(voice0, bassVoice);
        voiceConfiguration.put(voice1, homophonicVoice);
        voiceConfiguration.put(voice2, homophonicVoice);
        voiceConfiguration.put(voice3, melodyVoice);
    }

    public List<MelodyBlock> canonA3(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock2 = melodyGenerator.generateEmptyBlock(instrument2, voice1);
        melodyBlock2.setCalculable(false);
        melodyBlocks.add(melodyBlock2);


        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice3);
        operatorRelation.setTarget(voice1);
        operatorRelation.setSteps(5);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3, voice2);
        melodyBlock3.setCalculable(false);
        melodyBlocks.add(melodyBlock3);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice3);
        operatorRelation.setTarget(voice2);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset());
        operatorConfig.addOperatorRelations(operatorRelation::execute);

//        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument4, voice3);
//        melodyBlock4.setCalculable(false);
//        melodyBlocks.add(melodyBlock4);
//
//        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
//        operatorRelation.setSource(voice0);
//        operatorRelation.setTarget(voice3);
//        operatorRelation.setSteps(0);
//        operatorRelation.setTimeLine(timeLine);
//        operatorRelation.setOffset(getTimeConfig().getOffset() * 3);
//        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }

    public List<MelodyBlock> canon(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateEmptyBlock(instrument2, voice1);
        melodyBlock2.setCalculable(false);
        melodyBlocks.add(melodyBlock2);


        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice1);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3, voice2);
        melodyBlock3.setCalculable(false);
        melodyBlocks.add(melodyBlock3);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice2);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF + DurationConstants.QUARTER);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument4, voice3);
        melodyBlock4.setCalculable(false);
        melodyBlocks.add(melodyBlock4);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice3);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.WHOLE);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }

    public List<MelodyBlock> doubleCanon(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);


        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3, voice2);
        melodyBlock3.setCalculable(false);
        melodyBlocks.add(melodyBlock3);

        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice2);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF + DurationConstants.QUARTER);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument4, voice3);
        melodyBlock4.setCalculable(false);
        melodyBlocks.add(melodyBlock4);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice1);
        operatorRelation.setTarget(voice3);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF + DurationConstants.QUARTER);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }

    /**
     * Voice 0: bass halftime
     * Voice 1: halftime rhythm
     * Voice 2: halftime rhythm duplicate voice 1
     * Voice 3: melody
     * @return melodies
     */
    public List<MelodyBlock> accDuplicateRhythm(){
        voiceConfiguration.put(voice0, bassVoice);
        voiceConfiguration.put(voice1, homophonicVoice);
        voiceConfiguration.put(voice2, homophonicVoice);
        voiceConfiguration.put(voice3, melodyVoice);

        dependantHarmonyGenerators = new ArrayList<>();
        DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice1, voice2);
        dependantHarmonyGenerators.add(dependantGenerator);
//
//		dependantGenerator = new DependantGenerator(timeLine, voice2, voice4);
//		dependantHarmonyGenerators.add(dependantGenerator);
//
//		voiceConfiguration.put(voice3, homophonicVoice);
//		voiceConfiguration.put(voice4, homophonicVoice);
//
//		//has to be set first, before generation
        homophonicVoice.hasDependentHarmony(true);
        homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS);
        homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
        homophonicVoice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
        homophonicVoice.addChordType(ChordType.CH2_KWART);
//		homophonicVoice.addChordType(ChordType.CH2_KWINT);
//		homophonicVoice.addChordType(ChordType.ALL);
        homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT);
        homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
        homophonicVoice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
//		homophonicVoice.addChordType(ChordType.MAJOR);
//		homophonicVoice.addChordType(ChordType.MAJOR_1);
//      homophonicVoice.addChordType(ChordType.MAJOR_2);
//      homophonicVoice.addChordType(ChordType.DOM);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock4);

        return melodyBlocks;
    }

    /**
     * Voice 0: homophonicVoice
     * Voice 1: homophonicVoice
     * Voice 2: homophonicVoice
     * Voice 3: melody
     * @return melodies
     */
    public List<MelodyBlock> homophonicRhythm(){
        voiceConfiguration.put(voice0, homophonicVoice);
        voiceConfiguration.put(voice1, homophonicVoice);
        voiceConfiguration.put(voice2, homophonicVoice);
        voiceConfiguration.put(voice3, melodyVoice);
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock4);

        return melodyBlocks;
    }

    public List<MelodyBlock> dependingOneVoicesHomophonicRhythm(){
        voiceConfiguration.put(voice0, homophonicVoice);
        voiceConfiguration.put(voice1, homophonicVoice);
        voiceConfiguration.put(voice2, melodyVoice);
        voiceConfiguration.put(voice3, melodyVoice);

        dependantHarmonyGenerators = new ArrayList<>();
        DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice2, voice3);
        dependantHarmonyGenerators.add(dependantGenerator);

//		dependantGenerator = new DependantGenerator(timeLine, voice1, voice3);
//		dependantHarmonyGenerators.add(dependantGenerator);
//
//		voiceConfiguration.put(voice3, homophonicVoice);
//		voiceConfiguration.put(voice4, homophonicVoice);
//
//		//has to be set first, before generation
        melodyVoice.hasDependentHarmony(true);
        melodyVoice.addChordType(ChordType.CH2_GROTE_TERTS);
//        melodyVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
//        melodyVoice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
        melodyVoice.addChordType(ChordType.CH2_KWART);
        melodyVoice.addChordType(ChordType.CH2_KWINT);
//		homophonicVoice.addChordType(ChordType.ALL);
        melodyVoice.addChordType(ChordType.CH2_GROTE_SIXT);
//        melodyVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
//        melodyVoice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
//        homophonicVoice.addChordType(ChordType.MAJOR);
//        homophonicVoice.addChordType(ChordType.MAJOR_1);
//        homophonicVoice.addChordType(ChordType.MAJOR_1_CHR);
//        homophonicVoice.addChordType(ChordType.MAJOR_2);
//        homophonicVoice.addChordType(ChordType.DOM);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock1 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock1);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);

        return melodyBlocks;
    }


    public List<MelodyBlock> dependingTwoVoicesHomophonicRhythm(){
        voiceConfiguration.put(voice0, homophonicVoice);
        voiceConfiguration.put(voice1, homophonicVoice);
        voiceConfiguration.put(voice2, homophonicVoice);
        voiceConfiguration.put(voice3, homophonicVoice);

        dependantHarmonyGenerators = new ArrayList<>();
        DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice1, voice2, voice3);
        dependantHarmonyGenerators.add(dependantGenerator);

//		dependantGenerator = new DependantGenerator(timeLine, voice1, voice3);
//		dependantHarmonyGenerators.add(dependantGenerator);
//
//		voiceConfiguration.put(voice3, homophonicVoice);
//		voiceConfiguration.put(voice4, homophonicVoice);
//
//		//has to be set first, before generation
        homophonicVoice.hasDependentHarmony(true);
//        homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS);
//        homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
//        homophonicVoice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
//        homophonicVoice.addChordType(ChordType.CH2_KWART);
//		homophonicVoice.addChordType(ChordType.CH2_KWINT);
//		homophonicVoice.addChordType(ChordType.ALL);
//        homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT);
//        homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
//        homophonicVoice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
		homophonicVoice.addChordType(ChordType.MAJOR);
		homophonicVoice.addChordType(ChordType.MAJOR_1);
		homophonicVoice.addChordType(ChordType.MAJOR_1_CHR);
        homophonicVoice.addChordType(ChordType.MAJOR_2);
        homophonicVoice.addChordType(ChordType.DOM);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock1 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock1);

        return melodyBlocks;
    }

    public List<MelodyBlock> dependingFixedRhythm(){
        voiceConfiguration.put(voice0, fixedVoice);
        voiceConfiguration.put(voice1, fixedVoice);
        voiceConfiguration.put(voice2, fixedVoice);
        voiceConfiguration.put(voice3, fixedVoice);

        dependantHarmonyGenerators = new ArrayList<>();
        DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice1, voice2, voice3);
        dependantHarmonyGenerators.add(dependantGenerator);

//		dependantGenerator = new DependantGenerator(timeLine, voice1, voice3);
//		dependantHarmonyGenerators.add(dependantGenerator);
//
//		voiceConfiguration.put(voice3, homophonicVoice);
//		voiceConfiguration.put(voice4, homophonicVoice);
//
//		//has to be set first, before generation
        fixedVoice.hasDependentHarmony(true);
//        fixedVoice.addChordType(ChordType.CH2_GROTE_TERTS);
//        fixedVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
//        fixedVoice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
//        fixedVoice.addChordType(ChordType.CH2_KWART);
//		fixedVoice.addChordType(ChordType.CH2_KWINT);
//		fixedVoice.addChordType(ChordType.ALL);
//        fixedVoice.addChordType(ChordType.CH2_GROTE_SIXT);
//        fixedVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
//        fixedVoice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
        fixedVoice.addChordType(ChordType.MAJOR);
        fixedVoice.addChordType(ChordType.MAJOR_1);
        fixedVoice.addChordType(ChordType.MAJOR_1_CHR);
        fixedVoice.addChordType(ChordType.MAJOR_2);
        fixedVoice.addChordType(ChordType.DOM);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock1 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock1);

        return melodyBlocks;
    }

    public List<MelodyBlock> harmonize(){
        return super.harmonize();
    }
}

package cp.composition;

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
    }

    public List<MelodyBlock> canonA3(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);


        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock2 = melodyGenerator.generateEmptyBlock(instrument2, voice1);
        melodyBlock2.setMutable(false);
        melodyBlocks.add(melodyBlock2);

        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice3);
        operatorRelation.setTarget(voice1);
        operatorRelation.setSteps(5);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3, voice2);
        melodyBlock3.setMutable(false);
        melodyBlocks.add(melodyBlock3);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice3);
        operatorRelation.setTarget(voice2);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset());
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }

    public List<MelodyBlock> canonA4(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateEmptyBlock(instrument2, voice1);
        melodyBlock2.setMutable(false);
        melodyBlocks.add(melodyBlock2);


        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice1);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3, voice2);
        melodyBlock3.setMutable(false);
        melodyBlocks.add(melodyBlock3);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice2);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF + DurationConstants.QUARTER);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument4, voice3);
        melodyBlock4.setMutable(false);
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

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);


        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3, voice2);
        melodyBlock3.setMutable(false);
        melodyBlocks.add(melodyBlock3);

        OperatorRelation operatorRelation = new OperatorRelation(Operator.T);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice2);
        operatorRelation.setSteps(2);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF + DurationConstants.QUARTER);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument4, voice3);
        melodyBlock4.setMutable(false);
        melodyBlocks.add(melodyBlock4);

        operatorRelation = new OperatorRelation(Operator.T);
        operatorRelation.setSource(voice1);
        operatorRelation.setTarget(voice3);
        operatorRelation.setSteps(2);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.HALF + DurationConstants.QUARTER);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }

    /**
     * Voice 0: homophonicVoice
     * Voice 1: homophonicVoice
     * Voice 2: homophonicVoice
     * Voice 3: melody
     * @return melodies
     */
    public List<MelodyBlock> allRandom(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1);
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2);
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3);
        melodyBlocks.add(melodyBlock4);

        return melodyBlocks;
    }

    public List<MelodyBlock> harmonize(){
        return super.harmonize();
    }
}

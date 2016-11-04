package cp.composition;

import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.nsga.operator.relation.OperatorRelation;
import cp.out.instrument.Instrument;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 18/10/2016.
 */

@Component(value="fourVoiceComposition")
public class FourVoiceComposition extends  Composition {

    public List<MelodyBlock> canon(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        Instrument instrument1 = instruments.get(0);
        instrument1.setVoice(0);
        instrument1.setChannel(1);
//		cello.setKeySwitch(new KontactStringsKeySwitch());

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
        melodyBlock.setInstrument(instrument1);
        melodyBlocks.add(melodyBlock);

        Instrument instrument2 = instruments.get(1);
        instrument2.setVoice(1);
        instrument2.setChannel(2);
        MelodyBlock melodyBlock2 = melodyGenerator.generateEmptyBlock(instrument2);
        melodyBlock2.setCalculable(false);
        melodyBlocks.add(melodyBlock2);

        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(0);
        operatorRelation.setTarget(1);
        operatorRelation.setSteps(2);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset());
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        Instrument instrument3 = instruments.get(2);
        instrument3.setVoice(2);
        instrument3.setChannel(3);
        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3);
        melodyBlock3.setCalculable(false);
        melodyBlocks.add(melodyBlock3);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(0);
        operatorRelation.setTarget(2);
        operatorRelation.setSteps(4);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset() * 2);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        Instrument instrument4 = instruments.get(3);
        instrument4.setVoice(3);
        instrument4.setChannel(4);
        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument4);
        melodyBlock4.setCalculable(false);
        melodyBlocks.add(melodyBlock4);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(0);
        operatorRelation.setTarget(3);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset() * 3);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }
}

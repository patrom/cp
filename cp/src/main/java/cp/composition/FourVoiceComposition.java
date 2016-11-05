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

    private final int voice0 = 0;
    private final int voice1 = 1;
    private final int voice2 = 2;
    private final int voice3 = 3;
    private Instrument instrument1;
    private Instrument instrument2;
    private Instrument instrument3;
    private Instrument instrument4;


    public void initInstruments(){
        instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
        instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
        instrument3 = instrumentConfig.getInstrumentForVoice(voice2);
//        instrument4 = instrumentConfig.getInstrumentForVoice(voice3);
    }

    public List<MelodyBlock> canon(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlock.setInstrument(instrument1);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateEmptyBlock(instrument2, voice1);
        melodyBlock2.setCalculable(false);
        melodyBlocks.add(melodyBlock2);

        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice1);
        operatorRelation.setSteps(2);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset());
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument3, voice2);
        melodyBlock3.setCalculable(false);
        melodyBlocks.add(melodyBlock3);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice2);
        operatorRelation.setSteps(4);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset() * 2);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument4, voice3);
        melodyBlock4.setCalculable(false);
        melodyBlocks.add(melodyBlock4);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice0);
        operatorRelation.setTarget(voice3);
        operatorRelation.setSteps(0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(getTimeConfig().getOffset() * 3);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }
}

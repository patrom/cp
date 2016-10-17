package cp.composition;

import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.melody.OperatorType;
import cp.out.instrument.Instrument;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component(value="twoVoiceComposition")
public class TwoVoiceComposition extends Composition{
	
	public List<MelodyBlock> beatEven(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		
		melodyBlocks.add(melodyBlock);
		
//		Instrument instrument2 = instruments.get(1);
//		instrument2.setVoice(1);
//		instrument2.setChannel(1);
//		melodyBlock = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getFixedBeatGroup);
//		melodyBlock.setInstrument(instrument2);
		
		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(1);	
		MelodyBlock melodyBlock2 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock, instrument2);
		
		melodyBlocks.add(melodyBlock2);

		return melodyBlocks;
	}
	
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
		instrument2.setChannel(1);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setTimeConfig(getTimeConfig());
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setVoice(instrument2.getVoice());
		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.T_RELATIVE);
//		operatorType.setSteps(2);
//		operatorType.setFunctionalDegreeCenter(3);
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> fugueInverse(){
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
		instrument2.setChannel(1);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setTimeConfig(getTimeConfig());
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setVoice(instrument2.getVoice());
		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.I_RELATIVE);
//		operatorType.setSteps(1);
		operatorType.setFunctionalDegreeCenter(1);//start from 1
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
		
		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorT(){
		return operator(Operator.T, 0);
	}
	
	public List<MelodyBlock> operatorI(){
		return operator(Operator.I, 0);
	}
	
	public List<MelodyBlock> operatorR(){
		return operator(Operator.R, 0);
	}
	
	public List<MelodyBlock> operatorM(){
		return operator(Operator.M, 7);
	}

	public List<MelodyBlock> augmentation(){
		return operator(Operator.AUGMENTATION, 2.0);
	}

	public List<MelodyBlock> diminution(){
		return operator(Operator.DIMINUTION, 0.5);
	}

	private List<MelodyBlock> operator(Operator operator, int steps) {
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
		instrument2.setChannel(1);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setTimeConfig(getTimeConfig());
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setVoice(instrument2.getVoice());
		OperatorType operatorType = new OperatorType(operator);
		operatorType.setSteps(steps);
//		operatorType.setFunctionalDegreeCenter(1);//start from 1
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
		
		return melodyBlocks;
	}

	private List<MelodyBlock> operator(Operator operator, double factor) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(1);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setTimeConfig(getTimeConfig());
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setVoice(instrument2.getVoice());
		OperatorType operatorType = new OperatorType(operator);
		operatorType.setFactor(factor);
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		return melodyBlocks;
	}
}

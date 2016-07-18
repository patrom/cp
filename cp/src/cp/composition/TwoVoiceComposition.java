package cp.composition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.melody.OperatorType;
import cp.out.instrument.Instrument;
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
		
		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(1);
		melodyBlock = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getFixedBeatGroup);
		melodyBlock.setInstrument(instrument2);
		
		melodyBlocks.add(melodyBlock);

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
		melodyBlock2.setVoice(instrument2.getVoice());
		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.T_RELATIVE);
//		operatorType.setSteps(1);
//		operatorType.setFunctionalDegreeCenter(1);
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
}

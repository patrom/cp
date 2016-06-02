package cp.genre;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.melody.OperatorType;
import cp.out.instrument.Instrument;

@Component
public class ThreeVoiceComposition extends Composition{
	
	public List<MelodyBlock> canon2Voice1Acc(){
		return operator(Operator.T_RELATIVE, 0);
	}
	
	public List<MelodyBlock> accFixedRhythm(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), start, end, instrument1.pickRandomOctaveFromRange(), beatsAll);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		int voice1 = 1;
		noteCombination.setCombinationsEvenBeat(voice1, fixedBeat());//must match beats12
		Instrument instrument2 = instruments.get(voice1);
		instrument2.setVoice(voice1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), start, end, instrument2.pickRandomOctaveFromRange(), beats2X);
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
			
		noteCombination.setCombinationsEvenBeat(2, evenBeat());
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), start, end, instrument3.pickRandomOctaveFromRange(), beats2X);
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorTplusAcc(){
		return operator(Operator.T, 0);
	}

	private List<MelodyBlock> operator(Operator operator, int steps) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), start, end, instrument1.pickRandomOctaveFromRange(), beatsAll);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setVoice(instrument2.getVoice());
		melodyBlock2.setOffset(offset);
		OperatorType operatorType = new OperatorType(operator);
		operatorType.setSteps(steps);
//		operatorType.setFunctionalDegreeCenter(1);
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
		
		
		noteCombination.setCombinationsEvenBeat(2, evenBeat());
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), start, end, instrument3.pickRandomOctaveFromRange(), beats2X);
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorT(){
		return operator3voices(Operator.I_RELATIVE, 1);
	}
	
	private List<MelodyBlock> operator3voices(Operator operator, int steps) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), start, end, instrument1.pickRandomOctaveFromRange(), beatsAll);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setVoice(instrument2.getVoice());
		melodyBlock2.setOffset(offset);
		OperatorType operatorType = new OperatorType(operator);
//		operatorType.setSteps(steps);
		operatorType.setFunctionalDegreeCenter(1);
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
		
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = new MelodyBlock(instrument3.pickRandomOctaveFromRange(), instrument3.getVoice());
		melodyBlock3.setVoice(instrument3.getVoice());
		melodyBlock3.setOffset(offset * 2);
		OperatorType operatorType2 = new OperatorType(Operator.T_RELATIVE);
//		operatorType2.setSteps(1 + steps);
		operatorType.setFunctionalDegreeCenter(2);
		melodyBlock3.setOperatorType(operatorType2);
		melodyBlock3.dependsOn(melodyBlock.getVoice());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
}
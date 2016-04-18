package cp.genre;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.melody.MelodyBlock;
import cp.model.melody.OperatorType;
import cp.out.instrument.Instrument;

@Component
public class ThreeVoiceComposition extends Composition{
	
	public List<MelodyBlock> canon2Voice(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		List<Integer> beats = new ArrayList<>();
		beats.add(12);
		beats.add(24);
//		beats.add(48);
//		beats.add(36);
		
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), start, end, instrument1.pickRandomOctaveFromRange(), beats);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(1);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setVoice(instrument2.getVoice());
		melodyBlock2.setOffset(48);
		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.T_RELATIVE);
//		operatorType.setSteps(1);
//		operatorType.setFunctionalDegreeCenter(1);
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
		
		
		noteCombination.setCombinationsEvenBeat(2, evenBeat());
		List<Integer> beats24 = new ArrayList<>();
		beats24.add(24);
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(2);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), start, end, instrument3.pickRandomOctaveFromRange(), beats24);
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
}
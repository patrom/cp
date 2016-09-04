package cp.composition;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.melody.OperatorType;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;

@Component(value="threeVoiceComposition")
public class ThreeVoiceComposition extends Composition{
	
	public List<MelodyBlock> canon2Voice1Acc(){
		return operator(Operator.T_RELATIVE, 0);
	}
	
	public List<MelodyBlock> accFixedRhythm(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		int voice1 = 1;
		Instrument instrument2 = instruments.get(voice1);
		instrument2.setVoice(voice1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
			
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), instrument3.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
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
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setTimeConfig(getTimeConfig());
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setVoice(instrument2.getVoice());
		OperatorType operatorType = new OperatorType(operator);
		operatorType.setSteps(steps);
//		operatorType.setFunctionalDegreeCenter(1);
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
		
		
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), instrument3.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorT(){
		return operator3voices(Operator.T_RELATIVE, 0);
	}
	
	private List<MelodyBlock> operator3voices(Operator operator, int steps) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setTimeConfig(getTimeConfig());
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setVoice(instrument2.getVoice());
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
		melodyBlock3.setTimeConfig(getTimeConfig());
		melodyBlock3.setOffset(getTimeConfig().getOffset() * 2);
		OperatorType operatorType2 = new OperatorType(operator);
//		operatorType2.setSteps(1 + steps);
		operatorType.setFunctionalDegreeCenter(2);
		melodyBlock3.setOperatorType(operatorType2);
		melodyBlock3.dependsOn(melodyBlock.getVoice());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	public List<MelodyBlock> harmonize(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	
		
		//harmonization
//		Instrument piano = new Piano(0, 3);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(6).pc(2).len(3).build());
		notes.add(note().pos(9).pc(9).len(3).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(6).len(3).build());
		notes.add(note().pos(15).pc(9).len(3).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(2).len(DurationConstants.EIGHT).build());
		
		notes.add(note().pos(33).pc(1).len(3).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pc(11).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(42).pc(1).len(3).build());
		notes.add(note().pos(45).pc(11).len(3).build());
//		notes.add(note().pos(offset).pc(9).len(DurationConstants.QUARTER).build());
		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(2);
		CpMelody melody = new CpMelody(notes, instrument2.getVoice(), start, end);
		MelodyBlock melodyBlock2 = new MelodyBlock(3, instrument2.getVoice());
		melodyBlock2.addMelodyBlock(melody);
		melodyBlock2.setTimeConfig(getTimeConfig());
		melodyBlock2.setMutable(false);
		melodyBlock2.setInstrument(instrument2);
		
		melodyBlocks.add(melodyBlock2);
		
		
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), instrument3.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);
		
		return melodyBlocks;
	}
	
	/**
	 * Voice 0: melody
	 * Voice 1: homophonic
	 * Voice 2: half time
	 * @return
	 */
	public List<MelodyBlock> halfTimeHomophonicRhythm(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		int voice1 = 1;
		Instrument instrument2 = instruments.get(voice1);
		instrument2.setVoice(voice1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getHomophonicBeatGroup);
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
			
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	/**
	 * Voice 0: 3
	 * Voice 1: homophonic in 4
	 * Voice 2: in 4
	 * @return
	 */
	public List<MelodyBlock> threeOverXX(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getFixedBeatGroup);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		int voice1 = 1;
		Instrument instrument2 = instruments.get(voice1);
		instrument2.setVoice(voice1);
		instrument2.setChannel(2);
		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange(), time34::getFixedBeatGroup, time34);
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
			
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	/**
	 * Voice 0: bass halftime
	 * Voice 1: melody rhythm duplicate
	 * Voice 2: melody 
	 * @return
	 */
	public List<MelodyBlock> accDuplicateRhythm(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(1);
		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
			
		Instrument instrument3 = instruments.get(2);
		instrument3.setVoice(2);
		instrument3.setChannel(3);
		MelodyBlock melodyBlock3 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock2, instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> allRandom(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

//		int voice1 = 1;
//		Instrument instrument2 = instruments.get(voice1);
//		instrument2.setVoice(voice1);
//		instrument2.setChannel(2);
//		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange());
//		melodyBlock2.setInstrument(instrument2);
//		melodyBlocks.add(melodyBlock2);
//			
//		Instrument instrument3 = instruments.get(2);
//		instrument3.setVoice(2);
//		instrument3.setChannel(3);
//		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(instrument3.getVoice(), instrument3.pickRandomOctaveFromRange());
//		melodyBlock3.setInstrument(instrument3);
//		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}

	
	
}
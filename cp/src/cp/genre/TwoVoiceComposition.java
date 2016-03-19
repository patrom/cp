package cp.genre;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MelodyGenerator;
import cp.model.melody.MelodyBlock;
import cp.model.melody.OperatorType;
import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.woodwinds.Flute;

@Component
public class TwoVoiceComposition {
	
	@Autowired
	private MelodyGenerator melodyGenerator;

	public List<MelodyBlock> beatEven(List<Instrument> instruments){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		List<Integer> beats = new ArrayList<>();
		beats.add(12);
		beats.add(24);
//		beats.add(48);
//		beats.add(36);

		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), 0, 192, instrument1.pickRandomOctaveFromRange(), beats);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);
		
//		List<Integer> beats2 = new ArrayList<>();
//		beats2.add(12);
//		beats2.add(24);
//		beats2.add(36);
		
		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(1);
		melodyBlock = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), 0, 192, instrument2.pickRandomOctaveFromRange(), beats);
		melodyBlock.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock);

		//harmonization
//		Instrument piano = new Piano(0, 3);
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(6).pc(2).len(3).build());
//		notes.add(note().pos(9).pc(9).len(3).build());
//		notes.add(note().pos(12).pc(6).len(3).build());
//		notes.add(note().pos(15).pc(9).len(3).build());
//		notes.add(note().pos(18).pc(2).len(6).build());
//		
//		notes.add(note().pos(33).pc(1).len(3).build());
//		notes.add(note().pos(36).pc(11).len(6).build());
//		notes.add(note().pos(42).pc(1).len(3).build());
//		notes.add(note().pos(45).pc(11).len(3).build());
//		notes.add(note().pos(48).pc(9).len(12).build());
//		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, piano.getVoice());
//		MelodyBlock melodyBlock = new MelodyBlock(3, piano.getVoice());
//		melodyBlock.addMelodyBlock(melody);
//		melodyBlock.setMutable(false);
//		melodyBlock.setInstrument(piano);
//		
//		melodyBlocks.add(melodyBlock);
		
		return melodyBlocks;
	}
	
	public List<MelodyBlock> canon(List<Instrument> instruments){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		List<Integer> beats = new ArrayList<>();
		beats.add(12);
		beats.add(24);
//		beats.add(48);
//		beats.add(36);
		
		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), 0, 192, instrument1.pickRandomOctaveFromRange(), beats);
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

		return melodyBlocks;
	}
	
	public List<MelodyBlock> fugueInverse(List<Instrument> instruments){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		List<Integer> beats = new ArrayList<>();
		beats.add(12);
		beats.add(24);
//		beats.add(48);
//		beats.add(36);

		Instrument instrument1 = instruments.get(0);
		instrument1.setVoice(0);
		instrument1.setChannel(1);
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(instrument1.getVoice(), 0, 192, instrument1.pickRandomOctaveFromRange(), beats);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		Instrument instrument2 = instruments.get(1);
		instrument2.setVoice(1);
		instrument2.setChannel(1);
		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), instrument2.getVoice());
		melodyBlock2.setVoice(instrument2.getVoice());
		melodyBlock2.setOffset(48);
		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.I_RELATIVE);
//		operatorType.setSteps(1);
		operatorType.setFunctionalDegreeCenter(1);//start from 1
		melodyBlock2.setOperatorType(operatorType);
		melodyBlock2.dependsOn(melodyBlock.getVoice());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);
		
		return melodyBlocks;
	}
}

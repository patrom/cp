package cp.composition;

import cp.generator.dependant.DependantGenerator;
import cp.model.harmony.ChordType;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.nsga.operator.relation.OperatorRelation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
@Component(value="twoVoiceComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "2")
public class TwoVoiceComposition extends Composition{

	@PostConstruct
	public void initInstruments(){
		if(instrumentConfig.getSize() < 2){
			throw new IllegalStateException("Set instrument config to correct instrument");
		}
		instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
		instrument2 = instrumentConfig.getInstrumentForVoice(voice1);

		voiceConfiguration.put(voice0, melodyVoice);
		voiceConfiguration.put(voice1, melodyVoice);
	}

	public List<MelodyBlock> voiceConfig(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		return melodyBlocks;
	}

	public List<MelodyBlock> beatEven(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock dependantMelodyBlock = new MelodyBlock(5, voice2);
		dependantMelodyBlock.addMelodyBlock(new CpMelody(new ArrayList<>(),voice2,start,end));
		dependantMelodyBlock.setMutable(false);
		dependantMelodyBlock.setRhythmDependant(true);
		melodyBlocks.add(dependantMelodyBlock);

//		dependantMelodyBlock = new MelodyBlock(5, voice3);
//		dependantMelodyBlock.addMelodyBlock(new CpMelody(new ArrayList<>(),voice3,start,end));
//		dependantMelodyBlock.setMutable(false);
//		dependantMelodyBlock.setRhythmDependant(true);
//		melodyBlocks.add(dependantMelodyBlock);



//		HarmonyRelation harmonyRelation = new HarmonyRelation();
//		harmonyRelation.setSource(voice0);
//		harmonyRelation.setTarget(voice2);
//		operatorConfig.addOperatorRelations(harmonyRelation::execute);

//		Instrument instrument2 = instruments.get(1);
//		instrument2.setVoice(1);
//		instrument2.setChannel(1);
//		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getFixedBeatGroup);
//		melodyBlock2.setInstrument(instrument2);
		
//		Instrument instrument2 = instruments.get(1);
//		instrument2.setVoice(1);
//		instrument2.setChannel(1);
//		MelodyBlock melodyBlock2 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock, instrument2);

		return melodyBlocks;
	}

	public List<MelodyBlock> canon(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setInstrument(instrument2);
		melodyBlock2.setCalculable(false);
		melodyBlocks.add(melodyBlock2);

		OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
		operatorRelation.setSource(voice0);
		operatorRelation.setTarget(voice1);
		operatorRelation.setSteps(0);
		operatorRelation.setTimeLine(timeLine);
		operatorRelation.setOffset(getTimeConfig().getOffset());
		operatorConfig.addOperatorRelations(operatorRelation::execute);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> fugueInverse(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setInstrument(instrument2);
		melodyBlock2.setCalculable(false);
		melodyBlocks.add(melodyBlock2);

		OperatorRelation operatorRelation = new OperatorRelation(Operator.I_RELATIVE);
		operatorRelation.setSource(voice0);
		operatorRelation.setTarget(voice1);
		operatorRelation.setTimeLine(timeLine);
		operatorRelation.setFunctionalDegreeCenter(1);//between 1 and 7
		operatorRelation.setOffset(getTimeConfig().getOffset());
		operatorConfig.addOperatorRelations(operatorRelation::execute);
		
		return melodyBlocks;
	}

	public List<MelodyBlock> depending(){
		//dependant harmony
		dependantHarmonyGenerators = new ArrayList<>();
		DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice0, voice1);
		dependantHarmonyGenerators.add(dependantGenerator);

		dependantGenerator = new DependantGenerator(timeLine, voice0, voice1);
		dependantHarmonyGenerators.add(dependantGenerator);
		//has to be set first, before generation
		melodyVoice.hasDependentHarmony(true);
		melodyVoice.addChordType(ChordType.CH2_GROTE_TERTS);
		melodyVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
//		melodyVoice.addChordType(ChordType.CH2_KWART);
//		melodyVoice.addChordType(ChordType.CH2_KWINT);
//		melodyVoice.addChordType(ChordType.ALL);
		melodyVoice.addChordType(ChordType.CH2_GROTE_SIXT);
		melodyVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);

		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);
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
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setInstrument(instrument2);
		melodyBlock2.setCalculable(false);
		melodyBlocks.add(melodyBlock2);

		OperatorRelation operatorRelation = new OperatorRelation(operator);
		operatorRelation.setSource(voice0);
		operatorRelation.setTarget(voice1);
		operatorRelation.setSteps(steps);
		operatorRelation.setTimeLine(timeLine);
		operatorRelation.setOffset(getTimeConfig().getOffset());
		operatorConfig.addOperatorRelations(operatorRelation::execute);
		
		return melodyBlocks;
	}

	private List<MelodyBlock> operator(Operator operator, double factor) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setOffset(getTimeConfig().getOffset());
		melodyBlock2.setInstrument(instrument2);
		melodyBlock2.setCalculable(false);
		melodyBlocks.add(melodyBlock2);

		OperatorRelation operatorRelation = new OperatorRelation(operator);
		operatorRelation.setSource(voice0);
		operatorRelation.setTarget(voice1);
		operatorRelation.setTimeLine(timeLine);
		operatorRelation.setFactor(factor);
		operatorRelation.setOffset(getTimeConfig().getOffset());
		operatorConfig.addOperatorRelations(operatorRelation::execute);

		return melodyBlocks;
	}

	public List<MelodyBlock> harmonize(){
		return super.harmonize();
	}

}

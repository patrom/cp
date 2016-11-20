package cp.composition;

import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.relation.CopyRangeRelation;
import cp.nsga.operator.relation.OperatorRelation;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentMapping;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
@Component(value="twoVoiceComposition")
public class TwoVoiceComposition extends Composition{

	@PostConstruct
	public void initInstruments(){
		if (instrumentConfig.getSize() >= 2) {
			instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
			instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
		}
	}

	public List<MelodyBlock> beatEven(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateDependantMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange(), melodyBlock);
		melodyBlock2.setInstrument(instrument2);
		melodyBlock2.setMutable(false);

		CopyRangeRelation copyRangeRelation = new CopyRangeRelation();
		copyRangeRelation.setSource(voice0);
		copyRangeRelation.setTarget(voice1);
		copyRangeRelation.setTimeLine(timeLine);
		copyRangeRelation.setEndComposition(end);
		copyRangeRelation.setRangeLength(DurationConstants.WHOLE);
//		copyRangeRelation.setRangeLength(end);
		copyRangeRelation.setMinimumLength(DurationConstants.EIGHT);
		operatorConfig.addOperatorRelations(copyRangeRelation::execute);


//		Instrument instrument2 = instruments.get(1);
//		instrument2.setVoice(1);
//		instrument2.setChannel(1);
//		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(instrument2.getVoice(), instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getFixedBeatGroup);
//		melodyBlock2.setInstrument(instrument2);
		
//		Instrument instrument2 = instruments.get(1);
//		instrument2.setVoice(1);
//		instrument2.setChannel(1);
//		MelodyBlock melodyBlock2 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock, instrument2);
		
		melodyBlocks.add(melodyBlock2);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> canon(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

//		cello.setKeySwitch(new KontactStringsKeySwitch());

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);	

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setTimeConfig(getTimeConfig());
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

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setTimeConfig(getTimeConfig());
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

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setTimeConfig(getTimeConfig());
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

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = new MelodyBlock(instrument2.pickRandomOctaveFromRange(), voice1);
		melodyBlock2.setTimeConfig(getTimeConfig());
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
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		//harmonization
		List<Note> notes = harmonizeMelody.getNotesToHarmonize();
		InstrumentMapping instrumentHarmonize = instrumentConfig.getInstrumentMappingForVoice(harmonizeVoice);
		CpMelody melody = new CpMelody(notes, harmonizeVoice, start, end);
		MelodyBlock melodyBlockHarmonize = new MelodyBlock(instrumentHarmonize.getInstrument().pickRandomOctaveFromRange(), harmonizeVoice);
		melodyBlockHarmonize.addMelodyBlock(melody);
		melodyBlockHarmonize.setTimeConfig(getTimeConfig());
		melodyBlockHarmonize.setMutable(false);
		melodyBlockHarmonize.setInstrument(instrumentHarmonize.getInstrument());
//		melodyBlockHarmonize.I();

		melodyBlocks.add(melodyBlockHarmonize);
		int size = instrumentConfig.getSize();
		for (int i = 0; i < size; i++) {
			if (i != harmonizeVoice) {
				Instrument instrument = instrumentConfig.getInstrumentForVoice(i);
				MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(i, instrument.pickRandomOctaveFromRange());
				melodyBlock.setInstrument(instrument);
				melodyBlocks.add(melodyBlock);
			}
		}

		return melodyBlocks;
	}
}

package cp.composition;

import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.relation.OperatorRelation;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentMapping;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static cp.model.rhythm.DurationConstants.EIGHT;
import static cp.model.rhythm.DurationConstants.HALF;

@Component(value="threeVoiceComposition")
public class ThreeVoiceComposition extends Composition{

	private final int voice0 = 0;
	private final int voice1 = 1;
	private final int voice2 = 2;
	private Instrument instrument1;
	private Instrument instrument2;
	private Instrument instrument3;

	@PostConstruct
	public void initInstruments(){
		instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
		instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
		instrument3 = instrumentConfig.getInstrumentForVoice(voice2);
	}

	public List<MelodyBlock> canon2Voice1Acc(){
		return operator(Operator.T_RELATIVE, 0);
	}
	
	public List<MelodyBlock> accFixedRhythm(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(voice2, instrument3.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorTplusAcc(){
		return operator(Operator.T, 0);
	}

	private List<MelodyBlock> operator(Operator operator, int steps) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

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

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(voice2, instrument3.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorT(){
		return operator3voices(Operator.T_RELATIVE, 0);
	}
	
	private List<MelodyBlock> operator3voices(Operator operator, int steps) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

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

		MelodyBlock melodyBlock3 = new MelodyBlock(instrument3.pickRandomOctaveFromRange(),voice2);
		melodyBlock3.setTimeConfig(getTimeConfig());
		int offsetVoice2 = getTimeConfig().getOffset() * 2;
		melodyBlock3.setOffset(offsetVoice2);
		melodyBlock3.setInstrument(instrument3);
		melodyBlock3.setCalculable(false);
		melodyBlocks.add(melodyBlock3);

		OperatorRelation operatorRelation2 = new OperatorRelation(operator);
		operatorRelation2.setSource(voice0);
		operatorRelation2.setTarget(voice2);
		operatorRelation2.setSteps(steps);
		operatorRelation2.setTimeLine(timeLine);
		operatorRelation2.setOffset(offsetVoice2);
		operatorConfig.addOperatorRelations(operatorRelation2::execute);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> harmonize(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		//harmonization
		List<Note> notes = harmonizeMelody.getNotesToHarmonize();
		notes.forEach(n -> System.out.println(n.getPitchClass()));
		InstrumentMapping instrumentHarmonize = instrumentConfig.getInstrumentMappingForVoice(harmonizeVoice);
		CpMelody melody = new CpMelody(notes, harmonizeVoice, start, end);
		MelodyBlock melodyBlockHarmonize = new MelodyBlock(0, harmonizeVoice);
		melodyBlockHarmonize.addMelodyBlock(melody);
		melodyBlockHarmonize.setTimeConfig(getTimeConfig());
		melodyBlockHarmonize.setMutable(false);
		melodyBlockHarmonize.setInstrument(instrumentHarmonize.getInstrument());
		melodyBlockHarmonize.I();
		
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

	private List<Note> getMelodieToHarmonize() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
		notes.add(note().pos(HALF + EIGHT).pc(5).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(4).len(DurationConstants.SIXTEENTH).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(8).len(DurationConstants.SIXTEENTH).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(10).len(DurationConstants.SIXTEENTH).build());
		
		notes.add(note().pos(DurationConstants.WHOLE).pc(2).len(3).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(10).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(11).len(DurationConstants.QUARTER).build());
		return notes;
	}
	
	/**
	 * Voice 0: melody
	 * Voice 1: homophonic
	 * Voice 2: half time
	 * @return melodies
	 */
	public List<MelodyBlock> halfTimeHomophonicRhythm(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getHomophonicBeatGroup);
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	/**
	 * Voice 0: 3
	 * Voice 1: homophonic in 4
	 * Voice 2: in 4
	 * @return melodies
	 */
	public List<MelodyBlock> threeOverXX(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getFixedBeatGroup);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange(), time34::getFixedBeatGroup, time34);
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	/**
	 * Voice 0: bass halftime
	 * Voice 1: melody rhythm duplicate
	 * Voice 2: melody 
	 * @return melodies
	 */
	public List<MelodyBlock> accDuplicateRhythm(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock2, instrument3, voice2);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> allRandom(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlock(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}

}
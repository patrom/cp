package cp.composition;

import cp.generator.dependant.DependantGenerator;
import cp.model.harmony.ChordType;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.relation.OperatorRelation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static cp.model.rhythm.DurationConstants.EIGHT;
import static cp.model.rhythm.DurationConstants.HALF;

@Component(value="threeVoiceComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "3")
public class ThreeVoiceComposition extends Composition{

	@PostConstruct
	public void initInstruments(){
		if(instrumentConfig.getSize() < 3){
			throw new IllegalStateException("Set instrument config to correct instrument");
		}
		instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
		instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
		instrument3 = instrumentConfig.getInstrumentForVoice(voice2);

		voiceConfiguration.put(voice0, fixedVoice);
		voiceConfiguration.put(voice1, fixedVoice);
		voiceConfiguration.put(voice2, melodyVoice);

	}

	public List<MelodyBlock> canon2Voice1Acc(){
		return operator(Operator.T_RELATIVE, 0);
	}
	
	public List<MelodyBlock> accFixedRhythm(){
		voiceConfiguration.put(0, doubleTimeVoice);
		voiceConfiguration.put(1, doubleTimeVoice);

		voiceConfiguration.put(3, melodyVoice);

		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorTplusAcc(){
		return operator(Operator.T, 2);
	}

	private List<MelodyBlock> operator(Operator operator, int steps) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

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

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> operatorT(){
		return operator3voices(Operator.T_RELATIVE, 3);
	}
	
	private List<MelodyBlock> operator3voices(Operator operator, int steps) {
		List<MelodyBlock> melodyBlocks = new ArrayList<>();

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

		MelodyBlock melodyBlock3 = new MelodyBlock(instrument3.pickRandomOctaveFromRange(),voice2);
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
		return super.harmonize();
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
	 * Voice 0: bass
	 * Voice 1: homophonic
	 * Voice 2: melody
	 * @return melodies
	 */
	public List<MelodyBlock> halfTimeHomophonicRhythm(){
		voiceConfiguration.put(voice0, bassVoice);
		voiceConfiguration.put(voice1, homophonicVoice);
		voiceConfiguration.put(voice2, homophonicVoice);

		voiceConfiguration.put(voice3, melodyVoice);
//		//add only if triad chordtypes are available
//		voiceConfiguration.put(4, homophonicVoice);
//
		dependantHarmonyGenerators = new ArrayList<>();
		DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice1, voice2);
		dependantHarmonyGenerators.add(dependantGenerator);
//
//		dependantGenerator = new DependantGenerator(timeLine, voice2, voice4);
//		dependantHarmonyGenerators.add(dependantGenerator);
//
//		voiceConfiguration.put(voice3, homophonicVoice);
//		voiceConfiguration.put(voice4, homophonicVoice);
//
//		//has to be set first, before generation
		homophonicVoice.hasDependentHarmony(true);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
		homophonicVoice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
		homophonicVoice.addChordType(ChordType.CH2_KWART);
//		homophonicVoice.addChordType(ChordType.CH2_KWINT);
//		homophonicVoice.addChordType(ChordType.ALL);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
		homophonicVoice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
//		homophonicVoice.addChordType(ChordType.MAJOR);
//		homophonicVoice.addChordType(ChordType.MAJOR_1);
//      homophonicVoice.addChordType(ChordType.MAJOR_2);
//      homophonicVoice.addChordType(ChordType.DOM);

		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument4);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}

	public List<MelodyBlock> depending(){
		voiceConfiguration.put(voice0, homophonicVoice);
		voiceConfiguration.put(voice1, homophonicVoice);
		voiceConfiguration.put(voice2, melodyVoice);

		dependantHarmonyGenerators = new ArrayList<>();
		DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice0, voice1);
		dependantHarmonyGenerators.add(dependantGenerator);

//
//		//has to be set first, before generation
		homophonicVoice.hasDependentHarmony(true);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
		homophonicVoice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
		homophonicVoice.addChordType(ChordType.CH2_KWART);
//		homophonicVoice.addChordType(ChordType.CH2_KWINT);
//		homophonicVoice.addChordType(ChordType.ALL);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT);
		homophonicVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
		homophonicVoice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
//		homophonicVoice.addChordType(ChordType.MAJOR);
//		homophonicVoice.addChordType(ChordType.MAJOR_1);
//      homophonicVoice.addChordType(ChordType.MAJOR_2);
//      homophonicVoice.addChordType(ChordType.DOM);

		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
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
		voiceConfiguration.put(voice0, homophonicVoice);
		voiceConfiguration.put(voice1, timeVoice);
		voiceConfiguration.put(voice2, melodyVoice);
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	/**
	 * Voice 0: bass halftime
	 * Voice 1: rhythm bass duplicate
	 * Voice 2: melody 
	 * @return melodies
	 */
	public List<MelodyBlock> accDuplicateRhythm(){
		voiceConfiguration.put(voice0, homophonicVoice);
		voiceConfiguration.put(voice1, homophonicVoice);
		voiceConfiguration.put(voice2, melodyVoice);
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock, instrument2, voice1);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}
	
	public List<MelodyBlock> allRandom(){
		voiceConfiguration.put(0, melodyVoice);
		voiceConfiguration.put(1, melodyVoice);
		voiceConfiguration.put(2, melodyVoice);

		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
		melodyBlock.setInstrument(instrument1);
		melodyBlocks.add(melodyBlock);

		MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
		melodyBlock2.setInstrument(instrument2);
		melodyBlocks.add(melodyBlock2);

		MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
		melodyBlock3.setInstrument(instrument3);
		melodyBlocks.add(melodyBlock3);

		return melodyBlocks;
	}

}
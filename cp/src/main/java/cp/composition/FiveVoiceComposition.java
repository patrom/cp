package cp.composition;

import cp.config.TimbreConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Operator;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.model.timbre.Timbre;
import cp.nsga.operator.relation.OperatorRelation;
import cp.out.play.InstrumentMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 14/11/2016.
 */
@Component(value="fiveVoiceComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "5")
public class FiveVoiceComposition extends Composition {

    @Autowired
    private TimbreConfig timbreConfig;

    @PostConstruct
    public void initInstruments(){
        if(instrumentConfig.getSize() < 5){
            throw new IllegalStateException("Set instrument config to correct instrument");
        }
       instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
       instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
       instrument3 = instrumentConfig.getInstrumentForVoice(voice2);
       instrument4 = instrumentConfig.getInstrumentForVoice(voice3);
       instrument5 = instrumentConfig.getInstrumentForVoice(voice4);

    }

    public List<MelodyBlock> harmonize(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock4);

        //melody
        //harmonization
        Map<String, List<Note>> notesPerInstrument = harmonizeMelody.getNotesToHarmonize();
        Timbre timbreConfig = this.timbreConfig.getTimbreConfigForVoice(harmonizeVoice);
        notesPerInstrument.entrySet().stream().flatMap(entry -> entry.getValue().stream()).forEach(n -> {
            n.setVoice(harmonizeVoice);
            if(n.getDynamic() == null){
                n.setDynamic(timbreConfig.getDynamic());
                n.setDynamicLevel(timbreConfig.getDynamic().getLevel());
            }
            if(n.getTechnical() == null){
                n.setTechnical(timbreConfig.getTechnical());
            }
        });
        List<Note> notes = notesPerInstrument.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .filter(n -> Character.getNumericValue(n.getInstrument().charAt(1)) == harmonizeVoice)
                .collect(toList());

        InstrumentMapping instrumentHarmonize = instrumentConfig.getInstrumentMappingForVoice(harmonizeVoice);
        CpMelody melody = new CpMelody(notes, harmonizeVoice, start, end);
        List<Integer> contour = getContour(notes);
        melody.setContour(contour);
        MelodyBlock melodyBlockHarmonize = new MelodyBlock(6, harmonizeVoice);
        melodyBlockHarmonize.addMelodyBlock(melody);
        melodyBlockHarmonize.setMutable(false);
//        melodyBlockHarmonize.I();

//        MelodyBlock melodyBlock3 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlockHarmonize, instrument3, voice2);
//        melodyBlock3.setOffset(getTimeConfig().getOffset());
//        melodyBlocks.add(melodyBlock3);

        melodyBlocks.add(melodyBlockHarmonize);


//        int size = instrumentConfig.getSize();
//        for (int i = 0; i < size; i++) {
//            if (i != harmonizeVoice) {
//                Instrument instrument = instrumentConfig.getInstrumentForVoice(i);
//                MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(i, instrument.pickRandomOctaveFromRange());
//                melodyBlocks.add(melodyBlock);
//            }
//        }

        return melodyBlocks;
    }

    /**
     * Voice 0: homophonicVoice
     * Voice 1: homophonicVoice
     * Voice 2: homophonicVoice
     * Voice 3: homophonicVoice
     * Voice 4: melody
     * @return melodies
     */
    public List<MelodyBlock> homophonicRhythm(){
//        voiceConfiguration.put(voice0, homophonicVoice);
//        voiceConfiguration.put(voice1, homophonicVoice);
//        voiceConfiguration.put(voice2, homophonicVoice);
//        voiceConfiguration.put(voice3, homophonicVoice);
//        voiceConfiguration.put(voice4, melodyVoice);
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);


        MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlockConfig(voice4, instrument5.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock5);

        return melodyBlocks;
    }

    public List<MelodyBlock> allRandom(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1);
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2);
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3);
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlockConfig(voice4);
        melodyBlocks.add(melodyBlock5);

        return melodyBlocks;
    }

    public List<MelodyBlock> doubleCanon(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlockConfig(voice0);
        melodyBlocks.add(melodyBlock5);


        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice2, instrument3.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);


        MelodyBlock melodyBlock3 = melodyGenerator.generateEmptyBlock(instrument4, voice3);
        melodyBlock3.setMutable(false);
        melodyBlocks.add(melodyBlock3);

        OperatorRelation operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice1);
        operatorRelation.setTarget(voice3);
        operatorRelation.setSteps(4);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.WHOLE);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock4 = melodyGenerator.generateEmptyBlock(instrument5, voice4);
        melodyBlock4.setMutable(false);
        melodyBlocks.add(melodyBlock4);

        operatorRelation = new OperatorRelation(Operator.T_RELATIVE);
        operatorRelation.setSource(voice2);
        operatorRelation.setTarget(voice4);
        operatorRelation.setSteps(4);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setOffset(DurationConstants.WHOLE);
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        return melodyBlocks;
    }

    public List<MelodyBlock> partAugmentation() {
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0);
//        melodyBlock.setOffset(getTimeConfig().getOffset());
        melodyBlock.setMutable(false);
        melodyBlocks.add(melodyBlock);

        OperatorRelation operatorRelation = new OperatorRelation(Operator.AUGMENTATION);
        operatorRelation.setSource(voice4);
        operatorRelation.setTarget(voice0);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setFactor(2.0);
//        operatorRelation.setOffset(getTimeConfig().getOffset());
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice3);
//        melodyBlock2.setOffset(getTimeConfig().getOffset());
        melodyBlock2.setMutable(false);
        melodyBlocks.add(melodyBlock2);

        operatorRelation = new OperatorRelation(Operator.AUGMENTATION);
        operatorRelation.setSource(voice1);
        operatorRelation.setTarget(voice3);
        operatorRelation.setTimeLine(timeLine);
        operatorRelation.setFactor(2.0);
//        operatorRelation.setOffset(getTimeConfig().getOffset());
        operatorConfig.addOperatorRelations(operatorRelation::execute);

        MelodyBlock melodyBlock3 = melodyGenerator.generateMelodyBlockConfig(voice2);
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice1);
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlockConfig(voice4);
        melodyBlocks.add(melodyBlock5);

        return melodyBlocks;
    }



}


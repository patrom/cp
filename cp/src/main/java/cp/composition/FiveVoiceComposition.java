package cp.composition;

import cp.composition.voice.Voice;
import cp.generator.dependant.DependantGenerator;
import cp.model.harmony.ChordType;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.out.play.InstrumentMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 14/11/2016.
 */
@Component(value="fiveVoiceComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "5")
public class FiveVoiceComposition extends Composition {

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

//        voiceConfiguration.put(voice0, bassVoice);
//        voiceConfiguration.put(voice1, homophonicVoice);
//        voiceConfiguration.put(voice2, homophonicVoice);
//        voiceConfiguration.put(voice3, homophonicVoice);
//        voiceConfiguration.put(voice4, melodyVoice);

    }

    /**
     * Voice 0: bass halftime
     * Voice 1: halftime rhythm
     * Voice 2: halftime rhythm duplicate voice 1
     * voice 3: free acc
     * Voice 4: melody
     * @return melodies
     */
    public List<MelodyBlock> accDuplicateRhythm(){
//        voiceConfiguration.put(voice0, bassVoice);
//        voiceConfiguration.put(voice1, homophonicVoice);
//        voiceConfiguration.put(voice2, homophonicVoice);
//        voiceConfiguration.put(voice3, homophonicVoice);
//        voiceConfiguration.put(voice4, melodyVoice);
//        voiceConfiguration.put(voice4, melodyVoice);

        dependantHarmonyGenerators = new ArrayList<>();
        DependantGenerator dependantGenerator = new DependantGenerator(timeLine, voice1, voice2);
        dependantHarmonyGenerators.add(dependantGenerator);

//		dependantGenerator = new DependantGenerator(timeLine, voice3, voice4);
//		dependantHarmonyGenerators.add(dependantGenerator);

		//has to be set first, before generation
        Voice voice = voiceConfig.getVoiceConfiguration(voice1);
        voice.hasDependentHarmony(true);
        voice.addChordType(ChordType.CH2_GROTE_TERTS);
//        voice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
//        voice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
        voice.addChordType(ChordType.CH2_KWART);
        voice.addChordType(ChordType.CH2_KWINT);
//		voice.addChordType(ChordType.ALL_INTERVALS);
        voice.addChordType(ChordType.CH2_GROTE_SIXT);
//        voice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
//        voice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
//		voice.addChordType(ChordType.MAJOR);
//		voice.addChordType(ChordType.MAJOR_1);
//      voice.addChordType(ChordType.MAJOR_2);
//      voice.addChordType(ChordType.DOM);

        //has to be set first, before generation
//        melodyVoice.hasDependentHarmony(true);
//        melodyVoice.addChordType(ChordType.CH2_GROTE_TERTS);
////        melodyVoice.addChordType(ChordType.CH2_GROTE_TERTS_CHR);
////        melodyVoice.addChordType(ChordType.CH2_KLEINE_TERTS_CHR);
//        melodyVoice.addChordType(ChordType.CH2_KWART);
//		melodyVoice.addChordType(ChordType.CH2_KWINT);
//		melodyVoice.addChordType(ChordType.ALL_INTERVALS);
//        melodyVoice.addChordType(ChordType.CH2_GROTE_SIXT);
//        melodyVoice.addChordType(ChordType.CH2_GROTE_SIXT_CHR);
//        melodyVoice.addChordType(ChordType.CH2_KLEINE_SIXT_CHR);
//		melodyVoice.addChordType(ChordType.MAJOR);
//		melodyVoice.addChordType(ChordType.MAJOR_1);
//      melodyVoice.addChordType(ChordType.MAJOR_2);
//      melodyVoice.addChordType(ChordType.DOM);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, instrument1.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlockConfig(voice1, instrument2.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlockConfig(voice3, instrument4.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock4);

        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlockConfig(voice4, instrument5.pickRandomOctaveFromRange());
        melodyBlocks.add(melodyBlock5);

        return melodyBlocks;
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
        List<Note> notes = harmonizeMelody.getNotesToHarmonize();

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
//                melodyBlock.setInstrument(instrument);
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

}


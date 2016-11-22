package cp.composition;

import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.out.play.InstrumentMapping;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 14/11/2016.
 */
@Component(value="fiveVoiceComposition")
public class FiveVoiceComposition extends Composition {

    @PostConstruct
    public void initInstruments(){
       if(instrumentConfig.getSize() >= 4){
           instrument1 = instrumentConfig.getInstrumentForVoice(voice0);
           instrument2 = instrumentConfig.getInstrumentForVoice(voice1);
           instrument3 = instrumentConfig.getInstrumentForVoice(voice2);
           instrument4 = instrumentConfig.getInstrumentForVoice(voice3);
           instrument5 = instrumentConfig.getInstrumentForVoice(voice4);
       }
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
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getHomophonicBeatGroup);
        melodyBlock.setInstrument(instrument1);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getHomophonicBeatGroup);
        melodyBlock2.setInstrument(instrument2);
        melodyBlocks.add(melodyBlock2);

        MelodyBlock melodyBlock3 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlock2, instrument3, voice2);
        melodyBlocks.add(melodyBlock3);

        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlock(voice3, instrument4.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
        melodyBlock4.setInstrument(instrument4);
        melodyBlocks.add(melodyBlock4);

        //melody
        MelodyBlock melodyBlock5 = melodyGenerator.generateMelodyBlock(voice4, instrument5.pickRandomOctaveFromRange());
        melodyBlock5.setInstrument(instrument5);
        melodyBlocks.add(melodyBlock5);

        return melodyBlocks;
    }

    public List<MelodyBlock> harmonize(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(voice0, instrument1.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
        melodyBlock.setInstrument(instrument1);
        melodyBlocks.add(melodyBlock);

        MelodyBlock melodyBlock2 = melodyGenerator.generateMelodyBlock(voice1, instrument2.pickRandomOctaveFromRange(), getTimeConfig()::getHomophonicBeatGroup);
        melodyBlock2.setInstrument(instrument2);
        melodyBlocks.add(melodyBlock2);



        MelodyBlock melodyBlock4 = melodyGenerator.generateMelodyBlock(voice3, instrument4.pickRandomOctaveFromRange(), getTimeConfig()::getBeatsDoubleLength);
        melodyBlock4.setInstrument(instrument4);
        melodyBlocks.add(melodyBlock4);

        //melody
        //harmonization
        List<Note> notes = harmonizeMelody.getNotesToHarmonize();

        InstrumentMapping instrumentHarmonize = instrumentConfig.getInstrumentMappingForVoice(harmonizeVoice);
        CpMelody melody = new CpMelody(notes, harmonizeVoice, start, end);
        MelodyBlock melodyBlockHarmonize = new MelodyBlock(instrumentHarmonize.getInstrument().pickRandomOctaveFromRange(), harmonizeVoice);
        melodyBlockHarmonize.addMelodyBlock(melody);
        melodyBlockHarmonize.setTimeConfig(getTimeConfig());
        melodyBlockHarmonize.setMutable(false);
        melodyBlockHarmonize.setInstrument(instrumentHarmonize.getInstrument());
//        melodyBlockHarmonize.I();

        MelodyBlock melodyBlock3 = melodyGenerator.duplicateRhythmMelodyBlock(melodyBlockHarmonize, instrument3, voice2);
        melodyBlock3.setOffset(getTimeConfig().getOffset());
        melodyBlocks.add(melodyBlock3);

        melodyBlocks.add(melodyBlockHarmonize);


//        int size = instrumentConfig.getSize();
//        for (int i = 0; i < size; i++) {
//            if (i != harmonizeVoice) {
//                Instrument instrument = instrumentConfig.getInstrumentForVoice(i);
//                MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(i, instrument.pickRandomOctaveFromRange());
//                melodyBlock.setInstrument(instrument);
//                melodyBlocks.add(melodyBlock);
//            }
//        }

        return melodyBlocks;
    }
}


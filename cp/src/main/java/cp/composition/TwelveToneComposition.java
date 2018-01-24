package cp.composition;

import cp.config.InstrumentConfig;
import cp.config.ScaleConfig;
import cp.config.TwelveToneConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.twelve.TwelveToneBuilder;
import cp.nsga.operator.mutation.MutationType;
import cp.out.instrument.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component(value="twelveToneComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "12")
public class TwelveToneComposition extends Composition {


    @Autowired
    private TwelveToneConfig twelveToneConfig;

    @Autowired
    private InstrumentConfig instrumentConfig;



//    public List<MelodyBlock> compose() {
//        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//
//        List<Note> gridNotes = twelveToneBuilder.build(DurationConstants.QUARTER,
//                rhythmCombinations.threeNoteUneven::pos123,
//                rhythmCombinations.twoNoteEven::pos13,
//                rhythmCombinations.twoNoteEven::pos34,
//                rhythmCombinations.threeNoteUneven::pos123);
//        int endGrid = twelveToneBuilder.getEnd();
//
//        Map<Integer, Scale> twelveToneConfig = this.twelveToneConfig.getTwelveToneConfig();
//        for (Map.Entry<Integer, Scale> config : twelveToneConfig.entrySet()) {
//            Integer voice = config.getKey();
//            Scale scale = config.getValue();
//
//            List<Note> melodyNotes = twelveToneBuilder.getTwelveToneNotes(voice, scale);
//
//            Instrument instrumentForVoice = instrumentConfig.getInstrumentForVoice(voice);
//            CpMelody cpMelody = new CpMelody(melodyNotes, voice, 0, endGrid);
//            cpMelody.setMutationType(MutationType.TWELVE_TONE);
//            MelodyBlock melodyBlock = new MelodyBlock(instrumentForVoice.pickRandomOctaveFromRange(), voice);
//            melodyBlock.addMelodyBlock(cpMelody);
//            melodyBlocks.add(melodyBlock);
//        }
//
//        return melodyBlocks;
//    }

    public List<MelodyBlock> compose2() {
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        //voices??
        for (Map.Entry<Integer, List<ScaleConfig>> entry : twelveToneConfig.getScaleConfig().entrySet()) {
            MelodyBlock melodyBlock = null;
            int voice = entry.getKey();
            int start = 0;
            int i = 0 ;
            List<ScaleConfig> scaleConfigs = entry.getValue();
            int size = scaleConfigs.size();
            Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
            melodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);

            while (start < end) {
                ScaleConfig scaleConfig = scaleConfigs.get(i % size);

                TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(start,
                        scaleConfig.getBeat(), voice , scaleConfig.getScale(), scaleConfig.getRepeat(), scaleConfig.getRhythmCombinations());
                List<Note> notes = twelveToneBuilder.buildRepeat();
                int length = twelveToneBuilder.getLength();
                twelveToneConfig.addTwelveToneBuilder(voice ,twelveToneBuilder);//mutation link with melody - start

                CpMelody cpMelody = new CpMelody(notes, voice, start, length);
                cpMelody.setMutationType(MutationType.TWELVE_TONE);
                melodyBlock.addMelodyBlock(cpMelody);
                start = start + length;
                i++;
            }

            melodyBlocks.add(melodyBlock);
        }

//        Map<Integer, TwelveToneBuilder> twelveToneConfig = this.twelveToneConfig.getTwelveToneConfig();
//        for (Map.Entry<Integer, TwelveToneBuilder> entry : twelveToneConfig.entrySet()) {
//            Integer voice = entry.getKey();
//
//            TwelveToneBuilder twelveToneBuilder = this.twelveToneConfig.getTwelveToneConfigForVoice(voice);
//            List<Note> gridNotes = twelveToneBuilder.build();
//            gridNotes.forEach(note -> note.setVoice(voice));
//            Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
//
//            CpMelody cpMelody = new CpMelody(gridNotes, voice, twelveToneBuilder.getStart(), twelveToneBuilder.getEnd());
//            cpMelody.setMutationType(MutationType.TWELVE_TONE);
//            MelodyBlock melodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);
//            melodyBlock.addMelodyBlock(cpMelody);
//            melodyBlocks.add(melodyBlock);
//        }

        return melodyBlocks;
    }

}

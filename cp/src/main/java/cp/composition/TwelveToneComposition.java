package cp.composition;

import cp.config.ScaleConfig;
import cp.config.TwelveToneConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.twelve.AggregateBuilder;
import cp.model.twelve.AggregateBuilderFactory;
import cp.model.twelve.TwelveToneBuilder;
import cp.nsga.operator.mutation.MutationType;
import cp.out.instrument.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component(value="twelveToneComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "12")
public class TwelveToneComposition extends Composition {

    @Autowired
    private TwelveToneConfig twelveToneConfig;
    @Autowired
    private AggregateBuilderFactory aggregateBuilderFactory;


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

    public List<MelodyBlock> compose() {
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        for (Map.Entry<Integer, List<ScaleConfig>> entry : twelveToneConfig.getScaleConfig().entrySet()) {
            int voice = entry.getKey();
            int start = 0;
            int i = 0 ;
            List<ScaleConfig> scaleConfigs = entry.getValue();
            int size = scaleConfigs.size();
            Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
            MelodyBlock melodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);

//            while (start < end) {
//                ScaleConfig scaleConfig = scaleConfigs.get(i % size);
//
//                TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(start,
//                        scaleConfig.getBeats(), voice , scaleConfig.getScale(), scaleConfig.getRhythmCombinations());
//                List<Note> notes = twelveToneBuilder.buildRepeat();
//                int length = twelveToneBuilder.getLength();
//                twelveToneConfig.addTwelveToneBuilder(voice ,twelveToneBuilder);//mutation link with melody - start
//
//                CpMelody cpMelody = new CpMelody(notes, voice, start, start + length);
//                cpMelody.setMutationType(MutationType.TWELVE_TONE);
//                melodyBlock.addMelodyBlock(cpMelody);
//                start = start + length;
//                i++;
//            }
            for (ScaleConfig scaleConfig : scaleConfigs) {
                TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(start,
                        scaleConfig.getBeats(), voice , scaleConfig.getScale(), scaleConfig.getRhythmCombinations());
                List<Note> notes = twelveToneBuilder.buildRepeat();
                int length = twelveToneBuilder.getLength();
                twelveToneConfig.addTwelveToneBuilder(voice, twelveToneBuilder);//mutation link with melody - start

                CpMelody cpMelody = new CpMelody(notes, voice, start, start + length);
                cpMelody.setMutationType(MutationType.TWELVE_TONE);
                melodyBlock.addMelodyBlock(cpMelody);
                start = start + length;
                i++;
            }

            melodyBlocks.add(melodyBlock);
        }
        return melodyBlocks;
    }



    public List<MelodyBlock> composeMerge() {
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        twelveToneConfig.clearConfig();
        //create melodyBlocks
        Map<Integer, MelodyBlock> melodyBlockMap = new HashMap<>();
        Map<Integer, List<ScaleConfig>> scaleConfigMelodyBlocks = twelveToneConfig.getScaleConfig();
        for (Map.Entry<Integer, List<ScaleConfig>> integerListEntry : scaleConfigMelodyBlocks.entrySet()) {
            Integer voice = integerListEntry.getKey();
            Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
            MelodyBlock melodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);
            melodyBlockMap.put(voice, melodyBlock);
            List<ScaleConfig> scaleConfigs = integerListEntry.getValue();
            for (ScaleConfig scaleConfig : scaleConfigs) {
                List<Integer> splitVoices = scaleConfig.getSplitVoices();
                for (Integer splitVoice : splitVoices) {
                    instrument = instrumentConfig.getInstrumentForVoice(splitVoice);
                    MelodyBlock melodyBlockSplit = new MelodyBlock(instrument.pickRandomOctaveFromRange(), splitVoice);
                    melodyBlockMap.put(splitVoice, melodyBlockSplit);
                }
            }
        }

        //create grids
        Map<Integer, List<ScaleConfig>> scaleConfigCreate = twelveToneConfig.getScaleConfig();
        for (Map.Entry<Integer, List<ScaleConfig>> entry : scaleConfigCreate.entrySet()) {
            int start = 0;
            int voice = entry.getKey();
            List<ScaleConfig> scaleConfigs = entry.getValue();
            for (ScaleConfig scaleConfig : scaleConfigs) {
                AggregateBuilder aggregateBuilder = aggregateBuilderFactory.getAggregateBuilder(scaleConfig.getBuilderType(), start,
                        scaleConfig.getBeats(), voice, scaleConfig.getScale(), scaleConfig.getRhythmCombinations());
                aggregateBuilder.createGrid();
                twelveToneConfig.addTwelveToneBuilder(voice, aggregateBuilder);//mutation link with melody - start
                List<Integer> splitVoices = scaleConfig.getSplitVoices();
                for (Integer splitVoice : splitVoices) {
                    AggregateBuilder twelveToneBuilderSplitVoice = aggregateBuilderFactory.getAggregateBuilder(scaleConfig.getBuilderType(),start,
                            scaleConfig.getBeats(), splitVoice , scaleConfig.getScale(), scaleConfig.getRhythmCombinations());
                    twelveToneBuilderSplitVoice.createGrid();
                    twelveToneConfig.addTwelveToneBuilder(voice ,twelveToneBuilderSplitVoice);
                }
                int length = aggregateBuilder.getLength();
                start = start + length;
            }
        }

        //merge grids + update pitch clesses
        Map<Integer, List<AggregateBuilder>> twelveToneConfig = this.twelveToneConfig.getTwelveToneConfig();
        for (Map.Entry<Integer, List<AggregateBuilder>> entry : twelveToneConfig.entrySet()) {
            Integer voice = entry.getKey();
            TreeMap<Integer, List<AggregateBuilder>> builderPerPosition = entry.getValue().stream()
                    .collect(Collectors.groupingBy(AggregateBuilder::getStart, TreeMap::new, toList()));;
            for (Map.Entry<Integer, List<AggregateBuilder>> listEntry : builderPerPosition.entrySet()) {
                List<AggregateBuilder> builders = listEntry.getValue();
                AggregateBuilder firstBuilder = builders.get(0);
                List<Note> mergedNotes = builders.stream()
                        .map(twelveToneBuilder -> twelveToneBuilder.getGridNotes())
                        .flatMap(notes -> notes.stream())
                        .sorted()
                        .collect(toList());
                //update pcs
                AggregateBuilder tempBuilder = aggregateBuilderFactory.getAggregateBuilder(firstBuilder.getBuilderType(), firstBuilder.getStart(),
                        null, voice , firstBuilder.getScale(), null);
                tempBuilder.setGridNotes(mergedNotes);
                int[] pitchClasses = firstBuilder.getScale().getPitchClasses();
                long size = mergedNotes.stream().filter(note -> !note.isRest()).count();
                if (size >= pitchClasses.length) {
                    //repeat notes
                    tempBuilder.notesLargerOrEqualThanScale(pitchClasses);
                    TreeMap<Integer, List<Note>> notePerVoice = tempBuilder.getGridNotes().stream().collect(Collectors.groupingBy(Note::getVoice, TreeMap::new, toList()));
                    for (Map.Entry<Integer, List<Note>> mapEntry : notePerVoice.entrySet()) {
                        int voiceNote = mapEntry.getKey();
                        CpMelody cpMelody = new CpMelody(mapEntry.getValue(), voiceNote, firstBuilder.getStart(), firstBuilder.getEnd());
                        cpMelody.setMutationType(MutationType.TWELVE_TONE);
                        MelodyBlock melodyBlock = melodyBlockMap.get(voiceNote);
                        melodyBlock.addMelodyBlock(cpMelody);
                    }
                } else if (size < pitchClasses.length) {
                    //build dependant notes
                    List<Note> notesAddedDependencies = tempBuilder.addNoteDependenciesAndPitchClasses(pitchClasses);
                    TreeMap<Integer, List<Note>> notePerVoice = notesAddedDependencies.stream().collect(Collectors.groupingBy(Note::getVoice, TreeMap::new, toList()));
                    for (Map.Entry<Integer, List<Note>> mapEntry : notePerVoice.entrySet()) {
                        int voiceNote = mapEntry.getKey();
                        List<Note> noteDependencies = tempBuilder.createNoteDependencies(mapEntry.getValue());
                        List<Note> restsForVoice = mergedNotes.stream().filter(note -> note.isRest() && note.getVoice() == voiceNote).collect(toList());
                        noteDependencies.addAll(restsForVoice);
                        Collections.sort(noteDependencies);
                        CpMelody cpMelody = new CpMelody(noteDependencies, voiceNote, firstBuilder.getStart(), firstBuilder.getEnd());
                        cpMelody.setMutationType(MutationType.TWELVE_TONE);
                        MelodyBlock melodyBlock = melodyBlockMap.get(voiceNote);
                        melodyBlock.addMelodyBlock(cpMelody);
                    }
                }
            }
        }
        return new ArrayList<>(melodyBlockMap.values());
    }

//    public List<MelodyBlock> composeSplit() {
//        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//
//        //voices??
//        for (Map.Entry<TwelveToneSplit, List<ScaleConfig>> entry : twelveToneConfig.getScaleConfigSplit().entrySet()) {
//            TwelveToneSplit twelveToneSplit = entry.getKey();
//            int voice = twelveToneSplit.getVoice();
//            int start = 0;
//            int i = 0 ;
//            List<ScaleConfig> scaleConfigs = entry.getValue();
//            int size = scaleConfigs.size();
//            Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
//            MelodyBlock melodyBlock  = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);
//            MelodyBlock splitMelodyBlock  = null;
//            if (twelveToneSplit.isSplit()) {
//                int splitVoice = twelveToneSplit.getSplitVoice();
//
//                instrument = instrumentConfig.getInstrumentForVoice(splitVoice);
//                splitMelodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), splitVoice);
//
//            }
//            for (ScaleConfig scaleConfig : scaleConfigs) {
//                TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(start,
//                        scaleConfig.getBeats(), voice , scaleConfig.getScale(), scaleConfig.getRhythmCombinations());
//                List<Note> notes = twelveToneBuilder.buildRepeat();
//                int length = twelveToneBuilder.getLength();
//                twelveToneConfig.addTwelveToneBuilder(voice ,twelveToneBuilder);//mutation link with melody - start
//
//                if (twelveToneSplit.isSplit()) {
//                    int splitVoice = twelveToneSplit.getSplitVoice();
//                    twelveToneBuilder.setSplit(true);
//                    twelveToneBuilder.setSplitVoice(splitVoice);
//
//                    twelveToneBuilder.splitNotes();
//
//                    CpMelody cpMelody = new CpMelody(twelveToneBuilder.getSplitNotes(voice), voice, start, start + length);
//                    cpMelody.setMutationType(MutationType.TWELVE_TONE);
//                    melodyBlock.addMelodyBlock(cpMelody);
//
//                    cpMelody = new CpMelody(twelveToneBuilder.getSplitNotes(splitVoice), splitVoice, start, start + length);
//                    cpMelody.setMutationType(MutationType.TWELVE_TONE);
//                    cpMelody.setMutable(false);
//
//                    splitMelodyBlock.addMelodyBlock(cpMelody);
//                } else {
//                    CpMelody cpMelody = new CpMelody(notes, voice, start, start + length);
//                    cpMelody.setMutationType(MutationType.TWELVE_TONE);
//                    melodyBlock.addMelodyBlock(cpMelody);
//                }
//                start = start + length;
//                i++;
//            }
//            if (twelveToneSplit.isSplit()) {
//                melodyBlocks.add(splitMelodyBlock);
//            }
//            melodyBlocks.add(melodyBlock);
//        }
//
////        Map<Integer, TwelveToneBuilder> twelveToneConfig = this.twelveToneConfig.getTwelveToneConfig();
////        for (Map.Entry<Integer, TwelveToneBuilder> entry : twelveToneConfig.entrySet()) {
////            Integer voice = entry.getKey();
////
////            TwelveToneBuilder twelveToneBuilder = this.twelveToneConfig.getTwelveToneConfigForVoice(voice);
////            List<Note> gridNotes = twelveToneBuilder.build();
////            gridNotes.forEach(note -> note.setVoice(voice));
////            Instrument instrument = instrumentConfig.getInstrumentForVoice(voice);
////
////            CpMelody cpMelody = new CpMelody(gridNotes, voice, twelveToneBuilder.getStart(), twelveToneBuilder.getEnd());
////            cpMelody.setMutationType(MutationType.TWELVE_TONE);
////            MelodyBlock melodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), voice);
////            melodyBlock.addMelodyBlock(cpMelody);
////            melodyBlocks.add(melodyBlock);
////        }
//
//        return melodyBlocks;
//    }

}

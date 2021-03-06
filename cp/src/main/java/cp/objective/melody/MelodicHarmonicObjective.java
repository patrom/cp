package cp.objective.melody;

import cp.config.MelodyConfig;
import cp.model.Motive;
import cp.model.harmony.Chord;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.objective.Objective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class MelodicHarmonicObjective extends Objective {

    private static final Logger LOGGER = LoggerFactory.getLogger(MelodicHarmonicObjective.class);

    @Autowired
    private MelodyConfig melodyConfig;

    @Value("${skip.objective.melodicharmonic}")
    private List<Integer> skipVoices = new ArrayList<>();

    @Override
    public double evaluate(Motive motive) {
        List<MelodyBlock> melodies = motive.getMelodyBlocks();
        double total = 0;
        int melodyCount = 0;

        Map<Integer, List<MelodyBlock>> dependingVoices = melodies.stream().filter(melodyBlock -> !skipVoices.contains(melodyBlock.getVoice()))
                .collect(Collectors.groupingBy(melodyBlock -> melodyBlock.getDependingVoice()));

        for (Map.Entry<Integer, List<MelodyBlock>> entry : dependingVoices.entrySet()) {
            List<Note> allNotesVoices = entry.getValue().stream().flatMap(melodyBlock -> melodyBlock.getMelodyBlockNotes().stream()).sorted().collect(toList());
            double melodyDissonance = getMelodyDissonance(allNotesVoices, entry.getKey());
            total = total + melodyDissonance;
            melodyCount++;
        }

//        double averageDissonance = dependingVoices.entrySet().stream()
//                .flatMap(integerListEntry -> integerListEntry.getValue().stream())
//                .mapToDouble(melodyBlock -> getMelodyDissonance(melodyBlock.getMelodyBlockNotes(), melodyBlock.getVoice()))
//                .average().getAsDouble();
//
//        for(MelodyBlock melodyBlock: melodies){
//            if (!skipVoices.contains(melodyBlock.getVoice())){
//                List<Note> allVoicesNotes = melodyBlock.getMelodyBlockNotes();
//                double melodyAverage = getMelodyDissonance(allVoicesNotes, melodyBlock.getVoice());
//                total = total + melodyAverage;
//                melodyCount++;
//            }
//        }
        double avg = total /melodyCount;
//        LOGGER.info("melodic harmonic " + avg);
        return avg;
    }

    protected double getMelodyDissonance(List<Note> notes, int voice) {
        MelodyHarmonicDissonance melodyDissonance = melodyConfig.getMelodyHarmonicDissonanceForVoice(voice);
        if (melodyDissonance == null) {
            return 0.0; // no config
        }
        List<Chord> chords = extractMelodicChords(notes, melodyDissonance.getChordSize(), melodyDissonance.getStartingOverlap());
//        List<Chord> chords = extractConsecutiveMelodicChords(notes, melodyDissonance.getChordSize());
        if (chords.isEmpty()) {
            return 0;
        } else {
            return chords.stream().mapToDouble(chord -> melodyDissonance.getMelodicValue(chord)).average().getAsDouble();
        }
    }

    protected List<Chord> extractMelodicChords(List<Note> notes, int size, int startingOverlap) {
        Stream<List<Note>> slidingWindow = sliding(notes, size, startingOverlap);
        return  slidingWindow.map(windowNotes -> new Chord(windowNotes)).collect(toList());
    }

    protected List<Chord> extractConsecutiveMelodicChords(List<Note> notes, int size) {
        Collection<List<Note>> partition = partition(notes, size);
        return partition.stream().map(Chord::new).collect(toList());
    }

    private <T> Stream<List<T>> sliding(List<T> list, int size, int startingOverlap) {
        if(size > list.size())
            return Stream.empty();
        return IntStream.range(0, list.size()-size+1)
                .filter(value -> value % startingOverlap == 0)
                .mapToObj(start -> list.subList(start, start + size));
    }

    private <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }

}


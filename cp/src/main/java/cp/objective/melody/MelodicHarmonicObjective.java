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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class MelodicHarmonicObjective extends Objective {

    private static final Logger LOGGER = LoggerFactory.getLogger(MelodicHarmonicObjective.class);

    @Autowired
    private MelodyConfig melodyConfig;

    @Override
    public double evaluate(Motive motive) {
        List<MelodyBlock> melodies = motive.getMelodyBlocks();
        double total = 0;
        for(MelodyBlock melody: melodies){
            double melodyAverage = getMelodyDissonance(melody.getMelodyBlockNotes(), melody.getVoice());
            total = total + melodyAverage;
        }
        double avg = total / melodies.size();
//        LOGGER.info("melodic harmonic " + avg);
        return avg;
    }

    protected double getMelodyDissonance(List<Note> notes, int voice) {
        MelodyHarmonicDissonance melodyDissonance = melodyConfig.getMelodyHarmonicDissonanceForVoice(voice);
        List<Chord> chords = new ArrayList<>();
        int chordSize = melodyDissonance.getChordSize();
        for (int i = 2; i < chordSize; i++) {
            List<Chord> triads = extractMelodicChords(notes, i + 1);
            chords.addAll(triads);
        }
        if (chords.isEmpty()) {
            return 0;
        } else {
            return chords.stream().mapToDouble(chord -> melodyDissonance.getMelodicValue(chord)).average().getAsDouble();
        }
    }

    protected List<Chord> extractMelodicChords(List<Note> notes, int size) {
        Stream<List<Note>> slidingWindow = sliding(notes, size);
        return  slidingWindow.map(windowNotes -> new Chord(windowNotes)).collect(Collectors.toList());
    }

    public static <T> Stream<List<T>> sliding(List<T> list, int size) {
        if(size > list.size())
            return Stream.empty();
        return IntStream.range(0, list.size()-size+1)
                .mapToObj(start -> list.subList(start, start+size));
    }

}


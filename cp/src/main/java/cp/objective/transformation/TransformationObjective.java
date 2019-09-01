package cp.objective.transformation;

import cp.model.Motive;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import cp.model.harmony.HarmonyExtractor;
import cp.model.note.Note;
import cp.objective.Objective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class TransformationObjective extends Objective {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationObjective.class);

    @Autowired
    private HarmonyExtractor harmonyExtractor;
    @Autowired
//    @Qualifier(value = "functionalTransformation")
    @Qualifier(value = "neoRiemannTransformation")
//    @Qualifier(value = "voiceLeadingZoneTransformation")
    private TransformationDissonance transformationDissonance;

    @Override
    public double evaluate(Motive motive) {
        List<Note> harmonyNotes = motive.getMelodyBlocks().stream().filter(m -> m.getVoice() == 0 ||  m.getVoice() == 1 )
                .flatMap(m -> m.getMelodyBlockNotes().stream()).collect(toList());
        List<CpHarmony> transformationHarmonies = harmonyExtractor.extractHarmony(harmonyNotes);
//        List<CpHarmony> transformationHarmonies = motive.getHarmonies();
//                .stream()
//                .filter(cpHarmony -> cpHarmony.getNotes().get(0).getLength() > DurationConstants.EIGHT)
//                .collect(Collectors.toList());
        List<Transformation> transformations = new ArrayList<>();
        int size = transformationHarmonies.size() - 1;
        for (int i = 0; i < size; i++) {
            Chord chord = transformationHarmonies.get(i).getChord();
            Chord nextChord = transformationHarmonies.get(i + 1).getChord();
            Transformation transformation = Progression.getTransformation(chord, nextChord);
            transformations.add(transformation);
        }
        motive.setTransformations(transformations);
        OptionalDouble average = transformations.stream().mapToDouble(transformation -> transformationDissonance.getDissonance(transformation)).average();
        if (average.isPresent()) {
            return average.getAsDouble();
        }
        return 0.0;
    }

    public List<CpHarmony> filterHarmonies(List<CpHarmony> harmonies){
        DoubleSummaryStatistics stats = harmonies.stream().collect(Collectors.summarizingDouble(CpHarmony::getHarmonyWeight));
        LOGGER.debug("filtered at: " + stats.getAverage());
        return harmonies.stream().filter(h -> h.getHarmonyWeight() > stats.getAverage()).collect(toList());
    }

    private TreeMap<Integer, List<CpHarmony>> harmoniesForBeat(List<CpHarmony> harmonies, int beat){
        return  harmonies.stream().collect(groupingBy(h -> h.beat(beat), TreeMap::new, Collectors.toList()));
    }
}

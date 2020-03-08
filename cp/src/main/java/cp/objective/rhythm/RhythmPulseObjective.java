package cp.objective.rhythm;

import cp.composition.Composition;
import cp.composition.voice.Voice;
import cp.config.VoiceConfig;
import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.objective.Objective;
import cp.objective.meter.InnerMetricWeight;
import cp.objective.meter.InnerMetricWeightFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RhythmPulseObjective extends Objective {

    private static final Logger LOGGER = LoggerFactory.getLogger(RhythmPulseObjective.class.getName());

    @Autowired
    private Composition composition;

    @Override
    public double evaluate(Motive motive) {
        int pulse =  DurationConstants.EIGHT; //TODO extract to property?
        List<MelodyBlock> melodies = motive.getMelodyBlocks();
        Set<Integer> onsets = melodies.stream()
                .flatMap(melodyBlock -> melodyBlock.getMelodyBlockNotes().stream())
                .map(note -> note.getPosition())
                .collect(Collectors.toSet());
        int maxPulses = composition.getEnd() / pulse;
        return onsets.size() / (double)maxPulses;
    }


}


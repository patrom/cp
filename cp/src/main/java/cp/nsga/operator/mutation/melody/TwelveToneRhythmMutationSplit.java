package cp.nsga.operator.mutation.melody;

import cp.config.TimbreConfig;
import cp.config.TwelveToneConfig;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.timbre.Timbre;
import cp.model.twelve.AggregateBuilder;
import cp.model.twelve.AggregateBuilderFactory;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component(value = "twelveToneRhythmMutationSplit")
public class TwelveToneRhythmMutationSplit implements MutationOperator<Motive> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwelveToneRhythmMutationSplit.class);

    private double probabilityTwelveToneRhythmSplit;

    @Autowired
    private TwelveToneConfig twelveToneConfig;
    @Autowired
    private AggregateBuilderFactory aggregateBuilderFactory;
    @Autowired
    private TimbreConfig timbreConfig;

    @Autowired
    public TwelveToneRhythmMutationSplit(@Value("${probabilityTwelveToneRhythmSplit}") double probabilityTwelveToneRhythmSplit) {
        this.probabilityTwelveToneRhythmSplit = probabilityTwelveToneRhythmSplit;
    }

    public void doMutation(Motive motive) {
        if (PseudoRandom.randDouble() < probabilityTwelveToneRhythmSplit) {
            int voice = RandomUtil.getRandomFromSet(twelveToneConfig.getTwelveToneConfig().keySet());
            CpMelody melody = motive.getRandomMutableMelodyForVoice(voice);
            int start = melody.getStart();
            List<AggregateBuilder> builders = twelveToneConfig.getTwelveToneBuilders(voice, start);
            //create new combinations
            for (AggregateBuilder builder : builders) {
                builder.createGrid();
            }
            AggregateBuilder firstBuilder = builders.get(0);
            List<Note> mergedNotes = builders.stream()
                    .map(AggregateBuilder::getGridNotes)
                    .flatMap(notes -> notes.stream())
                    .sorted()
                    .collect(Collectors.toList());
            //update pcs
            AggregateBuilder tempBuilder = aggregateBuilderFactory.getAggregateBuilder(firstBuilder.getBuilderType(), firstBuilder.getStart(),
                    null, voice , firstBuilder.getPitchClasses(), null);
            tempBuilder.setGridNotes(mergedNotes);
            int[] pitchClasses = firstBuilder.getPitchClasses();
            long size = mergedNotes.stream().filter(note -> !note.isRest()).count();
            if (size >= pitchClasses.length) {
                //repeat notes
                tempBuilder.notesLargerOrEqualThanScale(pitchClasses);
                TreeMap<Integer, List<Note>> notePerVoice = tempBuilder.getGridNotes().stream().collect(Collectors.groupingBy(Note::getVoice, TreeMap::new, Collectors.toList()));
                for (Map.Entry<Integer, List<Note>> mapEntry : notePerVoice.entrySet()) {
                    int voiceNote = mapEntry.getKey();
                    CpMelody melodyToUpdate = motive.getMelody(voiceNote, start);
                    List<Note> melodyNotes = mapEntry.getValue();
                    Timbre timbre = timbreConfig.getTimbreConfigForVoice(voiceNote);
                    melodyNotes.forEach(n -> {
                        n.setDynamic(timbre.getDynamic());
                        n.setDynamicLevel(timbre.getDynamic().getLevel());
                        n.setTechnical(timbre.getTechnical());
                    });
                    melodyToUpdate.updateNotes(melodyNotes);
                }
            } else if (size < pitchClasses.length) {
                //build dependant notes
                List<Note> notesAddedDependencies = tempBuilder.addNoteDependenciesAndPitchClasses(pitchClasses);
                TreeMap<Integer, List<Note>> notePerVoice = notesAddedDependencies.stream().collect(Collectors.groupingBy(Note::getVoice, TreeMap::new, Collectors.toList()));
                for (Map.Entry<Integer, List<Note>> mapEntry : notePerVoice.entrySet()) {
                    int voiceNote = mapEntry.getKey();
                    List<Note> noteDependencies = tempBuilder.createNoteDependencies(mapEntry.getValue());
                    List<Note> restsForVoice = mergedNotes.stream().filter(note -> note.isRest() && note.getVoice() == voiceNote).collect(Collectors.toList());
                    noteDependencies.addAll(restsForVoice);
                    Collections.sort(noteDependencies);
                    Timbre timbre = timbreConfig.getTimbreConfigForVoice(voiceNote);
                    noteDependencies.forEach(n -> {
                        n.setDynamic(timbre.getDynamic());
                        n.setDynamicLevel(timbre.getDynamic().getLevel());
                        n.setTechnical(timbre.getTechnical());
                    });
                    CpMelody melodyToUpdate = motive.getMelody(voiceNote, start);
                    melodyToUpdate.updateNotes(noteDependencies);
                }
            }

          LOGGER.debug("TwelveToneBuilder tone rhythm split mutated: " + voice +", start: " + start);
        }
    }

    @Override
    public Motive execute(Motive motive) {
        doMutation(motive);
        return motive;
    }
}
